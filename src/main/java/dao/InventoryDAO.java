package dao;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import db.MongoConnection;
import model.Inventory;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import static com.mongodb.client.model.Filters.*;
import static com.mongodb.client.model.Updates.*;

public class InventoryDAO {

    private final MongoCollection<Document> collection;

    public InventoryDAO() {
        MongoDatabase database = MongoConnection.getDatabase();
        collection = database.getCollection("inventory");
    }

    public void addInventory(Inventory inventory) {
        Document document = new Document("inventoryId", inventory.getInventoryId())
                .append("productName", inventory.getProductName())
                .append("quantity", inventory.getQuantity())
                .append("reorderLevel", inventory.getReorderLevel())
                .append("supplierName", inventory.getSupplierName())
                .append("status", inventory.getStatus());

        collection.insertOne(document);
    }

    public void updateInventory(Inventory inventory) {
        collection.updateOne(
                eq("inventoryId", inventory.getInventoryId()),
                combine(
                        set("productName", inventory.getProductName()),
                        set("quantity", inventory.getQuantity()),
                        set("reorderLevel", inventory.getReorderLevel()),
                        set("supplierName", inventory.getSupplierName()),
                        set("status", inventory.getStatus())
                )
        );
    }

    public void deleteInventory(String inventoryId) {
        collection.deleteOne(eq("inventoryId", inventoryId));
    }

    public List<Inventory> getAllInventory() {
        List<Inventory> inventoryList = new ArrayList<>();

        FindIterable<Document> documents = collection.find();

        for (Document doc : documents) {
            inventoryList.add(documentToInventory(doc));
        }

        return inventoryList;
    }

    public List<Inventory> searchInventory(String keyword) {
        List<Inventory> inventoryList = new ArrayList<>();

        Pattern pattern = Pattern.compile(keyword, Pattern.CASE_INSENSITIVE);

        FindIterable<Document> documents = collection.find(
                or(
                        regex("inventoryId", pattern),
                        regex("productName", pattern),
                        regex("supplierName", pattern),
                        regex("status", pattern)
                )
        );

        for (Document doc : documents) {
            inventoryList.add(documentToInventory(doc));
        }

        return inventoryList;
    }

    public List<Inventory> getLowStockItems() {
        List<Inventory> lowStockItems = new ArrayList<>();

        FindIterable<Document> documents = collection.find();

        for (Document doc : documents) {
            Inventory inventory = documentToInventory(doc);

            if (inventory.getQuantity() <= inventory.getReorderLevel()) {
                lowStockItems.add(inventory);
            }
        }

        return lowStockItems;
    }

    public boolean inventoryIdExists(String inventoryId) {
        Document doc = collection.find(eq("inventoryId", inventoryId)).first();
        return doc != null;
    }

    private Inventory documentToInventory(Document doc) {
        return new Inventory(
                doc.getString("inventoryId"),
                doc.getString("productName"),
                doc.getInteger("quantity"),
                doc.getInteger("reorderLevel"),
                doc.getString("supplierName")
        );
    }
}