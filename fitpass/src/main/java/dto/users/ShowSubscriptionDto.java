package dto.users;

import beans.users.Customer;
import beans.users.SubscriptionType;

import java.time.LocalDate;

public class ShowSubscriptionDto {
    private String id;
    private SubscriptionType type;
    private LocalDate payDate;
    private LocalDate validUntil;
    private int dailyEnteringNumber;

    public ShowSubscriptionDto(String id, SubscriptionType type, LocalDate payDate, LocalDate validUntil, int dailyEnteringNumber) {
        this.id = id;
        this.type = type;
        this.payDate = payDate;
        this.validUntil = validUntil;
        this.dailyEnteringNumber = dailyEnteringNumber;
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

    public int getDailyEnteringNumber() {
        return dailyEnteringNumber;
    }

    public void setDailyEnteringNumber(int dailyEnteringNumber) {
        this.dailyEnteringNumber = dailyEnteringNumber;
    }
}
