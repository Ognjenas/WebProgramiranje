package dto.users;

import beans.users.SubscriptionType;

public class MakeSubscriptionDto {
    private SubscriptionType type;
    private double price;
    private int dailyTrainings;
    private String username;

    public MakeSubscriptionDto(SubscriptionType type, double price, int dailyTrainings, String username) {
        this.type = type;
        this.price = price;
        this.dailyTrainings = dailyTrainings;
        this.username = username;
    }

    public SubscriptionType getType() {
        return type;
    }

    public void setType(SubscriptionType type) {
        this.type = type;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getDailyTrainings() {
        return dailyTrainings;
    }

    public void setDailyTrainings(int dailyTrainings) {
        this.dailyTrainings = dailyTrainings;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
