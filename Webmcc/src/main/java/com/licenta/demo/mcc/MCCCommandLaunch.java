package com.licenta.demo.mcc;

import com.licenta.core.CommandLaunch;
import com.licenta.core.CommandParser;
import com.licenta.utils.UnzipUtil;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.Map;

/**
 * @author Lucian CONDESCU
 */
public class MCCCommandLaunch extends CommandLaunch {

    private static final String MCC_ROOT = "/home/lcondescu/tools/iplasma/iPlasma6/tools/mcc/";
    private static final String CONF_FILES = MCC_ROOT + "env_c/";
    private static final String MCC_EXE = MCC_ROOT + "mcC.exe";

    public MCCCommandLaunch(CommandParser parser) {
        super(parser);
    }

    @Override
    protected String buildCommand(Map<String, String> parametersMap, String workingDirectory) {
        String sourcesParam = parametersMap.get("Sources");
        String confFileParam = parametersMap.get("Configuration type");

        try {
            new UnzipUtil().unzip(sourcesParam,workingDirectory + "/src");
            FileUtils.forceMkdir(new File(workingDirectory + "/results"));

            workingDirectory = workingDirectory.replace("/","\\\\");
            String confFilePath = CONF_FILES.replace("/","\\\\");
            String defaultCommand = "wine " + MCC_EXE + " "
                    + workingDirectory + "\\\\src " + workingDirectory + "\\\\results";

            if (!"N/A".equals(confFileParam)) {
                return defaultCommand + " " + confFilePath + confFileParam;
            } else
                return defaultCommand;
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }

    }

    @Override
    public CommandLaunch doClone() {
        return new MCCCommandLaunch(parser);
    }
}
