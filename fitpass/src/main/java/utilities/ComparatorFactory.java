package utilities;

import beans.sportfacility.SportFacility;
import dto.sportfacility.SportFacilityDto;
import dto.users.UserDto;

import java.util.Comparator;

public class ComparatorFactory{

   public static class FacilityCompareName implements Comparator<SportFacilityDto>{
       @Override
       public int compare(SportFacilityDto o1, SportFacilityDto o2) {
           return o1.getName().toLowerCase().compareTo(o2.getName().toLowerCase());
       }
   }

    public static class FacilityCompareLocation implements Comparator<SportFacilityDto>{
        @Override
        public int compare(SportFacilityDto o1, SportFacilityDto o2) {
            return o1.getLocation().getCity().toLowerCase().compareTo(o2.getLocation().getCity().toLowerCase());
        }
    }

    public static class FacilityCompareGrade implements Comparator<SportFacilityDto>{
        @Override
        public int compare(SportFacilityDto o1, SportFacilityDto o2) {
            return Double.compare(o1.getAverageGrade(),o2.getAverageGrade());
        }
    }


    public static class UserCompareName implements Comparator<UserDto>{
        @Override
        public int compare(UserDto o1, UserDto o2) {
            return o1.getName().toLowerCase().compareTo(o2.getName().toLowerCase());
        }
    }

    public static class UserCompareSurname implements Comparator<UserDto>{
        @Override
        public int compare(UserDto o1, UserDto o2) {
            return o1.getSurname().toLowerCase().compareTo(o2.getSurname().toLowerCase());
        }
    }

    public static class UserCompareUsername implements Comparator<UserDto>{
        @Override
        public int compare(UserDto o1, UserDto o2) {
            return o1.getUsername().toLowerCase().compareTo(o2.getUsername().toLowerCase());
        }
    }

}
