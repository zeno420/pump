module pump.main {
    requires javafx.base;
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires jakarta.xml.bind;
    requires com.sun.xml.bind;

    exports main;
    exports controller;
    exports design;
    exports domain;
    exports persistence;

    opens domain to jakarta.xml.bind;
    opens controller to javafx.fxml;
}
