package beans.users;

import beans.sportfacility.SportFacility;

import java.time.LocalDate;
import java.util.List;

public class Customer extends User{
    private Subscription subscription;
    private List<SportFacility> visitedFacilities;
    private CustomerType type;

    public Customer(User user,Subscription subscription, List<SportFacility> visitedFacilities, CustomerType type) {
        super(user.getId(), user.getUsername(), user.getPassword(), user.getName(), user.getSurname(), user.isGender(), user.getBirthDate(), user.getRole());
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
