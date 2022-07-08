package beans.offer;

import beans.users.Trainer;

import java.time.Duration;

public class Training extends Offer{
    private TrainingType trainingType;
    private Trainer belongingTrainer;


    public Training(Offer offer, TrainingType trainingType, Trainer belongingTrainer) {
        super(offer.getId(), offer.getName(), offer.getType(), offer.getDuration(), offer.getDescription(), offer.getImageLocation(), offer.getPrice(), offer.isDeleted(), offer.getSportFacility());
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
