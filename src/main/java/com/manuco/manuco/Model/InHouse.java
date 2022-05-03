package com.manuco.manuco.Model;

/**
 * Subclass of Part
 */
public class InHouse extends Part {
    private int machineId;


    /**
     * Public construct
     * @param id for the part
     * @param name for the part
     * @param price for the part
     * @param stock for the part
     * @param min for the part
     * @param max for the part
     * @param machineId for the part
     */
    public InHouse(int id, String name, double price, int stock, int min, int max, int machineId) {
        super(id, name, price, stock, min, max);
        this.machineId = machineId;
    }

    /**
     * @param machineId - sets the machineId associated with this part
     */
    public void setMachineId(int machineId) {
        this.machineId = machineId;
    }

    /**
     * @return - the machineId associated with this part
     */
    public int getMachineId() {
        return machineId;
    }
}
