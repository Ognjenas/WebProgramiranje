package beans.training;

import beans.users.Trainer;

import java.time.Duration;

public class Training {
    private String name;
    private TrainingType type;
    private Duration duration;
    private Trainer belongingTrainer;
    private String description;
    private String imageLocation;

    public Training() {
    }

    public Training(String name, TrainingType type, Duration duration, Trainer belongingTrainer, String description, String imageLocation) {
        this.name = name;
        this.type = type;
        this.duration = duration;
        this.belongingTrainer = belongingTrainer;
        this.description = description;
        this.imageLocation = imageLocation;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public TrainingType getType() {
        return type;
    }

    public void setType(TrainingType type) {
        this.type = type;
    }

    public Duration getDuration() {
        return duration;
    }

    public void setDuration(Duration duration) {
        this.duration = duration;
    }

    public Trainer getBelongingTrainer() {
        return belongingTrainer;
    }

    public void setBelongingTrainer(Trainer belongingTrainer) {
        this.belongingTrainer = belongingTrainer;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageLocation() {
        return imageLocation;
    }

    public void setImageLocation(String imageLocation) {
        this.imageLocation = imageLocation;
    }
}
