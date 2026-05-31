package dao;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import db.MongoConnection;
import model.Product;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import static com.mongodb.client.model.Filters.*;
import static com.mongodb.client.model.Updates.*;

public class ProductDAO {

    private final MongoCollection<Document> collection;

    public ProductDAO() {
        MongoDatabase database = MongoConnection.getDatabase();
        collection = database.getCollection("products");
    }

    public void addProduct(Product product) {
        Document document = new Document("productId", product.getProductId())
                .append("name", product.getName())
                .append("category", product.getCategory())
                .append("price", product.getPrice())
                .append("ecoRating", product.getEcoRating())
                .append("description", product.getDescription());

        collection.insertOne(document);
    }

    public void updateProduct(Product product) {
        collection.updateOne(
                eq("productId", product.getProductId()),
                combine(
                        set("name", product.getName()),
                        set("category", product.getCategory()),
                        set("price", product.getPrice()),
                        set("ecoRating", product.getEcoRating()),
                        set("description", product.getDescription())
                )
        );
    }

    public void deleteProduct(String productId) {
        collection.deleteOne(eq("productId", productId));
    }

    public List<Product> getAllProducts() {
        List<Product> products = new ArrayList<>();

        FindIterable<Document> documents = collection.find();

        for (Document doc : documents) {
            products.add(documentToProduct(doc));
        }

        return products;
    }

    public List<Product> searchProducts(String keyword) {
        List<Product> products = new ArrayList<>();

        Pattern pattern = Pattern.compile(keyword, Pattern.CASE_INSENSITIVE);

        FindIterable<Document> documents = collection.find(
                or(
                        regex("productId", pattern),
                        regex("name", pattern),
                        regex("category", pattern),
                        regex("description", pattern)
                )
        );

        for (Document doc : documents) {
            products.add(documentToProduct(doc));
        }

        return products;
    }

    public boolean productIdExists(String productId) {
        Document doc = collection.find(eq("productId", productId)).first();
        return doc != null;
    }

    private Product documentToProduct(Document doc) {
        return new Product(
                doc.getString("productId"),
                doc.getString("name"),
                doc.getString("category"),
                doc.getDouble("price"),
                doc.getInteger("ecoRating"),
                doc.getString("description")
        );
    }
}