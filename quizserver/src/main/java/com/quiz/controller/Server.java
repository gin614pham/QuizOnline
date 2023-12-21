package com.quiz.controller;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Collections;

import com.quiz.model.ClientImp;
import com.quiz.model.ServerImp;
import com.quiz.model.data.Answer;
import com.quiz.model.data.AnswerQuiz;
import com.quiz.model.data.Question;
import com.quiz.model.data.Quiz;
import com.quiz.model.data.User;

public class Server extends UnicastRemoteObject implements ServerImp {
    private ArrayList<ClientImp> clients;
    private Connect connect;

    public Server() throws RemoteException {
        clients = new ArrayList<>();
        connect = new Connect();
    }

    public void registerClient(ClientImp client) throws Exception {
        System.out.println("Register client: " + client);
        clients.add(client);
        client.update("Hello, " + client);
    }

    public void unregisterClient(ClientImp client) throws Exception {
        System.out.println("Unregister client: " + client);
        clients.remove(client);
    }

    @Override
    public User login(String email, String password) throws Exception {
        System.out.println("Login: " + email + ", " + password);
        User userLogin = connect.login(email, password);
        return userLogin;
    }

    @Override
    public User register(String email, String password, String name) throws Exception {
        System.out.println("Register: " + email + ", " + password + ", " + name);
        if (connect.register(email, password, name)) {
            return connect.login(email, password);
        }
        return null;
    }

    @Override
    public ArrayList<Quiz> getLast10Quizzes() throws Exception {

        ArrayList<Quiz> list = new ArrayList<>();
        connect.getLast10Quizzes().forEach((quiz) -> list.add(quiz));
        return list;
    }

    @Override
    public ArrayList<Quiz> search(String label) throws Exception {

        ArrayList<Quiz> list = connect.searchQuiz(label);

        return list;
    }

    @Override
    public boolean addQuiz(Quiz quiz, ArrayList<Question> questions) throws Exception {

        quiz.setId(connect.addQuiz(quiz));
        if (quiz.getId() == -1) {
            return false;
        }

        for (Question question : questions) {
            if (!connect.addQuestionToQuiz(quiz.getId(), question)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public ArrayList<Quiz> getQuizzesByUserId(int userId) throws Exception {

        ArrayList<Quiz> list = new ArrayList<>();
        connect.getQuizzesByUserId(userId).forEach((quiz) -> list.add(quiz));
        return list;
    }

    @Override
    public ArrayList<Quiz> search(int userId, String label) throws Exception {

        ArrayList<Quiz> list = new ArrayList<>();
        connect.searchQuizByIdUser(userId, label).forEach((quiz) -> list.add(quiz));
        return list;
    }

    @Override
    public ArrayList<Question> getQuestionsByQuizId(int quizId) throws Exception {

        ArrayList<Question> list = new ArrayList<>();
        connect.getQuestionsByQuizId(quizId).forEach((question) -> list.add(question));
        Collections.shuffle(list);
        return list;
    }

    @Override
    public Answer doQuiz(int userId, int quizId, ArrayList<AnswerQuiz> answers) throws Exception {

        return connect.doQuiz(userId, quizId, answers);
    }

    @Override
    public ArrayList<Answer> getHistoryByUserId(int userId) throws Exception {

        ArrayList<Answer> list = new ArrayList<>();
        connect.getHistoryByUserId(userId).forEach((answer) -> list.add(answer));
        return list;
    }

    @Override
    public ArrayList<Answer> getHistoryByQuizId(int quizId) throws Exception {
        return connect.getHistoryByQuizId(quizId);
    }

    @Override
    public ArrayList<Quiz> getTopQuizzesWithMostAnswers() throws Exception {

        ArrayList<Quiz> list = new ArrayList<>();
        connect.getTopQuizzesWithMostAnswers().forEach((quiz) -> list.add(quiz));
        return list;
    }

    @Override
    public boolean deleteQuiz(int quizId) throws Exception {
        return connect.deleteQuiz(quizId);
    }

    @Override
    public boolean updateQuiz(Quiz quiz, ArrayList<Question> questions) throws Exception {
        connect.updateQuiz(quiz, quiz.getId());
        if (!connect.deletaAllQuestionByQuizId(quiz.getId())) {
            System.out.println("Error delete all question");
            return false;
        }
        for (Question question : questions) {
            if (!connect.addQuestionToQuiz(quiz.getId(), question)) {
                System.out.println("Error add question");
                return false;
            }
        }
        return true;
    }

}
