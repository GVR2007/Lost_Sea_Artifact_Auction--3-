package utils;

import artifacts.Artifact;
import auction.Auction;
import auction.ReverseBlackMarketAuction;
import bidders.Bidder;
import bidders.GovernmentAgency;
import bidders.Museum;
import bidders.PrivateCollector;

import java.util.*;

public class LostArtifactSystem {
    private final List<Artifact> artifacts = new ArrayList<>();
    private final List<Bidder> bidders = new ArrayList<>();
    private final PurchaseTracker tracker = PurchaseTracker.getInstance();
    private final Scanner scanner = new Scanner(System.in);

    public void run() {
        while (true) {
            displayMenu();
            int choice = getIntInput();

            switch (choice) {
                case 1:
                    searchArtifact();
                    break;
                case 2:
                    addBidder();
                    break;
                case 3:
                    startStandardAuction();
                    break;
                case 4:
                    startReverseAuction();
                    break;
                case 5:
                    displayBidderInfo();
                    break;
                case 6:
                    System.out.println("Exiting system...");
                    scanner.close(); // Close scanner before exiting
                    return;
                default:
                    System.out.println("Invalid choice! Please try again.");
            }
        }
    }

    private void searchArtifact() {
        ArtifactSearch artifactSearch = new ArtifactSearch(bidders); // Pass bidders to ArtifactSearch
        Artifact found = artifactSearch.searchArtifact(); // Call the correct method
        if (found != null) {
            artifacts.add(found);
            found.displayInfo();
            System.out.println("Added to available artifacts.");
        }
    }

    private void addBidder() {
        System.out.println("\n=== Add Bidder ===");
        System.out.println("1. Private Collector");
        System.out.println("2. Museum");
        System.out.println("3. Government Agency");
        System.out.print("Select type: ");
        
        int type = getIntInput();
        scanner.nextLine(); // Consume newline left-over
        System.out.print("Enter name: ");
        String name = scanner.nextLine();
        
        Bidder bidder = null;
        
        switch (type) {
            case 1:
                System.out.print("Enter budget: ");
                double budget = getDoubleInput();
                bidder = new PrivateCollector(name, budget);
                break;
            case 2:
                System.out.println("1. Maritime");
                System.out.println("2. Cultural");
                System.out.println("3. Natural History");
                System.out.print("Select specialization: ");
                int spec = getIntInput();
                Museum.Specialization specialization = null;
                
                switch (spec) {
                    case 1:
                        specialization = Museum.Specialization.MARITIME;
                        break;
                    case 2:
                        specialization = Museum.Specialization.CULTURAL;
                        break;
                    case 3:
                        specialization = Museum.Specialization.NATURAL_HISTORY;
                        break;
                    default:
                        System.out.println("Invalid specialization!");
                        return;
                }
                
                bidder = new Museum(name, Double.MAX_VALUE, specialization);
                break;
            case 3:
                bidder = new GovernmentAgency(name);
                break;
            default:
                System.out.println("Invalid bidder type!");
                return;
        }
        
        bidders.add(bidder);
        System.out.println("Bidder added successfully.");
    }

    private void startStandardAuction() {
        if (artifacts.isEmpty() || bidders.isEmpty()) {
            System.out.println("Need at least 1 artifact and 1 bidder");
            return;
        }
        
        Artifact artifact = selectArtifact();
        if (artifact == null) return; // Check for null selection
        
        Auction auction = new Auction(artifact);
        
        for (Bidder bidder : bidders) {
            auction.addBidder(bidder);
        }

        auction.startAuction();

        if (artifact.isSold()) {
            artifacts.remove(artifact);
            tracker.recordPurchase(new PurchasedItem(artifact, auction.getFinalPrice(), auction.getWinnerName()));
        }
    }

    private void startReverseAuction() {
        if (artifacts.isEmpty() || bidders.isEmpty()) {
            System.out.println("Need at least 1 artifact and 1 bidder");
            return;
        }

        Artifact artifact = selectArtifact();
        if (artifact == null) return; // Check for null selection
        
        ReverseBlackMarketAuction auction = new ReverseBlackMarketAuction(artifact);
        
        for (Bidder bidder : bidders) {
            auction.addBidder(bidder);
        }

        auction.startAuction();

        if (artifact.isSold()) {
            artifacts.remove(artifact);
            tracker.recordPurchase(new PurchasedItem(artifact, auction.getFinalPrice(), auction.getWinnerName()));
        }
    }

    private Artifact selectArtifact() {
        System.out.println("\nAvailable artifacts:");
        for (int i = 0; i < artifacts.size(); i++) {
            System.out.printf("%d. %s%n", i + 1, artifacts.get(i).getName());
        }
        System.out.print("Select artifact: ");
        int index = getIntInput() - 1;

        if (index < 0 || index >= artifacts.size()) {
            System.out.println("Invalid selection!");
            return null;
        }

        return artifacts.get(index);
    }

    private void displayBidderInfo() {
        for (Bidder b : bidders) {
            System.out.printf("%n=== %s ===%n", b.getName());
            if (b instanceof PrivateCollector) {
                ((PrivateCollector) b).displayCollection();
            } else if (b instanceof Museum) {
                ((Museum) b).displayExhibits();
            } else if (b instanceof GovernmentAgency) {
                ((GovernmentAgency) b).displayProtectedItems();
            }
        }
    }

    private void displayMenu() {
        System.out.println("\n=== Lost Artifact System ===");
        System.out.println("1. Search for Artifact");
        System.out.println("2. Add Bidder");
        System.out.println("3. Start Standard Auction");
        System.out.println("4. Start Reverse Auction");
        System.out.println("5. View Bidder Collections");
        System.out.println("6. Exit");
        System.out.print("Enter choice: ");
    }

    private int getIntInput() {
        while (true) {
            try {
                return Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input! Please enter a number.");
            }
        }
    }

    private double getDoubleInput() {
        while (true) {
            try {
                return Double.parseDouble(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input! Please enter a valid number.");
            }
        }
    }
}