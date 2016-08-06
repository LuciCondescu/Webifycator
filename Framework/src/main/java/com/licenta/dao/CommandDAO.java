package com.licenta.dao;

import com.licenta.dao.beans.CommandBean;

import java.util.List;

/**
 * @author Lucian CONDESCU
 */
public interface CommandDAO {

    List<CommandBean> getUserCommands(String userID);
    void addCommand(CommandBean commandBean);
    CommandBean getCommand(long commandID);
}
