package com.licenta.demo.mcc;

import com.licenta.core.CommandLaunch;
import com.licenta.page.LaunchCommandPageUI;
import com.licenta.page.Verifier;
import com.licenta.page.WebElement;
import com.licenta.page.ui.DropDown;
import com.licenta.page.ui.FileUpload;
import com.licenta.utils.UnzipUtil;

import java.util.Arrays;
import java.util.List;

/**
 * @author Lucian CONDESCU
 */
public class MCCCommandPageUI extends LaunchCommandPageUI {

    public MCCCommandPageUI(String commandName, CommandLaunch commandLaunch) {
        super(commandName, commandLaunch);
    }

    @Override
    public void buildPageUI() {
        Verifier zipVerifier = new Verifier() {
            @Override
            public boolean verify(String parameter) {
                return new UnzipUtil().isZipFile(parameter);
            }
        };
        WebElement sourcesZip = new FileUpload("Sources",zipVerifier,"A zip archive containing the C++ source code");
        final List<String> options = Arrays.asList("N/A","Aix","Alpha","Borland_30","Borland_50","C++","full_parser","Gnu_27","Hp","Ilog_30","MFC","Microsoft_15","Microsoft_20","Microsoft_50","Microsoft_60","Objectstore_5x","Orbix_2x","Sun");
        Verifier optionVerifier = new Verifier() {
            @Override
            public boolean verify(String parameter) {
                return options.contains(parameter);
            }
        };
        WebElement configurationFile = new DropDown("Configuration type",optionVerifier,options);
        super.addWebElement(sourcesZip);
        super.addWebElement(configurationFile);
    }

    @Override
    public LaunchCommandPageUI doClone() {
        return new MCCCommandPageUI(commandName,commandLaunch);
    }
}
