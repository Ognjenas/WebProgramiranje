package dto.users;

import java.time.LocalDate;

public class PromoCodeCreateDto {
    private String code;
    private double discount;
    private LocalDate endDate;
    private int usageTimes;

    public PromoCodeCreateDto(String code, double discount, LocalDate endDate, int usageTimes) {
        this.code = code;
        this.discount = discount;
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
