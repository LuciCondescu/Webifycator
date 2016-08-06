package com.licenta.dao.beans;

import java.sql.Timestamp;

/**
 * @author Lucian CONDESCU
 */
public class CommandBean {
    
    private long commandID;
    private String result;
    private String launchedCommand;
    private String userID;
    private String commandName;
    private Timestamp timestamp;
    private String standardOutput;
    private String standardError;
    private int errorCode;

    public CommandBean(long commandID, String result, String launchedCommand, String userID, String commandName, Timestamp timestamp, String standardOutput, String standardError) {
        this.commandID = commandID;
        this.result = result;
        this.launchedCommand = launchedCommand;
        this.userID = userID;
        this.commandName = commandName;
        this.timestamp = timestamp;
        this.standardOutput = standardOutput;
        this.standardError = standardError;
    }

    public CommandBean() {}

    public String getLaunchedCommand() {
        return launchedCommand;
    }

    public String getUserID() {
        return userID;
    }

    public String getResult() {
        return result;
    }

    public long getCommandID() {
        return commandID;
    }

    public String getCommandName() {
        return commandName;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setCommandID(long commandID) {
        this.commandID = commandID;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public void setLaunchedCommand(String launchedCommand) {
        this.launchedCommand = launchedCommand;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public void setCommandName(String commandName) {
        this.commandName = commandName;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public String getStandardOutput() {
        return standardOutput;
    }

    public void setStandardOutput(String standardOutput) {
        this.standardOutput = standardOutput;
    }

    public String getStandardError() {
        return standardError;
    }

    public void setStandardError(String standardError) {
        this.standardError = standardError;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }
}
