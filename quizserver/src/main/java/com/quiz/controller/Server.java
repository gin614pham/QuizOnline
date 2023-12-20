package com.quiz.controller;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Arrays;

import com.quiz.controller.database.Auth;
import com.quiz.model.ClientImp;
import com.quiz.model.ServerImp;
import com.quiz.model.data.Question;
import com.quiz.model.data.Quiz;
import com.quiz.model.data.User;

public class Server extends UnicastRemoteObject implements ServerImp {
    private ArrayList<ClientImp> clients;
    private Auth auth;

    public Server() throws RemoteException {
        clients = new ArrayList<>();
        auth = new Auth();
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
    public boolean login(User user) throws Exception {
        System.out.println("Login: " + user.getEmail() + ", " + user.getPassword());
        User userLogin = auth.login(user.getEmail(), user.getPassword());
        return userLogin != null;
    }

    @Override
    public boolean register(User user) throws Exception {
        System.out.println("Register: " + user.getEmail() + ", " + user.getPassword() + ", " + user.getName());
        return auth.register(user);
    }

    @Override
    public ArrayList<Quiz> getQuizzes() throws Exception {
        ArrayList<Question> dummyListQuestions = new ArrayList<Question>();
        dummyListQuestions.add(new Question(1, 1, "Question 1",
                new ArrayList<String>(Arrays.asList("Answer 1", "Answer 2", "Answer 3", "Answer 4")), 1));
        dummyListQuestions.add(new Question(2, 1, "Question 2",
                new ArrayList<String>(Arrays.asList("Answer 1", "Answer 2", "Answer 3", "Answer 4")), 1));
        Quiz dummyQuiz = new Quiz(1, "Quiz 1", 10, "admin");
        dummyQuiz.setQuestions(dummyListQuestions);
        ArrayList<Quiz> dummyList = new ArrayList<Quiz>();
        dummyList.add(dummyQuiz);
        dummyList.add(dummyQuiz);
        dummyList.add(dummyQuiz);
        dummyList.add(dummyQuiz);
        dummyList.add(dummyQuiz);
        return dummyList;
    }

    @Override
    public ArrayList<Quiz> search(String label) throws Exception {
        ArrayList<Question> dummyListQuestions = new ArrayList<Question>();
        dummyListQuestions.add(new Question(1, 1, "Question 1",
                new ArrayList<String>(Arrays.asList("Answer 1", "Answer 2", "Answer 3", "Answer 4")), 1));
        dummyListQuestions.add(new Question(2, 1, "Question 2",
                new ArrayList<String>(Arrays.asList("Answer 1", "Answer 2", "Answer 3", "Answer 4")), 1));
        Quiz dummyQuiz = new Quiz(1, "Quiz 1", 10, "admin");
        dummyQuiz.setQuestions(dummyListQuestions);
        ArrayList<Quiz> dummyList = new ArrayList<Quiz>();
        dummyList.add(dummyQuiz);
        dummyList.add(dummyQuiz);
        dummyList.add(dummyQuiz);
        dummyList.add(dummyQuiz);
        dummyList.add(dummyQuiz);

        return dummyList;
    }

}
