package dto.offer;

import dto.users.TrainerToChooseDto;

public class OfferDto {
    private int id;
    private String name;
    private String type;
    private String trainingType;
    private TrainerToChooseDto trainer;
    private int trainerId;
    private String description;
    private int hourDuration;
    private int minuteDuration;
    private int price;
    private String imgSource;

    public OfferDto(int id, String name, String type, String description, int hourDuration, int minuteDuration, int price, String imgSource) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.description = description;
        this.hourDuration = hourDuration;
        this.minuteDuration = minuteDuration;
        this.price = price;
        this.imgSource = imgSource;
    }

    public String getImgSource() {
        return imgSource;
    }

    public void setImgSource(String imgSource) {
        this.imgSource = imgSource;
    }

    public int getTrainerId() {
        return trainerId;
    }

    public void setTrainerId(int trainerId) {
        this.trainerId = trainerId;
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

    public String getTrainingType() {
        return trainingType;
    }

    public void setTrainingType(String trainingType) {
        this.trainingType = trainingType;
    }

    public TrainerToChooseDto getTrainer() {
        return trainer;
    }

    public void setTrainer(TrainerToChooseDto trainer) {
        this.trainer = trainer;
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
