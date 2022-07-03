package dto.sportfacility;

import beans.sportfacility.SportFacilityType;

import java.time.LocalDate;

public class CreateFacilityWithManagerDto {
    private String name;
    private SportFacilityType type;
    private String city;
    private String street;
    private int strNum;
    private int postCode;
    private double geoWidth;
    private double geoLength;
    private String managerName;
    private String managerSurname;
    private String managerUsername;
    private String managerPassword;
    private boolean managerGender;
    private LocalDate managerBirthDate;

    public CreateFacilityWithManagerDto(String name, SportFacilityType type, String city, String street, int strNum, int postCode, double geoWidth, double geoLength, String managerName, String managerSurname, String managerUsername, String managerPassword, boolean managerGender, LocalDate managerBirthDate) {
        this.name = name;
        this.type = type;
        this.city = city;
        this.street = street;
        this.strNum = strNum;
        this.postCode = postCode;
        this.geoWidth = geoWidth;
        this.geoLength = geoLength;
        this.managerName = managerName;
        this.managerSurname = managerSurname;
        this.managerUsername = managerUsername;
        this.managerPassword = managerPassword;
        this.managerGender = managerGender;
        this.managerBirthDate = managerBirthDate;
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

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public int getStrNum() {
        return strNum;
    }

    public void setStrNum(int strNum) {
        this.strNum = strNum;
    }

    public int getPostCode() {
        return postCode;
    }

    public void setPostCode(int postCode) {
        this.postCode = postCode;
    }

    public double getGeoWidth() {
        return geoWidth;
    }

    public void setGeoWidth(double geoWidth) {
        this.geoWidth = geoWidth;
    }

    public double getGeoLength() {
        return geoLength;
    }

    public void setGeoLength(double geoLength) {
        this.geoLength = geoLength;
    }

    public String getManagerName() {
        return managerName;
    }

    public void setManagerName(String managerName) {
        this.managerName = managerName;
    }

    public String getManagerSurname() {
        return managerSurname;
    }

    public void setManagerSurname(String managerSurname) {
        this.managerSurname = managerSurname;
    }

    public String getManagerUsername() {
        return managerUsername;
    }

    public void setManagerUsername(String managerUsername) {
        this.managerUsername = managerUsername;
    }

    public String getManagerPassword() {
        return managerPassword;
    }

    public void setManagerPassword(String managerPassword) {
        this.managerPassword = managerPassword;
    }

    public boolean isManagerGender() {
        return managerGender;
    }

    public void setManagerGender(boolean managerGender) {
        this.managerGender = managerGender;
    }

    public LocalDate getManagerBirthDate() {
        return managerBirthDate;
    }

    public void setManagerBirthDate(LocalDate managerBirthDate) {
        this.managerBirthDate = managerBirthDate;
    }
}
