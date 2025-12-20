module com.example.client {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.client to javafx.fxml;
    exports com.example.client;
    exports windowControllers;
    opens windowControllers to javafx.fxml;
    exports windowControllers.menus;
    opens windowControllers.menus to javafx.fxml;
    exports windowControllers.reviews;
    opens windowControllers.reviews to javafx.fxml;
    exports windowControllers.workWithProfile;
    opens windowControllers.workWithProfile to javafx.fxml;
    exports windowControllers.afisha;
    opens windowControllers.afisha to javafx.fxml;
    exports windowControllers.movies;
    opens windowControllers.movies to javafx.fxml;
    exports windowControllers.cinemas;
    opens windowControllers.cinemas to javafx.fxml;
    exports windowControllers.halls;
    opens windowControllers.halls to javafx.fxml;
    exports windowControllers.seances;
    opens windowControllers.seances to javafx.fxml;
    exports windowControllers.users;
    opens windowControllers.users to javafx.fxml;
    exports windowControllers.tickets;
    opens windowControllers.tickets to javafx.fxml;

    opens windowCoolTables to javafx.base;
    exports windowCoolTables;

    opens entities to javafx.base;
    exports entities;
}