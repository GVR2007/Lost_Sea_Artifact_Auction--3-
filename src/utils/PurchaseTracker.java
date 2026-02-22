package utils;


import java.util.ArrayList;
import java.util.List;

public class PurchaseTracker {
    private static PurchaseTracker instance;
    private final List<PurchasedItem> purchases;

    // Private constructor to prevent instantiation
    private PurchaseTracker() {
        purchases = new ArrayList<>();
    }

    // Singleton instance retrieval
    public static PurchaseTracker getInstance() {
        if (instance == null) {
            instance = new PurchaseTracker();
        }
        return instance;
    }

    // Method to record a purchase
    public void recordPurchase(PurchasedItem item) {
        purchases.add(item);
        System.out.println("Purchase recorded: " + item);
    }

    // Additional methods to retrieve purchase history can be added here
}