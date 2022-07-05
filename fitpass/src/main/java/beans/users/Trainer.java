package beans.users;

import beans.offer.TrainingHistory;


public class Trainer extends User{

    private TrainingHistory trainingHistory;

    public Trainer() {
    }

    public Trainer(User user, TrainingHistory trainingHistory) {
        super(user.getId(), user.getUsername(), user.getPassword(), user.getName(), user.getSurname(), user.isGender(), user.getBirthDate(), user.getRole());
        this.trainingHistory = trainingHistory;
    }

    public TrainingHistory getTrainingHistory() {
        return trainingHistory;
    }

    public void setTrainingHistory(TrainingHistory trainingHistory) {
        this.trainingHistory = trainingHistory;
    }
}
