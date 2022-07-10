package dto.offer;

public class ShortOfferDto {
    private int id;
    private String name;
    private String type;
    private String imgSource;

    public ShortOfferDto() {
    }

    public ShortOfferDto(int id, String name, String type, String imgSource) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.imgSource = imgSource;
    }

    public String getImgSource() {
        return imgSource;
    }

    public void setImgSource(String imgSource) {
        this.imgSource = imgSource;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
