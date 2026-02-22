package auction;

import artifacts.Artifact;
import bidders.Bidder;
import java.util.*;

public class Auction {
    protected List<Bidder> bidders = new ArrayList<>();
    protected Artifact artifact;
    protected double currentBid;
    protected Bidder highestBidder = null;
    protected boolean bmvClosed = false;

    public Auction(Artifact artifact) {
        this.artifact = artifact;
        this.currentBid = artifact.getEstimatedValue();
    }

    public void addBidder(Bidder bidder) {
        if (bidder.canBid(artifact)) {
            bidders.add(bidder);
        }
    }

    public void startAuction() {
        System.out.println("=== Starting Auction for: " + artifact.getName() + " ===");
        System.out.println("Current Bid: $" + currentBid);
        
        Scanner scanner = new Scanner(System.in);
        while (!bidders.isEmpty()) {
            List<Bidder> currentBidders = new ArrayList<>(bidders);
            for (Bidder bidder : currentBidders) {
                if (bidder.hasLeft()) continue;

                System.out.print(bidder.getName() + ", enter your bid (or 'exit'/'BMV'): ");
                String input = scanner.nextLine();

                if (handleSpecialCommands(bidder, input)) continue;
                handleBid(bidder, input);
            }
            bidders.removeIf(Bidder::hasLeft);
        }
        concludeAuction();
    }

    protected boolean handleSpecialCommands(Bidder bidder, String input) {
        if (input.equalsIgnoreCase("exit")) {
            bidder.leaveAuction();
            System.out.println(bidder.getName() + " has dropped out.");
            return true;
        }
        if (input.equalsIgnoreCase("BMV")) {
            handleBMVOption(bidder);
            return true;
        }
        return false;
    }

    protected void handleBMVOption(Bidder bidder) {
        if (bmvClosed) {
            System.out.println("Black market option closed");
            return;
        }
        if (bidder.placeBid(artifact.getBlackMarketValue())) {
            completePurchase(bidder, artifact.getBlackMarketValue());
        } else {
            System.out.println("Insufficient funds for BMV");
        }
    }

    protected void handleBid(Bidder bidder, String input) {
        try {
            double bid = Double.parseDouble(input);
            if (bid > currentBid && bidder.placeBid(bid)) {
                currentBid = bid;
                highestBidder = bidder;
                System.out.println("New highest bid: $" + currentBid);
                checkBMVClosure();
            } else {
                System.out.println("Invalid bid. Your bid must be greater than the current bid of $" + currentBid);
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a valid number for your bid.");
        }
    }

    protected void checkBMVClosure() {
        if (currentBid >= artifact.getBlackMarketValue() * 0.5) {
            bmvClosed = true;
            System.out.println("Black market option now closed");
        }
    }

    protected void completePurchase(Bidder winner, double price) {
        winner.addPurchasedItem(artifact, price);
        artifact.markAsSold();
        System.out.println(winner.getName() + " wins for $" + price);
        bidders.clear();
        artifact.displayInfo();
    }

    protected void concludeAuction() {
        if (highestBidder != null) {
            completePurchase(highestBidder, currentBid);
        } else {
            System.out.println("Auction ended with no winner");
        }
    }

    public double getFinalPrice() {
        return currentBid;
    }

    public String getWinnerName() {
        return highestBidder != null ? highestBidder.getName() : "No winner";
    }
}