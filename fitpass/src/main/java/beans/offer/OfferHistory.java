package beans.offer;

import beans.sportfacility.SportFacility;
import beans.users.Customer;
import beans.users.Trainer;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class OfferHistory {
    private int id;
    private LocalDateTime checkIn;
    private Customer customer;
    private Trainer trainer;
    private Offer offer;
    private SportFacility sportFacility;
    private boolean isDeleted;

    public OfferHistory() {
    }

    public OfferHistory(int id, LocalDateTime checkIn, Customer customer, Trainer trainer, Offer offer, SportFacility sportFacility, boolean isDeleted) {
        this.id = id;
        this.checkIn = checkIn;
        this.customer = customer;
        this.trainer = trainer;
        this.offer = offer;
        this.sportFacility = sportFacility;
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

    public boolean isActiveDate(String fromDate, String toDate){
        LocalDate from,to;

        try{
            String[] fromDates=fromDate.split("-");
            String[] toDates=toDate.split("-");
            from=LocalDate.of(Integer.parseInt(fromDates[0]),Integer.parseInt(fromDates[1]),Integer.parseInt(fromDates[2]));
            to=LocalDate.of(Integer.parseInt(toDates[0]),Integer.parseInt(toDates[1]),Integer.parseInt(toDates[2]));
        }catch (NumberFormatException e){
            if(fromDate.equals("") || toDate.equals("")) return true;
            return false;
        }
        return from.isBefore(checkIn.toLocalDate()) && checkIn.toLocalDate().isBefore(to);
        
    public SportFacility getSportFacility() {
        return sportFacility;
    }

    public void setSportFacility(SportFacility sportFacility) {
        this.sportFacility = sportFacility;
    }
}
