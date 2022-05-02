package com.manuco.manuco.Controller;

import javafx.scene.control.Alert;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;

/**
 * Validates user input into text fields
 */
public class ValidationController {

    private static boolean nameIsValid = false;
    private static boolean invIsValid = false;
    private static boolean priceIsValid = false;
    private static boolean maxIsValid = false;
    private static boolean minIsValid = false;
    private static boolean machineIDIsValid = false;
    private static boolean companyNameIsValid = false;



    private enum ValidationError {
        RADIO("In House or Outsourced must be selected."),
        NAME("Name field can only contain letters."),
        INV("Inventory must be a whole number."),
        INV_LESS_THAN("Inventory must be less than or equal to Max and greater than or equal to Min."),
        PRICE("Price must be in format xx.xx"),
        MAX("Max must be a whole number."),
        MAX_LESS_THAN_MIN("Max must be greater than Min."),
        MIN_GREATER_THAN_MAX("Min must be less than Max."),
        MIN("Min must be a whole number."),
        MACHINE_ID("Machine ID must be a whole number."),
        COMPANY_NAME("Company name can only contain letters and spaces."),
        INVALID_ENTRIES("Invalid entries. Save unsuccessful.");

        private final String errorText;

        ValidationError(String errorText) {
            this.errorText = errorText;
        }

        public void showValidationError() {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("Invalid entry." );
            alert.setContentText(errorText);
            alert.showAndWait();
        }
    }

    /**
     * Shows the user the INVALID_ENTRIES error
     */
    public static void showInvalidEntriesError() {
        ValidationError.INVALID_ENTRIES.showValidationError();
    }

    /**
     * Sets all entries to valid.
     * For use in ModifyPartView for parts with a machineId
     */
    public static void setAllEntriesToValidForMachineID() {
        nameIsValid = true;
        invIsValid = true;
        priceIsValid = true;
        maxIsValid = true;
        minIsValid = true;
        machineIDIsValid = true;
    }

    /**
     * Sets all entries to valid
     * For use in ModifyPartView for parts with a companyName
     */
    public static void setAllEntriesToValidForCompanyName() {
        nameIsValid = true;
        invIsValid = true;
        priceIsValid = true;
        maxIsValid = true;
        minIsValid = true;
        companyNameIsValid = true;
    }

    /**
     * Sets all entries to valid.
     * For use in ModifyProductView
     */
    public static void setAllEntriesToValidForProduct() {
        nameIsValid = true;
        invIsValid = true;
        priceIsValid = true;
        maxIsValid = true;
        minIsValid = true;
    }

    /**
     * Only allows entries with letters
     * @param name the text field to add the listener to
     */
    public static void addNameListener(TextField name) {

        // Listener to validate input after input changes
        name.textProperty().addListener((observableValue, oldValue, newValue) -> {
            if (newValue.isBlank()) {
                nameIsValid = false;
            } else if (newValue.matches("[a-zA-Z]+")) {
                nameIsValid = true;
            } else {
                ValidationError.NAME.showValidationError();
                nameIsValid = false;
                name.requestFocus();
            }
        });

    }

    /**
     * Only allows entries with whole numbers
     * @param inv the text field to add the listener to
     */
    public static void addInvListener(TextField inv) {

        // Listener to validate input after input changes
        inv.textProperty().addListener((observableValue, oldValue, newValue) -> {
            if (newValue.isBlank()) {
                invIsValid = false;
            } else if (newValue.matches("\\d+")) {
                invIsValid = true;
            } else {
                ValidationError.INV.showValidationError();
                invIsValid = false;
                inv.requestFocus();
            }
        });

    }

    /**
     * Only allows entries with whole numbers
     * @param max the text field to add the listener to
     */
    public static void addMaxListener(TextField max) {

        // Listener to validate input after input changes
        max.textProperty().addListener((observableValue, oldValue, newValue) -> {
            if (newValue.isBlank()) {
                maxIsValid = false;
            } else if (newValue.matches("\\d+")) {
                maxIsValid = true;
            } else {
                ValidationError.MAX.showValidationError();
                maxIsValid = false;
                max.requestFocus();
            }
        });

    }

    /**
     * Only allows entries with whole numbers
     * @param min the text field to add the listener to
     */
    public static void addMinListener(TextField min) {

        // Listener to validate input after input changes
        min.textProperty().addListener((observableValue, oldValue, newValue) -> {
            if (newValue.isBlank()) {
                minIsValid = false;
            } else if (newValue.matches("\\d+")) {
                minIsValid = true;
            } else {
                ValidationError.MIN.showValidationError();
                minIsValid = false;
                min.requestFocus();
            }
        });

    }

    /**
     * Only allows entries with whole numbers
     * @param machineID the text field to add the listener to
     */
    public static void addMachineIDListener(TextField machineID) {

        // Listener to validate input after input changes
        machineID.textProperty().addListener((observableValue, oldValue, newValue) -> {
            if (newValue.isBlank()) {
                machineIDIsValid = false;
            } else if (newValue.matches("\\d+")) {
                machineIDIsValid = true;
            } else {
                ValidationError.MACHINE_ID.showValidationError();
                machineIDIsValid = false;
                machineID.requestFocus();
            }
        });

    }

    /**
     * Only allows entries with letters
     * @param companyName the text field to add the listener to
     */
    public static void addCompanyNameListener(TextField companyName) {

        // Listener to validate input after input changes
        companyName.textProperty().addListener((observableValue, oldValue, newValue) -> {
            if (newValue.isBlank()) {
                companyNameIsValid = false;
            } else if (newValue.matches("[a-zA-Z ]+")) {
                companyNameIsValid = true;
            } else {
                ValidationError.COMPANY_NAME.showValidationError();
                companyNameIsValid = false;
                companyName.requestFocus();
            }
        });

    }

    /**
     * RUNTIME ERROR: first used a listener for the price field to only allow xx.xx entries. This triggered a warning
     * even if one number was entered. I corrected this by removing the listener and adding a method to
     * Validation Controller to check the price text field for correct input after the Save button was clicked.
     *
     * Validates that the price entered is in the valid format xxx.xx
     * @param price the text field to get input from
     */
    public static void validatePriceFormat(TextField price) {

        if (!price.getText().isEmpty() && price.getText().matches("\\d+\\.\\d+")) {
            priceIsValid = true;
        } else {
            ValidationError.PRICE.showValidationError();
            priceIsValid = false;
        }

    }

    /**
     * Validates that inv is less than max and greater than min
     * Validates that max is greater than min
     * Validates that min is less than max
     * @param inv the user entered current inventory
     * @param max the user entered max inventory
     * @param min the user entered minimum inventory
     */
    public static void validateInvMaxMin(int inv, int max, int min) {
        if (inv > max || inv < min) {
            ValidationError.INV_LESS_THAN.showValidationError();
            invIsValid = false;
        }

        if (max < min) {
            ValidationError.MAX_LESS_THAN_MIN.showValidationError();
            maxIsValid = false;
        }

        if (min > max) {
            ValidationError.MIN_GREATER_THAN_MAX.showValidationError();
            minIsValid = false;
        }
    }

    /**
     * Validates that one of the radio buttons is selected
     * @param inHouse the InHouse radio button
     * @param outsourced the Outsourced radio button
     */
    public static void validateRadioButtonInput(RadioButton inHouse, RadioButton outsourced) {
        if (!inHouse.isSelected() && !outsourced.isSelected()) {
            ValidationError.RADIO.showValidationError();
        }
    }

    /**
     * Checks that all entries are valid for parts with a machineId
     * @return true if all entries are valid
     */
    public static boolean verifyAllEntriesMachineID() {

        return nameIsValid && invIsValid && priceIsValid && maxIsValid && minIsValid && machineIDIsValid;

    }

    /**
     * Checks that all entries are valid for parts with a companyName
     * @return true if all entries are valid
     */
    public static boolean verifyAllEntriesCompanyName() {
        return nameIsValid && invIsValid && priceIsValid && maxIsValid && minIsValid && companyNameIsValid;
    }

    /**
     * Checks that all entries are valid for product entries
     * @return true if all entries are valid
     */
    public static boolean verifyProductEntries() {
        return nameIsValid && invIsValid && priceIsValid && maxIsValid && minIsValid;
    }

}
