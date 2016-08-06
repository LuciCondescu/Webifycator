package com.licenta.dao.impl;

import com.licenta.dao.ConnectionManager;
import com.licenta.dao.UserDAO;
import com.licenta.dao.beans.UserBean;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Lucian CONDESCU
 */
public class JDBCUserDAOImpl implements UserDAO {

    @Override
    public void addUser(UserBean userBean) {
        String sql = "INSERT INTO user (userID,name,email) VALUES (?,?,?)";
        PreparedStatement statement = null;
        Connection connection = null;
        try {
            connection = ConnectionManager.getInstance().getConnection();
            statement = connection.prepareStatement(sql);
            statement.setString(1,userBean.getId());
            statement.setString(2,userBean.getName());
            statement.setString(3,userBean.getEmail());
            int rawInserted = statement.executeUpdate();
            if(rawInserted > 0) {
                System.out.println("The user was successfully added in database !");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try { if (statement != null) statement.close(); } catch (SQLException e) { e.printStackTrace();}
            try { if (connection != null) connection.close(); } catch (SQLException e) { e.printStackTrace();}
        }
    }

    @Override
    public void removeUser(UserBean userBean) {
        //TO-DO implement remove user from database
    }

    @Override
    public boolean checkUser(String userID) {
        String sql = "SELECT * FROM user WHERE userID = ?";
        PreparedStatement statement = null;
        Connection connection = null;
        try {
            connection = ConnectionManager.getInstance().getConnection();
            statement = connection.prepareStatement(sql);
            statement.setString(1,userID);
            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next()) {
                String userIDfromDb = resultSet.getString("userID");
                return userID.equals(userIDfromDb);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try { if (statement != null) statement.close(); } catch (SQLException e) { e.printStackTrace();}
            try { if (connection != null) connection.close(); } catch (SQLException e) { e.printStackTrace();}
        }
        return false;
    }

}
