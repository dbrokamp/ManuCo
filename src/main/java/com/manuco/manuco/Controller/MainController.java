package com.manuco.manuco.Controller;

import com.manuco.manuco.Model.*;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Controls MainView and displays inventory lists of parts and products
 */
public class MainController implements Initializable {
    SceneController sceneController = SceneController.getSceneControllerInstance();
    private static Part partToModify;
    private static Product productToModify;

    @FXML TableView<Part> partsTable = new TableView<>();
    @FXML TableColumn<Part, Integer> partIdColumn = new TableColumn<>("Part IDs");
    @FXML TableColumn<Part, String> partNameColumn = new TableColumn<>("Part Name");
    @FXML TableColumn<Part, Integer> partInvColumn = new TableColumn<>("Inventory Level");
    @FXML TableColumn<Part, Double> partPriceColumn = new TableColumn<>("Price/Cost per Unit");

    @FXML TableView<Product> productsTable;
    @FXML TableColumn<Product, Integer> productIdColumn = new TableColumn<>("Product ID");
    @FXML TableColumn<Product, String> productNameColumn = new TableColumn<>("Product Name");
    @FXML TableColumn<Product, Integer> productInvColumn = new TableColumn<>("Inventory Level");
    @FXML TableColumn<Product, Double> productPriceColumn = new TableColumn<>("Price/Cost Per Unit");

    @FXML TextField searchPartField;
    @FXML TextField searchProductField;

    @FXML Pane partsPane;
    @FXML Pane productsPane;

    /**
     * Initializes MainView.
     * Populate parts table.
     * Populates products table.
     *
     * From https://docs.oracle.com/javase/8/javafx/api/javafx/fxml/Initializable.html:
     * @param url the location used to resolve relative paths for the root object, or null if the location is not known
     * @param rb  The resources used to localize the root object, or null if the root object was not localized.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        // Set up part table
        partIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        partNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        partInvColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

        partsTable.setItems(Inventory.getAllParts());

        // Set up product table
        productIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        productNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        productInvColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        productPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

        productsTable.setItems(Inventory.getAllProducts());

    }

    /**
     * Calls sceneController to open AddPartView
     *
     * @param event - the user event that triggers this method
     */
    public void openAddParts(ActionEvent event) {
        sceneController.setScene(event, "AddPartView.fxml");
    }

    /**
     * Calls sceneController to open AddProductView
     *
     * @param event the user event that triggers this method
     */
    public void openAddProduct(ActionEvent event) {
        sceneController.setScene(event, "AddProductView.fxml");
    }


    /**
     * Gets the part selected in the parts table
     *
     * @return the part selected in the parts table
     */
    public static Part getPartToModify() { return partToModify; }

    /**
     * Gets the product selected in the product table
     *
     * @return the product selected in the product table
     */
    public static Product getProductToModify() { return productToModify; }

    /**
     * If a part is selected in the parts table, sceneController is called to open ModifyPartView
     *
     * @param event the user event that triggers this method
     */
    public void openModifyPart(ActionEvent event) {

        partToModify = partsTable.getSelectionModel().getSelectedItem();

        if (partToModify == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("No part selected.");
            alert.setContentText("Please select a part in the table.");
            alert.showAndWait();
        } else {
            sceneController.setScene(event, "ModifyPartView.fxml");
        }
    }

    /**
     * If a product is selected in the products table, sceneController is called to open ModifyProductView
     *
     * @param event the user event that triggers this method
     */
    public void openModifyProduct(ActionEvent event) {

        productToModify = productsTable.getSelectionModel().getSelectedItem();

        if (productToModify == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("No product selected.");
            alert.setContentText("Please select a product in the table.");
            alert.showAndWait();
        } else {
            sceneController.setScene(event, "ModifyProductView.fxml");
        }


    }

    /**
     * Deletes the part selected in the part table
     */
    public void deletePart() {
        Part partToDelete = partsTable.getSelectionModel().getSelectedItem();
        boolean deleteSuccessful = false;

        if (partToDelete == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("No part selected.");
            alert.setContentText("Please select a part in the table.");
            alert.showAndWait();
        } else {
            deleteSuccessful = Inventory.deletePart(partToDelete);
        }

        if (deleteSuccessful) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Delete Successful");
            alert.setContentText(partToDelete.getName() + " deleted successfully.");
            alert.showAndWait();
        }
    }

    /**
     * Deletes the product selected in the product table
     */
    public void deleteProduct() {
        Product productToDelete = productsTable.getSelectionModel().getSelectedItem();
        boolean deleteSuccessful = false;

        if (productToDelete == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("No product selected.");
            alert.setContentText("Please select a product in the table.");
            alert.showAndWait();
        } else if (!productToDelete.getAllAssociatedParts().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Parts associated with product.");
            alert.setContentText("Cannot delete a product that has parts associated with it.");
            alert.showAndWait();

        } else {
            deleteSuccessful = Inventory.deleteProduct(productToDelete);
        }

        if (deleteSuccessful) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Delete Successful");
            alert.setContentText(productToDelete.getName() + " deleted successfully.");
            alert.showAndWait();
        }
    }

    /**
     * Searches the part list by either a string (name) or int (id)
     */
    public void searchPart() {
        String searchInput = searchPartField.getText();
        ObservableList<Part> partsToShow = FXCollections.observableArrayList();

        if (searchInput.isEmpty()) {
            partsTable.setItems(Inventory.getAllParts());
        } else if (searchInput.matches("\\d+")) {
            int idToSearch = Integer.parseInt(searchInput);
            partsToShow.add(Inventory.lookupPart(idToSearch));
            partsTable.setItems(partsToShow);
        } else if (searchInput.matches("[a-zA-Z]+")) {
            partsToShow = Inventory.lookupPart(searchInput);
            partsTable.setItems(partsToShow);
        }

        if (!searchInput.isEmpty()) {
            if (partsToShow == null || partsToShow.contains(null)) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Part Search");
                alert.setContentText("No part found");
                alert.showAndWait();
            }
        }
    }


    /**
     * Searches the product list by either a string (name) or int (id)
     */
    public void searchProduct() {
        String searchInput = searchProductField.getText();
        ObservableList<Product> productsToShow = FXCollections.observableArrayList();

        if (searchInput.isEmpty()) {
            productsTable.setItems(Inventory.getAllProducts());
        } else if (searchInput.matches("\\d+")) {
            int idToSearch = Integer.parseInt(searchInput);
            productsToShow.add(Inventory.lookupProduct(idToSearch));
            productsTable.setItems(productsToShow);
        } else if (searchInput.matches("[a-zA-Z]+")) {
            productsToShow = Inventory.lookupProduct(searchInput);
            productsTable.setItems(productsToShow);
        }

        if (!searchInput.isEmpty()) {
            if (productsToShow == null || productsToShow.contains(null)) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Part Search");
                alert.setContentText("No part found");
                alert.showAndWait();
            }
        }
    }

    /**
     * Closes the application
     */
    public void exit() { Platform.exit(); }

}

