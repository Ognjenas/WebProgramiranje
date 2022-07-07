package beans.offer;

import beans.users.Customer;
import beans.users.Trainer;

import java.time.LocalDateTime;

public class OfferHistory {
    private int id;
    private LocalDateTime checkIn;
    private Customer customer;
    private Trainer trainer;
    private Offer offer;
    private boolean isDeleted;

    public OfferHistory() {
    }

    public OfferHistory(LocalDateTime checkIn, Customer customer, Trainer trainer, Offer offer, boolean isDeleted) {
        this.checkIn = checkIn;
        this.customer = customer;
        this.trainer = trainer;
        this.offer = offer;
        this.isDeleted = isDeleted;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDateTime getCheckIn() {
        return checkIn;
    }

    public void setCheckIn(LocalDateTime checkIn) {
        this.checkIn = checkIn;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Trainer getTrainer() {
        return trainer;
    }

    public void setTrainer(Trainer trainer) {
        this.trainer = trainer;
    }

    public Offer getOffer() {
        return offer;
    }

    public void setOffer(Offer offer) {
        this.offer = offer;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }
}
