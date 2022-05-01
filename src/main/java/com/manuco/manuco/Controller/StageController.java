package com.manuco.manuco.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * StageController implements the Singleton pattern to ensure only one instance is created and used to manage
 * the scene currently presented to the user.
 */

public class StageController {

    private static StageController instance;


    /**
     * Protected constructor to ensure only one instance is created.
     */
    protected StageController() {}

    /**
     * Gets the single instance of StageController or creates one if the single instance is null.
     * @return instance - returns the single instance of the StageController.
     */
    public static StageController getStageManagerInstance() {
        if (instance == null) {
            instance = new StageController();
        }
        return instance;
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
}
