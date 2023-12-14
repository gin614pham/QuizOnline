package com.quiz.model;

import java.rmi.Remote;

import com.quiz.model.data.Quiz;
import com.quiz.model.data.User;
import java.util.ArrayList;

public interface ServerImp extends Remote {

    public void registerClient(ClientImp client) throws Exception;

    public void unregisterClient(ClientImp client) throws Exception;

    public boolean login(User user) throws Exception;

    public boolean register(User user) throws Exception;

    public ArrayList<Quiz> getQuizzes() throws Exception;
}
