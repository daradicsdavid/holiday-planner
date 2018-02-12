package planner;

public class PlaceAndCheaperPlace {
    private final String placeName;
    private final String dependentPlaceName;

    PlaceAndCheaperPlace(String placeName, String dependentPlaceName) {
        this.placeName = placeName;
        this.dependentPlaceName = dependentPlaceName;
    }

    public String getPlaceName() {
        return placeName;
    }

    public String getDependentPlaceName() {
        return dependentPlaceName;
    }
}
