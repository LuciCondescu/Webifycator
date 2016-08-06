package com.licenta.dao;

import com.licenta.dao.beans.UserBean;

/**
 * @author Lucian CONDESCU
 */
public interface UserDAO {

    void addUser(UserBean userBean);
    void removeUser(UserBean userBean);
    boolean checkUser(String userID);
}
