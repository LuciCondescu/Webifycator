package com.licenta.dao;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * @author Lucian CONDESCU
 */
public class ConnectionManager {

    private static ConnectionManager ourInstance = new ConnectionManager();
    private DataSource dataSource;

    public static ConnectionManager getInstance() {
        return ourInstance;
    }

    private ConnectionManager() {
        InitialContext initialContext;
        try {
            initialContext = new InitialContext();
            dataSource = (DataSource) initialContext.lookup("java:/comp/env/jdbc/webtask");
        } catch (NamingException e) {
            e.printStackTrace();
        }
    }

    public synchronized Connection getConnection() throws SQLException {
        return this.dataSource.getConnection();
    }

}
