package com.licenta.core;

import com.licenta.dao.beans.UserBean;

import java.util.Map;

/**
 * @author Lucian CONDESCU
 */
public interface CommandParser {
    StringBuilder parseCommandResult(Process p, String workingDirectory, UserBean user, Map<String, String> parametersMap);
    CommandParser doClone();
}
