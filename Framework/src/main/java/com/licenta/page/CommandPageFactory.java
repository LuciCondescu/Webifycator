package com.licenta.page;

import java.util.LinkedList;
import java.util.List;

/**
 * @author Lucian CONDESCU
 */
public abstract class CommandPageFactory {
    private  List<LaunchCommandPageUI> launchCommandPageUIList;

    public CommandPageFactory () {
        launchCommandPageUIList = new LinkedList<>();
        setupPages();
    }

    public synchronized LaunchCommandPageUI getCommand(String commandName) {
        for (LaunchCommandPageUI aLaunchCommandPageUI : launchCommandPageUIList) {
            if (aLaunchCommandPageUI.toString().equals(commandName))
                return aLaunchCommandPageUI.doClone();
        }
        return null;
    }

    public List<String> getCommandsName() {
        List<String> commandsName = new LinkedList<>();
        for (LaunchCommandPageUI aLaunchCommandPageUI : launchCommandPageUIList) {
            commandsName.add(aLaunchCommandPageUI.toString());
        }
        return commandsName;
    }

    protected void addCommandPageUI(LaunchCommandPageUI launchCommandPageUI) {
        this.launchCommandPageUIList.add(launchCommandPageUI);
    }

    protected abstract void setupPages();
}
