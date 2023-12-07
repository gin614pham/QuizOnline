package com.quiz.model;

import java.rmi.Remote;

public interface ServerImp extends Remote {

    public void registerClient(ClientImp client) throws Exception;

    public void unregisterClient(ClientImp client) throws Exception;

    public boolean login(String emailText, String passwordText) throws Exception;

    public boolean register(String emailText, String nameText, String passwordText) throws Exception;

}
