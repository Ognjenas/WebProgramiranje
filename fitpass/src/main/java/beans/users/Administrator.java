package beans.users;

import beans.SportFacility;

import java.time.LocalDate;

public class Administrator extends User{
    private SportFacility sportFacility;

    public Administrator(int id ,String username, String password, String name, String surname, boolean sex, LocalDate birthDate,
                         Role role, SportFacility sportFacility) {
        super(id, username, password, name, surname, sex, birthDate, role);
        this.sportFacility = sportFacility;
    }

    public Administrator(SportFacility sportFacility) {
        this.sportFacility = sportFacility;
    }

    public SportFacility getSportFacility() {
        return sportFacility;
    }

    public void setSportFacility(SportFacility sportFacility) {
        this.sportFacility = sportFacility;
    }
}
