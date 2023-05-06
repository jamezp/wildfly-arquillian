/*
 * Copyright 2020 Red Hat, Inc.
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
package org.jboss.as.arquillian.container.bootable;

import org.jboss.as.arquillian.container.CommonManagedContainerConfiguration;

/**
 * The managed container configuration
 *
 * @author jdenise@redhat.com
 * @since 3.0.0
 */
@SuppressWarnings({ "InstanceVariableMayNotBeInitialized", "unused" })
public class BootableContainerConfiguration extends CommonManagedContainerConfiguration {

    private String jarFile;
    private String installDir;

    private String javaVmArguments = System.getProperty("jboss.options");

    private String jbossArguments;

    private boolean enableAssertions = true;

    public String getJavaVmArguments() {
        return javaVmArguments;
    }

    public void setJavaVmArguments(String javaVmArguments) {
        this.javaVmArguments = javaVmArguments;
    }

    public String getJbossArguments() {
        return jbossArguments;
    }

    public void setJbossArguments(String jbossArguments) {
        this.jbossArguments = jbossArguments;
    }

    /**
     * Get the bootable jar file.
     */
    public String getJarFile() {
        return jarFile;
    }

    /**
     * Set the bootable jar file.
     */
    public void setJarFile(String jarFile) {
        this.jarFile = jarFile;
    }

    public boolean isEnableAssertions() {
        return enableAssertions;
    }

    public void setEnableAssertions(final boolean enableAssertions) {
        this.enableAssertions = enableAssertions;
    }

    public String getInstallDir() {
        return installDir;
    }

    public void setInstallDir(String installDir) {
        this.installDir = installDir;
    }
}
