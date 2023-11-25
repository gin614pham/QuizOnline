package com.quiz.controller;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

import com.quiz.model.ClientImp;
import com.quiz.model.ServerImp;
import com.quiz.model.data;

public class Server extends UnicastRemoteObject implements ServerImp {
    private ArrayList<data> clients;

    public Server() throws RemoteException {
        clients = new ArrayList<>();
    }

    public void registerClient(ClientImp client) throws Exception {
        System.out.println("Register client: " + client);
        for (data clientImp : clients) {
            clientImp.getClient().update(client.toString());
        }
        clients.add(new data(client));
    }

    public void unregisterClient(ClientImp client) throws Exception {
        System.out.println("Unregister client: " + client);
        clients.remove(new data(client));
    }

}
