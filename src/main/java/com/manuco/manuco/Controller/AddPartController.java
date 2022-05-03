package com.manuco.manuco.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import java.net.URL;
import java.util.ResourceBundle;
import com.manuco.manuco.Model.*;

/**
 * Allows a user to add a new part to the inventory list
 */
public class AddPartController implements Initializable {

    SceneController sceneController = SceneController.getSceneControllerInstance();

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
     * Initializes AddPartView and adds listeners to text input fields.
     *
     * From https://docs.oracle.com/javase/8/javafx/api/javafx/fxml/Initializable.html:
     * @param url the location used to resolve relative paths for the root object, or null if the location is not known
     * @param resourceBundle  The resources used to localize the root object, or null if the root object was not localized.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        id.isDisabled();

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
            int newID = Inventory.getAllParts().get(Inventory.getAllParts().size() - 1).getId();
            newID++;
            String newName = name.getText();
            double newPrice = Double.parseDouble(price.getText());
            int newInv = Integer.parseInt(inv.getText());
            int newMax = Integer.parseInt(max.getText());
            int newMin = Integer.parseInt(min.getText());
            int newMachineID = Integer.parseInt(machineIdTextField.getText());
            Inventory.addPart(new InHouse(newID, newName, newPrice, newInv, newMin, newMax, newMachineID));
            sceneController.returnToMainView(event);
        } else if (outsourcedButton.isSelected() && ValidationController.verifyAllEntriesCompanyName()) {
            int newID = Inventory.getAllParts().get(Inventory.getAllParts().size() - 1).getId();
            newID++;
            String newName = name.getText();
            double newPrice = Double.parseDouble(price.getText());
            int newInv = Integer.parseInt(inv.getText());
            int newMax = Integer.parseInt(max.getText());
            int newMin = Integer.parseInt(min.getText());
            String newCompanyName = companyNameTextField.getText();
            Inventory.addPart(new Outsourced(newID, newName, newPrice, newInv, newMin, newMax, newCompanyName));
            sceneController.returnToMainView(event);
        } else {
            ValidationController.showInvalidEntriesError();
        }

    }

    /**
     * Returns the user to the MainView without saving
     * @param event the user clicking the Cancel button triggers this event
     */
    public void cancel(ActionEvent event) {
        sceneController.returnToMainView(event);
    }
}
