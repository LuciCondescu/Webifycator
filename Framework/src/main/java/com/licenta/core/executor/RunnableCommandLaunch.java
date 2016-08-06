package com.licenta.core.executor;

import com.licenta.dao.beans.UserBean;
import com.licenta.page.LaunchCommandPageUI;

import java.util.Map;

/**
 * @author Lucian CONDESCU
 */
public class RunnableCommandLaunch implements Runnable{
    private LaunchCommandPageUI commandPageUI;
    private Map<String,String> parametersMap;
    private UserBean user;
    private String workingDirectory;

    public RunnableCommandLaunch(LaunchCommandPageUI commandPageUI, Map<String, String> parametersMap, UserBean user, String workingDirectory) {
        this.commandPageUI = commandPageUI;
        this.parametersMap = parametersMap;
        this.user = user;
        this.workingDirectory = workingDirectory;
    }

    @Override
    public void run() {
        System.out.println("Starting : " + commandPageUI);
        this.commandPageUI.launchCommand(parametersMap, user,workingDirectory);
    }

    public void cancel() {
        this.commandPageUI.cancel();
    }

    public boolean belongTo(String userID) {
        return this.user.getId().equals(userID);
    }

    public String toString() {
        return this.commandPageUI.toString();
    }
}
