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
package net.tirasa.connid.bundles.ad;

import static org.junit.Assert.*;

import java.util.AbstractMap;
import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import org.identityconnectors.common.logging.Log;
import org.identityconnectors.framework.api.ConnectorFacade;
import org.identityconnectors.framework.common.objects.Attribute;
import org.identityconnectors.framework.common.objects.AttributeBuilder;
import org.identityconnectors.framework.common.objects.Name;
import org.identityconnectors.framework.common.objects.ObjectClass;
import org.identityconnectors.framework.common.objects.Uid;

public class TestUtil {

    /**
     * Setup logging for the {@link ADConnector}.
     */
    protected static final Log LOG = Log.getLog(TestUtil.class);

    private final ObjectClass oclass;

    private final ConnectorFacade connector;

    private final ADConfiguration conf;

    public TestUtil(
            final ConnectorFacade connector,
            final ADConfiguration conf,
            final ObjectClass oclass,
            final String basecontext) {
        this.oclass = oclass;
        this.connector = connector;
        this.conf = conf;
    }

    /**
     * Create a set of test etries.
     *
     * @param num number of entries to be created.
     */
    public void createEntry(final int num) {
        // check entries existence
        for (int i = 1; i <= num; i++) {
            final Map.Entry<String, String> ids = getEntryIDs(String.valueOf(i));

            assertNull("Please remove etry 'sAMAccountName: " + ids.getValue() + "'",
                    connector.getObject(oclass, new Uid(ids.getValue()), null));
        }

        Set<Attribute> attributes;

        // add new users
        for (int i = 1; i <= num; i++) {
            final Map.Entry<String, String> ids = getEntryIDs(String.valueOf(i));

            attributes = getSimpleProfile(ids);

            final Uid uid = connector.create(oclass, attributes, null);

            assertNotNull(uid);
            assertEquals(ids.getValue(), uid.getUidValue());
        }

    }

    public String getEntryDN(final String cn, final ObjectClass oclass) {
        return String.format("cn=%s,%s", cn, oclass.equals(ObjectClass.ACCOUNT)
                ? conf.getDefaultPeopleContainer() : conf.getDefaultGroupContainer());
    }

    public Set<Attribute> getSimpleProfile(final Map.Entry<String, String> ids, final boolean withDN) {
        if (oclass.is(ObjectClass.ACCOUNT_NAME)) {
            return getSimpleUserProfile(ids, conf, withDN);
        } else {
            return getSimpleGroupProfile(ids, withDN);
        }
    }

    public Set<Attribute> getSimpleProfile(final Map.Entry<String, String> ids) {
        if (oclass.is(ObjectClass.ACCOUNT_NAME)) {
            return getSimpleUserProfile(ids, conf, true);
        } else {
            return getSimpleGroupProfile(ids, true);
        }
    }

    public Set<Attribute> getSimpleUserProfile(
            final Map.Entry<String, String> ids, final ADConfiguration conf, final boolean withDN) {

        final Set<Attribute> attributes = new HashSet<Attribute>();

        if (withDN) {
            attributes.add(new Name(getEntryDN(ids.getKey(), ObjectClass.ACCOUNT)));
        } else {
            attributes.add(new Name(ids.getValue()));
            attributes.add(AttributeBuilder.build("cn", Collections.singletonList(ids.getKey())));
        }

        attributes.add(AttributeBuilder.build(conf.getUidAttribute(), Collections.singletonList(ids.getValue())));

        attributes.add(AttributeBuilder.buildEnabled(true));

        if (conf.isSsl()) {
            attributes.add(AttributeBuilder.buildPassword("Password123".toCharArray()));
        }

        attributes.add(AttributeBuilder.build("sn", Collections.singletonList("sntest")));

        attributes.add(AttributeBuilder.build("givenName", Collections.singletonList("gntest")));

        attributes.add(AttributeBuilder.build("displayName", Collections.singletonList("dntest")));

        return attributes;
    }

    public Set<Attribute> getSimpleGroupProfile(final Map.Entry<String, String> ids, final boolean withDN) {

        final Set<Attribute> attributes = new HashSet<Attribute>();

        if (withDN) {
            attributes.add(new Name(getEntryDN(ids.getKey(), ObjectClass.GROUP)));
        } else {
            attributes.add(new Name(ids.getValue()));
            attributes.add(AttributeBuilder.build("cn", Collections.singletonList(ids.getKey())));
        }

        attributes.add(AttributeBuilder.build(conf.getGidAttribute(), Collections.singletonList(ids.getValue())));

        attributes.add(AttributeBuilder.build("member", Collections.singletonList(
                getEntryDN(getEntryIDs("OfAll", ObjectClass.ACCOUNT).getKey(), ObjectClass.ACCOUNT))));

        attributes.add(AttributeBuilder.build("ldapGroups", Collections.singletonList(
                getEntryDN(getEntryIDs("InFilter", ObjectClass.GROUP).getKey(), ObjectClass.GROUP))));

        return attributes;
    }

    public void cleanup(final int num) {
        for (int i = 1; i <= num; i++) {
            Uid uid = new Uid(getEntryIDs(String.valueOf(i)).getValue());

            try {
                connector.delete(oclass, uid, null);
            } catch (Exception ignore) {
                // ignore exception
                LOG.error(ignore, "Error removing user {0}", uid.getUidValue());
            }

            assertNull(connector.getObject(oclass, uid, null));
        }
    }

    public Map.Entry<String, String> getEntryIDs(final String suffix, final ObjectClass oclass) {
        final String prefix = oclass.equals(ObjectClass.ACCOUNT) ? "UserTest" : "GroupTest";
        return new AbstractMap.SimpleEntry<String, String>(prefix + suffix, "SAAN_" + prefix + suffix);
    }

    public Map.Entry<String, String> getEntryIDs(final String suffix) {
        return getEntryIDs(suffix, oclass);
    }
}
