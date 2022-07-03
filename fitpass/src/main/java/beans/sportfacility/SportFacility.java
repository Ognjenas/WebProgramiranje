package beans.sportfacility;

import utilities.WorkingHours;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class SportFacility {
    private int id;
    private String name;
    private SportFacilityType type;
    private List<String> offers;
    private boolean isOpen;
    // STA STAVITI ZA SLIKU?
    private String imgSource;
    private Location location;
    private double averageGrade;
    private WorkingHours openTime; //OVDE BIH NAPRAVIO KLASU POSEBNU ZA RADNO VREME ZA SVAKI DAN
    private boolean isDeleted;

    public SportFacility(int id, String name, SportFacilityType type, List<String> offers, boolean isOpen, String imgSource, Location location, double averageGrade, WorkingHours openTime,boolean deleted) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.offers = offers;
        this.isOpen = isOpen;
        this.imgSource = imgSource;
        this.location = location;
        this.averageGrade = averageGrade;
        this.openTime = openTime;
        this.isDeleted = deleted;
    }

    public SportFacility(int id, String name, SportFacilityType facType, Location loc) {
        this.id = id;
        this.name = name;
        this.type = facType;
        this.imgSource = "";
        this.location = loc;

        this.offers = new ArrayList<>();
        this.isOpen = false;
        this.averageGrade = 0.0;
        LocalTime zeroTime = LocalTime.of(0, 0, 0, 0);
        this.openTime = new WorkingHours(zeroTime, zeroTime, zeroTime, zeroTime, zeroTime, zeroTime);
        this.isDeleted = false;
    }

    public int getId() { return id;  }

    public void setId(int id) { this.id = id; }

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

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
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

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }

    public boolean isSearched(String name,String type,String city,String grade){
        // IF AVERAGE GRADE NOT SELECTED THEN BETWEEN 0-5
        double minGrade;
        double maxGrade;
        if(grade.equals("")) {
            minGrade=0;
            maxGrade=5;
        }else{
            String[] grades = grade.split("-");
            minGrade = Double.parseDouble(grades[0]);
            maxGrade = Double.parseDouble(grades[1]);
        }
        //MAKES UPPER LOWER VALUES FOR AVERAGE GRADE

        return this.name.toLowerCase().contains(name.toLowerCase()) && this.type.toString().toLowerCase().contains(type.toLowerCase())
                && this.location.getAdress().getCity().toLowerCase().contains(city.toLowerCase()) && minGrade<=averageGrade && averageGrade<=maxGrade;
    }


}