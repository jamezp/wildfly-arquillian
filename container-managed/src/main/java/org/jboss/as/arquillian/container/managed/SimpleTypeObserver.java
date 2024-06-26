/*
 * Copyright The WildFly Authors
 * SPDX-License-Identifier: Apache-2.0
 */

package org.jboss.as.arquillian.container.managed;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import org.jboss.arquillian.core.api.InstanceProducer;
import org.jboss.arquillian.core.api.annotation.ApplicationScoped;
import org.jboss.arquillian.core.api.annotation.Inject;
import org.jboss.arquillian.core.api.annotation.Observes;
import org.jboss.arquillian.test.spi.event.suite.BeforeClass;

/**
 * @author <a href="mailto:jperkins@redhat.com">James R. Perkins</a>
 */
public class SimpleTypeObserver {

    @Inject
    @ApplicationScoped
    private InstanceProducer<SimpleType<?>> instance;

    public void beforeClass(@Observes final BeforeClass beforeClass) {
        final var testClass = beforeClass.getTestClass();
        if (testClass.isAnnotationPresent(InjectType.class)) {
            final InjectType injectType = testClass.getAnnotation(InjectType.class);
            try {
                final Class<?> c = Class.forName(injectType.value());
                if (!SimpleType.class.isAssignableFrom(c)) {
                    throw new IllegalArgumentException("SimpleType " + c.getName() + " is not a SimpleType");
                }
                final Constructor<?> constructor = c.getConstructor();
                instance.set((SimpleType<?>) constructor.newInstance());
            } catch (ClassNotFoundException | NoSuchMethodException | InstantiationException | IllegalAccessException
                    | InvocationTargetException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
