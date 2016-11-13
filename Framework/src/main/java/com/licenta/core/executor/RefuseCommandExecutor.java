package com.licenta.core.executor;

/**
 * @author Lucian CONDESCU
 */
public class RefuseCommandExecutor extends QueuedCommandExecutor {
    private static final String MAX_GLOBAL_COMMAND_INSTANCES = "The maximum number of simultaneous commands was reached. Please try again later.";
    private static final String MAX_USER_COMMAND_INSTANCES = "The maximum number of simultaneous commands per user was reached. Please try again later.";
    private int maxUserCommands;

    public RefuseCommandExecutor(int threadsNumber, int maxUserCommands) {
        super(threadsNumber);
        this.maxUserCommands = maxUserCommands;
    }

    @Override
    public String submitCommand(RunnableCommandLaunch command, String id) {
        if(this.getNumberOfRunningCommands(id) < maxUserCommands)
            if(this.numberOfThreads > this.commandsMap.size())
                return super.submitCommand(command, id);
            else return MAX_GLOBAL_COMMAND_INSTANCES;
        else return MAX_USER_COMMAND_INSTANCES;
    }
}
