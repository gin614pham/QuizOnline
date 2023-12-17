package com.quiz.controller;

import java.io.IOException;
import java.util.ArrayList;
import com.quiz.App;
import com.quiz.model.data.Quiz;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

public class Store {

    @FXML
    private VBox contentStore;

    @FXML
    private Label userLabel;

    @FXML
    private ImageView searchBtn;

    @FXML
    private TextField searchInput;

    private ArrayList<Quiz> list;

    public void setData(String label, ArrayList<Quiz> list) throws IOException {
        this.userLabel.setText(label);
        this.list = list;
        for (Quiz quiz : list) {
            FXMLLoader fxmlLoader = App.lFXML("components/cardSearch");
            VBox card = fxmlLoader.load();
            CardSearch controller = fxmlLoader.getController();
            controller.setCard(quiz);
            contentStore.getChildren().add(card);
        }
    }

    @FXML
    public void search() throws Exception {
        String label = searchInput.getText();
        ArrayList<Quiz> list = App.getServer().search(label);
        setData(label, list);
    }

}
