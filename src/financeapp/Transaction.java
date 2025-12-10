package financeapp;

import org.bson.Document;

public class Transaction {

    private String type;
    private double amount;
    private String description;
    private String category;


    public Transaction(String type, double amount, String description, String category) {
        this.type = type;
        this.amount = amount;
        this.description = description;
        this.category = category;

    }

    public Document toDocument() {
        Document doc = new Document("type", type)
                .append("amount", amount)
                .append("description", description)
                .append("category", category);
        return doc;
    }


    public void setType(String type) {
        this.type = type;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCategory(String category) {
        this.category = category;
    }


    public String getType() {
        return type;
    }

    public double getAmount() {
        return amount;
    }

    public String getDescription() {
        return description;
    }

    public String getCategory() {
        return category;
    }
}
