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
package net.tirasa.connid.bundles.ad.schema;

import static net.tirasa.connid.bundles.ad.ADConnector.OBJECTGUID;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;
import javax.naming.ldap.LdapContext;
import net.tirasa.connid.bundles.ad.ADConfiguration;
import net.tirasa.connid.bundles.ad.ADConnection;
import net.tirasa.connid.bundles.ad.ADConnector;
import net.tirasa.connid.bundles.ldap.search.LdapInternalSearch;
import org.identityconnectors.common.StringUtil;
import org.identityconnectors.common.logging.Log;
import org.identityconnectors.framework.common.objects.AttributeInfo;
import org.identityconnectors.framework.common.objects.AttributeInfo.Flags;
import org.identityconnectors.framework.common.objects.AttributeInfoBuilder;
import org.identityconnectors.framework.common.objects.ObjectClass;
import org.identityconnectors.framework.common.objects.ObjectClassInfo;
import org.identityconnectors.framework.common.objects.ObjectClassInfoBuilder;
import org.identityconnectors.framework.common.objects.Schema;
import org.identityconnectors.framework.common.objects.SchemaBuilder;

class ADSchemaBuilder {

    private static final Log LOG = Log.getLog(ADSchemaBuilder.class);

    private final ADConnection connection;

    private Schema schema;

    private static final String[] ATTRIBUTES_TO_GET = {
        "maycontain",
        "systemmaycontain",
        "mustcontain",
        "systemmustcontain" };

    public ADSchemaBuilder(final ADConnection connection) {
        this.connection = connection;
    }

    public Schema getSchema() {
        if (schema == null) {
            buildSchema();
        }
        return schema;
    }

    private void buildSchema() {
        final SchemaBuilder schemaBld = new SchemaBuilder(ADConnector.class);
        build(ObjectClass.ACCOUNT_NAME, schemaBld);
        build(ObjectClass.GROUP_NAME, schemaBld);
        build(ObjectClass.ALL_NAME, schemaBld);
        schema = schemaBld.build();
    }

    private void build(final String oname, final SchemaBuilder schemaBld) {
        final ADConfiguration conf = (ADConfiguration) connection.getConfiguration();

        final StringBuilder filter = new StringBuilder();

        // -----------------------------------
        // Create search control
        // -----------------------------------
        final SearchControls searchCtls = LdapInternalSearch.createDefaultSearchControls();

        searchCtls.setSearchScope(SearchControls.SUBTREE_SCOPE);

        searchCtls.setReturningAttributes(ATTRIBUTES_TO_GET);
        // -----------------------------------

        // -----------------------------------
        // Specify filter
        // -----------------------------------
        for (String oclass : oname.equalsIgnoreCase(ObjectClass.ACCOUNT_NAME)
                ? conf.getAccountObjectClasses()
                : oname.equalsIgnoreCase(ObjectClass.GROUP_NAME)
                ? conf.getGroupObjectClasses()
                : new String[] { oname }) {
            filter.append("(lDAPDisplayName=").append(oclass).append(")");
        }

        filter.insert(0, "(&(|").append(")(objectClass=classSchema))");
        // -----------------------------------

        final LdapContext ctx = connection.getInitialContext();

        final Set<String> schemaNames = new HashSet<String>();

        // Issue http://code.google.com/p/connid/issues/detail?id=24
        if (oname.equalsIgnoreCase(ObjectClass.ACCOUNT_NAME)) {
            schemaNames.add(conf.getUidAttribute());
        } else if (oname.equalsIgnoreCase(ObjectClass.GROUP_NAME)) {
            schemaNames.add(conf.getGidAttribute());
        } else {
            schemaNames.add(OBJECTGUID);
        }

        final String schemaConfigurationPath = "CN=Schema,CN=Configuration";

        for (String suffix : conf.getBaseContextsToSynchronize()) {
            try {
                final NamingEnumeration<SearchResult> oclasses = ctx.search(
                        schemaConfigurationPath + "," + suffix,
                        filter.toString(),
                        searchCtls);

                while (oclasses.hasMoreElements()) {
                    final SearchResult oclass = oclasses.next();
                    schemaNames.addAll(getObjectSchemaNames(oclass));
                }
            } catch (NamingException e) {
                LOG.error(e, "Error retrieving schema names from {0}", suffix);
            }
        }

        schemaNames.remove(ADConnector.SDDL_ATTR);
        schemaNames.remove(ADConfiguration.LOCK_OUT_FLAG);
        schemaNames.remove(ADConfiguration.PROMPT_USER_FLAG);
        schemaNames.remove(ADConfiguration.PRIMARY_GROUP_DN_NAME);

        final ObjectClassInfoBuilder objClassBld = new ObjectClassInfoBuilder();

        // ObjectClass.ACCOUNT/ObjectClass.GROUP
        objClassBld.setType(oname);
        objClassBld.setContainer(false);
        objClassBld.addAllAttributeInfo(createAttrInfos(schemaNames));

        objClassBld.addAttributeInfo(AttributeInfoBuilder.build(ADConfiguration.UCCP_FLAG, Boolean.class));
        objClassBld.addAttributeInfo(AttributeInfoBuilder.build(ADConfiguration.LOCK_OUT_FLAG, Boolean.class));
        objClassBld.addAttributeInfo(AttributeInfoBuilder.build(ADConfiguration.PROMPT_USER_FLAG, Boolean.class));
        objClassBld.addAttributeInfo(AttributeInfoBuilder.build(ADConfiguration.PRIMARY_GROUP_DN_NAME, String.class));

        final ObjectClassInfo oci = objClassBld.build();
        schemaBld.defineObjectClass(oci);
    }

    private Set<String> getObjectSchemaNames(final SearchResult oclass)
            throws NamingException {

        final Set<String> schemaNames = new HashSet<String>();

        for (String attrName : ATTRIBUTES_TO_GET) {
            final Attribute attr = oclass.getAttributes().get(attrName);

            if (attr != null) {
                final NamingEnumeration<?> en = attr.getAll();
                while (en.hasMoreElements()) {
                    final String elem = (String) en.nextElement();

                    if (StringUtil.isNotBlank(elem)) {
                        schemaNames.add(elem.trim());
                    }
                }
            }
        }

        return schemaNames;
    }

    private List<AttributeInfo> createAttrInfos(final Set<String> schemaNames) {

        final List<AttributeInfo> infos = new ArrayList<AttributeInfo>();

        for (String schemaName : schemaNames) {
            infos.add(handleAttribute(schemaName));
        }

        return infos;
    }

    private AttributeInfo handleAttribute(final String displayName) {
        final String IS_SINGLE_VALUE = "isSingleValued";
        final String SYSTEM_ONLY = "systemOnly";
        final String LDAP_DISPLAY_NAME = "lDAPDisplayName";

        final Set<Flags> flags = EnumSet.noneOf(Flags.class);

        boolean binary = connection.isBinarySyntax(displayName);

        boolean objectClass = displayName == null || "objectClass".equalsIgnoreCase(displayName);

        final LdapContext ctx = connection.getInitialContext();

        final String[] baseContexts = connection.getConfiguration().getBaseContextsToSynchronize();

        // ------------------------------
        // Search control
        // ------------------------------
        final SearchControls searchCtls = LdapInternalSearch.createDefaultSearchControls();
        searchCtls.setSearchScope(SearchControls.SUBTREE_SCOPE);
        searchCtls.setReturningAttributes(new String[] { IS_SINGLE_VALUE, SYSTEM_ONLY });
        // ------------------------------

        int i = 0;
        Attributes attributes = null;

        while (attributes == null && i < baseContexts.length) {

            final StringBuilder dn = new StringBuilder();

            dn.append("cn=schema, cn=configuration,").append(baseContexts[i]);

            try {

                final NamingEnumeration<SearchResult> result = ctx.search(
                        dn.toString(),
                        LDAP_DISPLAY_NAME + "=" + displayName,
                        searchCtls);

                if (result != null && result.hasMoreElements()) {
                    attributes = result.next().getAttributes();
                }

            } catch (NamingException e) {
                LOG.error(e, "Error retrieving attributes for {0}", dn);
            }

            i++;
        }

        if (attributes != null) {
            final Attribute isSingle = attributes.get(IS_SINGLE_VALUE);

            try {
                if (isSingle == null || isSingle.get() == null || "false".equalsIgnoreCase(isSingle.get().toString())) {
                    flags.add(Flags.MULTIVALUED);
                }
            } catch (NamingException e) {
                LOG.error(e, "Failure retrieving attribute " + IS_SINGLE_VALUE);
            }

            final Attribute systemOnly = attributes.get(SYSTEM_ONLY);
            try {
                if ((systemOnly != null
                        && systemOnly.get() != null
                        && "true".equalsIgnoreCase(systemOnly.get().toString()))
                        || objectClass) {
                    flags.add(Flags.NOT_CREATABLE);
                    flags.add(Flags.NOT_UPDATEABLE);
                }
            } catch (NamingException e) {
                LOG.error(e, "Failure retrieving attribute " + SYSTEM_ONLY);
            }
        }

        return AttributeInfoBuilder.build(
                displayName,
                binary ? byte[].class : String.class,
                flags);
    }
}
