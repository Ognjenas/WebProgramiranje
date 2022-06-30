package services;

import beans.Address;
import beans.Location;
import beans.SportFacility;
import beans.SportFacilityType;
import dto.sportfacility.*;
import storage.SportFacilityStorage;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class SportFacilityService {
    private static List<SportFacility> AllFacilities = new ArrayList<SportFacility>(); //MORAO SAM OVO SVE U STATIC?

    public SportFacilityService() {

    }

    public static AllFacilitiesDto getAllFacilities() {  //MORAO SAM OVO SVE U STATIC?
        List<SportFacilityDto> list = new ArrayList<>();
        for (SportFacility facility : SportFacilityStorage.getInstance().getAll()) {
            list.add(makeFacilityDto(facility));
        }
        return new AllFacilitiesDto(list);
    }
    
    public static AllFacilitiesDto getSearchedFacilities(String name,String type,String city,String grade) {
        List<SportFacilityDto> list = new ArrayList<>();

        for(SportFacility facility:SportFacilityStorage.getInstance().getAll()){
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

        SportFacilityStorage storage=SportFacilityStorage.getInstance();
        return storage.create(facility);
    }

    private static SportFacilityDto makeFacilityDto(SportFacility facility){
        LocationDto loc = new LocationDto(facility.getLocation().getGeoLength(), facility.getLocation().getGeoWidth(), facility.getLocation().getAdress().getCity(),
                facility.getLocation().getAdress().getStreet(), facility.getLocation().getAdress().getStrNumber(), facility.getLocation().getAdress().getPostalCode());
        WorkingHoursDto work = new WorkingHoursDto(facility.getOpenTime().getStartWorkingDays(), facility.getOpenTime().getEndWorkingDays(), facility.getOpenTime().getStartSaturday(),
                facility.getOpenTime().getEndSaturday(), facility.getOpenTime().getStartSunday(), facility.getOpenTime().getEndSunday());
        SportFacilityDto fac = new SportFacilityDto(facility.getName(), facility.getType(), loc, facility.isOpen(), facility.getAverageGrade(), work, facility.getImgSource());
        return fac;
    }


}
