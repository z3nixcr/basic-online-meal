package com.zenedict.onlinemeal.utils;

import org.apache.commons.dbcp2.BasicDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MySQLConn {
    private static final String DRIVER  = "com.mysql.cj.jdbc.Driver";
    private static final String URL     = "jdbc:mysql://localhost:3306/onlinemealdb";
    private static final String USER    = "root";
    private static final String PASS    = "M1cu3nt@d3mysql";

    private static DataSource getDataSource() {
        BasicDataSource ds = new BasicDataSource();
        ds.setUrl(URL);
        ds.setUsername(USER);
        ds.setPassword(PASS);
        ds.setInitialSize(50);
        return ds;
    }

    public static Connection getConnection() throws ClassNotFoundException, SQLException {
        Class.forName(DRIVER);
        return getDataSource().getConnection();
    }

    public static void close(ResultSet resultSet) throws SQLException {
        resultSet.close();
    }

    public static void close(PreparedStatement preparedStatement) throws SQLException {
        preparedStatement.close();
    }

    public static void close(Connection connection) throws SQLException {
        connection.close();
    }
}
