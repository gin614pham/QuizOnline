package com.quiz.model;

import java.io.Serializable;

public class data implements Serializable {
    private ClientImp client;

    public data(ClientImp client) {
        this.client = client;
    }

    public ClientImp getClient() {
        return client;
    }

    public void setClient(ClientImp client) {
        this.client = client;
    }
}
