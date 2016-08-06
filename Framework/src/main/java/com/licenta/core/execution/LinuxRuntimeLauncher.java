package com.licenta.core.execution;

/**
 * @author Lucian CONDESCU
 */
public class LinuxRuntimeLauncher extends RuntimeLauncher {

    @Override
    protected String[] getCommandLineStrings(String command) {
        return new String[] {"/bin/sh", "-c", command};
    }
}
