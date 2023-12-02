package com.quiz.model;

import java.rmi.Remote;

public interface ClientImp extends Remote {

    public void update(String client) throws Exception;

}
