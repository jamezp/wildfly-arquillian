/*
 * Copyright The WildFly Authors
 * SPDX-License-Identifier: Apache-2.0
 */

package org.wildfly.arquillian.integration.test.junit5;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.as.arquillian.api.ServerSetup;
import org.jboss.as.arquillian.api.ServerSetupTask;
import org.jboss.as.arquillian.container.ManagementClient;
import org.jboss.as.arquillian.container.managed.InjectType;
import org.jboss.as.arquillian.container.managed.StringType;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.wildfly.arquillian.junit.annotations.WildFlyArquillian;

/**
 * @author <a href="mailto:jperkins@redhat.com">James R. Perkins</a>
 */
@WildFlyArquillian
@RunAsClient
@InjectType("org.jboss.as.arquillian.container.managed.StringType")
@ServerSetup(SimpleTypeTest.MySetupTask.class)
public class SimpleTypeTest {
    private static StringType CHECKER;

    public static class MySetupTask implements ServerSetupTask {

        @ArquillianResource
        private StringType stringType;

        @Override
        public void setup(final ManagementClient managementClient, final String containerId) throws Exception {
            CHECKER = stringType;
        }

        @Override
        public void tearDown(final ManagementClient managementClient, final String containerId) throws Exception {

        }
    }

    @ArquillianResource
    private StringType stringType;

    @Deployment
    public static WebArchive create() {
        return ShrinkWrap.create(WebArchive.class)
                .addClass(GreeterServlet.class);
    }

    @Test
    public void checkType() {
        Assertions.assertNotNull(stringType);
        Assertions.assertNotNull(CHECKER);
        Assertions.assertEquals(CHECKER, stringType);
    }
}
