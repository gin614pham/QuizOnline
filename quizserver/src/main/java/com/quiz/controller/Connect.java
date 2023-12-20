package com.quiz.controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.quiz.model.data.Quiz;
import com.quiz.model.data.User;

public class Connect {
    private Connection conn;

    public Connect() {
        Connection conn = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String url = "jdbc:mysql://localhost:3306/quiz";
            conn = DriverManager.getConnection(url, "root", "");
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        this.conn = conn;
    }

    public Connection getConn() {
        return conn;
    }

    public boolean addQuiz(Quiz quiz, int id) {
        try (PreparedStatement pst = conn.prepareStatement("INSERT INTO quiz ( iduser, name) VALUES (?, ?)")) {
            pst.setInt(1, id);
            pst.setString(2, quiz.getName());
            int result = pst.executeUpdate();
            return result > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteQuiz(int id) {
        try (PreparedStatement pst = conn.prepareStatement("DELETE FROM quiz WHERE id = ?")) {
            pst.setInt(1, id);
            int result = pst.executeUpdate();
            return result > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

}
