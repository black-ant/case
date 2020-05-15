package com.gang.study.adtest.demo.output;

import com.gang.study.adtest.demo.to.ADConfigInfoTO;
import net.tirasa.connid.bundles.ad.ADConfiguration;
import net.tirasa.connid.bundles.ad.ADConnection;
import net.tirasa.connid.bundles.ad.schema.ADSchema;
import net.tirasa.connid.bundles.ldap.search.LdapInternalSearch;
import org.identityconnectors.common.StringUtil;
import org.identityconnectors.framework.common.objects.Schema;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;
import javax.naming.ldap.LdapContext;
import java.util.HashSet;
import java.util.Set;


/**
 * @Classname ADBundlesController
 * @Description TODO
 * @Date 2020/5/14 15:56
 * @Created by zengzg
 */
@Component
public class ADBundlesLogic {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private static final String[] ATTRIBUTES_TO_GET = {
            "maycontain",
            "systemmaycontain",
            "mustcontain",
            "systemmustcontain"};

    @Autowired
    private ADBaseInfo adBaseInfo;

    public void test(ADConfigInfoTO abstractConfigTO) {
        ADConfiguration configItem = adBaseInfo.getConfiguration(abstractConfigTO);
        //        logger.info("------> this is config :{} <-------", configItem);
        //
        //        String map = configItem.getGroupOwnerReferenceAttribute();
        //        logger.info("------> map :{} <-------", map);
        ADConnection adConnection = new ADConnection(configItem);

        //        ADSchema builder = new ADSchema(adConnection);
        //        Schema schema = builder.getSchema();
        //        logger.info("------> schema :{} <-------", schema);
        findSchema(adConnection);

    }

    public void findSchema(ADConnection adConnection) {

        LdapContext ctx = adConnection.getInitialContext();

        final SearchControls searchCtls = LdapInternalSearch.createDefaultSearchControls();

        searchCtls.setSearchScope(SearchControls.SUBTREE_SCOPE);

        Set<String> attr = new HashSet<>();

        try {
            final NamingEnumeration<SearchResult> oclasses = ctx.search(
                    "CN=Schema,CN=Configuration,DC=devad,DC=com,DC=cn",
                    "(&(|(lDAPDisplayName=top)(lDAPDisplayName=person)(lDAPDisplayName=organizationalPerson)" +
                            "(lDAPDisplayName=user))(objectClass=classSchema))",
                    searchCtls);

            while (oclasses.hasMoreElements()) {
                final SearchResult oclass = oclasses.next();
                attr.addAll(getObjectSchemaNames(oclass));
            }
        } catch (NamingException e) {
        }

        attr.forEach(item -> {
            logger.info("------> set attr name:{} <-------", item);
        });

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
}
