package com.quiz.controller.database;

import com.quiz.controller.Connect;
import com.quiz.model.data.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Auth extends Connect {

    public Auth() throws Exception {
        super();
    }

    public boolean register(User user) {
        // check if user already exists
        try (PreparedStatement pst = getConn().prepareStatement("SELECT COUNT(*) FROM user WHERE email = ?")) {
            pst.setString(1, user.getEmail());
            ResultSet rs = pst.executeQuery();
            rs.next();
            int count = rs.getInt(1);
            if (count > 0) {
                // user already exists
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        try (PreparedStatement pst = getConn()
                .prepareStatement("INSERT INTO user (email, password, name) VALUES (?, ?, ?)")) {
            pst.setString(1, user.getEmail());
            pst.setString(2, user.getPassword());
            pst.setString(3, user.getName());
            int result = pst.executeUpdate();
            return result > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public User login(String email, String password) {
        try (PreparedStatement pst = getConn()
                .prepareStatement("SELECT * FROM user WHERE email = ? AND password = ?")) {
            pst.setString(1, email);
            pst.setString(2, password);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                User user = new User();
                user.setId(rs.getInt("id"));
                user.setEmail(rs.getString("email"));
                user.setPassword(rs.getString("password"));
                user.setName(rs.getString("name"));
                return user;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return null;
    }
}
