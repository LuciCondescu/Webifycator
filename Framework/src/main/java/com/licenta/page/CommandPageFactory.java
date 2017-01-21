package com.licenta.page;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
        Optional<LaunchCommandPageUI> command = this.launchCommandPageUIList.stream().filter(c -> c.toString().equals(commandName)).findFirst();
        if(command.isPresent()) return command.get();

        return null;
    }

    public List<String> getCommandsName() {
        return this.launchCommandPageUIList.stream().map(LaunchCommandPageUI::toString).collect(Collectors.toList());
    }

    protected void addCommandPageUI(LaunchCommandPageUI launchCommandPageUI) {
        this.launchCommandPageUIList.add(launchCommandPageUI);
    }

    protected abstract void setupPages();
}
