package auction;

import artifacts.Artifact;
import bidders.Bidder;
import java.util.Scanner;

public class ReverseBlackMarketAuction extends Auction {
    private static final double PRICE_REDUCTION_RATE = 0.95;

    public ReverseBlackMarketAuction(Artifact artifact) {
        super(artifact);
    }

    @Override
    public void startAuction() {
        if (!artifact.isFragile()) {
            System.out.println("Reverse auction only for fragile artifacts");
            return;
        }

        double currentPrice = artifact.getBlackMarketValue();
        System.out.println("=== Starting Reverse Auction for: " + artifact.getName() + " ===");
        
        Scanner scanner = new Scanner(System.in);
        while (currentPrice > artifact.getEstimatedValue() * 0.3 && !bidders.isEmpty()) {
            System.out.println("\nCurrent Price: $" + currentPrice);
            System.out.print("Freeze price? (yes/no): ");
            
            if (scanner.nextLine().equalsIgnoreCase("yes")) {
                handlePurchaseAttempt(currentPrice, scanner);
                if (artifact.isSold()) {
    
                    return;
                }
            }
            currentPrice *= PRICE_REDUCTION_RATE;
        }
        System.out.println("Reverse auction ended unsold.");
        scanner.close();
    }

    private void handlePurchaseAttempt(double price, Scanner scanner) {
        System.out.print("Enter bidder name: ");
        String name = scanner.nextLine();
        
        bidders.stream()
            .filter(b -> b.getName().equalsIgnoreCase(name))
            .findFirst()
            .ifPresentOrElse(
                bidder -> attemptPurchase(bidder, price),
                () -> System.out.println("Bidder not found")
            );
    }

    private void attemptPurchase(Bidder bidder, double price) {
        if (bidder.placeBid(price)) {
            super.completePurchase(bidder, price);
        } else {
            System.out.println(bidder.getName() + " cannot afford this price.");
        }
    }
}