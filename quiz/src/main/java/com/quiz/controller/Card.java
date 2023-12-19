package com.quiz.controller;

import java.io.IOException;

import com.quiz.App;
import com.quiz.model.data.Quiz;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class Card {
    int id;

    @FXML
    private Label lab1;

    @FXML
    private Label sub;

    @FXML
    private Label title;

    // function handle click
    @FXML
    public void handleClick() throws IOException {
        System.out.println("clicked on card " + id);
        FXMLLoader fxmlLoader = App.lFXML("components/doQuiz");
        VBox form = fxmlLoader.load();
        DoQuiz controller = fxmlLoader.getController();
        setContent(form);
    }

    public void setCard(Quiz quiz) {
        this.id = quiz.getId();
        this.title.setText(quiz.getName());
        this.sub.setText(quiz.getAuthor());
        this.lab1.setText(String.valueOf(quiz.getNumQuestions()));
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    private void setContent(VBox content) throws IOException {
        FXMLLoader homeController = App.lFXML("screen/app/home");
        Parent root = homeController.load();
        Home home = homeController.getController();
        VBox homeContentVBox = home.getContent();
        // clear content of home and add search
        homeContentVBox.getChildren().clear();
        homeContentVBox.getChildren().add(content);
        home.setMenu();
        App.setRoot(root);
    }
}
