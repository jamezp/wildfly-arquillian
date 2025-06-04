/*
 * Copyright The WildFly Authors
 * SPDX-License-Identifier: Apache-2.0
 */
package org.jboss.as.arquillian.container.remote;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.URL;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.OperateOnDeployment;
import org.jboss.arquillian.junit5.ArquillianExtension;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.as.arquillian.container.remote.servlet.Servlet1;
import org.jboss.as.arquillian.container.remote.servlet.Servlet2;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.StringAsset;
import org.jboss.shrinkwrap.api.spec.EnterpriseArchive;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

/**
 * Test to verify correct Protocol Metadata returned from Deployment
 *
 * @author <a href="aslak@redhat.com">Aslak Knutsen</a>
 */
@ExtendWith(ArquillianExtension.class)
public class ServletClientTestCase {

    @Deployment(name = "war", testable = false)
    public static WebArchive createWarDeployment() throws Exception {
        return ShrinkWrap.create(WebArchive.class).addClass(Servlet1.class);
    }

    @Deployment(name = "ear", testable = false)
    public static EnterpriseArchive createEarDeployment() throws Exception {
        return ShrinkWrap
                .create(EnterpriseArchive.class)
                .addAsModule(
                        ShrinkWrap.create(WebArchive.class).addClass(
                                Servlet1.class))
                .addAsModule(
                        ShrinkWrap.create(WebArchive.class).addClass(
                                Servlet2.class));
    }

    @Deployment(name = "war-no-servlet", testable = false)
    public static WebArchive createNoContextWebDeployment() {
        return ShrinkWrap.create(WebArchive.class)
                .addAsWebResource(new StringAsset("JSP"), "index.jsp");
    }

    @Test
    @OperateOnDeployment("war")
    public void shouldBeAbleToLookupServletURLInAWar(@OperateOnDeployment("war") @ArquillianResource URL baseURL)
            throws Exception {
        Assertions.assertNotNull(baseURL,
                "Should have injected Base URL for deployed WebContext");

        Assertions.assertEquals(Servlet1.class.getName(), getContent(new URL(baseURL, Servlet1.PATTERN)));
    }

    @Test
    @OperateOnDeployment("ear")
    public void shouldBeAbleToLookupServletURLInAEar(
            @OperateOnDeployment("ear") @ArquillianResource(Servlet1.class) URL servlet1BaseURL,
            @OperateOnDeployment("ear") @ArquillianResource(Servlet2.class) URL servlet2BaseURL) throws Exception {

        Assertions.assertNotNull(servlet1BaseURL,
                "Should have injected Base URL for deployed WebContext");
        Assertions.assertEquals(Servlet1.class.getName(), getContent(new URL(servlet1BaseURL, Servlet1.PATTERN)));

        Assertions.assertNotNull(servlet2BaseURL,
                "Should have injected Base URL for deployed WebContext");
        Assertions.assertEquals(Servlet2.class.getName(), getContent(new URL(servlet2BaseURL, Servlet2.PATTERN)));
    }

    @Test
    @OperateOnDeployment("war-no-servlet")
    public void shouldBeAbleToDeployWarWithNoServlets(@OperateOnDeployment("war-no-servlet") @ArquillianResource URL baseURL)
            throws Exception {
        Assertions.assertNotNull(baseURL,
                "Should have injected Base URL for deployed WebContext");

        Assertions.assertEquals("JSP", getContent(new URL(baseURL, "index.jsp")));
    }

    private String getContent(URL url) throws Exception {
        InputStream is = url.openStream();
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try {
            int read;
            while ((read = is.read()) != -1) {
                out.write(read);
            }
        } finally {
            try {
                is.close();
            } catch (Exception e) {
            }
        }
        return out.toString();
    }
}
