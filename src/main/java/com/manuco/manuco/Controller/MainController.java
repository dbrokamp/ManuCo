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

    @FXML
    TextField searchPartField;
    @FXML TextField searchProductField;

    @FXML
    Pane partsPane;
    @FXML Pane productsPane;


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        System.out.println("Main initializing");

        partsPane.setId("parts-pane");
        productsPane.setId("products-pane");

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

    public void openAddParts(ActionEvent event) {
        System.out.println("Opening add part form.");
        sceneController.setScene(event, "AddPart.fxml");
    }

    public static Part getPartToModify() {
        return partToModify;
    }

    public void openModifyPart(ActionEvent event) {

        partToModify = partsTable.getSelectionModel().getSelectedItem();

        if (partToModify == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("No part selected.");
            alert.setContentText("Please select a part in the table.");
            alert.showAndWait();
        } else {
            sceneController.setScene(event, "ModifyPart.fxml");
        }
    }

    public void openModifyProduct(ActionEvent event) {
        System.out.println("Opening Modify Product form.");

        productToModify = productsTable.getSelectionModel().getSelectedItem();

        if (productToModify == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("No product selected.");
            alert.setContentText("Please select a product in the table.");
            alert.showAndWait();
        } else {
            sceneController.setScene(event, "ModifyProduct.fxml");
        }


    }

    public static Product getProductToModify() { return productToModify; }

    public void deletePart(ActionEvent event) {
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

        if (deleteSuccessful == true) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Delete Successful");
            alert.setContentText(partToDelete.getName() + " deleted successfully.");
            alert.showAndWait();
        }
    }

    public void deleteProduct(ActionEvent event) {
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

        if (deleteSuccessful == true) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Delete Successful");
            alert.setContentText(productToDelete.getName() + " deleted successfully.");
            alert.showAndWait();
        }
    }

    public void searchPart(ActionEvent event) {
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

    public void searchProduct(ActionEvent event) {
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
    }

    public void exit() {
        Platform.exit();
    }

    public void openAddProduct(ActionEvent event) {
        System.out.println("Opening Add Product form.");
        sceneController.setScene(event,"AddProduct.fxml");
    }
}