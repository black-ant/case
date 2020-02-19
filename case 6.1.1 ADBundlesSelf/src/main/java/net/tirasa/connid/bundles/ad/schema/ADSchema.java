/**
 * Copyright (C) 2011 ConnId (connid-dev@googlegroups.com)
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package net.tirasa.connid.bundles.ad.schema;

import net.tirasa.connid.bundles.ad.ADConnection;
import org.identityconnectors.framework.common.objects.Schema;

public class ADSchema {

    private final ADConnection connection;

    private Schema schema;

    public ADSchema(final ADConnection connection) {
        this.connection = connection;
    }

    public Schema getSchema() {
        if (schema == null) {
            schema = new ADSchemaBuilder(connection).getSchema();
        }

        return schema;
    }
}
