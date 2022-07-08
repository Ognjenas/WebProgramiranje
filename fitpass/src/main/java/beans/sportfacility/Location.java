package beans.sportfacility;

public class Location {
    private double geoLength;
    private double geoWidth;
    private Address address;

    public Location(double geoLength, double geoWidth, Address address) {
        this.geoLength = geoLength;
        this.geoWidth = geoWidth;
        this.address = address;
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

    public Address getAdress() {
        return address;
    }

    public void setAdress(Address address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return  "GeoLength=" + geoLength +
                ", GeoWidth=" + geoWidth +
                ", Adress=" + address.toString();
    }
}
