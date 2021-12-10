package com.company.Repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public abstract class JDBC_repository<T> implements CrudRepo<T>{

    static final String url = "jdbc:mysql://localhost:3306/university";
    static final String username = "root";
    static final String password = "amaliablidar";

    protected Connection connection;

    public JDBC_repository() {
        connection = connectDB();
    }

    public Connection connectDB() {
        Connection connection = null;
        try {

            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(url, username,
                    password);
        }

        catch (SQLException | ClassNotFoundException throwable) {
            throwable.printStackTrace();
        }

        return connection;
    }
}