package com.licenta.core;

import com.licenta.core.execution.ProcessLauncher;
import com.licenta.core.execution.ProcessLauncherFactory;
import com.licenta.dao.CommandDAO;
import com.licenta.dao.beans.CommandBean;
import com.licenta.dao.beans.UserBean;
import com.licenta.dao.impl.JDBCCommandDAOImpl;

import java.sql.Timestamp;
import java.util.Date;
import java.util.Map;

/**
 * @author Lucian CONDESCU
 */
public abstract class CommandLaunch {
    private final ProcessLauncher processLauncher = ProcessLauncherFactory.getLauncher();
    protected CommandParser parser;
    private CommandDAO commandDAO = new JDBCCommandDAOImpl();

    public CommandLaunch(CommandParser parser) {
        this.parser = parser.doClone();
    }

    public void launchCommand(Map<String, String> parametersMap, UserBean user, String commandName, String workingDirectory){
        Timestamp timestamp = new Timestamp(new Date().getTime());
        CommandBean commandBean = new CommandBean();
        String command = this.buildCommand(parametersMap,workingDirectory);
        Process launchedProcess;
        StringBuilder commandResult = new StringBuilder("Command exited abnormally.");
        try {
            launchedProcess = processLauncher.execCommand(command,workingDirectory,commandBean);
            if(commandBean.getErrorCode()==0)
                commandResult = parser.parseCommandResult(launchedProcess,workingDirectory,user,parametersMap);
                System.out.println("commandResult = [" + commandResult + "]");
        } catch (InterruptedException e) {
            commandResult = new StringBuilder("Command was canceled.");
        } finally {
            //File workingDir = new File(workingDirectory);
            //FileUtils.deleteQuietly(workingDir);
            commandBean.setCommandName(commandName);
            commandBean.setLaunchedCommand(commandResult.toString());
            commandBean.setTimestamp(timestamp);
            commandBean.setLaunchedCommand(command);
            commandBean.setUserID(user.getId());
            commandBean.setResult(commandResult.toString());

            commandDAO.addCommand(commandBean);
        }
    }

    protected abstract String buildCommand(Map<String, String> parametersMap, String workingDirectory);

    public void cancel() {
        processLauncher.killProcess();
    }

    public abstract CommandLaunch doClone();
}
