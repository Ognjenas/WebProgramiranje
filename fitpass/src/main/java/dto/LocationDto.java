package dto;

public class LocationDto {
    private double geoLength;
    private double geoWidth;
    private String city;
    private String street;
    private int strNumber;
    private int postalCode;

    public LocationDto() {
    }

    public LocationDto(double geoLength, double geoWidth, String city, String street, int strNumber, int postalCode) {
        this.geoLength = geoLength;
        this.geoWidth = geoWidth;
        this.city = city;
        this.street = street;
        this.strNumber = strNumber;
        this.postalCode = postalCode;
    }

    public double getGeoLength() {
        return geoLength;
    }

    public void setGeoLength(double geoLength) {
        this.geoLength = geoLength;
    }

    public double getGeoWidth() {
        return geoWidth;
    }

    public void setGeoWidth(double geoWidth) {
        this.geoWidth = geoWidth;
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

    public int getStrNumber() {
        return strNumber;
    }

    public void setStrNumber(int strNumber) {
        this.strNumber = strNumber;
    }

    public int getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(int postalCode) {
        this.postalCode = postalCode;
    }
}
