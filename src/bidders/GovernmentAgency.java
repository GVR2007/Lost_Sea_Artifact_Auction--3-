package bidders;

import artifacts.Artifact;
import java.util.ArrayList;
import java.util.List;

public class GovernmentAgency extends Bidder {
    private List<Artifact> protectedArtifacts = new ArrayList<>();

    public GovernmentAgency(String name) {
        super(name, Double.POSITIVE_INFINITY);
    }

    @Override
    public boolean canBid(Artifact artifact) {
        return artifact.isProtected();
    }

    @Override
    public void addPurchasedItem(Artifact artifact, double price) {
        protectedArtifacts.add(artifact);
        System.out.println(name + " secured protected artifact: " + artifact.getName());
    }

    public void displayProtectedItems() {
        System.out.println("\nNational Heritage Items:");
        protectedArtifacts.forEach(artifact ->
            System.out.println("- " + artifact.getName() + 
                " (Heritage: " + artifact.getHeritageStatus() + ")")
        );
    }

    public List<Artifact> getProtectedArtifacts() {
        return new ArrayList<>(protectedArtifacts);
    }
}