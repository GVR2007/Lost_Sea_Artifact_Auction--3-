
package artifacts;

import artifacts.Artifact.HeritageStatus;
import artifacts.Artifact.Material;

public class Naturalformation extends Artifact {
    public Naturalformation(String name, double value, double bmv, boolean fragile,
                                 Material material, HeritageStatus heritageStatus, int ageYears) {
        super(name, value, bmv, fragile, material, heritageStatus, ageYears);
    }

    @Override
    public void displayInfo() {
        System.out.println("=== Artifact Details ===");
        System.out.println("Name: " + name);
        System.out.println("Estimated Value: $" + estimatedValue);
        System.out.println("Black Market Value: $" + blackMarketValue);
        System.out.println("Fragile: " + isFragile);
        System.out.println("Material: " + material);
        System.out.println("Heritage Status: " + heritageStatus);
        System.out.println("Age: " + ageYears + " years");
        System.out.println("========================");
    }
}