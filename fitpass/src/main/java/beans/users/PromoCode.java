package beans.users;

import java.time.LocalDate;

public class PromoCode {
    private String code;
    private double discount;
    private LocalDate startDate;
    private LocalDate endDate;
    private int usageTimes;

    public PromoCode(String code, double discount, LocalDate startDate, LocalDate endDate, int usageTimes) {
        this.code = code;
        this.discount = discount;
        this.startDate = startDate;
        this.endDate = endDate;
        this.usageTimes = usageTimes;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public int getUsageTimes() {
        return usageTimes;
    }

    public void setUsageTimes(int usageTimes) {
        this.usageTimes = usageTimes;
    }
}
