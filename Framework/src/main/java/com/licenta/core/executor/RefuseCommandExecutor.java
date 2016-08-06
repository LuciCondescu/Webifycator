package com.licenta.core.executor;

/**
 * @author Lucian CONDESCU
 */
public class RefuseCommandExecutor extends QueuedCommandExecutor {
    private int maxUserCommands;

    public RefuseCommandExecutor(int threadsNumber, int maxUserCommands) {
        super(threadsNumber);
        this.maxUserCommands = maxUserCommands;
    }

    @Override
    public String submitCommand(RunnableCommandLaunch command, String id) {
        if(this.getNumberOfRunningCommands(id) < maxUserCommands)
            if(this.threadNumber > this.commandsMap.size())
                return super.submitCommand(command, id);
            else return "The maximum number of simultaneous commands was reached. Please try again later.";
        else return "You have reached the maximum number of simultaneous commands. Please try again later.";
    }
}
