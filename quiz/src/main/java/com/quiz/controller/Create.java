package com.quiz.controller;

import com.quiz.App;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class Create {

    @FXML
    private Button btnCreate;

    @FXML
    private VBox contentCreate;

    @FXML
    private Label userLabel;

    @FXML
    public void create() throws Exception {
        System.out.println("create");
    }

    @FXML
    public void addForm() throws Exception {
        System.out.println("add form");
        FXMLLoader fxmlLoader = App.lFXML("components/formCreate");
        VBox form = fxmlLoader.load();
        contentCreate.getChildren().add(form);
    }

}
