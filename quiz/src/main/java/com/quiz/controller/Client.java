package com.quiz.controller;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import com.quiz.model.ClientImp;
import com.quiz.model.data;

public class Client extends UnicastRemoteObject implements ClientImp {

    public Client() throws RemoteException {
        super();
    }

    @Override
    public void update(String client) {
        System.out.println("Update: " + client);
    }

}
