package com.licenta.core.execution;

import org.apache.commons.lang.SystemUtils;

/**
 * @author Lucian CONDESCU
 */
public class RuntimeLauncherFactory {

    public static RuntimeLauncher getLauncher() {

        if(SystemUtils.IS_OS_LINUX)
            return new LinuxRuntimeLauncher();
        else
            return new WindowsRuntimeLauncher();
    }
}
