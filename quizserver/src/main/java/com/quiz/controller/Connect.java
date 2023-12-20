package com.quiz.controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;

import com.quiz.model.data.Question;
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

    public boolean register(String email, String password, String name) {
        // check if user already exists
        try (PreparedStatement pst = conn.prepareStatement("SELECT COUNT(*) FROM user WHERE email = ?")) {
            pst.setString(1, email);
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
        try (PreparedStatement pst = conn
                .prepareStatement("INSERT INTO user (email, password, name) VALUES (?, ?, ?)")) {
            pst.setString(1, email);
            pst.setString(2, password);
            pst.setString(3, name);
            int result = pst.executeUpdate();
            return result > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public User login(String email, String password) {
        try (PreparedStatement pst = conn.prepareStatement("SELECT * FROM user WHERE email = ? AND password = ?")) {
            pst.setString(1, email);
            pst.setString(2, password);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                User user = new User();
                user.setId(rs.getInt("id"));
                user.setEmail(rs.getString("email"));
                user.setName(rs.getString("name"));
                return user;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return null;
    }

    public String getUsernameById(int id) {
        try (PreparedStatement pst = conn.prepareStatement("SELECT name FROM user WHERE id = ?")) {
            pst.setInt(1, id);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                return rs.getString("name");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
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

    public boolean updateQuiz(Quiz quiz, int id) {
        try (PreparedStatement pst = conn.prepareStatement("UPDATE quiz SET name = ? WHERE id = ?")) {
            pst.setString(1, quiz.getName());
            pst.setInt(2, id);
            int result = pst.executeUpdate();
            return result > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public ArrayList<Quiz> getQuizzesByUserId(int userId) {
        ArrayList<Quiz> quizzes = new ArrayList<>();
        try (PreparedStatement pst = conn.prepareStatement("SELECT * FROM quiz WHERE iduser = ?")) {
            pst.setInt(1, userId);
            try (ResultSet rs = pst.executeQuery()) {
                while (rs.next()) {
                    int id = rs.getInt("id");
                    String name = rs.getString("name");
                    int numQuestions = getNumQuestionsById(id);
                    String author = getUsernameById(userId);
                    quizzes.add(new Quiz(id, name, numQuestions, author));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return quizzes;
    }

    public ArrayList<Quiz> searchQuiz(String searchInput) {
        ArrayList<Quiz> quizzes = new ArrayList<>();
        try (PreparedStatement pst = conn.prepareStatement(
                "SELECT * FROM quiz WHERE CAST(id AS VARCHAR(10)) LIKE ? OR CAST(iduser AS VARCHAR(10)) LIKE ? OR name LIKE ?")) {
            String searchPattern = "%" + searchInput + "%";
            pst.setString(1, searchPattern);
            pst.setString(2, searchPattern);
            pst.setString(3, searchPattern);
            try (ResultSet rs = pst.executeQuery()) {
                while (rs.next()) {
                    int id = rs.getInt("id");
                    String name = rs.getString("name");
                    int numQuestions = getNumQuestionsById(id);
                    String author = getUsernameById(rs.getInt("iduser"));
                    quizzes.add(new Quiz(id, name, numQuestions, author));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return quizzes;
    }

    public ArrayList<Quiz> getLast10Quizzes() {
        ArrayList<Quiz> lastQuizzes = new ArrayList<>();
        try (PreparedStatement pst = conn.prepareStatement(
                "SELECT * FROM quiz ORDER BY created_at DESC LIMIT 10")) {
            try (ResultSet rs = pst.executeQuery()) {
                while (rs.next()) {
                    int id = rs.getInt("id");
                    String name = rs.getString("name");
                    int numQuestions = getNumQuestionsById(id);
                    String author = getUsernameById(rs.getInt("iduser"));
                    lastQuizzes.add(new Quiz(id, name, numQuestions, author));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lastQuizzes;
    }

    public int getNumQuestionsById(int quizId) {
        int numQuestions = 0;
        try (PreparedStatement pst = conn.prepareStatement("SELECT COUNT(*) FROM question WHERE quizId = ?")) {
            pst.setInt(1, quizId);
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    numQuestions = rs.getInt(1);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return numQuestions;
    }

    public boolean addQuestionToQuiz(int quizId, Question question) {
        // Check if the quiz exists
        try (PreparedStatement pstCheck = conn.prepareStatement("SELECT COUNT(*) FROM quiz WHERE id = ?")) {
            pstCheck.setInt(1, quizId);
            ResultSet rs = pstCheck.executeQuery();
            if (rs.next() && rs.getInt(1) == 0) {
                return false; // Quiz does not exist, so cannot add a question
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

        // Add the question to the quiz
        try (PreparedStatement pst = conn
                .prepareStatement("INSERT INTO questions (quiz_id, question, answercorrect) VALUES (?, ?, ?)")) {
            pst.setInt(1, quizId);
            pst.setString(2, question.getQuestion());
            pst.setInt(3, question.getCorrectAnswer());
            int result = pst.executeUpdate();
            return result > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteQuestionFromQuiz(int quizId, int questionId) {
        try (PreparedStatement pst = conn
                .prepareStatement("DELETE FROM questions WHERE quiz_id = ? AND id = ?")) {
            pst.setInt(1, quizId);
            pst.setInt(2, questionId);
            int result = pst.executeUpdate();
            return result > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateQuestionInQuiz(int quizId, Question question, int questionId) {
        try (PreparedStatement pst = conn
                .prepareStatement(
                        "UPDATE questions SET question = ?, answercorrect = ? WHERE quiz_id = ? AND id = ?")) {
            pst.setString(1, question.getQuestion());
            pst.setInt(2, question.getCorrectAnswer());
            pst.setInt(3, quizId);
            pst.setInt(4, questionId);
            int result = pst.executeUpdate();
            return result > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean addOption(int questionId, String content) {
        String sql = "INSERT INTO options ( question_id, content) VALUES ( ?, ?)";
        try (PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setInt(1, questionId);
            pst.setString(2, content);
            int result = pst.executeUpdate();
            return result > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteOption(int questionId, int optionId) {
        String sql = "DELETE FROM options WHERE question_id = ? AND id = ?";
        try (PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setInt(1, questionId);
            pst.setInt(2, optionId);
            int result = pst.executeUpdate();
            return result > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateOption(int questionId, int optionId, String content) {
        String sql = "UPDATE options SET content = ? WHERE question_id = ? AND id = ?";
        try (PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setString(1, content);
            pst.setInt(2, questionId);
            pst.setInt(3, optionId);
            int result = pst.executeUpdate();
            return result > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

}
