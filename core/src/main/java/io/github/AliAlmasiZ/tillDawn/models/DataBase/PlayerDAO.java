package io.github.AliAlmasiZ.tillDawn.models.DataBase;

import io.github.AliAlmasiZ.tillDawn.models.Player;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PlayerDAO {
    private Connection connection;

    public PlayerDAO(Connection connection) {
        this.connection = connection;
    }

//    public void savePlayer(Player player) throws SQLException {
//        String sql = "INSERT INTO player (username, password, securityAnswer) VALUES (?, ?, ?)";
//        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
//            pstmt.setString(1, player.getUsername());
//            pstmt.setString(2, player.getPassword());
////            pstmt.setString(3, player.getSecurityQuestion());
//            pstmt.setString(3, player.getSecurityAnswer());
//            pstmt.executeUpdate();
//        }
//    }
    public void savePlayer(Player player) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();


        session.save(player);
        tx.commit();
        session.close();

    }

//    public Player loadPlayer(String username) throws SQLException {
//        String sql = "SELECT * FROM player WHERE username = ?";
//        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
//            pstmt.setString(1, username);
//            ResultSet rs = pstmt.executeQuery();
//            if(rs.next()) {
//                Player player = new Player(
//                    rs.getString("username"),
//                    rs.getString("password"),
//                    rs.getString("securityAnswer")
//                );
//                return player;
//            }
//        }
//        return null;
//    }

    public Player loadPlayer(String username) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();

        Player player = session.createQuery("FROM Player WHERE username = :username", Player.class)
            .setParameter("username", username)
            .uniqueResult();

        tx.commit();
        session.close();

        return player;
    }

    public Player loadPlayer(int id) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();

        return session.get(Player.class, id);
    }

    public List<Player> loadAllPlayers() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();

        List<Player> players = new ArrayList<>(session.createQuery("FROM Player", Player.class).list());

        tx.commit();
        session.close();

        return players;
    }
}
