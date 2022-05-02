package com.manuco.manuco.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * SceneController implements the Singleton pattern to ensure only one instance is created and used to manage
 * the scene currently presented to the user.
 */

public class SceneController {

    /**
     * Create single instance of SceneController object
     */
    private static SceneController instance = new SceneController();


    /**
     * Protected constructor to ensure only one instance is created.
     */
    protected SceneController() {}

    /**
     * Gets the single instance of SceneController or creates one if the single instance is null.
     * @return instance - returns the single instance of the SceneController.
     */
    public static SceneController getSceneControllerInstance() {
        if (instance == null) {
            instance = new SceneController();
        }
        return instance;
    }

    /**
     * Creates the top level stage and displays the MainView.
     * @param stage - top level JavaFX container
     */
    public void setInitialStageAndScene(Stage stage) {
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

    /**
     * Sets and loads a scene as well as the application's style sheet.
     * @param event - the event that triggers the scene change.
     * @param fxmlFileName - the FXML view to load.
     */
    public void setScene(ActionEvent event, String fxmlFileName) {

        Stage stage;
        Scene scene;
        Parent root;

        try {
            root = FXMLLoader.load(getClass().getClassLoader().getResource(fxmlFileName));
            stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            scene.getStylesheets().add("style.css");
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void returnToMainView(ActionEvent event) {
        Stage stage;
        Scene scene;
        Parent root;

        try {
            root = FXMLLoader.load(getClass().getClassLoader().getResource("MainView.fxml"));
            stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            scene.getStylesheets().add("style.css");
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
