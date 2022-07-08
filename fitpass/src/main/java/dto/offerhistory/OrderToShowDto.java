package dto.offerhistory;

public class OrderToShowDto {
    private int id;
    private String name;
    private String facilityName;
    private String type;
    private String time;

    public OrderToShowDto(int id, String name, String facilityName, String type, String time) {
        this.id = id;
        this.name = name;
        this.facilityName = facilityName;
        this.type = type;
        this.time = time;
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

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
