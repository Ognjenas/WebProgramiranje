package services;

import beans.offer.OfferType;
import beans.offer.Training;
import beans.sportfacility.Address;
import beans.sportfacility.Location;
import beans.sportfacility.SportFacility;
import beans.users.Manager;
import beans.users.Role;
import beans.users.User;
import dto.offer.OffersToShowDto;
import dto.offer.ShortOfferDto;
import dto.sportfacility.*;
import dto.users.AllUsersDto;
import dto.users.UserDto;
import storage.ManagerStorage;
import storage.SportFacilityStorage;
import storage.UserStorage;
import storage.offer.OfferStorage;
import storage.offer.TrainingStorage;
import utilities.ComparatorFactory;

import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

public class SportFacilityService {
    private static SportFacilityService instance = null;
    private static final SportFacilityStorage sportFacilityStorage = SportFacilityStorage.getInstance();
    private static final OfferStorage offerStorage = OfferStorage.getInstance();
    private static final TrainingStorage trainingStorage = TrainingStorage.getInstance();
    private static final UserStorage userStorage = UserStorage.getInstance();

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
}
