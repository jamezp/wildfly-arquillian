/*
 * Copyright The WildFly Authors
 * SPDX-License-Identifier: Apache-2.0
 */
package org.jboss.as.arquillian.container;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.jboss.arquillian.container.spi.ConfigurationException;
import org.jboss.arquillian.container.spi.client.deployment.Validate;

/**
 * The container configuration for a managed WildFly based container.
 *
 * @author Thomas.Diesler@jboss.com
 * @author <a href="mailto:alr@jboss.org">Andrew Lee Rubinger</a>
 * @since 3.0.0
 */
@SuppressWarnings({ "unused", "MagicNumber", "InstanceVariableMayNotBeInitialized" })
public class CommonManagedContainerConfiguration extends CommonContainerConfiguration {

    /**
     * Default timeout value waiting on ports is 10 seconds
     */
    private static final Integer DEFAULT_VALUE_WAIT_FOR_PORTS_TIMEOUT_SECONDS = 10;

    private String javaHome = System.getenv("JAVA_HOME");

    private int startupTimeoutInSeconds = 60;

    private int stopTimeoutInSeconds = 60;

    private boolean outputToConsole = true;

    private boolean allowConnectingToRunningServer = Boolean
            .parseBoolean(System.getProperty("allowConnectingToRunningServer", "false"));

    private Integer[] waitForPorts;

    private Integer waitForPortsTimeoutInSeconds;

    @Override
    public void validate() throws ConfigurationException {
        super.validate();
        if (javaHome != null)
            Validate.configurationDirectoryExists(javaHome, "javaHome '" + javaHome + "' must exist");
    }

    public String getJavaHome() {
        return javaHome;
    }

    public void setJavaHome(String javaHome) {
        this.javaHome = javaHome;
    }

    public void setStartupTimeoutInSeconds(int startupTimeoutInSeconds) {
        this.startupTimeoutInSeconds = startupTimeoutInSeconds;
    }

    public int getStartupTimeoutInSeconds() {
        return startupTimeoutInSeconds;
    }

    public void setStopTimeoutInSeconds(int stopTimeoutInSeconds) {
        this.stopTimeoutInSeconds = stopTimeoutInSeconds;
    }

    /**
     * Number of seconds to wait for the container process to shutdown; defaults to 60
     */
    public int getStopTimeoutInSeconds() {
        return stopTimeoutInSeconds;
    }

    public void setOutputToConsole(boolean outputToConsole) {
        this.outputToConsole = outputToConsole;
    }

    public boolean isOutputToConsole() {
        return outputToConsole;
    }

    public boolean isAllowConnectingToRunningServer() {
        return allowConnectingToRunningServer;
    }

    public void setAllowConnectingToRunningServer(final boolean allowConnectingToRunningServer) {
        this.allowConnectingToRunningServer = allowConnectingToRunningServer;
    }

    public Integer[] getWaitForPorts() {
        return waitForPorts;
    }

    public void setWaitForPorts(String waitForPorts) {
        final Scanner scanner = new Scanner(waitForPorts);
        final List<Integer> list = new ArrayList<>();
        while (scanner.hasNextInt()) {
            list.add(scanner.nextInt());
        }
        this.waitForPorts = list.toArray(new Integer[] {});
    }

    public Integer getWaitForPortsTimeoutInSeconds() {
        return waitForPortsTimeoutInSeconds != null ? waitForPortsTimeoutInSeconds
                : DEFAULT_VALUE_WAIT_FOR_PORTS_TIMEOUT_SECONDS;
    }

    public void setWaitForPortsTimeoutInSeconds(final Integer waitForPortsTimeoutInSeconds) {
        this.waitForPortsTimeoutInSeconds = waitForPortsTimeoutInSeconds;
    }
}
