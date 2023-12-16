package com.quiz.controller;

import java.io.IOException;

import com.quiz.App;
import com.quiz.model.data.Quiz;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class Home {

    @FXML
    private VBox content;
    @FXML
    private HBox menu;
    @FXML
    private HBox CardNew;
    @FXML
    private HBox CardRecent;

    public void setMenu() throws IOException {
        FXMLLoader fxmlLoader = App.lFXML("components/menu");
        HBox menu = fxmlLoader.load();
        this.menu.getChildren().add(menu);
    }

    // add card to card view
    public void addCard(Quiz quiz) throws IOException {
        FXMLLoader fxmlLoader = App.lFXML("components/card");
        VBox card = fxmlLoader.load();
        Card controller = fxmlLoader.getController();
        controller.setCard(quiz);
        CardNew.getChildren().add(card);
    }

    public void addCardRecent(Quiz quiz) throws IOException {
        FXMLLoader fxmlLoader = App.lFXML("components/card");
        VBox card = fxmlLoader.load();
        Card controller = fxmlLoader.getController();
        controller.setCard(quiz);
        CardRecent.getChildren().add(card);
    }

    // get content
    public VBox getContent() {
        return content;
    }

}
