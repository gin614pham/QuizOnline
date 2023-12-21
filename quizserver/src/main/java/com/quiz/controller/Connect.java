package com.quiz.controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;

import com.quiz.model.data.Answer;
import com.quiz.model.data.AnswerQuiz;
import com.quiz.model.data.Option;
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

    public String getEmailById(int id) {
        try (PreparedStatement pst = conn.prepareStatement("SELECT email FROM user WHERE id = ?")) {
            pst.setInt(1, id);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                return rs.getString("email");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public int addQuiz(Quiz quiz) {
        try (PreparedStatement pst = conn.prepareStatement("INSERT INTO quiz ( iduser, name) VALUES (?, ?)",
                Statement.RETURN_GENERATED_KEYS)) {
            pst.setInt(1, quiz.getIdAuthor());
            pst.setString(2, quiz.getName());
            int result = pst.executeUpdate();
            // get the id of the inserted quiz
            if (result == 0) {
                return -1;
            }
            ResultSet rs = pst.getGeneratedKeys();
            if (!rs.next()) {
                return -1;
            }
            return rs.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
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
                    String dateCreated = rs.getDate("created_at").toString();
                    quizzes.add(new Quiz(id, name, numQuestions, author, userId, dateCreated));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return quizzes;
    }

    public String getQuizNameById(int quizId) {
        String quizName = "";
        try (PreparedStatement pst = conn.prepareStatement("SELECT name FROM quiz WHERE id = ?")) {
            pst.setInt(1, quizId);
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    quizName = rs.getString("name");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return quizName;
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
                    int iduser = rs.getInt("iduser");
                    String author = getUsernameById(iduser);
                    String dateCreated = rs.getDate("created_at").toString();
                    quizzes.add(new Quiz(id, name, numQuestions, author, iduser, dateCreated));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return quizzes;
    }

    public ArrayList<Quiz> searchQuizByIdUser(int iduser, String search) {
        ArrayList<Quiz> quizzes = new ArrayList<>();
        try (PreparedStatement pst = conn.prepareStatement(
                "SELECT * FROM quiz WHERE iduser = ? AND (CAST(id AS VARCHAR(10)) LIKE ? OR name LIKE ?)")) {

            pst.setInt(1, iduser);
            String searchPattern = "%" + search + "%";
            pst.setString(2, searchPattern);
            pst.setString(3, searchPattern);

            try (ResultSet rs = pst.executeQuery()) {
                while (rs.next()) {
                    int id = rs.getInt("id");
                    String name = rs.getString("name");
                    int numQuestions = getNumQuestionsById(id);
                    String author = getUsernameById(rs.getInt("iduser"));
                    String dateCreated = rs.getDate("created_at").toString();
                    quizzes.add(new Quiz(id, name, numQuestions, author, iduser, dateCreated));
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
                    int iduser = rs.getInt("iduser");
                    String author = getUsernameById(iduser);
                    String dateCreated = rs.getDate("created_at").toString();
                    lastQuizzes.add(new Quiz(id, name, numQuestions, author, iduser, dateCreated));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lastQuizzes;
    }

    public int getNumQuestionsById(int quizId) {
        int numQuestions = 0;
        try (PreparedStatement pst = conn.prepareStatement("SELECT COUNT(*) FROM question WHERE idquiz = ?")) {
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
            if (!rs.next() || rs.getInt(1) == 0) {
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

        // Add the question to the quiz
        try (PreparedStatement pst = conn
                .prepareStatement("INSERT INTO question (idquiz, question, answercorrect) VALUES (?, ?, ?)",
                        Statement.RETURN_GENERATED_KEYS)) {
            pst.setInt(1, quizId);
            pst.setString(2, question.getQuestion());
            pst.setInt(3, question.getCorrectAnswer());
            int result = pst.executeUpdate();
            if (result > 0) {
                try {
                    ResultSet rs = pst.getGeneratedKeys();
                    if (rs.next()) {
                        question.setId(rs.getInt(1));
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                    return false;
                }
                for (Option answer : question.getAnswers()) {
                    int answerId = addAnswerToQuestion(question.getId(), answer.getContent());
                    if (question.getCorrectAnswer() == question.getAnswers().indexOf(answer)) {
                        question.setCorrectAnswer(answerId);
                    }
                }
                updateQuestionInQuiz(quizId, question, question.getId());
                return true;
            }
            return false;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteQuestionFromQuiz(int quizId, int questionId) {
        try (PreparedStatement pst = conn
                .prepareStatement("DELETE FROM question WHERE idquiz = ? AND id = ?")) {
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
                        "UPDATE question SET question = ?, answercorrect = ? WHERE idquiz = ? AND id = ?")) {
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

    public ArrayList<Question> getQuestionsByQuizId(int quizId) {
        ArrayList<Question> questions = new ArrayList<>();
        try (PreparedStatement pst = conn.prepareStatement("SELECT * FROM question WHERE idquiz = ?")) {
            pst.setInt(1, quizId);
            try (ResultSet rs = pst.executeQuery()) {
                while (rs.next()) {
                    int id = rs.getInt("id");
                    String question = rs.getString("question");
                    int correctAnswer = rs.getInt("answercorrect");
                    ArrayList<Option> answers = getAnswersByQuestionId(id);
                    questions.add(new Question(id, quizId, question, answers, correctAnswer));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return questions;
    }

    public ArrayList<Option> getAnswersByQuestionId(int questionId) {
        ArrayList<Option> answers = new ArrayList<>();
        try (PreparedStatement pst = conn.prepareStatement("SELECT * FROM options WHERE idquestion = ?")) {
            pst.setInt(1, questionId);
            try (ResultSet rs = pst.executeQuery()) {
                while (rs.next()) {
                    answers.add(new Option(rs.getInt("id"), rs.getString("content")));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return answers;
    }

    public int addAnswerToQuestion(int questionId, String content) {
        String sql = "INSERT INTO options ( idquestion, content) VALUES ( ?, ?)";
        try (PreparedStatement pst = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pst.setInt(1, questionId);
            pst.setString(2, content);
            int result = pst.executeUpdate();
            if (result > 0) {
                try (ResultSet rs = pst.getGeneratedKeys()) {
                    if (rs.next()) {
                        return rs.getInt(1);
                    }
                }
            }
            return -1;
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }

    public boolean deleteOption(int questionId, int optionId) {
        String sql = "DELETE FROM options WHERE idquestion = ? AND id = ?";
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
        String sql = "UPDATE options SET content = ? WHERE idquestion = ? AND id = ?";
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

    public Answer doQuiz(int userId, int quizId, ArrayList<AnswerQuiz> answers) throws SQLException {
        String sql = "INSERT INTO answer (iduser, idquiz, point, numc) VALUES (?, ?, ?, ?)";
        int numc = 0;
        double point = 0;
        int idAnswer = 0;
        try (PreparedStatement pst = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pst.setInt(1, userId);
            pst.setInt(2, quizId);
            pst.setInt(3, 0);
            pst.setInt(4, 0);
            int result = pst.executeUpdate();
            if (result > 0) {
                try (ResultSet rs = pst.getGeneratedKeys()) {
                    if (rs.next()) {
                        idAnswer = rs.getInt(1);

                        for (AnswerQuiz answer : answers) {
                            addAnswer(userId, idAnswer, answer);
                            if (answer.getSelectedAnswer() == getCorrectAnswerByQuestionId(answer.getIdQuestion())) {
                                numc++;
                            }
                        }

                    }
                }
            }
        }
        String correct = numc + "/" + answers.size();
        point = 10.00 / answers.size() * numc;
        sql = "UPDATE answer SET numc = ?, point = ? WHERE id = ?";
        try (PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setString(1, correct);
            pst.setDouble(2, point);
            pst.setInt(3, idAnswer);
            pst.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        Answer answer = new Answer();
        answer.setId(idAnswer);
        answer.setIdUser(userId);
        answer.setIdQuiz(quizId);
        answer.setNumCorrect(correct);
        answer.setPoint(point);
        return answer;
    }

    public boolean addAnswer(int idUser, int idAnswer, AnswerQuiz answer) {
        String sql = "INSERT INTO user_answer (iduser, idanswer, idquestion, selected) VALUES (?, ?, ?, ?)";
        try (PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setInt(1, idUser);
            pst.setInt(2, idAnswer);
            pst.setInt(3, answer.getIdQuestion());
            pst.setInt(4, answer.getSelectedAnswer());
            int result = pst.executeUpdate();
            return result > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public int getCorrectAnswerByQuestionId(int questionId) throws SQLException {
        String sql = "SELECT answercorrect FROM question WHERE id = ?";
        try (PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setInt(1, questionId);
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("answercorrect");
                }
            }
        }
        return -1; // or throw an exception if appropriate
    }

    public ArrayList<Answer> getHistoryByUserId(int userId) throws SQLException {
        ArrayList<Answer> list = new ArrayList<>();
        String sql = "SELECT * FROM answer WHERE iduser = ?";
        try (PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setInt(1, userId);
            try (ResultSet rs = pst.executeQuery()) {
                while (rs.next()) {
                    Answer answer = new Answer();
                    answer.setId(rs.getInt("id"));
                    answer.setIdUser(rs.getInt("iduser"));
                    answer.setIdQuiz(rs.getInt("idquiz"));
                    answer.setNumCorrect(rs.getString("numc"));
                    answer.setPoint(rs.getDouble("point"));
                    answer.setDateCreated(rs.getTimestamp("create_at").toString());
                    answer.setNameUser(getUsernameById(rs.getInt("iduser")));
                    answer.setNameQuiz(getQuizNameById(rs.getInt("idquiz")));
                    answer.setEmail(getEmailById(rs.getInt("iduser")));
                    list.add(answer);
                }
            }
        }
        return list;
    }

    public ArrayList<Answer> getHistoryByQuizId(int quizId) throws SQLException {
        ArrayList<Answer> list = new ArrayList<>();
        String sql = "SELECT * FROM answer WHERE idquiz = ?";
        try (PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setInt(1, quizId);
            try (ResultSet rs = pst.executeQuery()) {
                while (rs.next()) {
                    Answer answer = new Answer();
                    answer.setId(rs.getInt("id"));
                    answer.setIdUser(rs.getInt("iduser"));
                    answer.setIdQuiz(rs.getInt("idquiz"));
                    answer.setNumCorrect(rs.getString("numc"));
                    answer.setPoint(rs.getDouble("point"));
                    answer.setDateCreated(rs.getTimestamp("create_at").toString());
                    answer.setNameUser(getUsernameById(rs.getInt("iduser")));
                    answer.setNameQuiz(getQuizNameById(rs.getInt("idquiz")));
                    answer.setEmail(getEmailById(rs.getInt("iduser")));
                    list.add(answer);
                }
            }
        }

        return list;
    }

    public ArrayList<Quiz> getTopQuizzesWithMostAnswers() throws SQLException {
        ArrayList<Quiz> topQuizzes = new ArrayList<>();
        String sql = "SELECT q.*, COUNT(a.id) as answer_count FROM quiz q " +
                "JOIN answer a ON q.id = a.idquiz " +
                "GROUP BY q.id ORDER BY answer_count DESC LIMIT 10";
        try (PreparedStatement pst = conn.prepareStatement(sql)) {
            try (ResultSet rs = pst.executeQuery()) {
                while (rs.next()) {
                    int id = rs.getInt("id");
                    String name = rs.getString("name");
                    int numQuestions = getNumQuestionsById(id);
                    int iduser = rs.getInt("iduser");
                    String author = getUsernameById(iduser);
                    String dateCreated = rs.getDate("created_at").toString();
                    topQuizzes.add(new Quiz(id, name, numQuestions, author, iduser, dateCreated));
                }
            }
        }
        return topQuizzes;
    }

}
