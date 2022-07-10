package services;

import beans.offer.Offer;
import beans.offer.OfferType;
import beans.offer.Training;
import beans.offer.TrainingType;
import beans.sportfacility.Address;
import beans.sportfacility.Comment;
import beans.sportfacility.Location;
import beans.sportfacility.SportFacility;
import beans.users.*;
import dto.offer.*;
import dto.sportfacility.*;
import dto.users.AllUsersDto;
import dto.users.TrainerToChooseDto;
import dto.users.UserDto;
import storage.*;
import storage.offer.OfferStorage;
import storage.offer.TrainingStorage;
import utilities.ComparatorFactory;

import java.time.Duration;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

public class SportFacilityService {
    private static SportFacilityService instance = null;
    private static final SportFacilityStorage sportFacilityStorage = SportFacilityStorage.getInstance();
    private static final OfferStorage offerStorage = OfferStorage.getInstance();
    private static final TrainingStorage trainingStorage = TrainingStorage.getInstance();
    private static final UserStorage userStorage = UserStorage.getInstance();
    private static final TrainerStorage trainerStorage = TrainerStorage.getInstance();
    private static final CustomerStorage customerStorage= CustomerStorage.getInstance();
    private static final CommentStorage commentStorage = CommentStorage.getInstance();


    public static SportFacilityService getInstance() {
        if (instance == null) {
            instance = new SportFacilityService();
        }
        return instance;
    }


    public SportFacilityService() {

    }

    private static List<SportFacility> getAllFacilities(){
        return SportFacilityStorage.getInstance().getAll();
    }

    public static AllFacilitiesDto getAllFacilitiesDto() {
        List<SportFacilityDto> list = new ArrayList<>();
        for (SportFacility facility : getAllFacilities()) {
            list.add(makeFacilityDto(facility));
        }
        return new AllFacilitiesDto(list);
    }
    
    public static AllFacilitiesDto getSearchedFacilities(String name,String type,String city,String grade) {
        List<SportFacilityDto> list = new ArrayList<>();

        for(SportFacility facility:getAllFacilities()){
            if(facility.isSearched(name,type,city,grade)){
                list.add(makeFacilityDto(facility));
            }
        }
        return  new AllFacilitiesDto(list);
    }

    public boolean createFacility(CreateSportFacilityDto facilityDto) {
        Address adr=new Address(facilityDto.getCity(),facilityDto.getStreet(),facilityDto.getStrNum(),facilityDto.getPostCode());
        Location loc= new Location(facilityDto.getGeoLength(),facilityDto.getGeoWidth(),adr);
        SportFacility facility=new SportFacility(0,facilityDto.getName(),facilityDto.getType(),loc);
        facility.setImgSource(facilityDto.getImgSource());
        Manager manager= ManagerStorage.getInstance().getById(facilityDto.getManagerId());
        manager.setSportFacility(SportFacilityStorage.getInstance().create(facility));
        ManagerStorage.getInstance().update(manager);

        return true;
    }

    private static SportFacilityDto makeFacilityDto(SportFacility facility){
        LocationDto loc = new LocationDto(facility.getLocation().getGeoLength(), facility.getLocation().getGeoWidth(), facility.getLocation().getAdress().getCity(),
                facility.getLocation().getAdress().getStreet(), facility.getLocation().getAdress().getStrNumber(), facility.getLocation().getAdress().getPostalCode());
        WorkingHoursDto work = new WorkingHoursDto(facility.getOpenTime().getStartWorkingDays(), facility.getOpenTime().getEndWorkingDays(), facility.getOpenTime().getStartSaturday(),
                facility.getOpenTime().getEndSaturday(), facility.getOpenTime().getStartSunday(), facility.getOpenTime().getEndSunday());
        SportFacilityDto fac = new SportFacilityDto(facility.getId(),facility.getName(), facility.getType(), loc, facility.isOpen(), facility.getAverageGrade(), work, facility.getImgSource());
        return fac;
    }

    public AllFreeManagersDto getFreeManagers() {
        ManagerStorage storage=ManagerStorage.getInstance();
        List<Manager> allManagers=storage.getAll();
        List<FreeManagerDto> freeManagers = new ArrayList<>();

        for (Manager man:allManagers) {
            if(man.getSportFacility()==null){
                freeManagers.add(new FreeManagerDto(man.getId(),man.getName(),man.getSurname()));
            }
        }
        if(freeManagers.isEmpty()) freeManagers=null;

        return new AllFreeManagersDto(freeManagers);
    }

    public boolean createFacilityWithManager(CreateFacilityWithManagerDto dto) {
        if(UserStorage.getInstance().getUserByUsername(dto.getManagerUsername()) != null) {
            return false;
        }

        User user = UserStorage.getInstance().addUser(new User(dto.getManagerUsername(),
                dto.getManagerPassword(),
                dto.getManagerName(),
                dto.getManagerSurname(),
                dto.isManagerGender(),
                dto.getManagerBirthDate(),
                Role.MANAGER));
        ManagerStorage.getInstance().add(new Manager(user, null));

        return createFacility(new CreateSportFacilityDto(dto.getName(),dto.getType(),dto.getCity(),dto.getStreet(),dto.getStrNum(),dto.getPostCode(),dto.getGeoWidth(),dto.getGeoLength(),"",user.getId()));
    }

    public SportFacilityDto getFacilityToShow(int facilityId){
        for (SportFacility facility:getAllFacilities()) {
            if(facility.getId()==facilityId) return makeFacilityDto(facility);
        }
        return null;
    }

    //SORTIRANJE!
    public AllFacilitiesDto sortAndSearchFacilities(String name,String type,String city,String grade,String colIndex,String sortDir){
        AllFacilitiesDto searched=getSearchedFacilities(name,type,city,grade);
        List<SportFacilityDto> searchedFacilities=searched.getAllFacilities();

        if(Integer.parseInt(colIndex)==0){
            Collections.sort(searchedFacilities,new ComparatorFactory.FacilityCompareName());
        } else if (Integer.parseInt(colIndex)==1) {
            Collections.sort(searchedFacilities,new ComparatorFactory.FacilityCompareLocation());
        } else if (Integer.parseInt(colIndex)==2) {
            Collections.sort(searchedFacilities,new ComparatorFactory.FacilityCompareGrade());
        }

        if(sortDir.equals("desc")) Collections.reverse(searchedFacilities);

        return new AllFacilitiesDto(searchedFacilities);
    }

    public AllUsersDto getTrainersFromFacility(SportFacility sportFacility) {
        List<UserDto> trainers = new ArrayList<>();
        for (var offer : sportFacility.getOffers()) {
            offer = offerStorage.getById(offer.getId());
            if(offer.getType().equals(OfferType.TRAINING)) {
                Training training = trainingStorage.getById(offer.getId());
                User trainer = userStorage.getById(training.getBelongingTrainer().getId());
                addUserToDtoList(trainer, trainers);
            }
        }
        return new AllUsersDto(trainers);
    }

    public OfferDto getOfferById(int id) {
        Offer offer = offerStorage.getById(id);
        OfferDto offerDto = new OfferDto(id, offer.getName(),
                offer.getType().toString(),
                offer.getDescription(),
                ((int) offer.getDuration().toHours()),
                offer.getDuration().toMinutesPart(),
                ((int) offer.getPrice()), offer.getImageLocation());
        if(offer.getType().equals(OfferType.TRAINING)) {
            Training training = trainingStorage.getById(offer.getId());
            User trainer = userStorage.getById(training.getBelongingTrainer().getId());
            offerDto.setTrainer(new TrainerToChooseDto(trainer.getId(), trainer.getName(), trainer.getSurname(), trainer.getUsername()));
            offerDto.setTrainingType(training.getTrainingType().toString());
            offerDto.setTrainerId(trainer.getId());
        }
        return offerDto;
    }

    public boolean updateOffer(OfferDto offerDto, SportFacility sportFacility) {
        Offer offer = offerStorage.getById(offerDto.getId());
        for(var iterOffer : sportFacility.getOffers()) {
            iterOffer = offerStorage.getById(iterOffer.getId());
            if(iterOffer.getName().equals(offerDto.getName()) && iterOffer.getId() != offerDto.getId()) {
                return false;
            }
        }
        offer.setName(offerDto.getName());
        offer.setDescription(offerDto.getDescription());
        offer.setPrice(offerDto.getPrice());
        offer.setDuration(Duration.ofHours(offerDto.getHourDuration()).plusMinutes(offerDto.getMinuteDuration()));
        offer.setImageLocation(offerDto.getImgSource());

        if(offer.getType().equals(OfferType.TRAINING) && !OfferType.valueOf(offerDto.getType()).equals(OfferType.TRAINING)) {
            trainingStorage.deleteById(offer.getId());
        }

        if(OfferType.valueOf(offerDto.getType()).equals(OfferType.TRAINING)) {
            Trainer trainer = trainerStorage.getById(offerDto.getTrainer().getId());
            if(offer.getType().equals(OfferType.TRAINING)) {
                Training training = trainingStorage.getById(offer.getId());
                training.setTrainingType(TrainingType.valueOf(offerDto.getTrainingType()));
                training.setBelongingTrainer(trainer);
                trainingStorage.update(training);
            } else {
                Training training = new Training(offer, TrainingType.valueOf(offerDto.getTrainingType()), trainer);
                trainingStorage.add(training);
            }
        }
        offer.setType(OfferType.valueOf(offerDto.getType()));
        offerStorage.update(offer);
        return true;
    }

    public OffersToChooseDto getOffersByFacilityId(int facilityId) {
        SportFacility sportFacility = sportFacilityStorage.getById(facilityId);
        List<ChooseOfferDto> chooseOfferDtos = new ArrayList<>();
        for(var offer : sportFacility.getOffers()) {
            offer = offerStorage.getById(offer.getId());
            chooseOfferDtos.add(new ChooseOfferDto(offer.getId(), offer.getName(), offer.getType().toString(), offer.getDuration().toString(), offer.getImageLocation()));
        }
        return new OffersToChooseDto(chooseOfferDtos);
    }

    public ChooseOfferDto getOffer(int offerId) {
        Offer offer = offerStorage.getById(offerId);
        return new ChooseOfferDto(offer.getId(), offer.getName(), offer.getType().toString(), offer.getDuration().toString(), offer.getImageLocation());
    }

    private void addUserToDtoList(User user, List<UserDto> users) {
        if(users.stream().noneMatch(userDto -> userDto.getUsername().equals(user.getUsername()))) {
            users.add(makeUserDto(user));
        }
    }

    private UserDto makeUserDto(User user) {
        String birth = user.getBirthDate().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        String gender = user.isGender() ? "MUSKO" : "ZENSKO";
        return new UserDto(user.getName(), user.getSurname(), user.getUsername(), user.getRole().toString(), gender, birth);
    }

    public boolean canComment(String username, String id) {
        Customer customer= customerStorage.getCustomerByUsername(username);
        int facID=Integer.parseInt(id);
        for(SportFacility facility:customer.getVisitedFacilities()){
            if(facility.getId()==facID) return true;
        }
        return false;
    }

    public AllConfirmedShowCommentDto getConfirmedCommentsForFacility(String id) {
        int facId=Integer.parseInt(id);
        List<Comment> confirmedComments=commentStorage.getAllConfirmed(facId);
        List<ShowCommentDto> showComments=new ArrayList<>();
        for(Comment comment:confirmedComments){
            Customer customer=customerStorage.getById(comment.getCustomer().getId());
            showComments.add(new ShowCommentDto(comment.getId(),customer.getUsername(),comment.getText(),comment.getGrade()));
        }
        if(confirmedComments.isEmpty()) return null;
        return new AllConfirmedShowCommentDto(showComments);
    }
}
