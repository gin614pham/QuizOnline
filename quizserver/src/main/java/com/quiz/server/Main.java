package com.quiz.server;

import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;

import com.quiz.controller.Server;
import com.quiz.model.ServerImp;

public class Main {
    public static void main(String[] args) {

        try {
            ServerImp server = new Server();
            LocateRegistry.createRegistry(1099);
            Naming.bind("//localhost/Quiz", server);
            System.out.println("Server ready");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}