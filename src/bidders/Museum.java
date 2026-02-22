package bidders;

import artifacts.Artifact;
import artifacts.Artifact.Material;
import java.util.ArrayList;
import java.util.List;

public class Museum extends Bidder {
    public enum Specialization { MARITIME, CULTURAL, NATURAL_HISTORY }
    
    private Specialization specialization;
    private List<Artifact> exhibits = new ArrayList<>();

    public Museum(String name, double budget, Specialization specialization) {
        super(name, budget);
        if (specialization == null) {
            throw new IllegalArgumentException("Specialization cannot be null");
        }
        this.specialization = specialization;
    }

    @Override
    public boolean canBid(Artifact artifact) {
        if (artifact == null) return false;

        switch (specialization) {
            case MARITIME:
                return artifact instanceof artifacts.SunkenShipwreck;
            case CULTURAL:
                return artifact.getMaterial() != Material.GEOLOGICAL;
            case NATURAL_HISTORY:
                return artifact.getMaterial() == Material.GEOLOGICAL;
            default:
                return false;
        }
    }

    @Override
    public void addPurchasedItem(Artifact artifact, double price) {
        if (artifact != null) {
            exhibits.add(artifact);
            System.out.println(name + " added to museum exhibits: " + artifact.getName());
        }
    }

    public void displayExhibits() {
        System.out.println("\n" + name + "'s Exhibits:");
        exhibits.forEach(artifact ->
            System.out.println("- " + artifact.getName() + " (" + artifact.getMaterial() + ")")
        );
    }

    public List<Artifact> getExhibits() {
        return new ArrayList<>(exhibits);
    }
}