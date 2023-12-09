package com.quiz;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.Parent;

public class PrimaryController {

    @FXML
    private void switchToSecondary() throws IOException {
        Parent root = App.loadFXML("secondary");
        App.setRoot(root);
    }
}
