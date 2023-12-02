package com.quiz.model;

import java.rmi.Remote;

public interface ClientImp extends Remote {

    // void update(String client);

    public void update(String client) throws Exception;

}
