package beans;

import utilities.WorkingHours;

import java.util.List;

public class SportFacility {
    private String name;
    private SportFacilityType type;
    private List<String> offers;
    private boolean isOpen;
    // STA STAVITI ZA SLIKU?
    private String imgSource;
    private Location location;
    private double averageGrade;
    private WorkingHours openTime; //OVDE BIH NAPRAVIO KLASU POSEBNU ZA RADNO VREME ZA SVAKI DAN

    public SportFacility(String name, SportFacilityType type, List<String> offers, boolean isOpen, String imgSource, Location location, double averageGrade, WorkingHours openTime) {
        this.name = name;
        this.type = type;
        this.offers = offers;
        this.isOpen = isOpen;
        this.imgSource = imgSource;
        this.location = location;
        this.averageGrade = averageGrade;
        this.openTime = openTime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public SportFacilityType getType() {
        return type;
    }

    public void setType(SportFacilityType type) {
        this.type = type;
    }

    public List<String> getOffers() {
        return offers;
    }

    public void setOffers(List<String> offers) {
        this.offers = offers;
    }

    public boolean isOpen() {
        return isOpen;
    }

    public void setOpen(boolean open) {
        isOpen = open;
    }

    public beans.Location getLocation() {
        return location;
    }

    public void setLocation(beans.Location location) {
        this.location = location;
    }

    public double getAverageGrade() {
        return averageGrade;
    }

    public void setAverageGrade(double averageGrade) {
        this.averageGrade = averageGrade;
    }

    public WorkingHours getOpenTime() {
        return openTime;
    }

    public void setOpenTime(WorkingHours openTime) {
        this.openTime = openTime;
    }

    public String getImgSource() {
        return imgSource;
    }

    public void setImgSource(String imgSource) {
        this.imgSource = imgSource;
    }
}
