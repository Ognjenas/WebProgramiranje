package dto.offer;

public class MakeOfferDto {
    private String name;
    private String type;
    private String trainingType;
    private int trainerId;
    private String description;
    private String imgSource;
    private int hourDuration;
    private int minuteDuration;
    private int price;

    public MakeOfferDto() {
    }

    public MakeOfferDto(String name, String type, String trainingType, int trainerId, String description, String imgSource, int hourDuration, int minuteDuration, int price) {
        this.name = name;
        this.type = type;
        this.trainingType = trainingType;
        this.trainerId = trainerId;
        this.description = description;
        this.imgSource = imgSource;
        this.hourDuration = hourDuration;
        this.minuteDuration = minuteDuration;
        this.price = price;
    }

    public String getImgSource() {
        return imgSource;
    }

    public void setImgSource(String imgSource) {
        this.imgSource = imgSource;
    }

    public String getTrainingType() {
        return trainingType;
    }

    public void setTrainingType(String trainingType) {
        this.trainingType = trainingType;
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

    public int getTrainerId() {
        return trainerId;
    }

    public void setTrainerId(int trainerId) {
        this.trainerId = trainerId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getHourDuration() {
        return hourDuration;
    }

    public void setHourDuration(int hourDuration) {
        this.hourDuration = hourDuration;
    }

    public int getMinuteDuration() {
        return minuteDuration;
    }

    public void setMinuteDuration(int minuteDuration) {
        this.minuteDuration = minuteDuration;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
