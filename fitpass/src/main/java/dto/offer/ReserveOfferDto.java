package dto.offer;

import java.time.LocalDate;

public class ReserveOfferDto {
    private int offerId;
    private LocalDate date;
    private String time;

    public ReserveOfferDto() {
    }

    public ReserveOfferDto(int offerId, LocalDate date, String time) {
        this.offerId = offerId;
        this.date = date;
        this.time = time;
    }

    public int getOfferId() {
        return offerId;
    }

    public void setOfferId(int offerId) {
        this.offerId = offerId;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
