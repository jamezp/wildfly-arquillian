/*
 * Copyright 2018 Red Hat, Inc.
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
package org.jboss.as.arquillian.container.managed;

import javax.enterprise.inject.spi.DeploymentException;
import javax.enterprise.inject.spi.Extension;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.ShouldThrowException;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.as.arquillian.container.managed.archive.FailDeploymentExtension;
import org.jboss.as.arquillian.container.managed.archive.FailOnDeploy;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Tests basic deployment
 */
@RunWith(Arquillian.class)
public class FailedDeploymentTestCase {

    @Deployment
    @ShouldThrowException(value = DeploymentException.class, testable = true)
    public static WebArchive create() {
        return ShrinkWrap.create(WebArchive.class)
                .addClasses(FailOnDeploy.class, FailDeploymentExtension.class)
                .addAsServiceProvider(Extension.class, FailDeploymentExtension.class)
                .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml");
    }

    @Test
    public void notUsed() {
    }
}