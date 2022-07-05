package dto.users;

import java.util.List;

public class AllTrainersToChooseDto {
    private List<TrainerToChooseDto> trainers;

    public AllTrainersToChooseDto() {
    }

    public AllTrainersToChooseDto(List<TrainerToChooseDto> trainers) {
        this.trainers = trainers;
    }

    public List<TrainerToChooseDto> getTrainers() {
        return trainers;
    }

    public void setTrainers(List<TrainerToChooseDto> trainers) {
        this.trainers = trainers;
    }
}
