package io.github.AliAlmasiZ.tillDawn.models.DataBase;

import io.github.AliAlmasiZ.tillDawn.models.Player;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PlayerDAO {
    private Connection connection;

    public PlayerDAO(Connection connection) {
        this.connection = connection;
    }

    public void savePlayer(Player player) throws SQLException {
        String sql = "INSERT INTO player (username, password) VALUES (?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, player.getUsername());
            pstmt.setString(2, player.getPassword());
            pstmt.executeUpdate();
        }
    }

    public Player loadPlayer(String username) throws SQLException {
        String sql = "SELECT * FROM player WHERE username = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();
            if(rs.next()) {
                Player player = new Player();
                player.setPassword(rs.getString("password"));
                player.setUsername(rs.getString("username"));
                return player;
            }
        }
        return null;
    }
}
