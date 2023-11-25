package com.quiz.server.controller;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

import com.quiz.server.model.ClientImp;
import com.quiz.server.model.ServerImp;

public class Server extends UnicastRemoteObject implements ServerImp {
    private ArrayList<ClientImp> clients;

    public Server() throws RemoteException {
        clients = new ArrayList<>();
    }

    public void registerClient(ClientImp client) throws Exception {

        clients.add(client);
    }

    public void unregisterClient(ClientImp client) throws Exception {

        clients.remove(client);
    }

}
