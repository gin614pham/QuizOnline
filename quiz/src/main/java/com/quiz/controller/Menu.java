package com.quiz.controller;

import java.io.IOException;
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
        Parent root = null;
        // handle click
        switch (id) {
            case "homeBtn":
                System.out.println("clicked on button: " + id);

                fxmlLoader = App.lFXML("screen/app/home");
                root = fxmlLoader.load();
                Home home = fxmlLoader.getController();
                // create dummy data
                ArrayList<Quiz> list = App.getServer().getQuizzes();
                for (Quiz quiz : list) {
                    home.addCard(quiz);
                    home.addCardRecent(quiz);
                }

                home.setMenu();
                App.setRoot(root);
                break;
            case "createBtn":
                System.out.println("clicked on button: " + id);

                fxmlLoader = App.lFXML("components/create");
                VBox create = fxmlLoader.load();
                Create Create_controller = fxmlLoader.getController();
                Create_controller.addForm();
                setMenu(create);
                break;
            case "storeBtn":
                System.out.println("clicked on button: " + id);
                ArrayList<Quiz> quizList = App.getServer().search("");

                fxmlLoader = App.lFXML("components/store");
                VBox store = fxmlLoader.load();
                Store controller = fxmlLoader.getController();
                controller.setData("Store", quizList);
                setMenu(store);
                break;
        }
    }

    @FXML
    private void search() throws Exception {
        // get input from text field
        String input = searchInput.getText();
        // check if input is empty
        if (input.isEmpty()) {
            return;
        }
        // handle click
        System.out.println("Search: " + input);
        // dummy array of quiz
        ArrayList<Quiz> quizList = App.getServer().search(input);

        // set content to search component
        FXMLLoader fxmlLoader = App.lFXML("components/search");
        VBox search = fxmlLoader.load();
        Search controller = fxmlLoader.getController();
        controller.setData(input, quizList);
        setMenu(search);

    }

    private void setMenu(VBox search) throws Exception {
        FXMLLoader homeController = App.lFXML("screen/app/home");
        Parent root = homeController.load();
        Home home = homeController.getController();
        VBox homeContentVBox = home.getContent();
        // clear content of home and add search
        homeContentVBox.getChildren().clear();
        homeContentVBox.getChildren().add(search);
        home.setMenu();
        App.setRoot(root);
    }
}
