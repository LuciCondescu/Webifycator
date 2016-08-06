package com.licenta.demo;

import com.licenta.demo.mcc.MCCCommandLaunch;
import com.licenta.demo.mcc.MCCCommandPageUI;
import com.licenta.demo.mcc.MCCCommandParser;
import com.licenta.page.CommandPageFactory;

/**
 * @author Lucian CONDESCU
 */
public class PageFactory extends CommandPageFactory {

    @Override
    protected void setupPages() {
        MCCCommandParser mccCommandParser = new MCCCommandParser();
        MCCCommandLaunch mccCommandLaunch = new MCCCommandLaunch(mccCommandParser);
        MCCCommandPageUI launchCommandPageUI = new MCCCommandPageUI("Launch MCC", mccCommandLaunch);

        super.addCommandPageUI(launchCommandPageUI);
    }
}
