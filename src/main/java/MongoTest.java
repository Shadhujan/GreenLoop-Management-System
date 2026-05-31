import com.mongodb.client.MongoDatabase;
import db.MongoConnection;

public class MongoTest {
    public static void main(String[] args) {
        // Attempt to connect and fetch the database
        MongoDatabase database = MongoConnection.getDatabase();

        // Print success message
        System.out.println("Connected to database: " + database.getName());
    }
}