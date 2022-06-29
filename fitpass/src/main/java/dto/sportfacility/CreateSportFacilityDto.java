package dto.sportfacility;

import beans.SportFacilityType;

public class CreateSportFacilityDto {

    private String name;
    private SportFacilityType type;
    private String city;
    private String street;
    private int strNum;
    private int postCode;
    private double geoWidth;
    private double geoLength;
    private String imgSource;

    public CreateSportFacilityDto(String name, SportFacilityType type, String city, String street, int strNum, int postCode, double geoWidth, double geoLength, String imgSource) {
        this.name = name;
        this.type = type;
        this.city = city;
        this.street = street;
        this.strNum = strNum;
        this.postCode = postCode;
        this.geoWidth = geoWidth;
        this.geoLength = geoLength;
        this.imgSource = imgSource;
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

    public String getImgSource() {
        return imgSource;
    }

    public void setImgSource(String imgSource) {
        this.imgSource = imgSource;
    }
}
