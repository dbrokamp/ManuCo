package com.manuco.manuco.Controller;

import com.manuco.manuco.Model.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Allows a user to modify a part's information
 */
public class ModifyPartController implements Initializable {

    private static Part partToModify;


    SceneController sceneController = SceneController.getSceneControllerInstance();

    @FXML private ToggleGroup partType;
    @FXML private RadioButton inHouseButton;
    @FXML private RadioButton outsourcedButton;
    @FXML private Label machineIdLabel;
    @FXML private Label companyNameLabel;
    @FXML private TextField id;
    @FXML private TextField name;
    @FXML private TextField inv;
    @FXML private TextField price;
    @FXML private TextField max;
    @FXML private TextField min;
    @FXML private TextField machineIdTextField;
    @FXML private TextField companyNameTextField;

    /**
     * Returns the user to the MainView
     * @param event the user clicking the Cancel button triggers this method
     */
    public void cancel(ActionEvent event) {
        sceneController.returnToMainView(event);
    }

    /**
     * Initializes the ModifyPartView.
     * Gets the part to modify from the MainController
     * Populates radio button and text fields based on part to modify
     * Sets all validation entries to valid
     * Adds validation listeners to text fields
     * From https://docs.oracle.com/javase/8/javafx/api/javafx/fxml/Initializable.html:
     * @param url the location used to resolve relative paths for the root object, or null if the location is not known
     * @param resourceBundle  The resources used to localize the root object, or null if the root object was not localized.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        id.isDisabled();

        partToModify = MainController.getPartToModify();

        if (partToModify instanceof InHouse) {

            // Select radio button and make machineId label and text field visible
            inHouseButton.setSelected(true);
            machineIdLabel.setVisible(true);
            machineIdTextField.setVisible(true);

            // Populate all fields with values passed from MainController
            name.setText(partToModify.getName());
            inv.setText(String.valueOf(partToModify.getStock()));
            price.setText(String.valueOf(partToModify.getPrice()));
            max.setText(String.valueOf(partToModify.getMax()));
            min.setText(String.valueOf(partToModify.getMin()));
            machineIdTextField.setText(String.valueOf(((InHouse) partToModify).getMachineId()));

            ValidationController.setAllEntriesToValidForMachineID();

        } else if (partToModify instanceof Outsourced) {

            // Select radio button and make companyName label and text field visible.
            outsourcedButton.setSelected(true);
            companyNameLabel.setVisible(true);
            companyNameTextField.setVisible(true);

            name.setText(partToModify.getName());
            inv.setText(String.valueOf(partToModify.getStock()));
            price.setText(String.valueOf(partToModify.getPrice()));
            max.setText(String.valueOf(partToModify.getMax()));
            min.setText(String.valueOf(partToModify.getMin()));
            companyNameTextField.setText(((Outsourced) partToModify).getCompanyName());

            ValidationController.setAllEntriesToValidForCompanyName();
        }


        ValidationController.addNameListener(name);
        ValidationController.addInvListener(inv);
        ValidationController.addMaxListener(max);
        ValidationController.addMinListener(min);

    }

    /**
     * Sets labels and text fields to visible or not based on the selection the radio buttons: InHouse or Outsourced
     * Adds appropriate listener to text fields based on selection
     */
    public void setPartType() {
        if (inHouseButton.isSelected()) {
            ValidationController.addMachineIDListener(machineIdTextField);
            machineIdLabel.setVisible(true);
            machineIdTextField.setVisible(true);
            companyNameLabel.setVisible(false);
            companyNameTextField.setVisible(false);

        } else if (outsourcedButton.isSelected()) {
            ValidationController.addCompanyNameListener(companyNameTextField);
            machineIdLabel.setVisible(false);
            machineIdTextField.setVisible(false);
            companyNameLabel.setVisible(true);
            companyNameTextField.setVisible(true);

        }
    }

    /**
     * Validates all text fields have values and are valid entries.
     * Validates a radio button is selected.
     * Validates the price format.
     * Validates that inv <= max and inv >= min and min <= max
     * If Validation succeeds, user is redirected to MainView
     * @param event User clicking the Save button triggers this method
     */
    public void save(ActionEvent event) {

        if (name.getText().isEmpty() || inv.getText().isEmpty() || price.getText().isEmpty() || max.getText().isEmpty() || min.getText().isEmpty()) {

            ValidationController.showInvalidEntriesError();

        }

        //Check for radio button selected
        ValidationController.validateRadioButtonInput(inHouseButton, outsourcedButton);

        // Check price for valid input
        ValidationController.validatePriceFormat(price);

        // Validate that inv <= max
        // and inv >= min
        // and min <= max
        ValidationController.validateInvMaxMin(Integer.parseInt(inv.getText()), Integer.parseInt(max.getText()), Integer.parseInt(min.getText()));

        if (inHouseButton.isSelected() && ValidationController.verifyAllEntriesMachineID()) {
            int newID = partToModify.getId();
            String newName = name.getText();
            double newPrice = Double.parseDouble(price.getText());
            int newInv = Integer.parseInt(inv.getText());
            int newMax = Integer.parseInt(max.getText());
            int newMin = Integer.parseInt(min.getText());
            int newMachineID = Integer.parseInt(machineIdTextField.getText());
            Part updatedPart = new InHouse(newID, newName, newPrice, newInv, newMax, newMin, newMachineID);
            int partToModifyIndex = Inventory.lookupPartIndex(partToModify);
            Inventory.updatePart(partToModifyIndex, updatedPart);
            sceneController.returnToMainView(event);
        } else if (outsourcedButton.isSelected() && ValidationController.verifyAllEntriesCompanyName()) {
            int newID = partToModify.getId();
            String newName = name.getText();
            double newPrice = Double.parseDouble(price.getText());
            int newInv = Integer.parseInt(inv.getText());
            int newMax = Integer.parseInt(max.getText());
            int newMin = Integer.parseInt(min.getText());
            String newCompanyName = companyNameTextField.getText();
            Part updatedPart = new Outsourced(newID, newName, newPrice, newInv, newMin, newMax, newCompanyName);
            int partToModifyIndex = Inventory.lookupPartIndex(partToModify);
            Inventory.updatePart(partToModifyIndex, updatedPart);
            sceneController.returnToMainView(event);
        } else {
            ValidationController.showInvalidEntriesError();
        }

    }
}
