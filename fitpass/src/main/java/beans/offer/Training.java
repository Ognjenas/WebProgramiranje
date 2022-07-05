package beans.offer;

import beans.users.Trainer;

import java.time.Duration;

public class Training extends Offer{
    private TrainingType trainingType;
    private Trainer belongingTrainer;

    public Training(int id, String name, OfferType type, Duration duration, String description, String imageLocation,
                    boolean isDeleted, TrainingType trainingType, Trainer belongingTrainer) {
        super(id, name, type, duration, description, imageLocation, isDeleted);
        this.trainingType = trainingType;
        this.belongingTrainer = belongingTrainer;
    }

    public Training(Offer offer, TrainingType trainingType, Trainer belongingTrainer) {
        super(offer.getId(), offer.getName(), offer.getType(), offer.getDuration(), offer.getDescription(), offer.getImageLocation(), offer.isDeleted());
        this.trainingType = trainingType;
        this.belongingTrainer = belongingTrainer;
    }

    public TrainingType getTrainingType() {
        return trainingType;
    }

    public void setTrainingType(TrainingType trainingType) {
        this.trainingType = trainingType;
    }

    public Trainer getBelongingTrainer() {
        return belongingTrainer;
    }

    public void setBelongingTrainer(Trainer belongingTrainer) {
        this.belongingTrainer = belongingTrainer;
    }
}
