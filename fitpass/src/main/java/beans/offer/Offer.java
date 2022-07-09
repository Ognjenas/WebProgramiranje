package beans.offer;

import beans.sportfacility.SportFacility;

import java.time.Duration;

public class Offer {
    private int id;
    private String name;
    private OfferType type;
    private Duration duration;
    private String description;
    private String imageLocation;
    private SportFacility sportFacility;
    private boolean isDeleted;
    private double price;

    public Offer() {
    }

    public Offer(String name, OfferType type, Duration duration, String description, String imageLocation, double price,boolean isDeleted, SportFacility sportFacility) {
        this.name = name;
        this.type = type;
        this.duration = duration;
        this.description = description;
        this.imageLocation = imageLocation;
        this.price = price;
        this.isDeleted = isDeleted;
        this.sportFacility = sportFacility;
    }

    public Offer(int id, String name, OfferType type, Duration duration, String description, String imageLocation, double price, boolean isDeleted, SportFacility sportFacility) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.duration = duration;
        this.description = description;
        this.imageLocation = imageLocation;
        this.price = price;
        this.isDeleted = isDeleted;
        this.sportFacility = sportFacility;
    }

    public SportFacility getSportFacility() {
        return sportFacility;
    }

    public void setSportFacility(SportFacility sportFacility) {
        this.sportFacility = sportFacility;
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

    public OfferType getType() {
        return type;
    }

    public void setType(OfferType type) {
        this.type = type;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Duration getDuration() {
        return duration;
    }

    public void setDuration(Duration duration) {
        this.duration = duration;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageLocation() {
        return imageLocation;
    }

    public void setImageLocation(String imageLocation) {
        this.imageLocation = imageLocation;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }


    public boolean searchedTypeAndPriceRange(String trainingType, String price) {

        double bottomPrice;
        double topPrice;
        if (price.equals("0")) {
            bottomPrice = 0;
            topPrice = 1000;
        } else if (price.equals("1")) {
            bottomPrice = 1000;
            topPrice = 5000;
        } else if (price.equals("2")) {
            bottomPrice = 5000;
            topPrice = 10000;
        } else if (price.equals("3")) {
            bottomPrice = 10000;
            topPrice = Double.POSITIVE_INFINITY;
        } else {
            bottomPrice = 0;
            topPrice = Double.POSITIVE_INFINITY;
        }

        return this.type.toString().toLowerCase().contains(trainingType.toLowerCase()) && bottomPrice<this.price && topPrice>this.price;
    }
}
