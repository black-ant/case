package net.tirasa.connid.bundles.ad.schema;

import net.tirasa.connid.bundles.ldap.LdapConnection;
import net.tirasa.connid.bundles.ldap.schema.LdapSchemaMapping;

/**
 * @Classname ADSchemaMapping
 * @Description TODO
 * @Date 2019/12/4 18:52
 * @Created by zengzg
 */
public class ADSchemaMapping extends LdapSchemaMapping {

    public ADSchemaMapping(LdapConnection conn) {
        super(conn);
    }
}
