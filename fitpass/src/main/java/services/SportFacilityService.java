package services;

import beans.sportfacility.Address;
import beans.sportfacility.Location;
import beans.sportfacility.SportFacility;
import beans.users.Manager;
import beans.users.Role;
import beans.users.User;
import dto.sportfacility.*;
import storage.ManagerStorage;
import storage.SportFacilityStorage;
import storage.UserStorage;

import java.util.ArrayList;
import java.util.List;

public class SportFacilityService {
    private static SportFacilityService instance = null;

    public static SportFacilityService getInstance() {
        if (instance == null) {
            instance = new SportFacilityService();
        }
        return instance;
    }


    public SportFacilityService() {

    }

    public static AllFacilitiesDto getAllFacilities() {
        List<SportFacilityDto> list = new ArrayList<>();
        for (SportFacility facility : SportFacilityStorage.getInstance().getAll()) {
            list.add(returnFacilityDto(facility));
        }
        return new AllFacilitiesDto(list);
    }
    
    public static AllFacilitiesDto getSearchedFacilities(String name,String type,String city,String grade) {
        List<SportFacilityDto> list = new ArrayList<>();

        for(SportFacility facility:SportFacilityStorage.getInstance().getAll()){
            if(facility.isSearched(name,type,city,grade)){
                list.add(returnFacilityDto(facility));
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

    private static SportFacilityDto returnFacilityDto(SportFacility facility){
        LocationDto loc = new LocationDto(facility.getLocation().getGeoLength(), facility.getLocation().getGeoWidth(), facility.getLocation().getAdress().getCity(),
                facility.getLocation().getAdress().getStreet(), facility.getLocation().getAdress().getStrNumber(), facility.getLocation().getAdress().getPostalCode());
        WorkingHoursDto work = new WorkingHoursDto(facility.getOpenTime().getStartWorkingDays(), facility.getOpenTime().getEndWorkingDays(), facility.getOpenTime().getStartSaturday(),
                facility.getOpenTime().getEndSaturday(), facility.getOpenTime().getStartSunday(), facility.getOpenTime().getEndSunday());
        SportFacilityDto fac = new SportFacilityDto(facility.getName(), facility.getType(), loc, facility.isOpen(), facility.getAverageGrade(), work, facility.getImgSource());
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
}
