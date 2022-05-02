package com.manuco.manuco.Controller;

import com.manuco.manuco.Model.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Allows a user to modify a product's information
 */
public class ModifyProductController implements Initializable {

    private static Product productToModify;

    SceneController sceneController = SceneController.getSceneControllerInstance();

    @FXML
    private TextField id;
    @FXML private TextField name;
    @FXML private TextField inv;
    @FXML private TextField price;
    @FXML private TextField max;
    @FXML private TextField min;

    @FXML private TextField searchPartField;

    @FXML TableView<Part> partsTable = new TableView<>();
    @FXML TableColumn<Part, Integer> partIdColumn = new TableColumn<>("Part IDs");
    @FXML TableColumn<Part, String> partNameColumn = new TableColumn<>("Part Name");
    @FXML TableColumn<Part, Integer> partInvColumn = new TableColumn<>("Inventory Level");
    @FXML TableColumn<Part, Double> partPriceColumn = new TableColumn<>("Price/Cost per Unit");

    @FXML TableView<Part> productPartsTable = new TableView<>();
    @FXML TableColumn<Part, Integer> productPartIdColumn = new TableColumn<>("Part IDs");
    @FXML TableColumn<Part, String> productPartNameColumn = new TableColumn<>("Part Name");
    @FXML TableColumn<Part, Integer> productPartInvColumn = new TableColumn<>("Inventory Level");
    @FXML TableColumn<Part, Double> productPartPriceColumn = new TableColumn<>("Price/Cost per Unit");

    /**
     * Returns the user to MainView without saving
     * @param event the user clicking the Cancel button triggers this method
     */
    public void cancel(ActionEvent event) {
        sceneController.returnToMainView(event);
    }

    /**
     * Initializes the ModifyProductView.
     * Gets the product to modify from the MainController
     * Sets all validation entries to valid
     * Adds validation listeners to text fields
     *
     * From https://docs.oracle.com/javase/8/javafx/api/javafx/fxml/Initializable.html:
     * @param url the location used to resolve relative paths for the root object, or null if the location is not known
     * @param resourceBundle  The resources used to localize the root object, or null if the root object was not localized.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        id.isDisabled();

        productToModify = MainController.getProductToModify();

        // Populate all fields with values passed from MainController
        name.setText(String.valueOf(productToModify.getName()));
        inv.setText(String.valueOf(productToModify.getStock()));
        price.setText(String.valueOf(productToModify.getPrice()));
        max.setText(String.valueOf(productToModify.getMax()));
        min.setText(String.valueOf(productToModify.getMin()));

        ValidationController.setAllEntriesToValidForProduct();

        // Set up part table
        partIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        partNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        partInvColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

        partsTable.setItems(Inventory.getAllParts());


        // Set up product table
        productPartIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        productPartNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        productPartInvColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        productPartPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        productPartsTable.setItems(productToModify.getAllAssociatedParts());

        ValidationController.addNameListener(name);
        ValidationController.addInvListener(inv);
        ValidationController.addMaxListener(max);
        ValidationController.addMinListener(min);

    }

    /**
     * Searches for a part in the allParts list by a string (name) or int (id)
     */
    public void searchForPart() {
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
    }

    /**
     * Adds a part from the part table to the product's parts list.
     * Updates table displaying the product's parts list.
     */
    public void addPartToProduct() {

        Part partToAdd = partsTable.getSelectionModel().getSelectedItem();

        if (partToAdd == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("No part selected.");
            alert.setContentText("Please select a part in the table.");
            alert.showAndWait();
        } else {
            productToModify.addAssociatedPart(partToAdd);
        }
    }

    /**
     * Deletes a part from the product's parts list.
     */
    public void deletePartFromProduct() {
        Part partToDelete = productPartsTable.getSelectionModel().getSelectedItem();

        if (partToDelete == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("No part selected.");
            alert.setContentText("Please select a part in the table.");
            alert.showAndWait();
        } else {
            productToModify.deleteAssociatedPart(partToDelete);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Part deleted.");
            alert.setContentText("Part deleted from product successfully.");
            alert.showAndWait();
        }
    }

    /**
     * Validates all text fields have values and are valid entries.
     * Validates the price format.
     * Validates that inv <= max and inv >= min and min <= max
     * If Validation succeeds, user is redirected to MainView
     * @param event the user clicking the Save button triggers this method
     */
    public void save(ActionEvent event) {
        if (name.getText().isEmpty() || inv.getText().isEmpty() || price.getText().isEmpty() || max.getText().isEmpty() || min.getText().isEmpty()) {

            ValidationController.showInvalidEntriesError();

        }

        // Check price for valid input
        ValidationController.validatePriceFormat(price);

        // Validate that inv <= max
        // and inv >= min
        // and min <= max
        ValidationController.validateInvMaxMin(Integer.parseInt(inv.getText()), Integer.parseInt(max.getText()), Integer.parseInt(min.getText()));


        if (ValidationController.verifyProductEntries()) {
            productToModify.setName(name.getText());
            productToModify.setStock(Integer.parseInt(inv.getText()));
            productToModify.setPrice(Double.parseDouble(price.getText()));
            productToModify.setMax(Integer.parseInt(max.getText()));
            productToModify.setMin(Integer.parseInt(min.getText()));
            int productToModifyIndex = Inventory.lookupProductIndex(productToModify);
            Inventory.updateProduct(productToModifyIndex, productToModify);
            sceneController.returnToMainView(event);
        }
    }

}
