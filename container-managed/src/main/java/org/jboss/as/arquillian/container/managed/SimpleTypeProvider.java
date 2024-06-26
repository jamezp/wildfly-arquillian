/*
 * Copyright The WildFly Authors
 * SPDX-License-Identifier: Apache-2.0
 */

package org.jboss.as.arquillian.container.managed;

import java.lang.annotation.Annotation;

import org.jboss.arquillian.core.api.Instance;
import org.jboss.arquillian.core.api.annotation.Inject;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.as.arquillian.container.AbstractTargetsContainerProvider;
import org.wildfly.plugin.tools.server.ServerManager;

/**
 * A provider for {@link ArquillianResource} injection of a {@link ServerManager}.
 *
 * @author <a href="mailto:jperkins@redhat.com">James R. Perkins</a>
 */
public class SimpleTypeProvider extends AbstractTargetsContainerProvider {

    @Inject
    private Instance<SimpleType<?>> simpleTypeInstance;

    @Override
    public boolean canProvide(final Class<?> type) {
        return SimpleType.class.isAssignableFrom(type);
    }

    @Override
    public Object doLookup(final ArquillianResource resource, final Annotation... qualifiers) {
        return simpleTypeInstance.get();
    }
}
