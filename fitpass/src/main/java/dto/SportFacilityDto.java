package dto;

import beans.SportFacilityType;
import dto.sportfacility.WorkingHoursDto;

public class SportFacilityDto {
    private String name;
    private SportFacilityType type;
    private LocationDto location;
    private boolean isOpen;
    private double averageGrade;
    private WorkingHoursDto openTime;

    public SportFacilityDto() {
    }

    public SportFacilityDto(String name, SportFacilityType type, LocationDto location, boolean isOpen, double averageGrade, WorkingHoursDto openTime) {
        this.name = name;
        this.type = type;
        this.location = location;
        this.isOpen = isOpen;
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
