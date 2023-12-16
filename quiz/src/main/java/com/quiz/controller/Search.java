package com.quiz.controller;

import java.io.IOException;
import java.util.ArrayList;

import com.quiz.App;
import com.quiz.model.data.Quiz;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class Search {

    @FXML
    private VBox contentSearch;

    @FXML
    private Label searchLabel;

    public void setData(String label, ArrayList<Quiz> list) throws IOException {
        this.searchLabel.setText("Search result for: " + label);
        for (Quiz quiz : list) {
            FXMLLoader fxmlLoader = App.lFXML("components/cardSearch");
            VBox card = fxmlLoader.load();
            CardSearch controller = fxmlLoader.getController();
            controller.setCard(quiz);
            contentSearch.getChildren().add(card);
        }
    }

}
