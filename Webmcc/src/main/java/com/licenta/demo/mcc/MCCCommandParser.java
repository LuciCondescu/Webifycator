package com.licenta.demo.mcc;

import com.licenta.core.CommandParser;
import com.licenta.dao.beans.UserBean;
import com.licenta.utils.SendMail;
import com.licenta.utils.ZipFolder;

import java.io.IOException;
import java.util.Map;

/**
 * @author Lucian CONDESCU
 */
public class MCCCommandParser implements CommandParser {

    @Override
    public StringBuilder parseCommandResult(Process p, String workingDir, UserBean user, Map<String,String> parametersMap) {
        StringBuilder result = new StringBuilder();
        String email = user.getEmail();
        String subject = "[MCC] Results are available";
        String messageBody = "Hello " + user.getName() + ",\n\n" +
                "Please find attached the results of MCC analyze.\n\n";
        try {
            ZipFolder.zipFolder(workingDir + "/results", workingDir + "/results.zip");
            boolean mailSent = SendMail.sendMail(email, subject,
                    messageBody, workingDir + "/results.zip");
            if (mailSent)
                result.append("An email with the analyse" +
                        " results was sent to : ").append(email);
            else
                result.append("Couldn't send email with" +
                        " the results to : ").append(email);
        } catch (IOException e) {
            e.printStackTrace();
            result.append("An error occurred when " +
                    "parsing command output");
        }
        return result;
    }

    @Override
    public CommandParser doClone() {
        return new MCCCommandParser();
    }
}
