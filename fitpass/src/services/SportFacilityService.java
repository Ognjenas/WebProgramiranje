package services;

import beans.Address;
import beans.Location;
import beans.SportFacility;
import beans.SportFacilityType;
import dto.AllFacilitiesDto;
import dto.LocationDto;
import dto.SportFacilityDto;
import dto.sportfacility.WorkingHoursDto;
import utilities.WorkingHours;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class SportFacilityService {
    private static List<SportFacility> AllFacilities = new ArrayList<SportFacility>(); //MORAO SAM OVO SVE U STATIC?

    public SportFacilityService() {
        List<String> Offer1 = new ArrayList<String>();
        List<String> Offer2 = new ArrayList<String>();
        List<String> Offer3 = new ArrayList<String>();
        Offer1.add("Sauna");
        Offer1.add("Teretana");
        Offer2.add("Disco");
        Offer2.add("Cardio");
        Offer2.add("Dancing");
        Offer3.add("Shower");
        Offer3.add("Swimming Pools");
        Location loc1 = new Location(12.2, 13.2, new Address("Novi Sad", "Danila Kisa", 2, 21000));
        Location loc2 = new Location(12.2, 13.2, new Address("Apatin", "Srpskih Vladara", 52, 25260));
        Location loc3 = new Location(12.2, 13.2, new Address("Vojska", "Ustanicka", 1804, 1389));
        WorkingHours work1 = new WorkingHours(LocalTime.of(8, 0), LocalTime.of(20, 0), LocalTime.of(8, 0), LocalTime.of(14, 0), LocalTime.of(0, 0), LocalTime.of(0, 0));
        WorkingHours work2 = new WorkingHours(LocalTime.of(8, 0), LocalTime.of(21, 0), LocalTime.of(8, 0), LocalTime.of(15, 0), LocalTime.of(0, 0), LocalTime.of(0, 0));
        WorkingHours work3 = new WorkingHours(LocalTime.of(8, 0), LocalTime.of(22, 0), LocalTime.of(8, 0), LocalTime.of(16, 0), LocalTime.of(0, 0), LocalTime.of(0, 0));


        //SportFacility(string name, SportFacilityType type, List<string> offers, boolean isOpen, beans.Location location, float avrageGrade, LocalDateTime openTime)
        AllFacilities.add(new SportFacility("Prva", SportFacilityType.GYM, Offer1, true, loc1, 4.5, work1));
        AllFacilities.add(new SportFacility("Druga", SportFacilityType.DANCE_STUDIO, Offer2, true, loc2, 3.9, work2));
        AllFacilities.add(new SportFacility("Treca", SportFacilityType.SWIMMING_POOL, Offer3, true, loc3, 4.9, work3));
    }

    public static AllFacilitiesDto getAllFacilities() {  //MORAO SAM OVO SVE U STATIC?
        List<SportFacilityDto> list = new ArrayList<>();
        for (SportFacility facility : AllFacilities) {
            LocationDto loc = new LocationDto(facility.getLocation().getGeoLength(), facility.getLocation().getGeoWidth(), facility.getLocation().getAdress().getCity(),
                    facility.getLocation().getAdress().getStreet(), facility.getLocation().getAdress().getStrNumber(), facility.getLocation().getAdress().getPostalCode());
            WorkingHoursDto work = new WorkingHoursDto(facility.getOpenTime().getStartWorkingDays(), facility.getOpenTime().getEndWorkingDays(), facility.getOpenTime().getStartSaturday(),
                    facility.getOpenTime().getEndSaturday(), facility.getOpenTime().getStartSunday(), facility.getOpenTime().getEndSunday());
            SportFacilityDto fac = new SportFacilityDto(facility.getName(), facility.getType(), loc, facility.isOpen(), facility.getAverageGrade(), work);
            list.add(fac);
        }
        return new AllFacilitiesDto(list);
    }


}
