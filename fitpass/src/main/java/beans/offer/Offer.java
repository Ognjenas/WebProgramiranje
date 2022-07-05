package beans.offer;

import beans.users.Trainer;

import java.time.Duration;

public class Offer {
    private int id;
    private String name;
    private OfferType type;
    private Duration duration;
    private String description;
    private String imageLocation;
    private boolean isDeleted;

    public Offer() {
    }

    public Offer(String name, OfferType type, Duration duration, String description, String imageLocation, boolean isDeleted) {
        this.name = name;
        this.type = type;
        this.duration = duration;
        this.description = description;
        this.imageLocation = imageLocation;
        this.isDeleted = isDeleted;
    }

    public Offer(int id, String name, OfferType type, Duration duration, String description, String imageLocation, boolean isDeleted) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.duration = duration;
        this.description = description;
        this.imageLocation = imageLocation;
        this.isDeleted = isDeleted;
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
}
