package com.licenta.dao.impl;

import com.licenta.dao.CommandDAO;
import com.licenta.dao.ConnectionManager;
import com.licenta.dao.beans.CommandBean;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Lucian CONDESCU
 */
public class JDBCCommandDAOImpl implements CommandDAO {

    @Override
    public List<CommandBean> getUserCommands(String id) {
        List<CommandBean> commandList = new ArrayList<>();
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        String sql = "SELECT * FROM command WHERE userID= ? ORDER BY launchedDate DESC ";
        try {
            connection = ConnectionManager.getInstance().getConnection();
            statement = connection.prepareStatement(sql);
            statement.setString(1,id);
            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                long commandID = resultSet.getLong("commandID");
                String result = resultSet.getString("result");
                String launchedCommand = resultSet.getString("launchedCommand");
                String userID = resultSet.getString("userID");
                String commandName = resultSet.getString("commandName");
                Timestamp launchedDate = resultSet.getTimestamp("launchedDate");
                String standardOutput = resultSet.getString("standardOutput");
                String standardError = resultSet.getString("standardError");
                commandList.add(new CommandBean(commandID,result,launchedCommand,userID, commandName,launchedDate, standardOutput, standardError));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try { if (resultSet != null) resultSet.close(); } catch (SQLException e) { e.printStackTrace();}
            try { if (statement != null) statement.close(); } catch (SQLException e) { e.printStackTrace();}
            try { if (connection != null) connection.close(); } catch (SQLException e) { e.printStackTrace();}
        }

        return commandList;
    }

    @Override
    public void addCommand(CommandBean commandBean) {
        String sql = "INSERT INTO command (result,launchedCommand,userID,commandName,launchedDate,standardOutput,standardError) VALUES (?,?,?,?,?,?,?)";
        PreparedStatement statement = null;
        Connection connection = null;
        try {
            connection = ConnectionManager.getInstance().getConnection();
            statement = connection.prepareStatement(sql);
            statement.setString(1,commandBean.getResult());
            statement.setString(2,commandBean.getLaunchedCommand());
            statement.setString(3,commandBean.getUserID());
            statement.setString(4,commandBean.getCommandName());
            statement.setTimestamp(5, commandBean.getTimestamp());
            statement.setString(6,commandBean.getStandardOutput());
            statement.setString(7,commandBean.getStandardError());
            int rawInserted = statement.executeUpdate();
            if(rawInserted > 0) {
                System.out.println("The command was successfully added !");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try { if (statement != null) statement.close(); } catch (SQLException e) { e.printStackTrace();}
            try { if (connection != null) connection.close(); } catch (SQLException e) { e.printStackTrace();}
        }
    }

    @Override
    public CommandBean getCommand(long commandID) {
        CommandBean commandBean = null;
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        String sql = "SELECT * FROM command WHERE commandID= ?";
        try {
            connection = ConnectionManager.getInstance().getConnection();
            statement = connection.prepareStatement(sql);
            statement.setLong(1,commandID);
            resultSet = statement.executeQuery();
            if(resultSet.next()) {
                long commandId = resultSet.getLong("commandID");
                String result = resultSet.getString("result");
                String launchedCommand = resultSet.getString("launchedCommand");
                String userID = resultSet.getString("userID");
                String commandName = resultSet.getString("commandName");
                Timestamp launchedDate = resultSet.getTimestamp("launchedDate");
                String standardOutput = resultSet.getString("standardOutput");
                String standardError = resultSet.getString("standardError");
                commandBean = new CommandBean(commandId, result, launchedCommand, userID, commandName,launchedDate, standardOutput, standardError);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try { if (resultSet != null) resultSet.close(); } catch (SQLException e) { e.printStackTrace();}
            try { if (statement != null) statement.close(); } catch (SQLException e) { e.printStackTrace();}
            try { if (connection != null) connection.close(); } catch (SQLException e) { e.printStackTrace();}
        }

        return commandBean;

    }

}
