package com.licenta.core.execution;

/**
 * @author Lucian CONDESCU
 */
public class WindowsRuntimeLauncher extends RuntimeLauncher {

    protected String[] getCommandLineStrings(String command) {
        return new String[]{"cmd.exe", "/c", command};
    }
}
