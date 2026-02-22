package bidders;

import artifacts.Artifact;

public abstract class Bidder {
    protected String name;
    protected double budget;
    protected boolean hasLeft = false;

    public Bidder(String name, double budget) {
        this.name = name;
        this.budget = budget;
    }

    public String getName() { return name; }
    public double getBudget() { return budget; }
    public boolean hasLeft() { return hasLeft; }
    public void leaveAuction() { hasLeft = true; }

    public boolean placeBid(double amount) {
        if (amount <= budget) {
            budget -= amount;
            return true;
        }
        return false;
    }

    public abstract boolean canBid(Artifact artifact);
    public abstract void addPurchasedItem(Artifact artifact, double price);
}