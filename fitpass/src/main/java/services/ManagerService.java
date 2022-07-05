package services;

import beans.offer.Offer;
import beans.offer.OfferType;
import beans.offer.Training;
import beans.offer.TrainingType;
import beans.sportfacility.SportFacility;
import beans.users.Manager;
import beans.users.Trainer;
import beans.users.User;
import dto.offer.MakeOfferDto;
import dto.offer.OffersToShowDto;
import dto.offer.ShortOfferDto;
import dto.sportfacility.LocationDto;
import dto.sportfacility.SportFacilityDto;
import dto.sportfacility.WorkingHoursDto;
import dto.users.AllTrainersToChooseDto;
import dto.users.AllUsersDto;
import dto.users.TrainerToChooseDto;
import storage.ManagerStorage;
import storage.SportFacilityStorage;
import storage.TrainerStorage;
import storage.UserStorage;
import storage.offer.OfferStorage;
import storage.offer.TrainingStorage;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class ManagerService {

    private static ManagerService instance = null;
    private static final SportFacilityService sportFacilityService = SportFacilityService.getInstance();
    private static final ManagerStorage managerStorage = ManagerStorage.getInstance();
    private static final SportFacilityStorage sportFacilityStorage = SportFacilityStorage.getInstance();
    private static final OfferStorage offerStorage = OfferStorage.getInstance();
    private static final UserStorage userStorage = UserStorage.getInstance();
    private static final TrainingStorage trainingStorage = TrainingStorage.getInstance();
    private static final TrainerStorage trainerStorage = TrainerStorage.getInstance();

    public static ManagerService getInstance() {
        if (instance == null) {
            instance = new ManagerService();
        }
        return instance;
    }

    private ManagerService() {

    }

    public SportFacilityDto getManagersFacility(String username) {
        Manager manager = managerStorage.getByUsername(username);
        SportFacility sportFacility = sportFacilityStorage.getById(manager.getSportFacility().getId());
        return makeFacilityDto(sportFacility);
    }

    public boolean makeOffer(MakeOfferDto makeOfferDto, String managerUsername) {
        Manager manager = managerStorage.getByUsername(managerUsername);
        SportFacility sportFacility = sportFacilityStorage.getById(manager.getSportFacility().getId());
        if(checkIfOfferExist(sportFacility, makeOfferDto.getName())) return false;

        Offer offer = new Offer(makeOfferDto.getName(),
                OfferType.valueOf(makeOfferDto.getType()),
                Duration.ofHours(makeOfferDto.getHourDuration()).plus(Duration.ofMinutes(makeOfferDto.getMinuteDuration())),
                makeOfferDto.getDescription(),
                "",
                false);
        offer = offerStorage.add(offer);
        sportFacility.addOffer(offer);
        sportFacilityStorage.update(sportFacility);
        makeTraining(makeOfferDto, offer);
        return true;
    }

    public AllUsersDto getTrainersFromFacility(String username) {
        Manager manager = managerStorage.getByUsername(username);
        SportFacility sportFacility = sportFacilityStorage.getById(manager.getSportFacility().getId());
        return sportFacilityService.getTrainersFromFacility(sportFacility);
    }

    public OffersToShowDto getOffers(String username) {
        Manager manager = managerStorage.getByUsername(username);
        SportFacility sportFacility = sportFacilityStorage.getById(manager.getSportFacility().getId());
        List<ShortOfferDto> shortOfferDtos = new ArrayList<>();
        for(var offer : sportFacility.getOffers()){
            offer = offerStorage.getById(offer.getId());
            shortOfferDtos.add(new ShortOfferDto(offer.getName(), offer.getType().toString()));
        }

        return new OffersToShowDto(shortOfferDtos);
    }

    public AllTrainersToChooseDto getAllTrainers() {
        List<User> trainers = userStorage.getAllTrainers();
        List<TrainerToChooseDto> trainerDtos = new ArrayList<>();
        for(var trainer : trainers) {
            trainerDtos.add(new TrainerToChooseDto(trainer.getId(), trainer.getName(), trainer.getSurname(), trainer.getUsername()));
        }
        return new AllTrainersToChooseDto(trainerDtos);
    }

    private void makeTraining(MakeOfferDto makeOfferDto, Offer offer) {
        if(offer.getType().equals(OfferType.TRAINING)) {
            Trainer trainer = trainerStorage.getById(makeOfferDto.getTrainerId());
            trainingStorage.add(new Training(offer, TrainingType.valueOf(makeOfferDto.getTrainingType()), trainer));
        }
    }

    private boolean checkIfOfferExist(SportFacility sportFacility, String name) {
        for(var offer : sportFacility.getOffers()) {
            offer = offerStorage.getById(offer.getId());
            if(offer.getName().equals(name)){
                return true;
            }
        }
        return false;
    }

    private static SportFacilityDto makeFacilityDto(SportFacility facility){
        LocationDto loc = new LocationDto(facility.getLocation().getGeoLength(), facility.getLocation().getGeoWidth(), facility.getLocation().getAdress().getCity(),
                facility.getLocation().getAdress().getStreet(), facility.getLocation().getAdress().getStrNumber(), facility.getLocation().getAdress().getPostalCode());
        WorkingHoursDto work = new WorkingHoursDto(facility.getOpenTime().getStartWorkingDays(), facility.getOpenTime().getEndWorkingDays(), facility.getOpenTime().getStartSaturday(),
                facility.getOpenTime().getEndSaturday(), facility.getOpenTime().getStartSunday(), facility.getOpenTime().getEndSunday());
        SportFacilityDto fac = new SportFacilityDto(facility.getId(),facility.getName(), facility.getType(), loc, facility.isOpen(), facility.getAverageGrade(), work, facility.getImgSource());
        return fac;
    }
}
