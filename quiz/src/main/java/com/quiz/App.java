package com.quiz;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.io.IOException;
import java.rmi.Naming;

import com.quiz.controller.Client;
import com.quiz.controller.Home;
import com.quiz.model.ClientImp;
import com.quiz.model.ServerImp;
import com.quiz.model.data.Quiz;
import com.quiz.model.data.User;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;
    private static ServerImp server;
    private static User user;

    @Override
    public void start(Stage stage) throws IOException {

        try {
            server = (ServerImp) Naming.lookup("//localhost/Quiz");
            ClientImp client = new Client();
            server.registerClient(client);
            scene = new Scene(loadFXML("screen/auth/login"));
            stage.setResizable(false);
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Connection Error");
            alert.setHeaderText(null);
            alert.setContentText("Cannot connect to server:" + e.getMessage());

            alert.showAndWait();

        }
    }

    public static void returnHome() throws Exception {
        FXMLLoader fxmlLoader = lFXML("screen/app/home");
        Parent root = fxmlLoader.load();
        Home home = fxmlLoader.getController();
        home.setMenu();
        for (Quiz quiz : getServer().getLast10Quizzes()) {
            home.addCard(quiz);
            home.addCardRecent(quiz);
        }
        setRoot(root);

    }

    public static void setRoot(Parent root) throws IOException {
        scene.setRoot(root);
        scene.getWindow().sizeToScene();
        scene.getWindow().centerOnScreen();
    }

    public static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static FXMLLoader lFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader;
    }

    public static ServerImp getServer() {
        return server;
    }

    public static User getUser() {
        return user;
    }

    public static void setUser(User user) {
        App.user = user;
    }

    public static void showDialog(String string, String string2) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(string);
        alert.setHeaderText(null);
        alert.setContentText(string2);
        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch();
    }

}