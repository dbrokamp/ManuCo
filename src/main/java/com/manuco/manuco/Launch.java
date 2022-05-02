package com.manuco.manuco;

import com.manuco.manuco.Model.*;
import com.manuco.manuco.Controller.SceneController;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.stage.Stage;

/**
 * Application launch point
 *
 * FUTURE ENHANCEMENT: Allow new parts to be added to the inventory from the Add Product or Modify Product screens
 * to allow for a more efficient work flow.
 */

public class Launch extends Application {

    /**
     * Get SceneController instance
     */
    SceneController sceneController = SceneController.getSceneControllerInstance();

    /**
     * Launch point for JavaFX; creates main stage
     * @param stage - the top level JavaFX container for all scenes
     */
    @Override
    public void start(Stage stage)  {

        // Sets initial scene to MainView.fxml
        sceneController.setInitialStageAndScene(stage);

    }

    /**
     * Entry point for application
     * @param args - arguments to launch application
     */
    public static void main(String[] args) {

        // Create and populate sample products for inventory
        Part inHousePartSample = new InHouse(1, "Brakes", 15.00, 10, 1, 10, 1);
        Part outsourcedPartSample = new Outsourced(2, "Wheel", 15.00, 20, 2, 40, "Wheels Forever");

        ObservableList<Part> samplePartsForProduct = FXCollections.observableArrayList();
        samplePartsForProduct.add(inHousePartSample);
        samplePartsForProduct.add(outsourcedPartSample);
        Product sampleProduct = new Product(samplePartsForProduct, 7001, "Bicycle", 150.00, 20, 1, 25);
        Product sampleProduct2 = new Product(samplePartsForProduct, 7002, "Giant Bike", 300.00, 10, 1, 25);

        Inventory.addPart(inHousePartSample);
        Inventory.addPart(outsourcedPartSample);
        Inventory.addProduct(sampleProduct);
        Inventory.addProduct(sampleProduct2);

        launch();
    }
}