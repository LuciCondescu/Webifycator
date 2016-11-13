package com.licenta.core.executor;

import java.util.Map;
import java.util.concurrent.*;
import java.util.stream.Collectors;

/**
 * @author Lucian CONDESCU
 */
public class QueuedCommandExecutor {
    private final ExecutorService executor;
    Map<Future, RunnableCommandLaunch> commandsMap = new ConcurrentHashMap<>();
    int numberOfThreads;

    public QueuedCommandExecutor(int threadsNumber) {
        this.numberOfThreads = threadsNumber;
        executor = Executors.newFixedThreadPool(threadsNumber);
        ScheduledExecutorService mapCleaner = Executors.newSingleThreadScheduledExecutor();

        mapCleaner.scheduleAtFixedRate(() ->
                        commandsMap.entrySet().removeIf(command -> command.getKey().isDone()),
                3, 3, TimeUnit.SECONDS);
    }

    public String submitCommand(RunnableCommandLaunch command, String id) {
        final Future<?> future = executor.submit(command);
        System.out.println("Command " + command + " was submitted to executor.");
        commandsMap.put(future, command);
        return "Command was submitted. Id " + future.hashCode() + " was assigned.";
    }

    public void shutDown() {
        executor.shutdownNow();
        commandsMap.values().forEach(RunnableCommandLaunch::cancel);
        commandsMap.clear();
    }

    public Map<String, String> getRunningCommands(String userID) {
        return commandsMap.entrySet().stream()
                .filter(command -> command.getValue().belongTo(userID))
                .collect(Collectors.toMap(
                        e -> String.valueOf(e.getKey().hashCode()),
                        e -> e.getValue().toString()
                ));
    }

    long getNumberOfRunningCommands(String userID) {
        return commandsMap.entrySet().stream()
                .filter(command -> command.getValue().belongTo(userID))
                .count();
    }

    public void cancelCommand(int futureHash) {
        commandsMap.entrySet().stream()
                .filter(command -> command.getKey().hashCode() == futureHash)
                .forEach(command -> {
                    command.getValue().cancel();
                    command.getKey().cancel(true);
                });
    }
}
