/*
 * Copyright 2021 Red Hat, Inc.
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

package org.wildfly.arquillian.subsystem;import static org.jboss.as.controller.OperationContext.Stage.RUNTIME;
import static org.jboss.as.server.deployment.Phase.DEPENDENCIES;

import java.util.Collection;
import java.util.Collections;

import org.jboss.as.controller.AbstractBoottimeAddStepHandler;
import org.jboss.as.controller.AttributeDefinition;
import org.jboss.as.controller.ModelOnlyRemoveStepHandler;
import org.jboss.as.controller.OperationContext;
import org.jboss.as.controller.OperationFailedException;
import org.jboss.as.controller.PersistentResourceDefinition;
import org.jboss.as.controller.SimpleResourceDefinition;
import org.jboss.as.controller.capability.RuntimeCapability;
import org.jboss.as.controller.registry.ManagementResourceRegistration;
import org.jboss.as.server.AbstractDeploymentChainStep;
import org.jboss.as.server.DeploymentProcessorTarget;
import org.jboss.dmr.ModelNode;

/**
 * @author <a href="mailto:jperkins@redhat.com">James R. Perkins</a>
 */
public class ArquillianSubsystemDefinition extends PersistentResourceDefinition {

    private static final String SUBSYSTEM_CAPABILITY_NAME = "org.wildfly.arquillian.extension";
    private static final String UNDERTOW_CAPABILITY_NAME = "org.wildfly.undertow";

    private static final RuntimeCapability<Void> CONTEXT_PROPAGATION_CAPABILITY = RuntimeCapability.Builder
            .of(SUBSYSTEM_CAPABILITY_NAME)
            .addRequirements(UNDERTOW_CAPABILITY_NAME)
            .build();

    public ArquillianSubsystemDefinition() {
        super(
                new SimpleResourceDefinition.Parameters(
                        ArquillianSubsystemExtension.SUBSYSTEM_PATH,
                        ArquillianSubsystemExtension.getResourceDescriptionResolver(ArquillianSubsystemExtension.SUBSYSTEM_NAME))
                .setAddHandler(AddHandler.INSTANCE)
                .setRemoveHandler(new ModelOnlyRemoveStepHandler())
                .setCapabilities(CONTEXT_PROPAGATION_CAPABILITY)
        );
    }

    @Override
    public Collection<AttributeDefinition> getAttributes() {
        return Collections.emptyList();
    }

    @Override
    public void registerAdditionalRuntimePackages(ManagementResourceRegistration resourceRegistration) {
        super.registerAdditionalRuntimePackages(resourceRegistration);
        //TODO - If your feature-pack needs any other modules, you should add those here, and remove the line above
        /*
        resourceRegistration.registerAdditionalRuntimePackages(
                // Required dependencies are always added
                RuntimePackageDependency.required("my.required.module"),
                // Optional and passive modules depend on the 'optional-packages' mode. See the Galleon
                // documentation for more details as this is an advanced feature
                RuntimePackageDependency.optional("my.optional.module"),
                RuntimePackageDependency.passive("my.passive.module")
        );*/
    }

    static class AddHandler extends AbstractBoottimeAddStepHandler {

        static AddHandler INSTANCE = new AddHandler();

        private AddHandler() {
            super(Collections.emptyList());
        }

        @Override
        protected void performBoottime(OperationContext context, ModelNode operation, ModelNode model) throws OperationFailedException {
            super.performBoottime(context, operation, model);

            context.addStep(new AbstractDeploymentChainStep() {
                public void execute(DeploymentProcessorTarget processorTarget) {
                    final int DEPENDENCIES_TEMPLATE = 6304;
                    processorTarget.addDeploymentProcessor(ArquillianSubsystemExtension.SUBSYSTEM_NAME, DEPENDENCIES, DEPENDENCIES_TEMPLATE, new DependencyProcessor());
                }
            }, RUNTIME);

            TemplateLogger.LOGGER.activatingSubsystem();
        }
    }
}
