package com.manuco.manuco.Controller;

import javafx.scene.control.Alert;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;

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
        INVALID_ENTRIES("Invalid entries. Save unsuccessful."),
        NO_PARTS("Product must have at least one part added.");


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

    public static void showInvalidEntriesError() {
        ValidationError.INVALID_ENTRIES.showValidationError();
    }

    public static void setAllEntriesToValidForMachineID() {
        nameIsValid = true;
        invIsValid = true;
        priceIsValid = true;
        maxIsValid = true;
        minIsValid = true;
        machineIDIsValid = true;
    }

    public static void setAllEntriesToValidForCompanyName() {
        nameIsValid = true;
        invIsValid = true;
        priceIsValid = true;
        maxIsValid = true;
        minIsValid = true;
        companyNameIsValid = true;
    }

    public static void setAllEntriesToValidForProduct() {
        nameIsValid = true;
        invIsValid = true;
        priceIsValid = true;
        maxIsValid = true;
        minIsValid = true;
    }

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

    public static void validatePriceFormat(TextField price) {

        if (!price.getText().isEmpty() && price.getText().matches("\\d+\\.\\d+")) {
            priceIsValid = true;
        } else {
            ValidationError.PRICE.showValidationError();
            priceIsValid = false;
        }

    }

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

    public static void validateRadioButtonInput(RadioButton inHouse, RadioButton outsourced) {
        if (!inHouse.isSelected() && !outsourced.isSelected()) {
            ValidationError.RADIO.showValidationError();
        }
    }

    public static boolean verifyAllEntriesMachineID() {

        if (nameIsValid && invIsValid && priceIsValid && maxIsValid && minIsValid && machineIDIsValid) {
            return true;
        }

        return false;
    }

    public static boolean verifyAllEntriesCompanyName() {
        if (nameIsValid && invIsValid && priceIsValid && maxIsValid && minIsValid && companyNameIsValid) {
            return true;
        }

        return false;
    }

    public static boolean verifyProductEntries() {
        if (nameIsValid && invIsValid && priceIsValid && maxIsValid && minIsValid) {
            return true;
        }

        return false;
    }

}
