package com.quiz.model;

import java.rmi.Remote;

import com.quiz.model.data.Quiz;
import com.quiz.model.data.User;
import java.util.ArrayList;

public interface ServerImp extends Remote {

    public void registerClient(ClientImp client) throws Exception;

    public void unregisterClient(ClientImp client) throws Exception;

    public User login(String email, String password) throws Exception;

    public User register(String email, String password, String name) throws Exception;

    public ArrayList<Quiz> getLast10Quizzes() throws Exception;

    public ArrayList<Quiz> search(String label) throws Exception;
}
