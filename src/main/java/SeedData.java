import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import db.MongoConnection;
import org.bson.Document;

import java.util.Arrays;
import java.util.List;

public class SeedData {

    public static void main(String[] args) {

        MongoDatabase database = MongoConnection.getDatabase();

        MongoCollection<Document> products = database.getCollection("products");
        MongoCollection<Document> clients = database.getCollection("clients");
        MongoCollection<Document> inventory = database.getCollection("inventory");

        // Clear old data first
        products.deleteMany(new Document());
        clients.deleteMany(new Document());
        inventory.deleteMany(new Document());

        insertProducts(products);
        insertClients(clients);
        insertInventory(inventory);

        System.out.println("Sample data inserted successfully.");
    }

    private static void insertProducts(MongoCollection<Document> products) {
        List<Document> productList = Arrays.asList(
                new Document("productId", "P001")
                        .append("name", "Recycled Cardboard Box")
                        .append("category", "Boxes")
                        .append("price", 12.50)
                        .append("ecoRating", 5)
                        .append("description", "Strong recycled packaging box"),

                new Document("productId", "P002")
                        .append("name", "Compostable Trash Bag")
                        .append("category", "Bags")
                        .append("price", 18.50)
                        .append("ecoRating", 5)
                        .append("description", "Compostable trash bags for retail use"),

                new Document("productId", "P003")
                        .append("name", "Biodegradable Food Wrap")
                        .append("category", "Wraps")
                        .append("price", 9.75)
                        .append("ecoRating", 4)
                        .append("description", "Plant-based food wrap for food packaging"),

                new Document("productId", "P004")
                        .append("name", "Paper Shopping Bag")
                        .append("category", "Bags")
                        .append("price", 6.25)
                        .append("ecoRating", 4)
                        .append("description", "Recyclable paper bags for shops"),

                new Document("productId", "P005")
                        .append("name", "Bamboo Straw Pack")
                        .append("category", "Utensils")
                        .append("price", 4.90)
                        .append("ecoRating", 5)
                        .append("description", "Reusable bamboo straws pack of 10"),

                new Document("productId", "P006")
                        .append("name", "Recycled Tissue Paper")
                        .append("category", "Paper Products")
                        .append("price", 5.00)
                        .append("ecoRating", 3)
                        .append("description", "Tissue paper made from recycled fibers"),

                new Document("productId", "P007")
                        .append("name", "Compostable Food Container")
                        .append("category", "Containers")
                        .append("price", 14.25)
                        .append("ecoRating", 5)
                        .append("description", "Compostable takeaway food container"),

                new Document("productId", "P008")
                        .append("name", "Jute Shopping Bag")
                        .append("category", "Reusable Bags")
                        .append("price", 22.00)
                        .append("ecoRating", 5)
                        .append("description", "Durable reusable jute shopping bag")
        );

        products.insertMany(productList);
    }

    private static void insertClients(MongoCollection<Document> clients) {
        List<Document> clientList = Arrays.asList(
                new Document("clientId", "C001")
                        .append("clientName", "John Perera")
                        .append("businessName", "Green Mart")
                        .append("phone", "0771234567")
                        .append("email", "john@greenmart.com")
                        .append("address", "Colombo"),

                new Document("clientId", "C002")
                        .append("clientName", "Maria Fernando")
                        .append("businessName", "Eco Grocery")
                        .append("phone", "0779876543")
                        .append("email", "maria@ecogrocery.com")
                        .append("address", "Kandy"),

                new Document("clientId", "C003")
                        .append("clientName", "Nimal Silva")
                        .append("businessName", "Fresh Basket")
                        .append("phone", "0712223333")
                        .append("email", "nimal@freshbasket.com")
                        .append("address", "Galle"),

                new Document("clientId", "C004")
                        .append("clientName", "Ayesha Khan")
                        .append("businessName", "Nature Foods")
                        .append("phone", "0764445555")
                        .append("email", "ayesha@naturefoods.com")
                        .append("address", "Negombo"),

                new Document("clientId", "C005")
                        .append("clientName", "Ruwan Jayasekara")
                        .append("businessName", "Island Retailers")
                        .append("phone", "0758889999")
                        .append("email", "ruwan@islandretailers.com")
                        .append("address", "Matara"),

                new Document("clientId", "C006")
                        .append("clientName", "Sarah Joseph")
                        .append("businessName", "Pure Planet Store")
                        .append("phone", "0741112222")
                        .append("email", "sarah@pureplanet.com")
                        .append("address", "Jaffna"),

                new Document("clientId", "C007")
                        .append("clientName", "David Lee")
                        .append("businessName", "Eco Essentials")
                        .append("phone", "0783334444")
                        .append("email", "david@ecoessentials.com")
                        .append("address", "Kurunegala"),

                new Document("clientId", "C008")
                        .append("clientName", "Kavindi Dias")
                        .append("businessName", "Organic Corner")
                        .append("phone", "0705556666")
                        .append("email", "kavindi@organiccorner.com")
                        .append("address", "Ratnapura")
        );

        clients.insertMany(clientList);
    }

    private static void insertInventory(MongoCollection<Document> inventory) {
        List<Document> inventoryList = Arrays.asList(
                new Document("inventoryId", "INV001")
                        .append("productName", "Recycled Cardboard Box")
                        .append("quantity", 100)
                        .append("reorderLevel", 20)
                        .append("supplierName", "EcoPack Suppliers")
                        .append("status", "Available"),

                new Document("inventoryId", "INV002")
                        .append("productName", "Compostable Trash Bag")
                        .append("quantity", 10)
                        .append("reorderLevel", 25)
                        .append("supplierName", "Green Supplier Lanka")
                        .append("status", "Low Stock"),

                new Document("inventoryId", "INV003")
                        .append("productName", "Biodegradable Food Wrap")
                        .append("quantity", 80)
                        .append("reorderLevel", 30)
                        .append("supplierName", "NaturePack Co.")
                        .append("status", "Available"),

                new Document("inventoryId", "INV004")
                        .append("productName", "Paper Shopping Bag")
                        .append("quantity", 15)
                        .append("reorderLevel", 20)
                        .append("supplierName", "EcoPack Suppliers")
                        .append("status", "Low Stock"),

                new Document("inventoryId", "INV005")
                        .append("productName", "Bamboo Straw Pack")
                        .append("quantity", 150)
                        .append("reorderLevel", 50)
                        .append("supplierName", "Bamboo World Lanka")
                        .append("status", "Available"),

                new Document("inventoryId", "INV006")
                        .append("productName", "Recycled Tissue Paper")
                        .append("quantity", 8)
                        .append("reorderLevel", 15)
                        .append("supplierName", "PaperCycle Suppliers")
                        .append("status", "Low Stock"),

                new Document("inventoryId", "INV007")
                        .append("productName", "Compostable Food Container")
                        .append("quantity", 60)
                        .append("reorderLevel", 25)
                        .append("supplierName", "Green Supplier Lanka")
                        .append("status", "Available"),

                new Document("inventoryId", "INV008")
                        .append("productName", "Jute Shopping Bag")
                        .append("quantity", 40)
                        .append("reorderLevel", 10)
                        .append("supplierName", "JuteCraft Lanka")
                        .append("status", "Available")
        );

        inventory.insertMany(inventoryList);
    }
}