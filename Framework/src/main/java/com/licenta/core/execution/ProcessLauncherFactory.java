package com.licenta.core.execution;

import org.apache.commons.lang.SystemUtils;

/**
 * @author Lucian CONDESCU
 */
public class ProcessLauncherFactory {

    public static ProcessLauncher getLauncher() {

        if(SystemUtils.IS_OS_LINUX)
            return new LinuxProcessLauncher();
        else
            return new WindowsProcessLauncher();
    }
}
