package beans.users;

import beans.sportfacility.SportFacility;

public class Manager extends User{
    private SportFacility sportFacility;

    public Manager() {
    }

    public Manager(User user, SportFacility sportFacility) {
        super(user.getId(), user.getUsername(), user.getPassword(), user.getName(), user.getSurname(), user.isGender(), user.getBirthDate(), user.getRole());
        this.sportFacility = sportFacility;
    }

    public SportFacility getSportFacility() {
        return sportFacility;
    }

    public void setSportFacility(SportFacility sportFacility) {
        this.sportFacility = sportFacility;
    }
}
