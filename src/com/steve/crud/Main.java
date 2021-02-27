package com.steve.crud;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 * The Main method extends java Application library and contains the main method to run launch command required by
 * JavaFX library.
 */

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("login.fxml"));
        primaryStage.setTitle("Cat Dating App");
        primaryStage.setScene(new Scene(root, 800, 500));
        primaryStage.getIcons().add(new Image("/Images/cat_icon_138789.png"));

        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
