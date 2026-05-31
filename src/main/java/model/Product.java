package model;

public class Product {

    private String productId;
    private String name;
    private String category;
    private double price;
    private int ecoRating;
    private String description;

    public Product(String productId, String name, String category, double price, int ecoRating, String description) {
        this.productId = productId;
        this.name = name;
        this.category = category;
        this.price = price;
        this.ecoRating = ecoRating;
        this.description = description;
    }

    public String getProductId() {
        return productId;
    }

    public String getName() {
        return name;
    }

    public String getCategory() {
        return category;
    }

    public double getPrice() {
        return price;
    }

    public int getEcoRating() {
        return ecoRating;
    }

    public String getDescription() {
        return description;
    }
}