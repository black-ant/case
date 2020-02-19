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

import static org.junit.Assert.*;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import javax.naming.NamingException;
import javax.naming.directory.Attributes;
import javax.naming.directory.BasicAttribute;
import javax.naming.directory.BasicAttributes;
import javax.naming.directory.DirContext;
import javax.naming.ldap.LdapContext;
import net.tirasa.connid.bundles.ad.ADConfiguration;
import net.tirasa.connid.bundles.ad.ADConnection;
import net.tirasa.connid.bundles.ad.ADConnector;
import net.tirasa.connid.bundles.ad.TestUtil;
import net.tirasa.connid.bundles.ad.UserTest;
import net.tirasa.connid.bundles.ldap.commons.LdapConstants;
import org.identityconnectors.common.security.GuardedString;
import org.identityconnectors.framework.api.APIConfiguration;
import org.identityconnectors.framework.api.ConnectorFacade;
import org.identityconnectors.framework.api.ConnectorFacadeFactory;
import org.identityconnectors.framework.common.exceptions.ConnectorException;
import org.identityconnectors.framework.common.objects.Attribute;
import org.identityconnectors.framework.common.objects.AttributeBuilder;
import org.identityconnectors.framework.common.objects.AttributeUtil;
import org.identityconnectors.framework.common.objects.ConnectorObject;
import org.identityconnectors.framework.common.objects.Name;
import org.identityconnectors.framework.common.objects.ObjectClass;
import org.identityconnectors.framework.common.objects.OperationOptions;
import org.identityconnectors.framework.common.objects.OperationOptionsBuilder;
import org.identityconnectors.framework.common.objects.OperationalAttributes;
import org.identityconnectors.framework.common.objects.ResultsHandler;
import org.identityconnectors.framework.common.objects.SearchResult;
import org.identityconnectors.framework.common.objects.SortKey;
import org.identityconnectors.framework.common.objects.Uid;
import org.identityconnectors.framework.common.objects.filter.Filter;
import org.identityconnectors.framework.common.objects.filter.FilterBuilder;
import org.identityconnectors.framework.impl.api.APIConfigurationImpl;
import org.identityconnectors.framework.impl.api.local.JavaClassProperties;
import org.identityconnectors.test.common.TestHelpers;
import org.junit.Test;

public class UserCrudTest extends UserTest {

    @Test
    public void pagedSearch() {
        final List<ConnectorObject> results = new ArrayList<ConnectorObject>();
        final ResultsHandler handler = new ResultsHandler() {

            @Override
            public boolean handle(final ConnectorObject co) {
                return results.add(co);
            }
        };

        final OperationOptionsBuilder oob = new OperationOptionsBuilder();
        oob.setAttributesToGet("sAMAccountName");
        oob.setPageSize(2);
        oob.setSortKeys(new SortKey("sAMAccountName", false));

        connector.search(ObjectClass.ACCOUNT, null, handler, oob.build());

        assertEquals(2, results.size());

        results.clear();

        String cookie = "";
        do {
            oob.setPagedResultsCookie(cookie);
            final SearchResult searchResult = connector.search(ObjectClass.ACCOUNT, null, handler, oob.build());
            cookie = searchResult.getPagedResultsCookie();
        } while (cookie != null);

        assertEquals(11, results.size());
    }

    @Test
    public void search() {

        final Map.Entry<String, String> ids = util.getEntryIDs("1");

        // create filter 
        final Filter filter = FilterBuilder.equalTo(AttributeBuilder.build("sAMAccountName", ids.getValue()));

        // create results handler
        final List<ConnectorObject> results = new ArrayList<ConnectorObject>();
        final ResultsHandler handler = new ResultsHandler() {

            @Override
            public boolean handle(final ConnectorObject co) {
                return results.add(co);
            }
        };

        // create options for returning attributes
        final OperationOptionsBuilder oob = new OperationOptionsBuilder();
        oob.setAttributesToGet("sAMAccountName");

        connector.search(ObjectClass.ACCOUNT, filter, handler, oob.build());

        assertEquals(1, results.size());
        assertEquals(Collections.singletonList(ids.getValue()),
                results.get(0).getAttributeByName("sAMAccountName").getValue());

        // AD-23 check
        assertNull(results.get(0).getAttributeByName("userCannotChangePassword"));
    }

    @Test
    public void read() {
        final Map.Entry<String, String> ids = util.getEntryIDs("2");

        // Ask just for sAMAccountName
        final OperationOptionsBuilder oob = new OperationOptionsBuilder();
        oob.setAttributesToGet("sAMAccountName");

        final ConnectorObject object = connector.getObject(ObjectClass.ACCOUNT, new Uid(ids.getValue()), oob.build());

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

        assertNull("Please remove user 'sAMAccountName: " + ids.getValue() + "' from AD",
                connector.getObject(ObjectClass.ACCOUNT, new Uid(ids.getValue()), null));

        final Set<Attribute> attributes = util.getSimpleProfile(ids);

        final Uid uid = connector.create(ObjectClass.ACCOUNT, attributes, null);
        assertNotNull(uid);
        assertEquals(ids.getValue(), uid.getUidValue());

        // Ask just for memberOf
        final OperationOptionsBuilder oob = new OperationOptionsBuilder();
        oob.setAttributesToGet(Arrays.asList("memberOf", "userAccountControl"));

        // retrieve created object
        final ConnectorObject object = connector.getObject(ObjectClass.ACCOUNT, uid, oob.build());

        // check for memberOf attribute     
        assertNotNull(object);
        assertNotNull(object.getAttributes());

        // Returned attributes: memberOf, NAME and UID
        assertEquals(4, object.getAttributes().size());
        assertNotNull(object.getAttributeByName("memberOf"));
        assertNotNull(object.getAttributeByName("userAccountControl"));

        final Set<String> expected = new TreeSet<String>(String.CASE_INSENSITIVE_ORDER);
        expected.addAll(Arrays.asList(conf.getMemberships()));

        final Set<String> actual = new TreeSet<String>(String.CASE_INSENSITIVE_ORDER);
        for (Object dn : object.getAttributeByName("memberOf").getValue()) {
            actual.add(dn.toString());
        }

        assertEquals(expected, actual);
        assertEquals(ids.getValue(), object.getUid().getUidValue());
        assertEquals(
                util.getEntryDN(ids.getKey(), ObjectClass.ACCOUNT).toLowerCase(),
                object.getName().getNameValue().toLowerCase());

        final Uid authUid = connector.authenticate(
                ObjectClass.ACCOUNT, // object class
                ids.getValue(), // uid
                new GuardedString("Password123".toCharArray()), // password
                null);

        assertNotNull(authUid);

        connector.delete(ObjectClass.ACCOUNT, uid, null);
        assertNull(connector.getObject(ObjectClass.ACCOUNT, uid, null));
    }

    @Test
    public void createWithoutDN() {
        assertNotNull(connector);
        assertNotNull(conf);

        final Map.Entry<String, String> ids = util.getEntryIDs("nodn11");

        assertNull("Please remove user 'sAMAccountName: " + ids.getValue() + "' from AD",
                connector.getObject(ObjectClass.ACCOUNT, new Uid(ids.getValue()), null));

        final Set<Attribute> attributes = util.getSimpleProfile(ids, false);

        final Uid uid = connector.create(ObjectClass.ACCOUNT, attributes, null);
        assertNotNull(uid);
        assertEquals(ids.getValue(), uid.getUidValue());

        // Ask just for memberOf
        final OperationOptionsBuilder oob = new OperationOptionsBuilder();
        oob.setAttributesToGet("memberOf");

        // retrieve created object
        final ConnectorObject object = connector.getObject(ObjectClass.ACCOUNT, uid, oob.build());

        // check for memberOf attribute
        assertNotNull(object);
        assertNotNull(object.getAttributes());

        // Returned attributes: memberOf, NAME and UID
        assertEquals(3, object.getAttributes().size());
        assertNotNull(object.getAttributeByName("memberOf"));

        final Set<String> expected = new TreeSet<String>(String.CASE_INSENSITIVE_ORDER);
        expected.addAll(Arrays.asList(conf.getMemberships()));

        final Set<String> actual = new TreeSet<String>(String.CASE_INSENSITIVE_ORDER);
        for (Object dn : object.getAttributeByName("memberOf").getValue()) {
            actual.add(dn.toString());
        }

        assertEquals(expected, actual);
        assertEquals(ids.getValue(), object.getUid().getUidValue());
        assertEquals(util.getEntryDN(ids.getKey(),
                ObjectClass.ACCOUNT).toLowerCase(), object.getName().getNameValue().toLowerCase());

        final Uid authUid = connector.authenticate(
                ObjectClass.ACCOUNT, // object class
                ids.getValue(), // uid
                new GuardedString("Password123".toCharArray()), // password
                null);

        assertNotNull(authUid);

        connector.delete(ObjectClass.ACCOUNT, uid, null);
        assertNull(connector.getObject(ObjectClass.ACCOUNT, uid, null));
    }

    @Test
    public void checkLdapGroups() {
        assertNotNull(connector);
        assertNotNull(conf);

        String baseContext = PROP.getProperty("baseContext");

        final Map.Entry<String, String> ids = util.getEntryIDs("20");

        assertNull("Please remove user 'sAMAccountName: " + ids.getValue() + "' from AD",
                connector.getObject(ObjectClass.ACCOUNT, new Uid(ids.getValue()), null));

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

        Uid uid = connector.create(ObjectClass.ACCOUNT, attributes, null);
        assertNotNull(uid);
        assertEquals(ids.getValue(), uid.getUidValue());

        // Ask just for memberOf
        final OperationOptionsBuilder oob = new OperationOptionsBuilder();
        oob.setAttributesToGet("memberOf", "ldapGroups");

        // retrieve created object
        ConnectorObject object = connector.getObject(ObjectClass.ACCOUNT, uid, oob.build());

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

        List<Attribute> attrToReplace = Arrays.asList(new Attribute[] {
            AttributeBuilder.build("ldapGroups", "CN=Schema Admins,CN=Users," + baseContext) });

        uid = connector.update(
                ObjectClass.ACCOUNT,
                uid,
                new HashSet<Attribute>(attrToReplace),
                null);

        assertNotNull(uid);
        assertEquals(ids.getValue(), uid.getUidValue());

        object = connector.getObject(ObjectClass.ACCOUNT, uid, oob.build());

        assertFalse(object.getAttributeByName("ldapGroups").getValue().contains(
                "CN=Cert Publishers,CN=Users," + baseContext));
        assertTrue(object.getAttributeByName("ldapGroups").getValue().contains(
                "CN=Schema Admins,CN=Users," + baseContext));

        attrToReplace = Arrays.asList(new Attribute[] { AttributeBuilder.build("ldapGroups",
            "CN=Schema Admins,CN=Users," + baseContext,
            "CN=Cert Publishers,CN=Users," + baseContext) });

        uid = connector.update(
                ObjectClass.ACCOUNT,
                uid,
                new HashSet<Attribute>(attrToReplace),
                null);

        assertNotNull(uid);
        assertEquals(ids.getValue(), uid.getUidValue());

        object = connector.getObject(ObjectClass.ACCOUNT, uid, oob.build());

        assertTrue(object.getAttributeByName("ldapGroups").getValue().contains(
                "CN=Cert Publishers,CN=Users," + baseContext));
        assertTrue(object.getAttributeByName("ldapGroups").getValue().contains(
                "CN=Schema Admins,CN=Users," + baseContext));

        connector.delete(ObjectClass.ACCOUNT, uid, null);
        assertNull(connector.getObject(ObjectClass.ACCOUNT, uid, null));
    }

    @Test
    public void update() {
        assertNotNull(connector);
        assertNotNull(conf);

        final Map.Entry<String, String> ids = util.getEntryIDs("3");

        Uid authUid = connector.authenticate(
                ObjectClass.ACCOUNT, // object class
                ids.getValue(), // uid
                new GuardedString("Password123".toCharArray()), // password
                null);

        assertNotNull(authUid);

        try {
            connector.authenticate(
                    ObjectClass.ACCOUNT, // object class
                    ids.getValue(), // uid
                    new GuardedString("Password321".toCharArray()), // password
                    null);
            fail();
        } catch (ConnectorException ignore) {
            // ignore
        }

        List<Attribute> attrToReplace = Arrays.asList(new Attribute[] {
            AttributeBuilder.build("givenName", "gnupdate"),
            AttributeBuilder.buildPassword(
            new GuardedString("Password321".toCharArray())) });

        Uid uid = connector.update(
                ObjectClass.ACCOUNT,
                new Uid(ids.getValue()),
                new HashSet<Attribute>(attrToReplace),
                null);

        assertNotNull(uid);
        assertEquals(ids.getValue(), uid.getUidValue());

        // Ask just for sAMAccountName
        final OperationOptionsBuilder oob = new OperationOptionsBuilder();
        oob.setAttributesToGet("givenName");

        final ConnectorObject object = connector.getObject(ObjectClass.ACCOUNT, uid, oob.build());

        assertNotNull(object);
        assertNotNull(object.getAttributes());
        // Returned attributes: givenName, NAME and UID
        assertEquals(3, object.getAttributes().size());
        assertNotNull(object.getAttributeByName("givenName"));
        assertEquals(
                Collections.singletonList("gnupdate"),
                object.getAttributeByName("givenName").getValue());

        try {
            connector.authenticate(
                    ObjectClass.ACCOUNT, // object class
                    ids.getValue(), // uid
                    new GuardedString("Password123".toCharArray()), // password
                    null);
            fail();
        } catch (ConnectorException ignore) {
            // ignore
        }

        authUid = connector.authenticate(
                ObjectClass.ACCOUNT, // object class
                ids.getValue(), // uid
                new GuardedString("Password321".toCharArray()), // password
                null);

        assertNotNull(authUid);

        // --------------------------
        // force change password
        // --------------------------
        attrToReplace = Arrays.asList(new Attribute[] { AttributeBuilder.build("pwdLastSet", true) });

        connector.update(
                ObjectClass.ACCOUNT,
                new Uid(ids.getValue()),
                new HashSet<Attribute>(attrToReplace),
                null);

        try {
            connector.authenticate(
                    ObjectClass.ACCOUNT, // object class
                    ids.getValue(), // uid
                    new GuardedString("Password123".toCharArray()), // password
                    null);
            fail();
        } catch (ConnectorException ignore) {
            // ignore
        }
        // --------------------------
    }

    @Test
    public void rename() {
        assertNotNull(connector);
        assertNotNull(conf);

        final Map.Entry<String, String> ids = util.getEntryIDs("5");

        final String DN = "cn=" + ids.getKey() + ",cn=Computers," + BASE_CONTEXT;

        final List<Attribute> attrToReplace = Arrays.asList(new Attribute[] {
            AttributeBuilder.build(Name.NAME, DN),
            AttributeBuilder.buildPassword(new GuardedString("Password321".toCharArray())) });

        Uid uid = connector.update(
                ObjectClass.ACCOUNT, new Uid(ids.getValue()), new HashSet<Attribute>(attrToReplace), null);

        assertNotNull(uid);
        assertEquals(ids.getValue(), uid.getUidValue());

        uid = connector.authenticate(
                ObjectClass.ACCOUNT, ids.getValue(), new GuardedString("Password321".toCharArray()), null);
        assertNotNull(uid);

        // Ask just for sAMAccountName
        final OperationOptionsBuilder oob = new OperationOptionsBuilder();
        oob.setAttributesToGet("memberOf");

        final ConnectorObject object = connector.getObject(ObjectClass.ACCOUNT, uid, oob.build());

        // Returned attributes: memberOf, NAME and UID
        assertEquals(3, object.getAttributes().size());
        assertNotNull(object.getAttributeByName("memberOf"));
        assertTrue(DN.equalsIgnoreCase(object.getName().getNameValue()));

        final Set<String> expected = new TreeSet<String>(String.CASE_INSENSITIVE_ORDER);
        expected.addAll(Arrays.asList(conf.getMemberships()));

        final Set<String> actual = new TreeSet<String>(String.CASE_INSENSITIVE_ORDER);
        for (Object dn : object.getAttributeByName("memberOf").getValue()) {
            actual.add(dn.toString());
        }

        assertEquals(expected, actual);
    }

    @Test
    public void noRenameWithTheSameCN() {
        assertNotNull(connector);
        assertNotNull(conf);

        final Map.Entry<String, String> ids = util.getEntryIDs("6");

        final List<Attribute> attrToReplace = Arrays.asList(new Attribute[] {
            AttributeBuilder.build(Name.NAME, ids.getKey()),
            AttributeBuilder.buildPassword(new GuardedString("Password321".toCharArray())) });

        Uid uid = connector.update(
                ObjectClass.ACCOUNT, new Uid(ids.getValue()), new HashSet<Attribute>(attrToReplace), null);

        assertNotNull(uid);
        assertEquals(ids.getValue(), uid.getUidValue());

        uid = connector.authenticate(
                ObjectClass.ACCOUNT, ids.getValue(), new GuardedString("Password321".toCharArray()), null);
        assertNotNull(uid);

        // Ask just for sAMAccountName
        final OperationOptionsBuilder oob = new OperationOptionsBuilder();
        oob.setAttributesToGet("memberOf");

        final ConnectorObject object = connector.getObject(ObjectClass.ACCOUNT, uid, oob.build());

        // Returned attributes: memberOf, NAME and UID
        assertEquals(3, object.getAttributes().size());
        assertNotNull(object.getAttributeByName("memberOf"));

        assertTrue(
                util.getEntryDN(ids.getKey(), ObjectClass.ACCOUNT).equalsIgnoreCase(object.getName().getNameValue()));

        final Set<String> expected = new TreeSet<String>(String.CASE_INSENSITIVE_ORDER);
        expected.addAll(Arrays.asList(conf.getMemberships()));

        final Set<String> actual = new TreeSet<String>(String.CASE_INSENSITIVE_ORDER);
        for (Object dn : object.getAttributeByName("memberOf").getValue()) {
            actual.add(dn.toString());
        }

        assertEquals(expected, actual);
    }

    @Test
    public void noRenameWithTheSameDN() {
        assertNotNull(connector);
        assertNotNull(conf);

        final Map.Entry<String, String> ids = util.getEntryIDs("6");

        final List<Attribute> attrToReplace = Arrays.asList(new Attribute[] {
            AttributeBuilder.build(Name.NAME, util.getEntryDN(ids.getKey(), ObjectClass.ACCOUNT)),
            AttributeBuilder.buildPassword(new GuardedString("Password321".toCharArray())) });

        Uid uid = connector.update(
                ObjectClass.ACCOUNT, new Uid(ids.getValue()), new HashSet<Attribute>(attrToReplace), null);

        assertNotNull(uid);
        assertEquals(ids.getValue(), uid.getUidValue());

        uid = connector.authenticate(
                ObjectClass.ACCOUNT, ids.getValue(), new GuardedString("Password321".toCharArray()), null);
        assertNotNull(uid);

        // Ask just for sAMAccountName
        final OperationOptionsBuilder oob = new OperationOptionsBuilder();
        oob.setAttributesToGet("memberOf");

        final ConnectorObject object = connector.getObject(ObjectClass.ACCOUNT, uid, oob.build());

        // Returned attributes: memberOf, NAME and UID
        assertEquals(3, object.getAttributes().size());
        assertNotNull(object.getAttributeByName("memberOf"));

        assertTrue(
                util.getEntryDN(ids.getKey(), ObjectClass.ACCOUNT).equalsIgnoreCase(object.getName().getNameValue()));

        final Set<String> expected = new TreeSet<String>(String.CASE_INSENSITIVE_ORDER);
        expected.addAll(Arrays.asList(conf.getMemberships()));

        final Set<String> actual = new TreeSet<String>(String.CASE_INSENSITIVE_ORDER);
        for (Object dn : object.getAttributeByName("memberOf").getValue()) {
            actual.add(dn.toString());
        }

        assertEquals(expected, actual);
    }

    @Test
    public void renameWithoutDN() {
        assertNotNull(connector);
        assertNotNull(conf);

        final Map.Entry<String, String> ids = util.getEntryIDs("5");

        final List<Attribute> attrToReplace = Arrays.asList(new Attribute[] { AttributeBuilder.build("cn", ids.getKey()
            + "_new") });

        Uid uid = connector.update(
                ObjectClass.ACCOUNT, new Uid(ids.getValue()), new HashSet<Attribute>(attrToReplace), null);

        assertNotNull(uid);
        assertEquals(ids.getValue(), uid.getUidValue());

        // Ask just for sAMAccountName
        final OperationOptionsBuilder oob = new OperationOptionsBuilder();
        oob.setAttributesToGet("cn");

        final ConnectorObject object = connector.getObject(ObjectClass.ACCOUNT, uid, oob.build());

        // Returned attributes: cn, NAME and UID
        assertEquals(3, object.getAttributes().size());
        assertNotNull(object.getAttributeByName("cn"));
        assertEquals(ids.getKey() + "_new", object.getAttributeByName("cn").getValue().get(0));
    }

    @Test
    public void disable() {

        assertNotNull(connector);
        assertNotNull(conf);

        final Map.Entry<String, String> ids = util.getEntryIDs("4");

        Uid authUid = connector.authenticate(
                ObjectClass.ACCOUNT, // object class
                ids.getValue(), // uid
                new GuardedString("Password123".toCharArray()), // password
                null);

        assertNotNull(authUid);

        List<Attribute> attrToReplace = Arrays.asList(new Attribute[] { AttributeBuilder.buildEnabled(false) });

        Uid uid = connector.update(
                ObjectClass.ACCOUNT,
                new Uid(ids.getValue()),
                new HashSet<Attribute>(attrToReplace),
                null);

        assertNotNull(uid);
        assertEquals(ids.getValue(), uid.getUidValue());

        try {
            connector.authenticate(
                    ObjectClass.ACCOUNT, // object class
                    ids.getValue(), // uid
                    new GuardedString("Password123".toCharArray()), // password
                    null);
            fail();
        } catch (ConnectorException ignore) {
            // ignore
        }

        attrToReplace = Arrays.asList(new Attribute[] { AttributeBuilder.buildEnabled(true) });

        uid = connector.update(
                ObjectClass.ACCOUNT,
                new Uid(ids.getValue()),
                new HashSet<Attribute>(attrToReplace),
                null);

        assertNotNull(uid);
        assertEquals(ids.getValue(), uid.getUidValue());

        authUid = connector.authenticate(
                ObjectClass.ACCOUNT, // object class
                ids.getValue(), // uid
                new GuardedString("Password123".toCharArray()), // password
                null);

        assertNotNull(authUid);
    }

    @Test
    public void issueAD22() {
        final Map.Entry<String, String> ids = util.getEntryIDs("AD22");

        assertNull("Please remove user 'uid: " + ids.getValue() + "' from AD",
                connector.getObject(ObjectClass.ACCOUNT, new Uid(ids.getValue()), null));

        int uacValue = 0x0200;

        final Set<Attribute> attributes = util.getSimpleProfile(ids);
        attributes.add(AttributeBuilder.build(
                "userAccountControl", Collections.singletonList(String.valueOf(uacValue))));

        final OperationOptions options = new OperationOptionsBuilder().setAttributesToGet("userAccountControl").build();

        final Uid uid = connector.create(ObjectClass.ACCOUNT, attributes, options);
        assertEquals(ids.getValue(), uid.getUidValue());

        try {
            ConnectorObject connectorObject = connector.getObject(ObjectClass.ACCOUNT, uid, options);
            assertEquals(String.valueOf(uacValue),
                    connectorObject.getAttributeByName("userAccountControl").getValue().get(0));

            uacValue = uacValue + 0x0002;
            final List<Attribute> attrToReplace = Arrays.asList(new Attribute[] {
                AttributeBuilder.build("userAccountControl", String.valueOf(uacValue)) });

            connector.update(
                    ObjectClass.ACCOUNT,
                    new Uid(ids.getValue()),
                    new HashSet<Attribute>(attrToReplace),
                    options);

            connectorObject = connector.getObject(ObjectClass.ACCOUNT, uid, options);
            assertEquals(String.valueOf(uacValue),
                    connectorObject.getAttributeByName("userAccountControl").getValue().get(0));
        } finally {
            connector.delete(ObjectClass.ACCOUNT, uid, null);
            assertNull(connector.getObject(ObjectClass.ACCOUNT, uid, null));
        }
    }

    @Test
    public void issueAD24() {
        final ADConfiguration newconf = getSimpleConf(PROP);
        newconf.setUidAttribute("uid");

        final ConnectorFacadeFactory factory = ConnectorFacadeFactory.getInstance();
        final APIConfiguration impl = TestHelpers.createTestConfiguration(ADConnector.class, newconf);
        // TODO: remove the line below when using ConnId >= 1.4.0.1
        ((APIConfigurationImpl) impl).
                setConfigurationProperties(JavaClassProperties.createConfigurationProperties(newconf));

        final ConnectorFacade newConnector = factory.newInstance(impl);

        final Map.Entry<String, String> ids = util.getEntryIDs("AD24");

        assertNull("Please remove user 'uid: " + ids.getValue() + "' from AD",
                newConnector.getObject(ObjectClass.ACCOUNT, new Uid(ids.getValue()), null));

        final Set<Attribute> attributes = new TestUtil(newConnector, newconf, ObjectClass.ACCOUNT, BASE_CONTEXT).
                getSimpleProfile(ids);

        final OperationOptionsBuilder oob = new OperationOptionsBuilder();
        oob.setAttributesToGet("memberOf");

        final Uid uid = newConnector.create(ObjectClass.ACCOUNT, attributes, oob.build());
        assertEquals(ids.getValue(), uid.getUidValue());

        try {
            assertNotNull(newConnector.getObject(ObjectClass.ACCOUNT, uid, oob.build()));
        } finally {
            newConnector.delete(ObjectClass.ACCOUNT, uid, null);
            assertNull(newConnector.getObject(ObjectClass.ACCOUNT, uid, null));
        }
    }

    @Test
    public void issueAD25() {
        assertNotNull(connector);
        assertNotNull(conf);

        // Ask just for cn, uid and sAMAccountName
        final OperationOptionsBuilder oob = new OperationOptionsBuilder();
        oob.setAttributesToGet("cn", "uid", "sAMAccountName");

        final Map.Entry<String, String> ids = util.getEntryIDs("6");
        ConnectorObject object = connector.getObject(ObjectClass.ACCOUNT, new Uid(ids.getValue()), oob.build());
        assertEquals(ids.getValue(), object.getUid().getUidValue());

        List<Attribute> attrToReplace = Arrays.asList(new Attribute[] { AttributeBuilder.build(Uid.NAME, ids.getValue()
            + "_new") });

        try {
            connector.update(
                    ObjectClass.ACCOUNT, new Uid(ids.getValue()), new HashSet<Attribute>(attrToReplace), null);
            fail();
        } catch (org.identityconnectors.framework.common.exceptions.InvalidAttributeValueException e) {
            // ignore
        }

        attrToReplace = Arrays.asList(new Attribute[] {
            AttributeBuilder.build(conf.getUidAttribute(), ids.getValue() + "_new") });

        Uid uid = connector.update(
                ObjectClass.ACCOUNT, new Uid(ids.getValue()), new HashSet<Attribute>(attrToReplace), null);

        assertEquals(ids.getValue() + "_new", uid.getUidValue());

        // restore ....
        attrToReplace = Arrays.asList(new Attribute[] {
            AttributeBuilder.build(conf.getUidAttribute(), ids.getValue()) });

        uid = connector.update(ObjectClass.ACCOUNT, uid, new HashSet<Attribute>(attrToReplace), null);

        assertEquals(ids.getValue(), uid.getUidValue());
    }

    @Test
    public void issueAD27() {
        final ADConfiguration newconf = getSimpleConf(PROP);
        newconf.setDefaultGroupContainer("CN=Builtin," + BASE_CONTEXT);
        newconf.setGroupBaseContexts(newconf.getDefaultGroupContainer());
        newconf.setUserBaseContexts(newconf.getDefaultPeopleContainer());

        final ConnectorFacadeFactory factory = ConnectorFacadeFactory.getInstance();
        final APIConfiguration impl = TestHelpers.createTestConfiguration(ADConnector.class, newconf);
        // TODO: remove the line below when using ConnId >= 1.4.0.1
        ((APIConfigurationImpl) impl).
                setConfigurationProperties(JavaClassProperties.createConfigurationProperties(newconf));

        final ConnectorFacade newConnector = factory.newInstance(impl);

        final TestUtil newutil = new TestUtil(newConnector, newconf, ObjectClass.ACCOUNT, BASE_CONTEXT);

        // 1. create a new group
        Map.Entry<String, String> groupIDs = new AbstractMap.SimpleEntry<String, String>("GroupTestAD27",
                "SAAN_GroupTestAD27");

        assertNull("Please remove group 'sAMAccountName: " + groupIDs.getValue() + "' from AD",
                newConnector.getObject(ObjectClass.GROUP, new Uid(groupIDs.getValue()), null));

        Set<Attribute> attributes = newutil.getSimpleGroupProfile(groupIDs, true);

        final Attribute ldapGroups = AttributeUtil.find("ldapGroups", attributes);
        attributes.remove(ldapGroups);

        final List<String> groupsToBeAdded = new ArrayList<String>();

        if (ldapGroups != null && ldapGroups.getValue() != null) {
            groupsToBeAdded.add(
                    util.getEntryDN(util.getEntryIDs("InFilter", ObjectClass.GROUP).getKey(), ObjectClass.GROUP));
        }

        attributes.add(AttributeBuilder.build("ldapGroups", groupsToBeAdded));

        Uid groupUID = null;
        Uid userUID = null;

        try {
            groupUID = newConnector.create(ObjectClass.GROUP, attributes, null);
            assertNotNull(groupUID);

            // 2. create a new user
            Map.Entry<String, String> userIDs = newutil.getEntryIDs("AD27");

            assertNull("Please remove user 'sAMAccountName: " + userIDs.getValue() + "' from AD",
                    newConnector.getObject(ObjectClass.ACCOUNT, new Uid(userIDs.getValue()), null));

            attributes = newutil.getSimpleProfile(userIDs, false);
            userUID = newConnector.create(ObjectClass.ACCOUNT, attributes, null);
            assertNotNull(userUID);

            // 3. update user by adding membership
            List<Attribute> attrToReplace = Arrays.asList(new Attribute[] {
                AttributeBuilder.build(
                "ldapGroups",
                String.format("CN=%s,CN=Builtin,%s", groupIDs.getKey(), BASE_CONTEXT)) });

            newConnector.update(
                    ObjectClass.ACCOUNT,
                    userUID,
                    new HashSet<Attribute>(attrToReplace),
                    null);

            final OperationOptionsBuilder oob = new OperationOptionsBuilder();
            oob.setAttributesToGet("memberOf");

            final ConnectorObject object = newConnector.getObject(ObjectClass.ACCOUNT, userUID, oob.build());

            assertTrue(object.getAttributeByName("memberOf").getValue().contains(
                    String.format("CN=%s,CN=Builtin,%s", groupIDs.getKey(), BASE_CONTEXT)));
        } finally {
            if (userUID != null) {
                newConnector.delete(ObjectClass.ACCOUNT, userUID, null);
                assertNull(newConnector.getObject(ObjectClass.ACCOUNT, userUID, null));
            }
            if (groupUID != null) {
                newConnector.delete(ObjectClass.GROUP, groupUID, null);
                assertNull(newConnector.getObject(ObjectClass.GROUP, groupUID, null));
            }
        }
    }

    @Test
    public void pwdUpdateOnly() {
        final ADConfiguration newconf = getSimpleConf(PROP);
        newconf.setPwdUpdateOnly(true);

        final ConnectorFacadeFactory factory = ConnectorFacadeFactory.getInstance();
        final APIConfiguration impl = TestHelpers.createTestConfiguration(ADConnector.class, newconf);
        // TODO: remove the line below when using ConnId >= 1.4.0.1
        ((APIConfigurationImpl) impl).
                setConfigurationProperties(JavaClassProperties.createConfigurationProperties(newconf));

        final ConnectorFacade newConnector = factory.newInstance(impl);

        final Map.Entry<String, String> ids = util.getEntryIDs("9");

        Uid authUid = newConnector.authenticate(
                ObjectClass.ACCOUNT, // object class
                ids.getValue(), // uid
                new GuardedString("Password123".toCharArray()), // password
                null);

        assertNotNull(authUid);

        // 0. delete should be denied
        try {
            newConnector.delete(ObjectClass.ACCOUNT, new Uid(ids.getValue()), null);
            fail();
        } catch (Exception e) {
            // ignore
        }

        // 1. create should be denied
        try {
            newConnector.create(ObjectClass.ACCOUNT, util.getSimpleProfile(util.getEntryIDs("100")), null);
            fail();
        } catch (Exception e) {
            // ignore
        }

        // 2. Update without pwd ....
        List<Attribute> attrToReplace = Arrays.asList(new Attribute[] {
            AttributeBuilder.build("givenName", "pwdUpdateOnlyName"),
            AttributeBuilder.buildEnabled(false),
            AttributeBuilder.build("pwdLastSet", true) });

        Uid uid = newConnector.update(
                ObjectClass.ACCOUNT,
                new Uid(ids.getValue()),
                new HashSet<Attribute>(attrToReplace),
                null);
        assertEquals(ids.getValue(), uid.getUidValue());

        // Ask just for sAMAccountName
        final OperationOptionsBuilder oob = new OperationOptionsBuilder();
        oob.setAttributesToGet("givenName", OperationalAttributes.PASSWORD_NAME, OperationalAttributes.ENABLE_NAME);

        ConnectorObject object = newConnector.getObject(ObjectClass.ACCOUNT, uid, oob.build());

        List<Object> gn = object.getAttributeByName("givenName").getValue();
        assertTrue("Actual givenName " + gn, gn.size() == 1 && !gn.contains("pwdUpdateOnlyName"));

        assertTrue("Actual status " + object.getAttributeByName(OperationalAttributes.ENABLE_NAME).getValue(),
                Boolean.class.cast(object.getAttributeByName(OperationalAttributes.ENABLE_NAME).getValue().get(0)));

        authUid = newConnector.authenticate(
                ObjectClass.ACCOUNT, // object class
                ids.getValue(), // uid
                new GuardedString("Password123".toCharArray()), // password
                null);
        assertNotNull(authUid);

        // 3. Update including pwd ....
        attrToReplace = Arrays.asList(new Attribute[] {
            AttributeBuilder.build("givenName", "pwdUpdateOnlyName"),
            AttributeBuilder.buildEnabled(false),
            AttributeBuilder.build("pwdLastSet", true),
            AttributeBuilder.buildPassword(new GuardedString("Password3210".toCharArray())) });

        uid = newConnector.update(
                ObjectClass.ACCOUNT,
                new Uid(ids.getValue()),
                new HashSet<Attribute>(attrToReplace),
                null);
        assertEquals(ids.getValue(), uid.getUidValue());

        object = newConnector.getObject(ObjectClass.ACCOUNT, uid, oob.build());

        gn = object.getAttributeByName("givenName").getValue();
        assertTrue("Actual givenName " + gn, gn.size() == 1 && !gn.contains("pwdUpdateOnlyName"));

        assertTrue("Actual status " + object.getAttributeByName(OperationalAttributes.ENABLE_NAME).getValue(),
                Boolean.class.cast(object.getAttributeByName(OperationalAttributes.ENABLE_NAME).getValue().get(0)));

        authUid = newConnector.authenticate(
                ObjectClass.ACCOUNT, // object class
                ids.getValue(), // uid
                new GuardedString("Password3210".toCharArray()), // password
                null);
        assertNotNull(authUid);
    }

    @Test
    public void issueAD23() {
        assertNotNull(connector);
        assertNotNull(conf);

        // create results handler
        final List<ConnectorObject> results = new ArrayList<ConnectorObject>();
        final ResultsHandler handler = new ResultsHandler() {

            @Override
            public boolean handle(final ConnectorObject co) {
                return results.add(co);
            }
        };

        // create options for returning attributes
        OperationOptionsBuilder oob = new OperationOptionsBuilder();
        oob.setAttributesToGet("sAMAccountName", ADConfiguration.UCCP_FLAG);

        // -----------------------------------------------------
        // Create Cannot Change Password
        // -----------------------------------------------------
        final Map.Entry<String, String> ids = util.getEntryIDs("AD23");

        // create filter 
        final Filter filter = FilterBuilder.equalTo(AttributeBuilder.build("sAMAccountName", ids.getValue()));

        assertNull("Please remove user 'sAMAccountName: " + ids.getValue() + "' from AD",
                connector.getObject(ObjectClass.ACCOUNT, new Uid(ids.getValue()), null));

        Set<Attribute> attributes = util.getSimpleProfile(ids);
        attributes.add(AttributeBuilder.build(ADConfiguration.UCCP_FLAG, true));

        Uid uid = connector.create(ObjectClass.ACCOUNT, attributes, null);
        assertNotNull(uid);
        try {
            assertEquals(ids.getValue(), uid.getUidValue());

            connector.search(ObjectClass.ACCOUNT, filter, handler, oob.build());

            assertEquals(1, results.size());
            assertEquals(Collections.singletonList(ids.getValue()),
                    results.get(0).getAttributeByName("sAMAccountName").getValue());

            // AD-23 check read
            assertTrue(Boolean.class.cast(
                    results.get(0).getAttributeByName(ADConfiguration.UCCP_FLAG).getValue().get(0)));
        } finally {
            connector.delete(ObjectClass.ACCOUNT, uid, null);
        }
        // -----------------------------------------------------

        results.clear();

        // -----------------------------------------------------
        // Create Can Change Password
        // -----------------------------------------------------
        assertNull("Please remove user 'sAMAccountName: " + ids.getValue() + "' from AD",
                connector.getObject(ObjectClass.ACCOUNT, new Uid(ids.getValue()), null));

        attributes = util.getSimpleProfile(ids);
        attributes.add(AttributeBuilder.build(ADConfiguration.UCCP_FLAG, false));

        uid = connector.create(ObjectClass.ACCOUNT, attributes, null);
        assertNotNull(uid);
        // -----------------------------------------------------

        try {
            assertEquals(ids.getValue(), uid.getUidValue());

            connector.search(ObjectClass.ACCOUNT, filter, handler, oob.build());

            assertEquals(1, results.size());
            assertEquals(Collections.singletonList(ids.getValue()),
                    results.get(0).getAttributeByName("sAMAccountName").getValue());

            // AD-23 check read
            assertFalse(Boolean.class.cast(
                    results.get(0).getAttributeByName(ADConfiguration.UCCP_FLAG).getValue().get(0)));

            // -----------------------------------------------------
            // Update ...
            // -----------------------------------------------------
            List<Attribute> attrToReplace = Arrays.asList(new Attribute[] {
                AttributeBuilder.build(ADConfiguration.UCCP_FLAG, true) });

            uid = connector.update(
                    ObjectClass.ACCOUNT,
                    new Uid(ids.getValue()),
                    new HashSet<Attribute>(attrToReplace),
                    null);

            assertNotNull(uid);
            assertEquals(ids.getValue(), uid.getUidValue());

            ConnectorObject object = connector.getObject(ObjectClass.ACCOUNT, uid, oob.build());

            assertEquals(Collections.singletonList(ids.getValue()), object.getAttributeByName("sAMAccountName").
                    getValue());

            // AD-23 check read
            assertTrue(Boolean.class.cast(object.getAttributeByName(ADConfiguration.UCCP_FLAG).getValue().get(0)));
            attrToReplace = Arrays.asList(new Attribute[] {
                AttributeBuilder.build(ADConfiguration.UCCP_FLAG, false) });

            uid = connector.update(
                    ObjectClass.ACCOUNT,
                    new Uid(ids.getValue()),
                    new HashSet<Attribute>(attrToReplace),
                    null);

            assertNotNull(uid);
            assertEquals(ids.getValue(), uid.getUidValue());

            object = connector.getObject(ObjectClass.ACCOUNT, uid, oob.build());

            assertEquals(Collections.singletonList(ids.getValue()), object.getAttributeByName("sAMAccountName").
                    getValue());

            // AD-23 check read
            assertFalse(Boolean.class.cast(object.getAttributeByName(ADConfiguration.UCCP_FLAG).getValue().get(0)));
            // -----------------------------------------------------
        } finally {
            connector.delete(ObjectClass.ACCOUNT, uid, null);
        }
    }

    @Test
    public void searchByObjectGUID() {
        final ADConfiguration newconf = getSimpleConf(PROP);
        newconf.setUidAttribute(ADConnector.OBJECTGUID);

        final ConnectorFacadeFactory factory = ConnectorFacadeFactory.getInstance();
        final APIConfiguration impl = TestHelpers.createTestConfiguration(ADConnector.class, newconf);
        // TODO: remove the line below when using ConnId >= 1.4.0.1
        ((APIConfigurationImpl) impl).
                setConfigurationProperties(JavaClassProperties.createConfigurationProperties(newconf));

        final ConnectorFacade newConnector = factory.newInstance(impl);

        // create options for returning attributes
        OperationOptionsBuilder oob = new OperationOptionsBuilder();
        oob.setAttributesToGet("sAMAccountName", ADConnector.OBJECTGUID);

        // -----------------------------------------------------
        // Create new user
        // -----------------------------------------------------
        final String sAMAccountName = "SAAN_AD33";
        final String cn = "AD33";

        final Set<Attribute> attributes = new HashSet<Attribute>();

        attributes.add(new Name(null));
        attributes.add(AttributeBuilder.build("cn", Collections.singletonList(cn)));

        attributes.add(AttributeBuilder.buildEnabled(true));

        if (conf.isSsl()) {
            attributes.add(AttributeBuilder.buildPassword("Password123".toCharArray()));
        }

        attributes.add(AttributeBuilder.build("sAMAccountName", Collections.singletonList(sAMAccountName)));
        attributes.add(AttributeBuilder.build("sn", Collections.singletonList("sntest")));
        attributes.add(AttributeBuilder.build("givenName", Collections.singletonList("gntest")));
        attributes.add(AttributeBuilder.build("displayName", Collections.singletonList("dntest")));

        final Uid uid = newConnector.create(ObjectClass.ACCOUNT, attributes, null);
        assertNotNull(uid);
        // -----------------------------------------------------

        try {
            final ConnectorObject user = newConnector.getObject(ObjectClass.ACCOUNT, uid, oob.build());
            final Attribute objectguid = user.getAttributeByName(ADConnector.OBJECTGUID);

            assertEquals(objectguid.getValue(), uid.getValue());

            // create results handler
            final List<ConnectorObject> results = new ArrayList<ConnectorObject>();
            final ResultsHandler handler = new ResultsHandler() {

                @Override
                public boolean handle(final ConnectorObject co) {
                    return results.add(co);
                }
            };

            // -----------------------------------------------------
            // Search by ObjectGUID
            // -----------------------------------------------------
            final Filter filter = FilterBuilder.equalTo(AttributeBuilder.build(
                    ADConnector.OBJECTGUID, objectguid.getValue().get(0)));

            newConnector.search(ObjectClass.ACCOUNT, filter, handler, oob.build());

            assertEquals(1, results.size());
            assertEquals(Collections.singletonList(sAMAccountName),
                    results.get(0).getAttributeByName("sAMAccountName").getValue());
            assertEquals(objectguid.getValue(), results.get(0).getUid().getValue());

            // -----------------------------------------------------
        } finally {
            newConnector.delete(ObjectClass.ACCOUNT, uid, null);
        }
    }

    @Test
    public void issueAD40() {
        final ADConfiguration newconf = getSimpleConf(PROP);
        newconf.setMemberships();
        newconf.setUserBaseContexts("ou=test1," + BASE_CONTEXT);
        newconf.setGroupBaseContexts("ou=groups,ou=test1," + BASE_CONTEXT);
        newconf.setDefaultPeopleContainer("ou=test1," + BASE_CONTEXT);
        newconf.setDefaultGroupContainer("ou=groups,ou=test1," + BASE_CONTEXT);

        final ConnectorFacadeFactory factory = ConnectorFacadeFactory.getInstance();
        final APIConfiguration impl = TestHelpers.createTestConfiguration(ADConnector.class, newconf);
        // TODO: remove the line below when using ConnId >= 1.4.0.1
        ((APIConfigurationImpl) impl).
                setConfigurationProperties(JavaClassProperties.createConfigurationProperties(newconf));

        final ConnectorFacade newConnector = factory.newInstance(impl);

        final OperationOptionsBuilder oob = new OperationOptionsBuilder();
        oob.setAttributesToGet("sAMAccountName", LdapConstants.LDAP_GROUPS_NAME, ADConnector.MEMBEROF,
                ADConnector.OBJECTGUID);

        final ADConnection connection = new ADConnection(newconf);
        final LdapContext ctx = connection.getInitialContext();

        try {
            // ----------------------------------
            // Setup a complex scenario
            // Root
            // --test1
            // ----users
            // ----groups
            // ----excludedgrps
            // --test2
            // ----------------------------------
            Attributes attrs = new BasicAttributes(true);
            BasicAttribute objclass = new BasicAttribute("objectclass");
            objclass.add("top");
            objclass.add("organizationalUnit");
            attrs.put(objclass);

            // Create the context
            final DirContext test1 = ctx.createSubcontext("ou=test1," + BASE_CONTEXT, attrs);
            createGrp(test1, "test1grp");

            final DirContext users = test1.createSubcontext("ou=users", attrs);
            createGrp(users, "usersgrp");

            final DirContext groups = test1.createSubcontext("ou=groups", attrs);
            createGrp(groups, "groupsgrp");

            final DirContext excluded = test1.createSubcontext("ou=excludedgrps", attrs);
            createGrp(excluded, "excludedgrp");

            final DirContext test2 = ctx.createSubcontext("ou=test2," + BASE_CONTEXT, attrs);
            createGrp(test2, "test2grp");
            // ----------------------------------

            // -----------------------------------------------------
            // Create new user
            // -----------------------------------------------------
            final String sAMAccountName = "SAAN_AD40";
            final String cn = "AD40";

            final Set<Attribute> attributes = new HashSet<Attribute>();

            attributes.add(new Name(null));
            attributes.add(AttributeBuilder.build("cn", Collections.singletonList(cn)));

            attributes.add(AttributeBuilder.buildEnabled(true));

            if (conf.isSsl()) {
                attributes.add(AttributeBuilder.buildPassword("Password123".toCharArray()));
            }

            attributes.add(AttributeBuilder.build("sAMAccountName", Collections.singletonList(sAMAccountName)));
            attributes.add(AttributeBuilder.build("sn", Collections.singletonList("sntest")));
            attributes.add(AttributeBuilder.build("givenName", Collections.singletonList("gntest")));
            attributes.add(AttributeBuilder.build("displayName", Collections.singletonList("dntest")));
            attributes.add(AttributeBuilder.build("ldapGroups",
                    "CN=test1grp,ou=test1," + BASE_CONTEXT,
                    "CN=test2grp,ou=test2," + BASE_CONTEXT,
                    "CN=groupsgrp,ou=groups,ou=test1," + BASE_CONTEXT,
                    "CN=excludedgrp,ou=excludedgrps,ou=test1," + BASE_CONTEXT,
                    "CN=usersgrp,ou=users,ou=test1," + BASE_CONTEXT));

            final Uid uid = newConnector.create(ObjectClass.ACCOUNT, attributes, null);
            assertNotNull(uid);
            // -----------------------------------------------------

            final ConnectorObject obj = newConnector.getObject(ObjectClass.ACCOUNT, uid, oob.build());

            assertEquals(1, obj.getAttributeByName(ADConnector.MEMBEROF).getValue().size(), 0);
            assertEquals("cn=groupsgrp,ou=groups,ou=test1," + BASE_CONTEXT.toLowerCase(),
                    obj.getAttributeByName(ADConnector.MEMBEROF).getValue().get(0).toString().toLowerCase());

            assertEquals(1, obj.getAttributeByName(LdapConstants.LDAP_GROUPS_NAME).getValue().size(), 0);
            assertEquals("cn=groupsgrp,ou=groups,ou=test1," + BASE_CONTEXT.toLowerCase(),
                    obj.getAttributeByName(LdapConstants.LDAP_GROUPS_NAME).getValue().get(0).toString().toLowerCase());

        } catch (NamingException e) {
            LOG.error(e, "Error setting-up");
            fail();
        } finally {
            try {
                // clean all ....
                newConnector.delete(ObjectClass.ACCOUNT, new Uid("SAAN_AD40"), null);
                ctx.destroySubcontext("CN=usersgrp,ou=users,ou=test1," + BASE_CONTEXT);
                ctx.destroySubcontext("ou=users,ou=test1," + BASE_CONTEXT);
                ctx.destroySubcontext("CN=groupsgrp,ou=groups,ou=test1," + BASE_CONTEXT);
                ctx.destroySubcontext("ou=groups,ou=test1," + BASE_CONTEXT);
                ctx.destroySubcontext("CN=excludedgrp,ou=excludedgrps,ou=test1," + BASE_CONTEXT);
                ctx.destroySubcontext("ou=excludedgrps,ou=test1," + BASE_CONTEXT);
                ctx.destroySubcontext("CN=test1grp,ou=test1," + BASE_CONTEXT);
                ctx.destroySubcontext("ou=test1," + BASE_CONTEXT);
                ctx.destroySubcontext("CN=test2grp,ou=test2," + BASE_CONTEXT);
                ctx.destroySubcontext("ou=test2," + BASE_CONTEXT);
            } catch (NamingException e) {
                LOG.error(e, "Error removing ad-hoc setup");
                fail();
            }
        }
    }

    @Test
    public void workWithPrimaryGroupID() {
        assertNotNull(connector);
        assertNotNull(conf);

        final String baseContext = PROP.getProperty("baseContext");

        final String before = "testBeforeGroup43";
        final String after = "testAfterGroup43";
        final String rootSuffix = String.format("CN=Users,%s", baseContext);
        final String beforeDN = String.format("CN=%s,%s", before, rootSuffix);
        final String afterDN = String.format("CN=%s,%s", after, rootSuffix);

        // create groups ....
        final ADConnection connection = new ADConnection(conf);
        final LdapContext ctx = connection.getInitialContext();

        try {
            createGrp(ctx, before, rootSuffix);
            createGrp(ctx, after, rootSuffix);

        } catch (Exception e) {
            LOG.error(e, "Error creating groups ...");

            try {
                ctx.destroySubcontext(beforeDN);
            } catch (NamingException ignore) {
            }

            try {
                ctx.destroySubcontext(afterDN);
            } catch (NamingException ignore) {
            }

            fail();
        }

        final Map.Entry<String, String> ids = util.getEntryIDs("43");

        assertNull("Please remove user 'sAMAccountName: " + ids.getValue() + "' from AD",
                connector.getObject(ObjectClass.ACCOUNT, new Uid(ids.getValue()), null));

        final Set<Attribute> attributes = util.getSimpleProfile(ids, false);

        final Attribute ldapGroups = AttributeUtil.find("ldapGroups", attributes);
        attributes.remove(ldapGroups);

        final List<String> groupsToBeAdded = new ArrayList<String>();

        if (ldapGroups != null && ldapGroups.getValue() != null) {
            for (Object obj : ldapGroups.getValue()) {
                groupsToBeAdded.add(obj.toString());
            }
        }

        groupsToBeAdded.add(beforeDN);
        groupsToBeAdded.add(afterDN);

        attributes.add(AttributeBuilder.build(LdapConstants.LDAP_GROUPS_NAME, groupsToBeAdded));

        // specify primary group
        attributes.add(AttributeBuilder.build(ADConfiguration.PRIMARY_GROUP_DN_NAME, beforeDN));

        Uid uid = connector.create(ObjectClass.ACCOUNT, attributes, null);
        assertNotNull(uid);
        assertEquals(ids.getValue(), uid.getUidValue());

        try {
            // Ask for ldapGroups and primary group info
            final OperationOptionsBuilder oob = new OperationOptionsBuilder();
            oob.setAttributesToGet(
                    LdapConstants.LDAP_GROUPS_NAME,
                    ADConnector.PRIMARYGROUPID,
                    ADConfiguration.PRIMARY_GROUP_DN_NAME);

            // retrieve created object
            ConnectorObject object = connector.getObject(ObjectClass.ACCOUNT, uid, oob.build());

            // check for memberOf attribute
            assertNotNull(object);
            assertNotNull(object.getAttributes());

            // Returned attributes: memberOf, NAME and UID
            assertEquals(5, object.getAttributes().size());
            assertNotNull(object.getAttributeByName(LdapConstants.LDAP_GROUPS_NAME));
            assertTrue(object.getAttributeByName(LdapConstants.LDAP_GROUPS_NAME).getValue().contains(beforeDN));
            assertTrue(object.getAttributeByName(LdapConstants.LDAP_GROUPS_NAME).getValue().contains(afterDN));

            final Attribute primaryGroupId = object.getAttributeByName(ADConnector.PRIMARYGROUPID);

            assertNotNull(primaryGroupId);
            assertEquals(beforeDN, object.getAttributeByName(ADConfiguration.PRIMARY_GROUP_DN_NAME).getValue().get(0));

            List<Attribute> attrToReplace = Arrays.asList(new Attribute[] { AttributeBuilder.build(
                ADConfiguration.PRIMARY_GROUP_DN_NAME, afterDN) });

            uid = connector.update(
                    ObjectClass.ACCOUNT,
                    uid,
                    new HashSet<Attribute>(attrToReplace),
                    null);

            assertNotNull(uid);
            assertEquals(ids.getValue(), uid.getUidValue());

            object = connector.getObject(ObjectClass.ACCOUNT, uid, oob.build());

            assertTrue(object.getAttributeByName(LdapConstants.LDAP_GROUPS_NAME).getValue().contains(beforeDN));
            assertTrue(object.getAttributeByName(LdapConstants.LDAP_GROUPS_NAME).getValue().contains(afterDN));

            assertNotEquals(primaryGroupId, object.getAttributeByName(ADConnector.PRIMARYGROUPID));
            assertEquals(afterDN, object.getAttributeByName(ADConfiguration.PRIMARY_GROUP_DN_NAME).getValue().get(0));
        } finally {
            connector.delete(ObjectClass.ACCOUNT, uid, null);
            assertNull(connector.getObject(ObjectClass.ACCOUNT, uid, null));

            try {
                ctx.destroySubcontext(beforeDN);
            } catch (NamingException ignore) {
            }

            try {
                ctx.destroySubcontext(afterDN);
            } catch (NamingException ignore) {
            }
        }
    }

    @Test
    public void conservativeMembershipPolicy() {
        assertNotNull(connector);
        assertNotNull(conf);

        final ADConfiguration newconf = getSimpleConf(PROP);
        newconf.setMembershipConservativePolicy(true);

        final String baseContext = PROP.getProperty("baseContext");

        final String first = "testFirstGroup44";
        final String second = "testSecondGroup44";
        final String third = "testThirdGroup44";
        final String rootSuffix = String.format("CN=Users,%s", baseContext);
        final String firstDN = String.format("CN=%s,%s", first, rootSuffix);
        final String secondDN = String.format("CN=%s,%s", second, rootSuffix);
        final String thirdDN = String.format("CN=%s,%s", third, rootSuffix);

        // create groups ....
        final ADConnection connection = new ADConnection(conf);
        final LdapContext ctx = connection.getInitialContext();

        try {
            createGrp(ctx, first, rootSuffix);
            createGrp(ctx, second, rootSuffix);
            createGrp(ctx, third, rootSuffix);

        } catch (Exception e) {
            LOG.error(e, "Error creating groups ...");

            try {
                ctx.destroySubcontext(firstDN);
            } catch (NamingException ignore) {
            }

            try {
                ctx.destroySubcontext(secondDN);
            } catch (NamingException ignore) {
            }

            try {
                ctx.destroySubcontext(thirdDN);
            } catch (NamingException ignore) {
            }

            fail();
        }

        final Map.Entry<String, String> ids = util.getEntryIDs("44");

        assertNull("Please remove user 'sAMAccountName: " + ids.getValue() + "' from AD",
                connector.getObject(ObjectClass.ACCOUNT, new Uid(ids.getValue()), null));

        final Set<Attribute> attributes = util.getSimpleProfile(ids, false);

        Attribute ldapGroups = AttributeUtil.find("ldapGroups", attributes);
        attributes.remove(ldapGroups);

        final List<String> groupsToBeAdded = new ArrayList<String>();

        if (ldapGroups != null && ldapGroups.getValue() != null) {
            for (Object obj : ldapGroups.getValue()) {
                groupsToBeAdded.add(obj.toString());
            }
        }

        groupsToBeAdded.add(firstDN);
        groupsToBeAdded.add(secondDN);

        attributes.add(AttributeBuilder.build(LdapConstants.LDAP_GROUPS_NAME, groupsToBeAdded));

        // specify primary group
        attributes.add(AttributeBuilder.build(ADConfiguration.PRIMARY_GROUP_DN_NAME, firstDN));

        Uid uid = connector.create(ObjectClass.ACCOUNT, attributes, null);
        assertNotNull(uid);
        assertEquals(ids.getValue(), uid.getUidValue());

        try {
            // Ask for ldapGroups and primary group info
            final OperationOptionsBuilder oob = new OperationOptionsBuilder();
            oob.setAttributesToGet(
                    LdapConstants.LDAP_GROUPS_NAME,
                    ADConnector.PRIMARYGROUPID,
                    ADConfiguration.PRIMARY_GROUP_DN_NAME);

            // retrieve created object
            ConnectorObject object = connector.getObject(ObjectClass.ACCOUNT, uid, oob.build());

            // check for memberOf attribute
            assertNotNull(object);
            assertNotNull(object.getAttributes());

            // Returned attributes: memberOf, NAME and UID
            assertEquals(5, object.getAttributes().size());
            assertNotNull(object.getAttributeByName(LdapConstants.LDAP_GROUPS_NAME));
            assertTrue(object.getAttributeByName(LdapConstants.LDAP_GROUPS_NAME).getValue().contains(firstDN));
            assertTrue(object.getAttributeByName(LdapConstants.LDAP_GROUPS_NAME).getValue().contains(secondDN));

            final Attribute primaryGroupId = object.getAttributeByName(ADConnector.PRIMARYGROUPID);

            assertNotNull(primaryGroupId);
            assertEquals(firstDN, object.getAttributeByName(ADConfiguration.PRIMARY_GROUP_DN_NAME).getValue().get(0));

            final List<Attribute> attrToReplace = new ArrayList<Attribute>();

            final List<String> groupsToBeUpdated = new ArrayList<String>();

            groupsToBeUpdated.add(secondDN);
            groupsToBeUpdated.add(thirdDN);

            attrToReplace.add(AttributeBuilder.build(ADConfiguration.PRIMARY_GROUP_DN_NAME, secondDN));
            attrToReplace.add(AttributeBuilder.build(LdapConstants.LDAP_GROUPS_NAME, groupsToBeUpdated));

            uid = connector.update(
                    ObjectClass.ACCOUNT,
                    uid,
                    new HashSet<Attribute>(attrToReplace),
                    null);

            assertNotNull(uid);
            assertEquals(ids.getValue(), uid.getUidValue());

            object = connector.getObject(ObjectClass.ACCOUNT, uid, oob.build());

            assertTrue(object.getAttributeByName(LdapConstants.LDAP_GROUPS_NAME).getValue().contains(firstDN));
            assertTrue(object.getAttributeByName(LdapConstants.LDAP_GROUPS_NAME).getValue().contains(secondDN));
            assertTrue(object.getAttributeByName(LdapConstants.LDAP_GROUPS_NAME).getValue().contains(thirdDN));

            assertNotEquals(primaryGroupId, object.getAttributeByName(ADConnector.PRIMARYGROUPID));
            assertEquals(secondDN, object.getAttributeByName(ADConfiguration.PRIMARY_GROUP_DN_NAME).getValue().get(0));
        } finally {
            connector.delete(ObjectClass.ACCOUNT, uid, null);
            assertNull(connector.getObject(ObjectClass.ACCOUNT, uid, null));

            try {
                ctx.destroySubcontext(firstDN);
            } catch (NamingException ignore) {
            }

            try {
                ctx.destroySubcontext(secondDN);
            } catch (NamingException ignore) {
            }

            try {
                ctx.destroySubcontext(thirdDN);
            } catch (NamingException ignore) {
            }
        }
    }

    @Test
    public void issueAD58() {
        assertNotNull(connector);
        assertNotNull(conf);

        final Map.Entry<String, String> ids = util.getEntryIDs("AD58");

        assertNull("Please remove user 'sAMAccountName: " + ids.getValue() + "' from AD",
                connector.getObject(ObjectClass.ACCOUNT, new Uid(ids.getValue()), null));

        final Set<Attribute> attributes = util.getSimpleProfile(ids);

        // include sAMAccountName because dn in in NAME not sAMAccountName
        final Attribute attr = AttributeUtil.find(conf.getUidAttribute(), attributes);
        if (attr == null) {
            attributes.add(AttributeBuilder.build(conf.getUidAttribute(), ids.getKey()));
        }

        final Uid uid = connector.create(ObjectClass.ACCOUNT, attributes, null);
        assertNotNull(uid);
        assertEquals(ids.getValue(), uid.getUidValue());

        // Ask just for sAMAccountName
        final OperationOptionsBuilder oob = new OperationOptionsBuilder();
        oob.setAttributesToGet(Arrays.asList("sAMAccountName"));

        // retrieve created object
        final ConnectorObject object = connector.getObject(ObjectClass.ACCOUNT, uid, oob.build());

        // Returned attributes: sAMAccountName, NAME and UID
        assertEquals(ids.getValue(), object.getAttributeByName("sAMAccountName").getValue().get(0));

        connector.delete(ObjectClass.ACCOUNT, uid, null);
        assertNull(connector.getObject(ObjectClass.ACCOUNT, uid, null));
    }

    @Test
    public void issueAD58WithoutDN() {
        assertNotNull(connector);
        assertNotNull(conf);

        final Map.Entry<String, String> ids = util.getEntryIDs("AD58NN");

        assertNull("Please remove user 'sAMAccountName: " + ids.getValue() + "' from AD",
                connector.getObject(ObjectClass.ACCOUNT, new Uid(ids.getValue()), null));

        final Set<Attribute> attributes = util.getSimpleProfile(ids, false);

        // remove sAMAccountName because sAMAccountName value is embedded into NAME
        final Attribute attr = AttributeUtil.find(conf.getUidAttribute(), attributes);
        attributes.remove(attr);

        final Uid uid = connector.create(ObjectClass.ACCOUNT, attributes, null);
        assertNotNull(uid);
        assertEquals(ids.getValue(), uid.getUidValue());

        // Ask just for sAMAccountName
        final OperationOptionsBuilder oob = new OperationOptionsBuilder();
        oob.setAttributesToGet(Arrays.asList("sAMAccountName"));

        // retrieve created object
        final ConnectorObject object = connector.getObject(ObjectClass.ACCOUNT, uid, oob.build());

        // Returned attributes: sAMAccountName, NAME and UID
        assertEquals(ids.getValue(), object.getAttributeByName("sAMAccountName").getValue().get(0));

        connector.delete(ObjectClass.ACCOUNT, uid, null);
        assertNull(connector.getObject(ObjectClass.ACCOUNT, uid, null));
    }

}
