package com.quiz.controller;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

import com.quiz.model.ClientImp;
import com.quiz.model.ServerImp;

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
    public boolean login(String emailText, String passwordText) throws Exception {
        System.out.println("Login: " + emailText + ", " + passwordText);
        return true;
    }

    @Override
    public boolean register(String emailText, String nameText, String passwordText) throws Exception {
        System.out.println("Register: " + emailText + ", " + nameText + ", " + passwordText);
        return true;
    }

}
