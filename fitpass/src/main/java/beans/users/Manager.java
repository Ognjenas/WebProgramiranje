package beans.users;

import beans.SportFacility;

import java.util.List;

public class Manager extends User{
    private List<SportFacility> sportFacilities;

    public Manager() {
    }

    public Manager(User user, List<SportFacility> sportFacilities) {
        super(user.getId(), user.getUsername(), user.getPassword(), user.getName(), user.getSurname(), user.isGender(), user.getBirthDate(), user.getRole());
        this.sportFacilities = sportFacilities;
    }

    public List<SportFacility> getSportFacilities() {
        return sportFacilities;
    }

    public void setSportFacilities(List<SportFacility> sportFacilities) {
        this.sportFacilities = sportFacilities;
    }
}
