package services;

import beans.offer.*;
import beans.sportfacility.SportFacility;
import beans.users.Manager;
import beans.users.Trainer;
import beans.users.User;
import dto.offer.MakeOfferDto;
import dto.offer.OfferDto;
import dto.offer.OffersToShowDto;
import dto.offer.ShortOfferDto;
import dto.offerhistory.AllOrdersToShowDto;
import dto.offerhistory.OrderToShowDto;
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
import storage.offer.OfferHistoryStorage;
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
    private static final OfferHistoryStorage offerHistoryStorage = OfferHistoryStorage.getInstance();

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
        for(var offer : sportFacility.getOffers()) {
            offer = offerStorage.getById(offer.getId());
            if(offer.getName().equals(makeOfferDto.getName())){
                return false;
            }
        }

        Offer offer = new Offer(makeOfferDto.getName(),
                OfferType.valueOf(makeOfferDto.getType()),
                Duration.ofHours(makeOfferDto.getHourDuration()).plus(Duration.ofMinutes(makeOfferDto.getMinuteDuration())),
                makeOfferDto.getDescription(),
                "",
                makeOfferDto.getPrice(),
                false, sportFacility);
        offer = offerStorage.add(offer);
        sportFacility.addOffer(offer);
        sportFacilityStorage.update(sportFacility);
        Trainer trainer = trainerStorage.getById(makeOfferDto.getTrainerId());
        if(offer.getType().equals(OfferType.TRAINING)) {
            trainingStorage.add(new Training(offer, TrainingType.valueOf(makeOfferDto.getTrainingType()), trainer));
        }
        return true;
    }

    public OffersToShowDto getOffers(String username) {
        Manager manager = managerStorage.getByUsername(username);
        SportFacility sportFacility = sportFacilityStorage.getById(manager.getSportFacility().getId());
        List<ShortOfferDto> shortOfferDtos = new ArrayList<>();
        for(var offer : sportFacility.getOffers()){
            offer = offerStorage.getById(offer.getId());
            shortOfferDtos.add(new ShortOfferDto(offer.getId(), offer.getName(), offer.getType().toString()));
        }

        return new OffersToShowDto(shortOfferDtos);
    }

    public OfferDto getOffer(int id, String username) {
        Manager manager = managerStorage.getByUsername(username);
        SportFacility sportFacility = sportFacilityStorage.getById(manager.getId());
        if(sportFacility.getOffers().stream().noneMatch(offer -> offer.getId() == id)) {
            return null;
        }
        return sportFacilityService.getOfferById(id);
    }

    public boolean editOffer(OfferDto offerDto, String username) {
        Manager manager = managerStorage.getByUsername(username);
        SportFacility sportFacility = sportFacilityStorage.getById(manager.getSportFacility().getId());
        return sportFacilityService.updateOffer(offerDto, sportFacility);
    }

    public AllTrainersToChooseDto getAllTrainers() {
        List<User> trainers = userStorage.getAllTrainers();
        List<TrainerToChooseDto> trainerDtos = new ArrayList<>();
        for(var trainer : trainers) {
            trainerDtos.add(new TrainerToChooseDto(trainer.getId(), trainer.getName(), trainer.getSurname(), trainer.getUsername()));
        }
        return new AllTrainersToChooseDto(trainerDtos);
    }

    public AllUsersDto getTrainersFromFacility(String username) {
        Manager manager = managerStorage.getByUsername(username);
        SportFacility sportFacility = sportFacilityStorage.getById(manager.getSportFacility().getId());
        return sportFacilityService.getTrainersFromFacility(sportFacility);
    }

    public AllOrdersToShowDto getTrainingsFromFacility(String username) {
        Manager manager = managerStorage.getByUsername(username);
        SportFacility sportFacility = sportFacilityStorage.getById(manager.getSportFacility().getId());
        List<OrderToShowDto> orders = new ArrayList<>();
        for(var offerHistory : offerHistoryStorage.getNotDeleted()) {
            Offer offer = offerStorage.getById(offerHistory.getOffer().getId());
            if(offer.getSportFacility().getId() == sportFacility.getId()) {
                orders.add(new OrderToShowDto(offerHistory.getId(), offer.getName(), sportFacility.getName(),
                        offer.getType().toString(), offerHistory.getCheckIn().toString()));
            }
        }
        return new AllOrdersToShowDto(orders);
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
