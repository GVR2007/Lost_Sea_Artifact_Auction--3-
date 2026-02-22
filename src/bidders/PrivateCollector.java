package bidders;

import artifacts.Artifact;
import java.util.ArrayList;
import java.util.List;

public class PrivateCollector extends Bidder {
    private List<Artifact> collection = new ArrayList<>();

    public PrivateCollector(String name, double budget) {
        super(name, budget);
    }

    @Override
    public boolean canBid(Artifact artifact) {
        if (artifact == null) return false;
        return !artifact.isFragile() && !artifact.isProtected();
    }

    @Override
    public void addPurchasedItem(Artifact artifact, double price) {
        if (artifact != null) {
            collection.add(artifact);
            System.out.println(name + " added to private collection: " + artifact.getName() + " for $" + price);
        }
    }

    public void displayCollection() {
        System.out.println("\n" + name + "'s Collection:");
        if (collection.isEmpty()) {
            System.out.println("No artifacts in collection.");
        } else {
            collection.forEach(artifact -> 
                System.out.println("- " + artifact.getName() + " (Estimated Value: $" + artifact.getEstimatedValue() + ")")
            );
        }
    }

    public List<Artifact> getCollection() {
        return new ArrayList<>(collection);
    }
}