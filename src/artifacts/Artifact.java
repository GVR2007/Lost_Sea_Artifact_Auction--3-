package artifacts;

public abstract class Artifact {
    public enum Material { PRECIOUS, ORGANIC, GEOLOGICAL }
    public enum HeritageStatus { NATIONAL, REGIONAL, NONE }

    protected String name;
    protected double estimatedValue;
    protected double blackMarketValue;
    protected boolean isFragile;
    protected boolean sold = false;
    protected Material material;
    protected HeritageStatus heritageStatus;
    protected int ageYears; // Age in years

    public Artifact(String name, double value, double bmv, boolean fragile, 
                    Material material, HeritageStatus heritageStatus, int ageYears) {
        this.name = name;
        this.estimatedValue = value;
        this.blackMarketValue = bmv;
        this.isFragile = fragile;
        this.material = material;
        this.heritageStatus = heritageStatus;
        this.ageYears = ageYears;
    }

    // Getters for new fields
    public Material getMaterial() { return material; }
    public HeritageStatus getHeritageStatus() { return heritageStatus; }
    public int getAgeYears() { return ageYears; }
    public String getName() { return name; }
    public double getEstimatedValue() { return estimatedValue; }
    public double getBlackMarketValue() { return blackMarketValue; }
    public boolean isFragile() { return isFragile; }
    public boolean isSold() { return sold; }
    
    public void markAsSold() { this.sold = true; }

    // New method to check if the artifact is protected
    public boolean isProtected() {
        return heritageStatus == HeritageStatus.NATIONAL || heritageStatus == HeritageStatus.REGIONAL;
    }

    public abstract void displayInfo();
}