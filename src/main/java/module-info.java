module com.example.rsa {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.google.common;


    opens com.example.rsa to javafx.fxml;
    exports com.example.rsa;
}