package com.manuco.manuco;

import com.manuco.manuco.Controller.SceneController;
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

    SceneController sceneController = SceneController.getSceneControllerInstance();

    /**
     * Launch point for JavaFX; creates main stage
     * @param stage - the top level JavaFX container for all scenes
     * @throws IOException - FXMLLoader.load throws I/O if fxml file is not found.
     */
    @Override
    public void start(Stage stage) throws IOException {

        sceneController.setInitialStageAndScene(stage);

    }

    /**
     * Entry point for application
     * @param args - arguments to launch application
     */
    public static void main(String[] args) {
        launch();
    }
}