package io.github.AliAlmasiZ.tillDawn.models.DataBase;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DataBaseManager {
    private static final String DB_URL = "jdbc:sqlite:game_data.db";
    private Connection connection;

    public void connect() throws SQLException {
        connection = DriverManager.getConnection(DB_URL);
    }

    public Connection getConnection() {
        return connection;
    }

    public void disconnect() throws SQLException {
        if(connection != null && !connection.isClosed()) {
            connection.close();
        }
    }
}
