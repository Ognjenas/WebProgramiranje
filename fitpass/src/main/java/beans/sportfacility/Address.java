package beans.sportfacility;

public class Address {
    private String city;
    private String street;
    private int strNumber;
    private int postalCode;

    public Address(String city, String street, int strNumber, int postalCode) {
        this.city = city;
        this.street = street;
        this.strNumber = strNumber;
        this.postalCode = postalCode;
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

    @Override
    public String toString() {
        return "City='" + city + '\'' +
                ", Street='" + street + '\'' +
                ", StrNumber=" + strNumber +
                ", PostalCode=" + postalCode;
    }
}
