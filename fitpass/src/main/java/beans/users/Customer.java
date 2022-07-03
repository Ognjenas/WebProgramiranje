package beans.users;

import beans.sportfacility.SportFacility;

import java.time.LocalDate;
import java.util.List;

public class Customer extends User{
    private Subscription subscription;
    private List<SportFacility> visitedFacilities;
    private CustomerType type;

    public Customer(String username, String password, String name, String surname, boolean gender, LocalDate birthDate, Role role) {
        super(username, password, name, surname, gender, birthDate, role);
    }

    public Customer(int id, String username, String password, String name, String surname, boolean gender,
                    LocalDate birthDate, Role role, Subscription subscription, List<SportFacility> visitedFacilities, CustomerType type) {
        super(id, username, password, name, surname, gender, birthDate, role);
        this.subscription = subscription;
        this.visitedFacilities = visitedFacilities;
        this.type = type;
    }

    public CustomerType getType() {
        return type;
    }

    public void setType(CustomerType type) {
        this.type = type;
    }

    public Subscription getSubscription() {
        return subscription;
    }

    public void setSubscription(Subscription subscription) {
        this.subscription = subscription;
    }

    public List<SportFacility> getVisitedFacilities() {
        return visitedFacilities;
    }

    public void setVisitedFacilities(List<SportFacility> visitedFacilities) {
        this.visitedFacilities = visitedFacilities;
    }
}
