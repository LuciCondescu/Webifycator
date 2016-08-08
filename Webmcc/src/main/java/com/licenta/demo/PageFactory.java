package com.licenta.demo;

import com.licenta.core.CommandLaunch;
import com.licenta.core.CommandParser;
import com.licenta.demo.mcc.MCCCommandLaunch;
import com.licenta.demo.mcc.MCCCommandPageUI;
import com.licenta.demo.mcc.MCCCommandParser;
import com.licenta.page.CommandPageFactory;
import com.licenta.page.LaunchCommandPageUI;

/**
 * @author Lucian CONDESCU
 */
public class PageFactory extends CommandPageFactory {

    @Override
    protected void setupPages() {
        CommandParser mccCommandParser = new MCCCommandParser();
        CommandLaunch mccCommandLaunch = new MCCCommandLaunch(mccCommandParser);
        LaunchCommandPageUI launchCommandPageUI = new MCCCommandPageUI("Launch MCC", mccCommandLaunch);

        super.addCommandPageUI(launchCommandPageUI);
    }
}
