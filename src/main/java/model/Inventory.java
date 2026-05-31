package model;

public class Inventory {

    private String inventoryId;
    private String productName;
    private int quantity;
    private int reorderLevel;
    private String supplierName;

    public Inventory(String inventoryId, String productName, int quantity, int reorderLevel, String supplierName) {
        this.inventoryId = inventoryId;
        this.productName = productName;
        this.quantity = quantity;
        this.reorderLevel = reorderLevel;
        this.supplierName = supplierName;
    }

    public String getInventoryId() {
        return inventoryId;
    }

    public String getProductName() {
        return productName;
    }

    public int getQuantity() {
        return quantity;
    }

    public int getReorderLevel() {
        return reorderLevel;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public String getStatus() {
        if (quantity <= reorderLevel) {
            return "Low Stock";
        }
        return "Available";
    }
}