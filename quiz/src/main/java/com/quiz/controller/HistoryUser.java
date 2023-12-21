package com.quiz.controller;

import java.io.IOException;
import java.util.ArrayList;

import com.quiz.App;
import com.quiz.model.data.Answer;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;

public class HistoryUser {

    @FXML
    private ListView<HBox> contentHistory;

    @FXML
    private Label noHistory;

    public void setHistory(ArrayList<Answer> list) throws IOException {
        if (list.size() == 0) {
            noHistory.setVisible(true);
            noHistory.setText("No history");
            return;
        }
        noHistory.setVisible(false);
        for (Answer a : list) {
            if (list.indexOf(a) == 0) {
                FXMLLoader fxmlLoader = App.lFXML("components/historyCard");
                HBox root = fxmlLoader.load();
                HistoryCard controller = fxmlLoader.getController();
                controller.setTitle();
                contentHistory.getItems().add(root);
            }
            FXMLLoader fxmlLoader = App.lFXML("components/historyCard");
            HBox root = fxmlLoader.load();
            HistoryCard controller = fxmlLoader.getController();
            controller.setData(a);
            contentHistory.getItems().add(root);
        }
    }

}
