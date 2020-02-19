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
package net.tirasa.connid.bundles.ad.util;

import javax.naming.ldap.Control;

public class ExtendedSearchControl implements Control {

    private static final long serialVersionUID = 4193813741963169566L;

    @Override
    public byte[] getEncodedValue() {
        return new byte[]{};
    }

    @Override
    public String getID() {
        return "1.2.840.113556.1.4.529";
    }

    @Override
    public boolean isCritical() {
        return true;
    }
}
