/**
 * Copyright (C) 2011 ConnId (connid-dev@googlegroups.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package net.tirasa.connid.bundles.ad.sync;

import static org.junit.Assert.*;

import java.util.AbstractMap;
import java.util.Arrays;
import java.util.Map;
import javax.naming.NamingException;
import javax.naming.directory.BasicAttribute;
import javax.naming.directory.DirContext;
import javax.naming.directory.ModificationItem;
import javax.naming.ldap.LdapContext;
import javax.naming.ldap.LdapName;
import net.tirasa.connid.bundles.ad.ADConfiguration;
import net.tirasa.connid.bundles.ad.ADConnection;
import net.tirasa.connid.bundles.ad.GroupTest;
import org.identityconnectors.framework.common.objects.ConnectorObject;
import org.identityconnectors.framework.common.objects.ObjectClass;
import org.identityconnectors.framework.common.objects.OperationOptionsBuilder;
import org.identityconnectors.framework.common.objects.SyncDelta;
import org.identityconnectors.framework.common.objects.SyncToken;
import org.identityconnectors.framework.common.objects.Uid;
import org.junit.Test;

public class SyncGroupTest extends GroupTest {

    @Test
    public void sync() {
        // We need to have several operation in the right sequence in order to verify synchronization ...

        // ----------------------------------
        // Handler specification
        // ----------------------------------
        final TestSyncResultsHandler handler = new TestSyncResultsHandler();
        // ----------------------------------

        // Ask just for sAMAccountName
        final OperationOptionsBuilder oob = new OperationOptionsBuilder();
        oob.setAttributesToGet(Arrays.asList(new String[] { "sAMAccountName", "member" }));

        SyncToken token = connector.getLatestSyncToken(ObjectClass.GROUP);

        // ----------------------------------
        // check sync without modification and deleted groups (token: null)
        // ----------------------------------
        connector.sync(ObjectClass.GROUP, token, handler, oob.build());

        assertTrue(handler.getDeleted().isEmpty());
        assertTrue(handler.getUpdated().isEmpty());

        handler.clear();

        final Map.Entry<String, String> ids11 = util.getEntryIDs("11");

        Uid uid11 = null;
        Uid groupTestFor11 = null;

        try {
            // ----------------------------------
            // check sync with new group (token updated)
            // ----------------------------------
            // group added sync
            uid11 = connector.create(ObjectClass.GROUP, util.getSimpleProfile(ids11), null);

            connector.sync(ObjectClass.GROUP, token, handler, oob.build());
            token = handler.getLatestReceivedToken();

            // group related to uid11 memberOf (it doesn't match the filter) 
            assertEquals(0, handler.getDeleted().size());

            // group and memberships (update from member update of GroupTestInFilter and the group itself) creation
            assertFalse(handler.getUpdated().isEmpty());

            ConnectorObject obj = null;

            for (SyncDelta updatedObj : handler.getUpdated()) {
                if (ids11.getValue().equals(updatedObj.getUid().getUidValue())) {
                    obj = updatedObj.getObject();
                }
            }

            assertNotNull(obj);

            // chek for returned attributes
            assertEquals(4, obj.getAttributes().size());
            assertNotNull(obj.getAttributeByName("sAMAccountName"));
            assertNotNull(obj.getAttributeByName("__NAME__"));
            assertNotNull(obj.getAttributeByName("__UID__"));
            assertNotNull(obj.getAttributeByName("member"));
            assertEquals(ids11.getValue(), obj.getUid().getUidValue());

            handler.clear();

            // check with updated token and without any modification
            connector.sync(ObjectClass.GROUP, token, handler, oob.build());
            token = handler.getLatestReceivedToken();

            assertTrue(handler.getDeleted().isEmpty());
            assertTrue(handler.getUpdated().isEmpty());
            // ----------------------------------

            // ----------------------------------
            // check sync with group 'IN' group (token updated)
            // ----------------------------------
            // created a new user without memberships specification
            final ADConfiguration configuration = getSimpleConf(PROP);

            final ADConnection connection = new ADConnection(configuration);
            final LdapContext ctx = connection.getInitialContext();

            try {
                createGrp(ctx, "GroupTestFor11", "CN=Users," + configuration.getUserBaseContexts()[0]);
                groupTestFor11 = new Uid("GroupTestFor11");
            } catch (NamingException e) {
                LOG.error(e, "Error creating user GroupTestFor11");
                fail();
            }

            handler.clear();

            connector.sync(ObjectClass.GROUP, token, handler, oob.build());
            token = handler.getLatestReceivedToken();

            assertFalse(handler.getDeleted().isEmpty());
            assertTrue(handler.getUpdated().isEmpty());

            ModificationItem[] mod = new ModificationItem[] {
                new ModificationItem(
                DirContext.ADD_ATTRIBUTE,
                new BasicAttribute("member", "CN=GroupTestFor11,CN=Users," + configuration.getUserBaseContexts()[0]))
            };

            try {
                ctx.modifyAttributes(util.getEntryDN(util.getEntryIDs("InFilter").getKey(), ObjectClass.GROUP), mod);
            } catch (NamingException e) {
                LOG.error(e, "Error adding membership for newMemberFor11");
                fail();
            }

            handler.clear();

            connector.sync(ObjectClass.GROUP, token, handler, oob.build());
            token = handler.getLatestReceivedToken();

            // group related to uid11 memberOf (it doesn't match the filter) 
            assertEquals(0, handler.getDeleted().size());

            // group in
            assertEquals(2, handler.getUpdated().size());
            // ----------------------------------

            // ----------------------------------
            // check sync with user 'OUT' group (token updated)
            // ----------------------------------
            mod = new ModificationItem[] {
                new ModificationItem(
                DirContext.REMOVE_ATTRIBUTE,
                new BasicAttribute("member", "CN=GroupTestFor11,CN=Users," + configuration.getUserBaseContexts()[0]))
            };

            try {
                ctx.modifyAttributes(util.getEntryDN(util.getEntryIDs("InFilter").getKey(), ObjectClass.GROUP), mod);
            } catch (NamingException e) {
                LOG.error(e, "Error adding membership for GroupTestFor11");
                fail();
            }

            handler.clear();

            connector.sync(ObjectClass.GROUP, token, handler, oob.build());
            token = handler.getLatestReceivedToken();

            assertEquals(1, handler.getDeleted().size());
            assertEquals(1, handler.getUpdated().size());

            // restore to be ready for deletion
            mod = new ModificationItem[] {
                new ModificationItem(
                DirContext.ADD_ATTRIBUTE,
                new BasicAttribute("member", "CN=GroupTestFor11,CN=Users," + configuration.getUserBaseContexts()[0]))
            };

            try {
                ctx.modifyAttributes(util.getEntryDN(util.getEntryIDs("InFilter").getKey(), ObjectClass.GROUP), mod);
            } catch (NamingException e) {
                LOG.error(e, "Error adding membership for newMemberFor11");
                assert (false);
            }
            // ----------------------------------

            // ----------------------------------
            // Check for group which doesn't match the specified group search filter
            // ----------------------------------
            handler.clear();

            Map.Entry<String, String> ids = new AbstractMap.SimpleEntry<String, String>("grptmp", "grptmp");
            final Uid uid = connector.create(ObjectClass.GROUP, util.getSimpleProfile(ids), null);
            assertNotNull(uid);

            connector.sync(ObjectClass.GROUP, token, handler, oob.build());
            token = handler.getLatestReceivedToken();

            assertFalse(handler.getDeleted().isEmpty());
            assertEquals(2, handler.getUpdated().size());

            ctx.destroySubcontext(new LdapName(util.getEntryDN(ids.getKey(), ObjectClass.GROUP)));

            connector.sync(ObjectClass.GROUP, token, handler, oob.build());
            token = handler.getLatestReceivedToken();

            // always returned
            assertFalse(handler.getDeleted().isEmpty());
            assertEquals(2, handler.getUpdated().size());

            // ----------------------------------
        } catch (Throwable t) {
            LOG.error(t, "Unexpected exception");
            fail();
        } finally {
            assertNotNull(uid11);
            connector.delete(ObjectClass.GROUP, uid11, null);

            assertNotNull(groupTestFor11);
            connector.delete(ObjectClass.GROUP, groupTestFor11, null);

            handler.clear();

            connector.sync(ObjectClass.GROUP, token, handler, oob.build());

            assertEquals(2, handler.getDeleted().size());
            assertTrue(handler.getUpdated().isEmpty());
        }
    }
}
