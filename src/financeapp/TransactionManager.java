
package financeapp;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.util.ArrayList;

public class TransactionManager {


    private final MongoCollection<Document> collection;

    public TransactionManager() {
        MongoDatabase db = MongoDBConnection.getDatabase();
        collection = db.getCollection("transactions");
    }

    public void addTransaction(Transaction t) {
        collection.insertOne(t.toDocument());
    }


    public ArrayList<Transaction> getAllTransactions() {
        ArrayList<Transaction> list = new ArrayList<>();
        MongoCursor<Document> cursor = collection.find().iterator();
        while (cursor.hasNext()) {
            Document d = cursor.next();
            list.add(new Transaction(

                    d.getString("type"),
                    d.getDouble("amount"),
                    d.getString("description")
            ));
        }

        return list;

    }

    public double getTotalIncome() {
        double total = 0;
        for (Transaction t : getAllTransactions()) {
            if ("Prihod".equalsIgnoreCase(t.getType())) {
                total += t.getAmount();
            }
        }
        return total;
    }

    public double getTotalExpense() {
        double total = 0;
        for (Transaction t : getAllTransactions()) {
            if ("Rashod".equalsIgnoreCase(t.getType())) {
                total += t.getAmount();
            }
        }
        return total;
    }




}
