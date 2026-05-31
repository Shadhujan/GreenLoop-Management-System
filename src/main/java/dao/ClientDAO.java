package dao;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import db.MongoConnection;
import model.Client;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import static com.mongodb.client.model.Filters.*;
import static com.mongodb.client.model.Updates.*;

public class ClientDAO {

    private final MongoCollection<Document> collection;

    public ClientDAO() {
        MongoDatabase database = MongoConnection.getDatabase();
        collection = database.getCollection("clients");
    }

    public void addClient(Client client) {
        Document document = new Document("clientId", client.getClientId())
                .append("clientName", client.getClientName())
                .append("businessName", client.getBusinessName())
                .append("phone", client.getPhone())
                .append("email", client.getEmail())
                .append("address", client.getAddress());

        collection.insertOne(document);
    }

    public void updateClient(Client client) {
        collection.updateOne(
                eq("clientId", client.getClientId()),
                combine(
                        set("clientName", client.getClientName()),
                        set("businessName", client.getBusinessName()),
                        set("phone", client.getPhone()),
                        set("email", client.getEmail()),
                        set("address", client.getAddress())
                )
        );
    }

    public void deleteClient(String clientId) {
        collection.deleteOne(eq("clientId", clientId));
    }

    public List<Client> getAllClients() {
        List<Client> clients = new ArrayList<>();

        FindIterable<Document> documents = collection.find();

        for (Document doc : documents) {
            clients.add(documentToClient(doc));
        }

        return clients;
    }

    public List<Client> searchClients(String keyword) {
        List<Client> clients = new ArrayList<>();

        Pattern pattern = Pattern.compile(keyword, Pattern.CASE_INSENSITIVE);

        FindIterable<Document> documents = collection.find(
                or(
                        regex("clientId", pattern),
                        regex("clientName", pattern),
                        regex("businessName", pattern),
                        regex("phone", pattern),
                        regex("email", pattern),
                        regex("address", pattern)
                )
        );

        for (Document doc : documents) {
            clients.add(documentToClient(doc));
        }

        return clients;
    }

    public boolean clientIdExists(String clientId) {
        Document doc = collection.find(eq("clientId", clientId)).first();
        return doc != null;
    }

    private Client documentToClient(Document doc) {
        return new Client(
                doc.getString("clientId"),
                doc.getString("clientName"),
                doc.getString("businessName"),
                doc.getString("phone"),
                doc.getString("email"),
                doc.getString("address")
        );
    }
}