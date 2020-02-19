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

import net.tirasa.connid.bundles.ad.ADConfiguration;
import net.tirasa.connid.bundles.ad.AbstractTest;
import org.identityconnectors.framework.common.objects.AttributeInfo;
import org.identityconnectors.framework.common.objects.ObjectClass;
import org.identityconnectors.framework.common.objects.ObjectClassInfo;
import org.identityconnectors.framework.common.objects.Schema;
import org.junit.Test;

public class SchemaTest extends AbstractTest {

    @Test
    public void schema() {
        AbstractTest.init();

        final Schema schema = connector.schema();
        assertNotNull(schema);

        final ObjectClassInfo info = schema.findObjectClassInfo(ObjectClass.ACCOUNT_NAME);

        assertNotNull(info);

        assertNotNull(info.getAttributeInfo());
        assertFalse(info.getAttributeInfo().isEmpty());
        assertNotNull(schema.getOperationOptionInfo());

        boolean sddl = false;
        boolean givenname = false;

        for (AttributeInfo attrInfo : info.getAttributeInfo()) {
            if (ADConfiguration.UCCP_FLAG.equals(attrInfo.getName())) {
                sddl = true;
                assertEquals(Boolean.class, attrInfo.getType());
            }

            if ("givenName".equalsIgnoreCase(attrInfo.getName())) {
                givenname = true;
                assertEquals(String.class, attrInfo.getType());
            }
        }

        assertTrue(sddl && givenname);
    }
}
