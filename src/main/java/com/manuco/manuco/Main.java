package com.manuco.manuco;

import com.manuco.manuco.Controller.StageController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Application launch point
 */

public class Main extends Application {

    /**
     * Create single StageController instance to use to change scenes
     */
    StageController stageController = StageController.getStageManagerInstance();

    /**
     * Launch point for Inventory Management System for ManuCo
     * @param stage - the top level JavaFX container for all scenes
     * @throws - FXMLLoader.load throws I/O if fxml file is not found.
     */
    @Override
    public void start(Stage stage) throws IOException {
        try {
            Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("MainView.fxml"));
            Scene scene = new Scene(root);
            scene.getStylesheets().add("style.css");
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args) {
        launch();
    }
}