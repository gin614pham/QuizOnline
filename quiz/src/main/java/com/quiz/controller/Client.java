package com.quiz.controller;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import com.quiz.model.ClientImp;

public class Client extends UnicastRemoteObject implements ClientImp {

    public Client() throws RemoteException {
        super();
    }

    @Override
    public void update(String client) throws RemoteException {
        System.out.println("Update: " + client);
    }
    // @Override
    // public void update(String client) {
    // System.out.println("Update: " + client);
    // }

}
