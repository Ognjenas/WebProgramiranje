package beans.users;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Subscription {
    private String id;
    private SubscriptionType type;
    private LocalDate payDate;
    private LocalDate validUntil;
    private double price;
    private Customer customer;
    private boolean status;
    private int dailyEnteringNumber;
    private boolean isDeleted;
    private int orderedAppointments;

    public Subscription(String id, SubscriptionType type, LocalDate payDate, LocalDate validUntil,
                        double price, Customer customer, boolean status, int dailyEnteringNumber) {
        this.id = id;
        this.type = type;
        this.payDate = payDate;
        this.validUntil = validUntil;
        this.price = price;
        this.customer = customer;
        this.status = status;
        this.dailyEnteringNumber = dailyEnteringNumber;
        this.isDeleted = false;
        this.orderedAppointments = 0;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public SubscriptionType getType() {
        return type;
    }

    public void setType(SubscriptionType type) {
        this.type = type;
    }

    public LocalDate getPayDate() {
        return payDate;
    }

    public void setPayDate(LocalDate payDate) {
        this.payDate = payDate;
    }

    public LocalDate getValidUntil() {
        return validUntil;
    }

    public void setValidUntil(LocalDate validUntil) {
        this.validUntil = validUntil;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public int getDailyEnteringNumber() {
        return dailyEnteringNumber;
    }

    public void setDailyEnteringNumber(int dailyEnteringNumber) {
        this.dailyEnteringNumber = dailyEnteringNumber;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }


    public int getOrderedAppointments() {
        return orderedAppointments;
    }

    public void setOrderedAppointments(int orderedAppointments) {
        this.orderedAppointments = orderedAppointments;
    }

    public int getMaximumAppointments() {
        if(type.equals(SubscriptionType.MONTHLY)) {
            return 31 * this.dailyEnteringNumber;
        } else {
            return 365 * this.dailyEnteringNumber;
        }
    }


    @Override
    public String toString() {
        return "Subscription{" +
                "id='" + id + '\'' +
                ", type=" + type +
                ", payDate=" + payDate +
                ", validUntil=" + validUntil +
                ", price=" + price +
                ", customer=" + customer +
                ", status=" + status +
                ", dailyEnteringNumber=" + dailyEnteringNumber +
                '}';
    }
}
