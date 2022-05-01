package com.manuco.manuco.Model;

/**
 * Subclass of Part
 */
public class Outsourced extends Part {
    private String companyName;

    public Outsourced(int id, String name, double price, int stock, int min, int max, String companyName) {
        super(id, name, price, stock, min, max);
        this.companyName = companyName;
    }

    /**
     * @param companyName - sets the company name associated with this part
     */
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    /**
     * @return the companyName associated with this part
     */
    public String getCompanyName() {
        return companyName;
    }
}
