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

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.beans.SimpleBeanInfo;
import java.util.ArrayList;
import java.util.List;
import net.tirasa.connid.bundles.ldap.LdapConfiguration;
import org.identityconnectors.common.logging.Log;
import org.identityconnectors.framework.spi.AbstractConfiguration;

public class ADConfigurationBeanInfo extends SimpleBeanInfo {

    private static final Log LOG = Log.getLog(ADConfigurationBeanInfo.class);

    @Override
    public PropertyDescriptor[] getPropertyDescriptors() {
        final List<PropertyDescriptor> props = new ArrayList<PropertyDescriptor>();
        try {
            // ssl
            props.add(new PropertyDescriptor("ssl", ADConfiguration.class));

            // host
            props.add(new PropertyDescriptor("host", LdapConfiguration.class));

            // port
            props.add(new PropertyDescriptor("port", LdapConfiguration.class));

            // principal
            props.add(new PropertyDescriptor("principal", LdapConfiguration.class));

            // uidAttribute
            props.add(new PropertyDescriptor("uidAttribute", LdapConfiguration.class));

            // gidAttribute
            props.add(new PropertyDescriptor("gidAttribute", LdapConfiguration.class));

            // defaultIdAttribute
            props.add(new PropertyDescriptor("defaultIdAttribute", ADConfiguration.class));

            // credentials
            props.add(new PropertyDescriptor("credentials", LdapConfiguration.class));

            // trustAllCerts
            props.add(new PropertyDescriptor("trustAllCerts", ADConfiguration.class));

            // membershipsInOr
            props.add(new PropertyDescriptor("membershipsInOr", ADConfiguration.class));

            // pwdUpdateOnly
            props.add(new PropertyDescriptor("pwdUpdateOnly", ADConfiguration.class));

            // failover
            props.add(new PropertyDescriptor("failover", LdapConfiguration.class));

            // baseContextsToSynchronize
            props.add(new PropertyDescriptor("baseContextsToSynchronize", ADConfiguration.class));

            // userBaseContexts
            props.add(new PropertyDescriptor("userBaseContexts", ADConfiguration.class));

            // groupBaseContexts
            props.add(new PropertyDescriptor("groupBaseContexts", ADConfiguration.class));

            // baseContexts
            props.add(new PropertyDescriptor("defaultPeopleContainer", ADConfiguration.class));

            // defaultGroupContainer
            props.add(new PropertyDescriptor("defaultGroupContainer", ADConfiguration.class));

            // memberships
            props.add(new PropertyDescriptor("memberships", ADConfiguration.class));

            // membershipConservativePolicy
            props.add(new PropertyDescriptor("membershipConservativePolicy", ADConfiguration.class));

            // accountSearchFilter
            props.add(new PropertyDescriptor("accountSearchFilter", LdapConfiguration.class));

            // groupSearchFilter
            props.add(new PropertyDescriptor("groupSearchFilter", ADConfiguration.class));

            // retrieveDeletedUser
            props.add(new PropertyDescriptor("retrieveDeletedUser", ADConfiguration.class));

            // retrieveDeletedGroup
            props.add(new PropertyDescriptor("retrieveDeletedGroup", ADConfiguration.class));

            // accountObjectClasses
            props.add(new PropertyDescriptor("accountObjectClasses", LdapConfiguration.class));

            // objectClassesToSynchronize
            props.add(new PropertyDescriptor("objectClassesToSynchronize", LdapConfiguration.class));

            // _connectorMessages
            props.add(new PropertyDescriptor("connectorMessages", AbstractConfiguration.class));

            // userSearchScope
            props.add(new PropertyDescriptor("userSearchScope", ADConfiguration.class));

            // groupSearchScope
            props.add(new PropertyDescriptor("groupSearchScope", ADConfiguration.class));

            // groupOwnerReferenceAttribute
            props.add(new PropertyDescriptor("groupOwnerReferenceAttribute", ADConfiguration.class));

            // groupMemberReferenceAttribute
            props.add(new PropertyDescriptor("groupMemberReferenceAttribute", ADConfiguration.class));
            // startSyncFromToday
            props.add(new PropertyDescriptor("startSyncFromToday", ADConfiguration.class));
        } catch (IntrospectionException e) {
            LOG.error(e, "Failure retrieving properties");
            props.clear();
        }

        return props.toArray(new PropertyDescriptor[props.size()]);
    }
}
