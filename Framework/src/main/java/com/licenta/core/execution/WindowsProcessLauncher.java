package com.licenta.core.execution;

/**
 * @author Lucian CONDESCU
 */
public class WindowsProcessLauncher extends ProcessLauncher {

    protected String[] getCommandLineString(String command) {
        return new String[]{"cmd.exe", "/c", command};
    }
}
