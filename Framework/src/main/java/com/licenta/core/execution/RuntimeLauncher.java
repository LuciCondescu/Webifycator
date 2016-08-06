package com.licenta.core.execution;

import com.licenta.core.StreamHandlerThread;
import com.licenta.dao.beans.CommandBean;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;

public abstract class RuntimeLauncher {

    private Process process;

    public Process execCommand(String command, String workingDirectory, CommandBean commandBean) throws InterruptedException {
        String [] commandArray = this.getCommandLineStrings(command);
        try {
            ProcessBuilder builder = new ProcessBuilder(commandArray);
            builder.directory(new File(workingDirectory));
            System.out.println("command = " + command);
            this.process = builder.start();
            StreamHandlerThread outThread = new StreamHandlerThread(this.process.getInputStream(),workingDirectory);
            StreamHandlerThread errorThread = new StreamHandlerThread(this.process.getErrorStream(),workingDirectory);
            outThread.start();
            errorThread.start();
            System.out.println("Process exited with : " + this.process.waitFor());
            commandBean.setErrorCode(this.process.exitValue());
            commandBean.setStandardOutput(outThread.tail(100));
            commandBean.setStandardError(errorThread.tail(30));
        } catch (IOException e) {
            e.printStackTrace();
            File workingDir = new File(workingDirectory);
            FileUtils.deleteQuietly(workingDir);
        }

        return process;
    }

    protected abstract String[] getCommandLineStrings(String command);

    public void killProcess() {
        if (process != null) {
            process.destroy();
            process = null;
        }
    }
}
