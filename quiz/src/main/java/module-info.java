module com.quiz {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.quiz to javafx.fxml;
    exports com.quiz;
}
