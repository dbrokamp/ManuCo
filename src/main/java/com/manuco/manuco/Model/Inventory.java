package com.manuco.manuco.Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.util.Iterator;

/**
 * Keeps track of all inventory in the system.
 * Provides methods to update the inventory.
 * Provides methods to search the inventory.
 */
public class Inventory {
    private static ObservableList<Part> allParts = FXCollections.observableArrayList();
    private static ObservableList<Product> allProducts = FXCollections.observableArrayList();

    /**
     * Adds a new part to allParts
     * @param part to add
     */
    public static void addPart(Part part) { allParts.add(part); }

    /**
     * Adds a new product to allProducts
     * @param product to add
     */
    public static void addProduct(Product product) { allProducts.add(product); }

    /**
     * Checks allParts for part with the partId specified
     * @param partId - the partId to search for
     * @return part if found, null if part is not found in allParts
     */
    public static Part lookupPart(int partId) {

        for (Part part : allParts) {
            if (part.getId() == partId) {
                return part;
            }
        }
        return null;
    }

    /**
     * Checks allProducts for product with the productId specified
     * @param productId - the productId to search for
     * @return product if found, null if product is not found in allProducts
     */
    public static Product lookupProduct(int productId) {

        for (Product product : allProducts) {
            if (product.getId() == productId) {
                return product;
            }
        }
        return null;
    }

    /**
     * Checks allParts for part(s) with a name that matches the string specified
     * @param name - the name to search for
     * @return part(s) that match the string specified
     */
    public static ObservableList<Part> lookupPart(String name) {
        ObservableList<Part> partsToReturn = FXCollections.observableArrayList();

        for (Part part : allParts) {
            if (part.getName().contains(name)) {
                partsToReturn.add(part);
            }
        }

        if (partsToReturn.isEmpty()) {
            return null;
        } else {
            return partsToReturn;
        }

    }

    /**
     * Checks allProducts for product(s) with a name that matches the string specified
     * @param name - the name to search for
     * @return product(s) that match the string specified
     */
    public static ObservableList<Product> lookupProduct(String name) {
        ObservableList<Product> productsToReturn = FXCollections.observableArrayList();

        for (Product product : allProducts) {
            if (product.getName().contains(name)) {
                productsToReturn.add(product);
            }
        }

        if (productsToReturn.isEmpty()) {
            return null;
        } else {
            return productsToReturn;
        }
    }

    /**
     * Find the index of a part in allParts
     * @param partToFind - the part to search for
     * @return the index of the part if found
     */
    public static int lookupPartIndex(Part partToFind) {
        int index = 0;
        int i = 0;
        for (Iterator<Part>iterator = allParts.iterator(); iterator.hasNext(); i++) {
            Part part = iterator.next();
            if (part == partToFind) {
                index = i;
                break;
            }
        }

        return index;
    }

    /**
     * Find the index of a product in allProducts
     * @param productToFind - the product to search for
     * @return the index of the product if found
     */
    public static int lookupProductIndex(Product productToFind) {
        int index = 0;
        int i = 0;
        for (Iterator<Product> iterator = allProducts.iterator(); iterator.hasNext(); i++) {
            Product product = iterator.next();
            if (product == productToFind) {
                index = i;
                break;
            }
        }

        return index;
    }


    /**
     * Updates a part that already exists in allParts
     * @param index - the index of the part to update
     * @param selectedPart - the part's new information to update
     */
    public static void updatePart(int index, Part selectedPart) { allParts.set(index, selectedPart); }

    /**
     * Updates a product that already exists in allProducts
     * @param index - the index of the product to update
     * @param selectedProduct - the product to update
     */
    public static void updateProduct(int index, Product selectedProduct) { allProducts.set(index, selectedProduct); }

    /**
     * Checks if part exists in allParts and deletes it if found
     * @param part - the part to search for
     * @return - true if part is deleted, false if not deleted
     */
    public static boolean deletePart(Part part) {
        if (allParts.contains(part)) {
            allParts.remove(part);
            return true;
        } else {
            return false;
        }
    }

    /**
     * Checks if product exists in allProducts and deletes it if found
     * @param product - the product to search for
     * @return - true if the product is deleted, false if not deleted
     */
    public static boolean deleteProduct(Product product) {
        if (allProducts.contains(product)) {
            allProducts.remove(product);
            return true;
        } else {
            return false;
        }
    }

    /**
     * @return allParts - all parts in the Inventory
     */
    public static ObservableList<Part> getAllParts() { return allParts; }

    /**
     * @return allProducts - all products in the Inventory
     */
    public static ObservableList<Product> getAllProducts() { return allProducts; }
}
