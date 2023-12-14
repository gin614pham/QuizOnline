package com.quiz.controller;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

import com.quiz.model.ClientImp;
import com.quiz.model.ServerImp;
import com.quiz.model.data.Quiz;
import com.quiz.model.data.User;

public class Server extends UnicastRemoteObject implements ServerImp {
    private ArrayList<ClientImp> clients;

    public Server() throws RemoteException {
        clients = new ArrayList<>();
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
        return true;
    }

    @Override
    public boolean register(User user) throws Exception {
        System.out.println("Register: " + user.getEmail() + ", " + user.getPassword() + ", " + user.getName());
        return true;
    }

    @Override
    public ArrayList<Quiz> getQuizzes() throws Exception {
        ArrayList<Quiz> dummyList = new ArrayList<Quiz>();
        dummyList.add(new Quiz(1, "Quiz 1", 10, "admin"));
        dummyList.add(new Quiz(2, "Quiz 2", 10, "admin"));
        dummyList.add(new Quiz(3, "Quiz 3", 10, "admin"));
        dummyList.add(new Quiz(4, "Quiz 4", 10, "admin"));
        dummyList.add(new Quiz(5, "Quiz 5", 10, "admin"));
        return dummyList;
    }

}
