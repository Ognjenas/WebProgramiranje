package dto.offerhistory;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class OrderToShowDto {
    private int id;
    private String name;
    private String facilityName;
    private String type;
    private LocalDateTime time;
    private double price;


    public OrderToShowDto(int id, String name, String facilityName, String type, LocalDateTime time,double price) {
        this.id = id;
        this.name = name;
        this.facilityName = facilityName;
        this.type = type;
        this.time = time;
        this.price=price;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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

    public String getFacilityName() {
        return facilityName;
    }

    public void setFacilityName(String facilityName) {
        this.facilityName = facilityName;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
