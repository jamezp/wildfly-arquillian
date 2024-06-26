/*
 * Copyright The WildFly Authors
 * SPDX-License-Identifier: Apache-2.0
 */
package org.jboss.as.arquillian.container.domain.managed;

import java.io.File;

import org.jboss.arquillian.container.spi.ConfigurationException;
import org.jboss.arquillian.container.spi.client.deployment.Validate;
import org.jboss.as.arquillian.container.domain.CommonDomainContainerConfiguration;

/**
 * @author <a href="mailto:aslak@redhat.com">Aslak Knutsen</a>
 * @version $Revision: $
 */
public class ManagedDomainContainerConfiguration extends CommonDomainContainerConfiguration {

    private String jbossHome = System.getenv("JBOSS_HOME");

    private String javaHome = System.getenv("JAVA_HOME");

    private String modulePath = System.getProperty("module.path");

    private String javaVmArguments = System.getProperty("jboss.options");

    private String jbossArguments;

    private int startupTimeoutInSeconds = 60;

    private int stopTimeoutInSeconds = 60;

    private int autoServerStartupTimeoutInSeconds = 60;

    private boolean outputToConsole = true;

    private String domainConfig = System.getProperty("jboss.domain.default.config");

    private String hostConfig = System.getProperty("jboss.host.default.config");

    private boolean allowConnectingToRunningServer = false;

    private boolean enableAssertions = true;

    private boolean setupCleanServerBaseDir = false;

    private String cleanServerBaseDir;

    public ManagedDomainContainerConfiguration() {
        // if no javaHome is set use java.home of already running jvm
        if (javaHome == null || javaHome.isEmpty()) {
            javaHome = System.getProperty("java.home");
        }
    }

    @Override
    public void validate() throws ConfigurationException {
        super.validate();

        Validate.configurationDirectoryExists(jbossHome, "jbossHome '" + jbossHome + "' must exist");
        if (javaHome != null) {
            Validate.configurationDirectoryExists(javaHome, "javaHome must exist");
        }
    }

    /**
     * @return the jbossHome
     */
    public String getJbossHome() {
        return jbossHome;
    }

    /**
     * @param jbossHome the jbossHome to set
     */
    public void setJbossHome(String jbossHome) {
        this.jbossHome = new File(jbossHome).getAbsolutePath();
    }

    /**
     * @return the javaHome
     */
    public String getJavaHome() {
        return javaHome;
    }

    /**
     * @param javaHome the javaHome to set
     */
    public void setJavaHome(String javaHome) {
        this.javaHome = javaHome;
    }

    /**
     * @return the javaVmArguments
     */
    public String getJavaVmArguments() {
        return javaVmArguments;
    }

    /**
     * @param javaVmArguments the javaVmArguments to set
     */
    public void setJavaVmArguments(String javaVmArguments) {
        this.javaVmArguments = javaVmArguments;
    }

    /**
     * A space delimited list of arguments to be passed to the process controller.
     *
     * @return the arguments the arguments to be passed or {@code null} if no arguments were set
     */
    public String getJbossArguments() {
        return jbossArguments;
    }

    /**
     * A space delimited list of arguments to be passed to the process controller.
     *
     * @param jbossArguments the space delimited arguments to set or {@code null} not pass any
     */
    public void setJbossArguments(String jbossArguments) {
        this.jbossArguments = jbossArguments;
    }

    /**
     * The number of seconds to wait before failing when starting domain controller process
     *
     * @param startupTimeoutInSeconds
     */
    public void setStartupTimeoutInSeconds(int startupTimeoutInSeconds) {
        this.startupTimeoutInSeconds = startupTimeoutInSeconds;
    }

    /**
     * @return the startupTimeoutInSeconds
     */
    public int getStartupTimeoutInSeconds() {
        return startupTimeoutInSeconds;
    }

    /**
     * @param stopTimeoutInSeconds number of seconds to wait for the container process to shutdown
     */
    public void setStopTimeoutInSeconds(int stopTimeoutInSeconds) {
        this.stopTimeoutInSeconds = stopTimeoutInSeconds;
    }

    /**
     * @return stopTimeoutInSeconds number of seconds to wait for the container process to shutdown;
     *             defaults to 60
     */
    public int getStopTimeoutInSeconds() {
        return stopTimeoutInSeconds;
    }

    /**
     * The number of seconds to wait before failing when starting servers in Auto start mode
     *
     * @param autoServerStartupTimeoutInSeconds
     */
    public void setAutoServerStartupTimeoutInSeconds(int autoServerStartupTimeoutInSeconds) {
        this.autoServerStartupTimeoutInSeconds = autoServerStartupTimeoutInSeconds;
    }

    /**
     * @return the autoServerStartupTimeoutInSeconds
     */
    public int getAutoServerStartupTimeoutInSeconds() {
        return autoServerStartupTimeoutInSeconds;
    }

    /**
     * @param outputToConsole the outputToConsole to set
     */
    public void setOutputToConsole(boolean outputToConsole) {
        this.outputToConsole = outputToConsole;
    }

    /**
     * @return the outputToConsole
     */
    public boolean isOutputToConsole() {
        return outputToConsole;
    }

    /**
     * Get the server configuration file name. Equivalent to [-server-config=...] on the command line.
     *
     * @return the server config
     */
    public String getDomainConfig() {
        return domainConfig;
    }

    /**
     * Set the server configuration file name. Equivalent to [-Djboss.domain.default.config=...] on the command line.
     *
     * @param domainConfig the domain xml file name
     */
    public void setDomainConfig(String domainConfig) {
        this.domainConfig = domainConfig;
    }

    public String getModulePath() {
        return modulePath;
    }

    public String getHostConfig() {
        return hostConfig;
    }

    /**
     * Set the host controller configuration file name. Equivalent to [-Djboss.host.default.config=...] on the command line.
     *
     * @param hostConfig the host xml file name
     */
    public void setHostConfig(String hostConfig) {
        this.hostConfig = hostConfig;
    }

    public void setModulePath(final String modulePath) {
        this.modulePath = modulePath;
    }

    public boolean isAllowConnectingToRunningServer() {
        return allowConnectingToRunningServer;
    }

    public void setAllowConnectingToRunningServer(final boolean allowConnectingToRunningServer) {
        this.allowConnectingToRunningServer = allowConnectingToRunningServer;
    }

    public boolean isEnableAssertions() {
        return enableAssertions;
    }

    public void setEnableAssertions(final boolean enableAssertions) {
        this.enableAssertions = enableAssertions;
    }

    public boolean isSetupCleanServerBaseDir() {
        return setupCleanServerBaseDir;
    }

    public void setSetupCleanServerBaseDir(boolean setupCleanServerBaseDir) {
        this.setupCleanServerBaseDir = setupCleanServerBaseDir;
    }

    public String getCleanServerBaseDir() {
        return cleanServerBaseDir;
    }

    public void setCleanServerBaseDir(String cleanServerBaseDir) {
        this.cleanServerBaseDir = cleanServerBaseDir;
    }
}
