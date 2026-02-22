package utils; // Ensure this is the correct package

import artifacts.Artifact;
import java.time.LocalDateTime;

public class PurchasedItem {
    private final Artifact artifact;
    private final double price;
    private final LocalDateTime purchaseDate;
    private final String buyerName;

    public PurchasedItem(Artifact artifact, double price, String buyerName) {
        this.artifact = artifact;
        this.price = price;
        this.buyerName = buyerName;
        this.purchaseDate = LocalDateTime.now();
    }

    public Artifact getArtifact() {
        return artifact;
    }

    public double getPrice() {
        return price;
    }

    public LocalDateTime getPurchaseDate() {
        return purchaseDate;
    }

    public String getBuyerName() {
        return buyerName;
    }

    @Override
    public String toString() {
        return String.format("[%s] %s bought by %s for $%,.2f",
            purchaseDate,
            artifact.getName(),
            buyerName,
            price); // Corrected to include price
    }
}