package dto.sportfacility;

import beans.sportfacility.SportFacilityType;

public class SportFacilityDto {
    private int id;
    private String name;
    private SportFacilityType type;
    private LocationDto location;
    private boolean isOpen;
    private double averageGrade;
    private WorkingHoursDto openTime;
    private String imgSource;
    public SportFacilityDto() {
    }

    public SportFacilityDto(int id,String name, SportFacilityType type, LocationDto location, boolean isOpen, double averageGrade, WorkingHoursDto openTime, String imgSource) {
        this.id=id;
        this.name = name;
        this.type = type;
        this.location = location;
        this.isOpen = isOpen;
        this.averageGrade = averageGrade;
        this.openTime = openTime;
        this.imgSource = imgSource;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public LocationDto getLocation() {
        return location;
    }

    public void setLocation(LocationDto location) {
        this.location = location;
    }

    public boolean isOpen() {
        return isOpen;
    }

    public void setOpen(boolean open) {
        isOpen = open;
    }

    public double getAverageGrade() {
        return averageGrade;
    }

    public void setAverageGrade(double averageGrade) {
        this.averageGrade = averageGrade;
    }

    public WorkingHoursDto getOpenTime() {
        return openTime;
    }

    public void setOpenTime(WorkingHoursDto openTime) {
        this.openTime = openTime;
    }

}
