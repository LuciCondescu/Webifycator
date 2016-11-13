package com.licenta.core.execution;

/**
 * @author Lucian CONDESCU
 */
class LinuxProcessLauncher extends ProcessLauncher {

    @Override
    protected String[] getCommandLineString(String command) {
        return new String[] {"/bin/sh", "-c", command};
    }
}
