package com.manuco.manuco;

import com.manuco.manuco.Controller.SceneController;
import javafx.application.Application;
import javafx.stage.Stage;

/**
 * Application launch point
 */

public class Launch extends Application {

    SceneController sceneController = SceneController.getSceneControllerInstance();

    /**
     * Launch point for JavaFX; creates main stage
     * @param stage - the top level JavaFX container for all scenes
     */
    @Override
    public void start(Stage stage)  {

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