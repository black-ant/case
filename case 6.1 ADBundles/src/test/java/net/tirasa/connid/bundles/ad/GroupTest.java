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

import org.identityconnectors.framework.common.objects.ObjectClass;
import org.junit.AfterClass;
import org.junit.BeforeClass;

public class GroupTest extends AbstractTest {

    protected static TestUtil util;

    @BeforeClass
    public static void init() {
        AbstractTest.init();
        util = new TestUtil(connector, conf, ObjectClass.GROUP, BASE_CONTEXT);
        AbstractTest.baseSetup(util);
    }

    @AfterClass
    public static void cleanup() {
        AbstractTest.cleanup(util);
    }
}
