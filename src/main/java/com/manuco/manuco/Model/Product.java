package com.manuco.manuco.Model;

import javafx.collections.ObservableList;

/**
 * Class model for a Product
 */
public class Product {
    private ObservableList<Part> associatedParts;
    private int id;
    private String name;
    private double price;
    private int stock;
    private int min;
    private int max;

    public Product(ObservableList<Part> associatedParts, int id, String name, double price, int stock, int min, int max) {
        this.associatedParts = associatedParts;
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.min = min;
        this.max = max;
    }

    /**
     * @param id - sets the id for the product
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @param name - sets the name for the product
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @param price - sets the price for the product
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * @param stock - sets the stock for the product
     */
    public void setStock(int stock) {
        this.stock = stock;
    }

    /**
     * @param min - sets the minimum stock allowed for this product
     */
    public void setMin(int min) {
        this.min = min;
    }

    /**
     * @param max - sets the maximum stock allowed for this product
     */
    public void setMax(int max) {
        this.max = max;
    }

    /**
     * @return the product id
     */
    public int getId() {
        return id;
    }

    /**
     * @return the product name
     */
    public String getName() {
        return name;
    }

    /**
     * @return the product price
     */
    public double getPrice() {
        return price;
    }

    /**
     * @return the product stock
     */
    public int getStock() {
        return stock;
    }

    /**
     * @return the product minimum stock
     */
    public int getMin() {
        return min;
    }

    /**
     * @return the product maximum stock
     */
    public int getMax() {
        return max;
    }

    /**
     * Adds a part to the product's part list
     * @param part - the part to be added to the product's part list
     */
    public void addAssociatedPart(Part part) {
        associatedParts.add(part);
    }

    /**
     * Deletes a part from the product's part list
     * @param selectedAssociatedPart - the part to delete
     * @return - true if delete successful, false if delete unsuccessful
     */
    public boolean deleteAssociatedPart(Part selectedAssociatedPart) {
        if (associatedParts.contains(selectedAssociatedPart)) {
            associatedParts.remove(selectedAssociatedPart);
            return true;
        } else {
            return false;
        }
    }

    /**
     * Returns all the parts associated with the product
     * @return associatedParts - all the parts associated with the product
     */
    public ObservableList<Part> getAllAssociatedParts() {
        return associatedParts;
    }
}