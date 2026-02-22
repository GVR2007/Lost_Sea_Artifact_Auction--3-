package utils;

import artifacts.*;
import bidders.Bidder;
import java.util.*;

public class ArtifactSearch {
    private static final Random random = new Random();
    private List<Bidder> bidders; // List of bidders to check who can bid

    public ArtifactSearch(List<Bidder> bidders) {
        this.bidders = bidders; // Initialize bidders
    }

    public Artifact searchArtifact() {
        Scanner scanner = new Scanner(System.in);
        
        int depth = getDepthInput(scanner);
        SearchMethod method = getSearchMethod(scanner);
        ArtifactType type = getArtifactType(scanner);
        
        double probability = calculateProbability(depth, method);
        System.out.printf("Success probability: %.2f%%%n", probability * 100);
       
        
        
        if (random.nextDouble() <= probability) {
            Artifact foundArtifact = generateArtifact(type, depth);
            return foundArtifact; // Return the found artifact
        } else {
            System.out.println("Search failed. No artifact found.");
            return null;
        }
    }

    private int getDepthInput(Scanner scanner) {
        int depth;
        while (true) {
            try {
                System.out.print("Enter search depth (meters): ");
                depth = Integer.parseInt(scanner.nextLine());
                if (depth >= 0) break; // Allow zero as a valid input
                System.out.println("Depth must be a non-negative number!");
            } catch (NumberFormatException e) {
                System.out.println("Invalid input! Please enter a number.");
            }
        }
        return depth;
    }

    private SearchMethod getSearchMethod(Scanner scanner) {
        System.out.println("Search methods:");
        for (SearchMethod method : SearchMethod.values()) {
            System.out.printf("%d. %s%n", method.ordinal() + 1, method);
        }
        
        while (true) {
            try {
                System.out.print("Select method: ");
                int choice = Integer.parseInt(scanner.nextLine());
                if (choice >= 1 && choice <= SearchMethod.values().length) {
                    return SearchMethod.values()[choice - 1];
                }
                System.out.println("Invalid choice! Try again.");
            } catch (NumberFormatException e) {
                System.out.println("Invalid input! Enter a number.");
            }
        }
    }

    private ArtifactType getArtifactType(Scanner scanner) {
        System.out.println("Artifact types:");
        for (ArtifactType type : ArtifactType.values()) {
            System.out.printf("%d. %s%n", type.ordinal() + 1, type);
        }

        while (true) {
            try {
                System.out.print("Select type: ");
                int choice = Integer.parseInt(scanner.nextLine());
                if (choice >= 1 && choice <= ArtifactType.values().length) {
                    return ArtifactType.values()[choice - 1];
                }
                System.out.println("Invalid choice! Try again.");
            } catch (NumberFormatException e) {
                System.out.println("Invalid input! Enter a number.");
            }
        }
    }

    private double calculateProbability(int depth, SearchMethod method) {
        double baseProbability;
        if (depth <= 50) {
            baseProbability = 0.9;
        } else if (depth <= 200) {
            baseProbability = 0.6;
        } else if (depth <= 1000) {
            baseProbability = 0.3;
        } else {
            baseProbability = 0.1;
        }
        return baseProbability * method.getSuccessMultiplier();
    }

    private Artifact generateArtifact(ArtifactType type, int depth) {
        switch (type) {
            case SHIPWRECK:
                return new SunkenShipwreck(
                    "Shipwreck Artifact", 
                    calculateValue(depth, 10000, 50000),
                    calculateValue(depth, 50000, 100000),
                    random.nextBoolean(), // Fragility
                    Artifact.Material.GEOLOGICAL, // Assuming material type
                    Artifact.HeritageStatus.NONE, // Assuming no heritage status
                    depth // Assuming age is the same as depth for simplicity
                );

            case SUBMERGED_CITY:
                return new AncientSubmergedCity(
                    "Ancient City Relic",
                    calculateValue(depth, 20000, 70000),
                    calculateValue(depth, 60000, 120000),
                    random.nextBoolean(),
                    Artifact.Material.ORGANIC, // Corrected material type
                    Artifact.HeritageStatus.NONE, // Assuming no heritage status
                    depth // Assuming age is the same as depth for simplicity
                );

            case NATURAL_FORMATION: 
                return new Artifact(
                    "Deep-Sea Formation",
                    calculateValue(depth, 15000, 40000),
                    calculateValue(depth, 40000, 80000),
                    false, null, null, depth
                ) {
                    @Override 
                    public void displayInfo() {
                        System.out.printf("Natural Formation: %s (Value: $%,.2f)%n", name, estimatedValue);
                    }
                };
                
            default:
                return null;
        }
    }

    private double calculateValue(int depth, int min, int max) {
        return min + (max - min) * (random.nextDouble());
    }

    public enum SearchMethod {
        DIVER(1.0), ROBOT(1.5), SONAR(0.8), SUBMERSIBLE(2.0);
        
        private final double multiplier;
        SearchMethod(double multiplier) { this.multiplier = multiplier; }
        public double getSuccessMultiplier() { return multiplier; }
    }

    public enum ArtifactType {
        SHIPWRECK, SUBMERGED_CITY, NATURAL_FORMATION
    }
}