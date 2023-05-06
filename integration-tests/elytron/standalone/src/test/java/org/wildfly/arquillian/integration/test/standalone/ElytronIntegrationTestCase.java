/*
 * Copyright 2017 Red Hat, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.wildfly.arquillian.integration.test.standalone;

import java.io.IOException;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.as.arquillian.container.ManagementClient;
import org.jboss.as.controller.client.helpers.Operations;
import org.jboss.dmr.ModelNode;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.StringAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author <a href="mailto:jperkins@redhat.com">James R. Perkins</a>
 */
@RunWith(Arquillian.class)
public class ElytronIntegrationTestCase {

    @Deployment
    public static JavaArchive deployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addAsResource(new StringAsset("Dependencies: org.jboss.dmr, org.jboss.as.controller\n"),
                        "META-INF/MANIFEST.MF");
    }

    @Test
    @RunAsClient
    public void testClientUser(@ArquillianResource ManagementClient client) throws IOException {
        final ModelNode result = client.getControllerClient().execute(Operations.createOperation("whoami"));
        Assert.assertTrue(Operations.isSuccessfulOutcome(result));
        final ModelNode identity = Operations.readResult(result);
        Assert.assertEquals("Expected the connected user to be test-admin", "test-admin",
                identity.get("identity", "username").asString());
    }

    @Test
    public void testInContainerClientUser(@ArquillianResource ManagementClient client) throws IOException {
        final ModelNode result = client.getControllerClient().execute(Operations.createOperation("whoami"));
        Assert.assertTrue(Operations.isSuccessfulOutcome(result));
        final ModelNode identity = Operations.readResult(result);
        Assert.assertEquals("Expected the connected user to be test-admin", "test-admin",
                identity.get("identity", "username").asString());
    }
}
