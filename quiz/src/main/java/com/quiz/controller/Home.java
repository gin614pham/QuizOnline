package com.quiz.controller;

import java.io.IOException;

import com.quiz.App;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class Home {

    @FXML
    private HBox cardview;

    // add card to card view
    public void addCard(Card card) throws IOException {
        FXMLLoader fxmlLoader = App.lFXML("components/card");
        VBox cardd = fxmlLoader.load();
        Card controller = fxmlLoader.getController();
        controller.setCard(card);
        cardview.getChildren().add(cardd);

    }

}
