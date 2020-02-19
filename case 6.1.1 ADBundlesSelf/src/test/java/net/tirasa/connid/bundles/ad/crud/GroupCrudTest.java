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
package net.tirasa.connid.bundles.ad.crud;

import static net.tirasa.connid.bundles.ad.ADConnector.OBJECTSID;
import static net.tirasa.connid.bundles.ad.ADConnector.PRIMARYGROUPID;
import static org.junit.Assert.*;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.naming.ldap.LdapContext;
import javax.naming.ldap.LdapName;
import net.tirasa.adsddl.ntsd.SID;
import net.tirasa.adsddl.ntsd.utils.NumberFacility;
import net.tirasa.connid.bundles.ad.ADConfiguration;
import net.tirasa.connid.bundles.ad.ADConnection;
import net.tirasa.connid.bundles.ad.ADConnector;
import net.tirasa.connid.bundles.ad.GroupTest;
import net.tirasa.connid.bundles.ad.util.ADUtilities;
import org.identityconnectors.framework.api.APIConfiguration;
import org.identityconnectors.framework.api.ConnectorFacade;
import org.identityconnectors.framework.api.ConnectorFacadeFactory;
import org.identityconnectors.framework.common.objects.Attribute;
import org.identityconnectors.framework.common.objects.AttributeBuilder;
import org.identityconnectors.framework.common.objects.AttributeUtil;
import org.identityconnectors.framework.common.objects.ConnectorObject;
import org.identityconnectors.framework.common.objects.Name;
import org.identityconnectors.framework.common.objects.ObjectClass;
import org.identityconnectors.framework.common.objects.OperationOptionsBuilder;
import org.identityconnectors.framework.common.objects.ResultsHandler;
import org.identityconnectors.framework.common.objects.Uid;
import org.identityconnectors.framework.common.objects.filter.Filter;
import org.identityconnectors.framework.common.objects.filter.FilterBuilder;
import org.identityconnectors.test.common.TestHelpers;
import org.junit.Test;

public class GroupCrudTest extends GroupTest {

    @Test
    public void search() {

        final Map.Entry<String, String> ids = util.getEntryIDs("1");

        // create filter
        final Filter filter = FilterBuilder.equalTo(AttributeBuilder.build("sAMAccountName", ids.getValue()));

        // create results handler
        final List<Attribute> results = new ArrayList<Attribute>();
        final ResultsHandler handler = new ResultsHandler() {

            @Override
            public boolean handle(ConnectorObject co) {
                return results.add(co.getAttributeByName("sAMAccountName"));
            }
        };

        // create options for returning attributes
        final OperationOptionsBuilder oob = new OperationOptionsBuilder();
        oob.setAttributesToGet(Collections.singleton("sAMAccountName"));

        connector.search(ObjectClass.GROUP, filter, handler, oob.build());

        assertEquals(1, results.size());
        assertEquals(Collections.singletonList(ids.getValue()), results.get(0).getValue());
    }

    @Test
    public void searchByDefaultCustomFilter() {

        // create results handler
        final List<Attribute> results = new ArrayList<Attribute>();
        final ResultsHandler handler = new ResultsHandler() {

            @Override
            public boolean handle(ConnectorObject co) {
                return results.add(co.getAttributeByName("sAMAccountName"));
            }
        };

        // create options for returning attributes
        final OperationOptionsBuilder oob = new OperationOptionsBuilder();
        oob.setAttributesToGet(Collections.singleton("sAMAccountName"));

        connector.search(ObjectClass.GROUP, null, handler, oob.build());

        assertEquals(11, results.size());

        final Map.Entry<String, String> ids = new AbstractMap.SimpleEntry<String, String>("grptmp", "grptmp");
        final Uid uid = connector.create(ObjectClass.GROUP, util.getSimpleProfile(ids), null);
        assertNotNull(uid);

        results.clear();
        connector.search(ObjectClass.GROUP, null, handler, oob.build());

        assertEquals(11, results.size());

        try {
            final ADConfiguration configuration = getSimpleConf(PROP);
            final ADConnection connection = new ADConnection(configuration);
            final LdapContext ctx = connection.getInitialContext();
            ctx.destroySubcontext(new LdapName(util.getEntryDN(ids.getKey(), ObjectClass.GROUP)));
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void read() {
        final Map.Entry<String, String> ids = util.getEntryIDs("2");

        // Ask just for sAMAccountName
        final OperationOptionsBuilder oob = new OperationOptionsBuilder();
        oob.setAttributesToGet(Collections.singleton("sAMAccountName"));

        final ConnectorObject object = connector.getObject(ObjectClass.GROUP, new Uid(ids.getValue()), oob.build());

        assertNotNull(object);
        assertNotNull(object.getAttributes());

        // Returned attributes: sAMAccountName, NAME and UID
        assertEquals(3, object.getAttributes().size());
        assertNotNull(object.getAttributeByName("sAMAccountName"));
        assertEquals(Collections.singletonList(ids.getValue()), object.getAttributeByName("sAMAccountName").getValue());
    }

    @Test
    public void create() {
        assertNotNull(connector);
        assertNotNull(conf);

        final Map.Entry<String, String> ids = util.getEntryIDs("11");

        assertNull("Please remove group 'sAMAccountName: " + ids.getValue() + "' from AD",
                connector.getObject(ObjectClass.GROUP, new Uid(ids.getValue()), null));

        final Set<Attribute> attributes = util.getSimpleProfile(ids);

        final Uid uid = connector.create(ObjectClass.GROUP, attributes, null);
        assertNotNull(uid);
        assertEquals(ids.getValue(), uid.getUidValue());

        // Ask just for memberOf
        final OperationOptionsBuilder oob = new OperationOptionsBuilder();
        oob.setAttributesToGet(Collections.singleton("member"));

        // retrieve created object
        final ConnectorObject object = connector.getObject(ObjectClass.GROUP, uid, oob.build());

        // check for memberOf attribute
        assertNotNull(object);
        assertNotNull(object.getAttributes());

        // Returned attributes: NAME and UID
        assertEquals(3, object.getAttributes().size());

        assertEquals(ids.getValue(), object.getUid().getUidValue());

        assertEquals(
                util.getEntryDN(ids.getKey(), ObjectClass.GROUP).toLowerCase(),
                object.getName().getNameValue().toLowerCase());

        connector.delete(ObjectClass.GROUP, uid, null);
        assertNull(connector.getObject(ObjectClass.GROUP, uid, null));
    }

    @Test
    public void createWithoutDN() {
        assertNotNull(connector);
        assertNotNull(conf);

        final Map.Entry<String, String> ids = util.getEntryIDs("nodn11");

        assertNull("Please remove group 'sAMAccountName: " + ids.getValue() + "' from AD",
                connector.getObject(ObjectClass.GROUP, new Uid(ids.getValue()), null));

        final Set<Attribute> attributes = util.getSimpleProfile(ids, false);

        final Uid uid = connector.create(ObjectClass.GROUP, attributes, null);
        assertNotNull(uid);
        assertEquals(ids.getValue(), uid.getUidValue());

        // Ask just for memberOf
        final OperationOptionsBuilder oob = new OperationOptionsBuilder();
        oob.setAttributesToGet(Collections.singleton("member"));

        // retrieve created object
        final ConnectorObject object = connector.getObject(ObjectClass.GROUP, uid, oob.build());

        // check for memberOf attribute
        assertNotNull(object);
        assertNotNull(object.getAttributes());

        // Returned attributes: memberOf, NAME and UID
        assertEquals(3, object.getAttributes().size());
        assertFalse(object.getAttributeByName("member").getValue().isEmpty());

        assertEquals(ids.getValue(), object.getUid().getUidValue());

        assertEquals(
                util.getEntryDN(ids.getKey(), ObjectClass.GROUP).toLowerCase(),
                object.getName().getNameValue().toLowerCase());

        connector.delete(ObjectClass.GROUP, uid, null);
        assertNull(connector.getObject(ObjectClass.GROUP, uid, null));
    }

    @Test
    public void checkLdapGroups() {
        assertNotNull(connector);
        assertNotNull(conf);

        String baseContext = PROP.getProperty("baseContext");

        final Map.Entry<String, String> ids = util.getEntryIDs("20");

        assertNull("Please remove user 'sAMAccountName: " + ids.getValue() + "' from AD",
                connector.getObject(ObjectClass.GROUP, new Uid(ids.getValue()), null));

        final Set<Attribute> attributes = util.getSimpleProfile(ids, false);

        final Attribute ldapGroups = AttributeUtil.find("ldapGroups", attributes);
        attributes.remove(ldapGroups);

        final List<String> groupsToBeAdded = new ArrayList<String>();

        if (ldapGroups != null && ldapGroups.getValue() != null) {
            for (Object obj : ldapGroups.getValue()) {
                groupsToBeAdded.add(obj.toString());
            }
        }

        groupsToBeAdded.add("CN=Cert Publishers,CN=Users," + baseContext);
        groupsToBeAdded.add("CN=Schema Admins,CN=Users," + baseContext);

        attributes.add(AttributeBuilder.build("ldapGroups", groupsToBeAdded));

        Uid uid = connector.create(ObjectClass.GROUP, attributes, null);
        assertNotNull(uid);
        assertEquals(ids.getValue(), uid.getUidValue());

        // Ask just for memberOf
        final OperationOptionsBuilder oob = new OperationOptionsBuilder();
        oob.setAttributesToGet("memberOf", "ldapGroups");

        // retrieve created object
        ConnectorObject object = connector.getObject(ObjectClass.GROUP, uid, oob.build());

        // check for memberOf attribute
        assertNotNull(object);
        assertNotNull(object.getAttributes());

        // Returned attributes: memberOf, NAME and UID
        assertEquals(4, object.getAttributes().size());
        assertNotNull(object.getAttributeByName("memberOf"));
        assertTrue(object.getAttributeByName("ldapGroups").getValue().contains(
                "CN=Cert Publishers,CN=Users," + baseContext));
        assertTrue(object.getAttributeByName("ldapGroups").getValue().contains(
                "CN=Schema Admins,CN=Users," + baseContext));

        List<Attribute> attrToReplace = Arrays.asList(new Attribute[] { AttributeBuilder.build("ldapGroups",
            "CN=Schema Admins,CN=Users," + baseContext,
            "CN=GroupTestInFilter,CN=Users," + baseContext) });

        uid = connector.update(
                ObjectClass.GROUP,
                uid,
                new HashSet<Attribute>(attrToReplace),
                null);

        assertNotNull(uid);
        assertEquals(ids.getValue(), uid.getUidValue());

        object = connector.getObject(ObjectClass.GROUP, uid, oob.build());

        assertFalse(object.getAttributeByName("ldapGroups").getValue().contains(
                "CN=Cert Publishers,CN=Users," + baseContext));
        assertTrue(object.getAttributeByName("ldapGroups").getValue().contains(
                "CN=Schema Admins,CN=Users," + baseContext));

        attrToReplace = Arrays.asList(new Attribute[] { AttributeBuilder.build("ldapGroups",
            "CN=Schema Admins,CN=Users," + baseContext,
            "CN=Cert Publishers,CN=Users," + baseContext,
            "CN=GroupTestInFilter,CN=Users," + baseContext) });

        uid = connector.update(
                ObjectClass.GROUP,
                uid,
                new HashSet<Attribute>(attrToReplace),
                null);

        assertNotNull(uid);
        assertEquals(ids.getValue(), uid.getUidValue());

        object = connector.getObject(ObjectClass.GROUP, uid, oob.build());

        assertTrue(object.getAttributeByName("ldapGroups").getValue().contains(
                "CN=Cert Publishers,CN=Users," + baseContext));
        assertTrue(object.getAttributeByName("ldapGroups").getValue().contains(
                "CN=Schema Admins,CN=Users," + baseContext));

        connector.delete(ObjectClass.GROUP, uid, null);
        assertNull(connector.getObject(ObjectClass.GROUP, uid, null));
    }

    @Test
    public void update() {
        assertNotNull(connector);
        assertNotNull(conf);

        final Map.Entry<String, String> ids = util.getEntryIDs("3");

        // Ask just for sAMAccountName
        final OperationOptionsBuilder oob = new OperationOptionsBuilder();
        oob.setAttributesToGet(Collections.singleton("description"));

        ConnectorObject object = connector.getObject(ObjectClass.GROUP, new Uid(ids.getValue()), oob.build());
        assertNotNull(object);

        final List<Attribute> attrToReplace = Arrays.asList(new Attribute[] {
            AttributeBuilder.build("description", "a new description") });

        Uid uid = connector.update(
                ObjectClass.GROUP,
                new Uid(ids.getValue()),
                new HashSet<Attribute>(attrToReplace),
                null);

        assertNotNull(uid);
        assertEquals(ids.getValue(), uid.getUidValue());

        object = connector.getObject(ObjectClass.GROUP, uid, oob.build());

        assertNotNull(object);
        assertNotNull(object.getAttributes());
        // Returned attributes: description, NAME and UID
        assertEquals(3, object.getAttributes().size());
        assertNotNull(object.getAttributeByName("description"));
        assertEquals(
                Collections.singletonList("a new description"), object.getAttributeByName("description").getValue());
    }

    @Test
    public void rename() {
        assertNotNull(connector);
        assertNotNull(conf);

        final Map.Entry<String, String> ids = util.getEntryIDs("5");

        final String DN = util.getEntryDN(ids.getKey() + "-new", ObjectClass.GROUP);

        final List<Attribute> attrToReplace = Arrays.asList(new Attribute[] { AttributeBuilder.build(Name.NAME, DN) });

        Uid uid = connector.update(
                ObjectClass.GROUP, new Uid(ids.getValue()), new HashSet<Attribute>(attrToReplace), null);

        assertNotNull(uid);
        assertEquals(ids.getValue(), uid.getUidValue());

        // Ask just for sAMAccountName
        final OperationOptionsBuilder oob = new OperationOptionsBuilder();
        oob.setAttributesToGet(Collections.singleton("member"));

        final ConnectorObject object = connector.getObject(ObjectClass.GROUP, uid, oob.build());

        // Returned attributes: NAME and UID
        assertEquals(3, object.getAttributes().size());
        assertFalse(object.getAttributeByName("member").getValue().isEmpty());
        assertTrue(DN.equalsIgnoreCase(object.getName().getNameValue()));
    }

    @Test
    public void noRenameWithTheSameCN() {
        assertNotNull(connector);
        assertNotNull(conf);

        final Map.Entry<String, String> ids = util.getEntryIDs("6");

        final List<Attribute> attrToReplace = Arrays.asList(new Attribute[] {
            AttributeBuilder.build(Name.NAME, ids.getKey()) });

        Uid uid = connector.update(
                ObjectClass.GROUP, new Uid(ids.getValue()), new HashSet<Attribute>(attrToReplace), null);

        assertNotNull(uid);
        assertEquals(ids.getValue(), uid.getUidValue());

        // Ask just for sAMAccountName
        final OperationOptionsBuilder oob = new OperationOptionsBuilder();
        oob.setAttributesToGet(Collections.singleton("member"));

        final ConnectorObject object = connector.getObject(ObjectClass.GROUP, uid, oob.build());

        // Returned attributes: memberOf, NAME and UID
        assertEquals(3, object.getAttributes().size());
        assertFalse(object.getAttributeByName("member").getValue().isEmpty());

        assertTrue(util.getEntryDN(ids.getKey(), ObjectClass.GROUP).equalsIgnoreCase(object.getName().getNameValue()));
    }

    @Test
    public void noRenameWithTheSameDN() {
        assertNotNull(connector);
        assertNotNull(conf);

        final Map.Entry<String, String> ids = util.getEntryIDs("6");

        final List<Attribute> attrToReplace = Arrays.asList(new Attribute[] {
            AttributeBuilder.build(Name.NAME, util.getEntryDN(ids.getKey(), ObjectClass.GROUP)) });

        Uid uid = connector.update(
                ObjectClass.GROUP, new Uid(ids.getValue()), new HashSet<Attribute>(attrToReplace), null);

        assertNotNull(uid);
        assertEquals(ids.getValue(), uid.getUidValue());

        // Ask just for sAMAccountName
        final OperationOptionsBuilder oob = new OperationOptionsBuilder();
        oob.setAttributesToGet(Collections.singleton("member"));

        final ConnectorObject object = connector.getObject(ObjectClass.GROUP, uid, oob.build());

        // Returned attributes: NAME and UID
        assertEquals(3, object.getAttributes().size());
        assertFalse(object.getAttributeByName("member").getValue().isEmpty());

        assertTrue(util.getEntryDN(ids.getKey(), ObjectClass.GROUP).equalsIgnoreCase(object.getName().getNameValue()));
    }

    @Test
    public void renameWithoutDN() {
        assertNotNull(connector);
        assertNotNull(conf);

        final Map.Entry<String, String> ids = util.getEntryIDs("5");

        final List<Attribute> attrToReplace = Arrays.asList(new Attribute[] { AttributeBuilder.build("cn", ids.getKey()
            + "_new") });

        Uid uid = connector.update(
                ObjectClass.GROUP, new Uid(ids.getValue()), new HashSet<Attribute>(attrToReplace), null);

        assertNotNull(uid);
        assertEquals(ids.getValue(), uid.getUidValue());

        // Ask just for sAMAccountName
        final OperationOptionsBuilder oob = new OperationOptionsBuilder();
        oob.setAttributesToGet("cn");

        final ConnectorObject object = connector.getObject(ObjectClass.GROUP, uid, oob.build());

        // Returned attributes: cn, NAME and UID
        assertEquals(3, object.getAttributes().size());
        assertNotNull(object.getAttributeByName("cn"));
        assertEquals(ids.getKey() + "_new", object.getAttributeByName("cn").getValue().get(0));
    }

    @Test
    public void issueAD29() {
        assertNotNull(connector);
        assertNotNull(conf);

        final List<ConnectorObject> results = new ArrayList<ConnectorObject>();
        final ResultsHandler handler = new ResultsHandler() {

            @Override
            public boolean handle(final ConnectorObject co) {
                return results.add(co);
            }
        };

        final String baseContext = PROP.getProperty("baseContext");

        // create options for returning attributes
        final OperationOptionsBuilder oob = new OperationOptionsBuilder();
        oob.setAttributesToGet("sAMAccountName", "objectSID", "primaryGroupID", "memberOf");

        Uid uuid = null;
        Uid guid = null;

        try {
            // -------------------------------------------------
            // Create a new Group
            // -------------------------------------------------
            final Map.Entry<String, String> gid = util.getEntryIDs("GAD29", ObjectClass.GROUP);

            assertNull("Please remove group 'sAMAccountName: " + gid.getValue() + "' from AD",
                    connector.getObject(ObjectClass.GROUP, new Uid(gid.getValue()), null));

            Set<Attribute> attributes = util.getSimpleGroupProfile(gid, true);

            guid = connector.create(ObjectClass.GROUP, attributes, null);
            assertNotNull(guid);
            assertEquals(gid.getValue(), guid.getUidValue());

            final ConnectorObject group = connector.getObject(ObjectClass.GROUP, guid, oob.build());
            final SID gsid = SID.parse((byte[]) group.getAttributeByName("objectSID").getValue().get(0));
            assertNotNull(gsid);

            final byte[] groupID = gsid.getSubAuthorities().get(gsid.getSubAuthorityCount() - 1);
            // -------------------------------------------------

            results.clear();

            // -------------------------------------------------
            // Create a new Group member
            // -------------------------------------------------
            final Map.Entry<String, String> ids = util.getEntryIDs("UAD29", ObjectClass.ACCOUNT);

            assertNull("Please remove user 'sAMAccountName: " + ids.getValue() + "' from AD",
                    connector.getObject(ObjectClass.ACCOUNT, new Uid(ids.getValue()), null));

            attributes = util.getSimpleUserProfile(ids, conf, false);

            // add group
            Attribute ldapGroups = AttributeUtil.find("ldapGroups", attributes);
            attributes.remove(ldapGroups);

            final List<String> groupsToBeAdded = new ArrayList<String>();

            if (ldapGroups != null && ldapGroups.getValue() != null) {
                for (Object obj : ldapGroups.getValue()) {
                    groupsToBeAdded.add(obj.toString());
                }
            }

            groupsToBeAdded.add(String.format("CN=%s,CN=Users,%s", gid.getKey(), baseContext));
            attributes.add(AttributeBuilder.build("ldapGroups", groupsToBeAdded));

            uuid = connector.create(ObjectClass.ACCOUNT, attributes, null);
            assertNotNull(uuid);
            assertEquals(ids.getValue(), uuid.getUidValue());

            ConnectorObject user = connector.getObject(ObjectClass.ACCOUNT, uuid, oob.build());
            SID usid = SID.parse((byte[]) user.getAttributeByName("objectSID").getValue().get(0));
            assertNotNull(usid);

            String primaryGID = String.class.cast(user.getAttributeByName("primaryGroupID").getValue().get(0));
            assertNotNull(primaryGID);
            // -------------------------------------------------

            // -------------------------------------------------
            // Update PrimaryGroupID
            // -------------------------------------------------
            List<Attribute> attrToReplace = Arrays.asList(new Attribute[] {
                AttributeBuilder.build(PRIMARYGROUPID, String.valueOf(NumberFacility.getUInt(groupID))) });

            uuid = connector.update(
                    ObjectClass.ACCOUNT,
                    new Uid(ids.getValue()),
                    new HashSet<Attribute>(attrToReplace),
                    null);

            user = connector.getObject(ObjectClass.ACCOUNT, uuid, oob.build());
            usid = SID.parse((byte[]) user.getAttributeByName(OBJECTSID).getValue().get(0));
            assertNotNull(usid);

            primaryGID = String.class.cast(user.getAttributeByName(PRIMARYGROUPID).getValue().get(0));
            assertNotNull(primaryGID);

            final SID groupSID = ADUtilities.getPrimaryGroupSID(usid,
                    NumberFacility.getUIntBytes(Integer.parseInt(primaryGID)));

            connector.search(ObjectClass.GROUP,
                    FilterBuilder.equalTo(AttributeBuilder.build(OBJECTSID, groupSID.toByteArray())),
                    handler,
                    oob.build());
            assertEquals(group.getName(), results.get(0).getName());
            // -------------------------------------------------

            // -------------------------------------------------
            // Check for primary groups into ldapGroups
            // -------------------------------------------------
            user = connector.getObject(ObjectClass.ACCOUNT, uuid, oob.build());
            ldapGroups = user.getAttributeByName("memberOf");

            boolean found = false;
            for (Object ldapGroup : ldapGroups.getValue()) {
                if (String.class.cast(ldapGroup).equals(group.getName().getNameValue())) {
                    found = true;
                }
            }
            assertTrue(found);
            // -------------------------------------------------

            // -------------------------------------------------
            // Update primary group
            // -------------------------------------------------
            attrToReplace = Arrays.asList(new Attribute[] {
                AttributeBuilder.build("description", "a new description") });

            connector.update(ObjectClass.GROUP, guid, new HashSet<Attribute>(attrToReplace), null);
            // -------------------------------------------------

            // -------------------------------------------------
            // Add group to user
            // -------------------------------------------------
            attrToReplace = Arrays.asList(new Attribute[] {
                AttributeBuilder.build("ldapGroups", Collections.singleton(group.getName().getNameValue())) });

            connector.update(ObjectClass.ACCOUNT, uuid, new HashSet<Attribute>(attrToReplace), null);
            // -------------------------------------------------

        } finally {
            if (uuid != null) {
                connector.delete(ObjectClass.ACCOUNT, uuid, null);
            }

            if (guid != null) {
                connector.delete(ObjectClass.GROUP, guid, null);
            }
        }
    }

    @Test
    public void issueAD56() {
        assertNotNull(connector);
        assertNotNull(conf);

        Uid guid = null;
        try {
            // create options for returning attributes
            final OperationOptionsBuilder oob = new OperationOptionsBuilder();
            oob.setAttributesToGet("cn", "sAMAccountName", "objectSID", "primaryGroupID", "memberOf");

            final Map.Entry<String, String> gid = util.getEntryIDs("GAD56", ObjectClass.GROUP);

            assertNull("Please remove group 'sAMAccountName: " + gid.getValue() + "' from AD",
                    connector.getObject(ObjectClass.GROUP, new Uid(gid.getValue()), null));

            Set<Attribute> attributes = util.getSimpleGroupProfile(gid, true);

            guid = connector.create(ObjectClass.GROUP, attributes, null);
            assertNotNull(guid);
            assertEquals(gid.getValue(), guid.getUidValue());

            ConnectorObject group = connector.getObject(ObjectClass.GROUP, guid, oob.build());
            assertNotNull(group);
            assertEquals(gid.getValue(), group.getAttributeByName("sAMAccountName").getValue().iterator().next());
            assertNotEquals(gid.getValue(), group.getAttributeByName("cn").getValue().iterator().next());

            ADConfiguration newConf = getSimpleConf(PROP);
            newConf.setGidAttribute("cn");

            assertNotNull(conf);
            conf.validate();

            ConnectorFacadeFactory factory = ConnectorFacadeFactory.getInstance();

            APIConfiguration impl = TestHelpers.createTestConfiguration(ADConnector.class, newConf);
            impl.getResultsHandlerConfiguration().setFilteredResultsHandlerInValidationMode(true);

            ConnectorFacade newConnector = factory.newInstance(impl);
            assertNotNull(newConnector);

            group = newConnector.getObject(ObjectClass.GROUP, guid, oob.build());
            assertNull(group);

            newConf = getSimpleConf(PROP);
            newConf.setGidAttribute("sAMAccountName");

            assertNotNull(conf);
            conf.validate();

            factory = ConnectorFacadeFactory.getInstance();

            impl = TestHelpers.createTestConfiguration(ADConnector.class, newConf);
            impl.getResultsHandlerConfiguration().setFilteredResultsHandlerInValidationMode(true);

            newConnector = factory.newInstance(impl);
            assertNotNull(newConnector);

            group = newConnector.getObject(ObjectClass.GROUP, guid, oob.build());
            assertNotNull(group);
        } finally {
            if (guid != null) {
                connector.delete(ObjectClass.GROUP, guid, null);
            }
        }
    }
}
