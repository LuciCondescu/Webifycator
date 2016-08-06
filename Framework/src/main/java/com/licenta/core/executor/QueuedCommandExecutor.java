package com.licenta.core.executor;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.*;

/**
 * @author Lucian CONDESCU
 */
public class QueuedCommandExecutor {
    private final ExecutorService executor;
    protected Map<Future,RunnableCommandLaunch> commandsMap;
    protected int threadNumber;

    public QueuedCommandExecutor(int threadsNumber) {
        this.threadNumber = threadsNumber;
        executor = Executors.newFixedThreadPool(threadsNumber);
        commandsMap = new ConcurrentHashMap<>();
        ScheduledExecutorService mapCleaner = Executors.newSingleThreadScheduledExecutor();
        mapCleaner.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                Iterator<Map.Entry<Future, RunnableCommandLaunch>> iterator = commandsMap.entrySet().iterator();

                while (iterator.hasNext()) {
                    Map.Entry<Future, RunnableCommandLaunch> entry = iterator.next();
                    if (entry.getKey().isDone()) {
                        iterator.remove();
                        System.out.println("Command " + entry.getValue().toString() + " was terminated.Cleaning the map");
                    }

                }
            }
        },3,3,TimeUnit.SECONDS);
    }

    public String submitCommand(RunnableCommandLaunch command, String id) {
        final Future<?> future = executor.submit(command);
        System.out.println("Command " + command + " was submitted to executor.");
        commandsMap.put(future, command);
        return "Command was submitted. Id " + future.hashCode() + " was assigned.";
    }

    public void shutDown() {
        executor.shutdownNow();
        for (RunnableCommandLaunch runnableCommandLaunch : commandsMap.values()) {
            runnableCommandLaunch.cancel();
        }
        commandsMap.clear();
    }

    public Map<String,String> getRunningCommands(String userID) {
        Iterator<Map.Entry<Future,RunnableCommandLaunch>> iterator = this.commandsMap.entrySet().iterator();
        Map<String, String> runningCommandsList = new HashMap<>();

        while(iterator.hasNext()) {
            Map.Entry<Future,RunnableCommandLaunch> entry = iterator.next();
            if (entry.getValue().belongTo(userID))
                runningCommandsList.put(String.valueOf(entry.getKey().hashCode()), entry.getValue().toString());
        }
        return runningCommandsList;
    }

    protected int getNumberOfRunningCommands(String userID) {
        int i=0;
        for (Map.Entry<Future, RunnableCommandLaunch> entry : this.commandsMap.entrySet()) {
            if (entry.getValue().belongTo(userID))
                i++;
        }
        return i;
    }

    public void cancelCommand(int futureHash) {
        for (Map.Entry<Future, RunnableCommandLaunch> entry : this.commandsMap.entrySet()) {
            if (entry.getKey().hashCode() == futureHash) {
                entry.getValue().cancel();
                entry.getKey().cancel(true);
                System.out.println("Cancel was triggered for " + entry.getValue());
                break;
            }
        }
    }
}
