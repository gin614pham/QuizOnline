package com.quiz.controller;

import java.util.ArrayList;

import com.quiz.App;
import com.quiz.model.data.Quiz;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

public class Menu {

    @FXML
    private Button createBtn;

    @FXML
    private Button homeBtn;

    @FXML
    private TextField searchInput;

    @FXML
    private ImageView searchBtn;

    @FXML
    private Button storeBtn;

    @FXML
    private void handleButtonAction(ActionEvent event) throws Exception {
        // get id from button
        String id = ((Button) event.getSource()).getId();
        FXMLLoader fxmlLoader = null;
        // handle click
        switch (id) {
            case "homeBtn":
                App.returnHome();
                break;
            case "createBtn":
                fxmlLoader = App.lFXML("components/create");
                VBox create = fxmlLoader.load();
                Create Create_controller = fxmlLoader.getController();
                Create_controller.addForm();
                setContent(create);
                break;
            case "storeBtn":
                ArrayList<Quiz> quizList = App.getServer().getQuizzesByUserId(App.getUser().getId());
                fxmlLoader = App.lFXML("components/store");
                VBox store = fxmlLoader.load();
                Store controller = fxmlLoader.getController();
                controller.setData("Store", quizList);
                setContent(store);
                break;
        }
    }

    @FXML
    private void search() throws Exception {
        String input = searchInput.getText();
        if (input.isEmpty()) {
            return;
        }

        ArrayList<Quiz> quizList = App.getServer().search(input);

        // set content to search component
        FXMLLoader fxmlLoader = App.lFXML("components/search");
        VBox search = fxmlLoader.load();
        Search controller = fxmlLoader.getController();
        controller.setData(input, quizList);
        setContent(search);

    }

    private void setContent(VBox vbox) throws Exception {
        FXMLLoader homeController = App.lFXML("screen/app/home");
        Parent root = homeController.load();
        Home home = homeController.getController();
        VBox homeContentVBox = home.getContent();
        // clear content of home and add search
        homeContentVBox.getChildren().clear();
        homeContentVBox.getChildren().add(vbox);
        home.setMenu();
        App.setRoot(root);
    }
}
