package beans.users;

import beans.offer.OfferHistory;

import java.util.List;


public class Trainer extends User{

    private List<OfferHistory> offerHistories;

    public Trainer() {
    }

    public Trainer(User user, List<OfferHistory> offerHistories) {
        super(user.getId(), user.getUsername(), user.getPassword(), user.getName(), user.getSurname(), user.isGender(), user.getBirthDate(), user.getRole());
        this.offerHistories = offerHistories;
    }

    public List<OfferHistory> getTrainingHistory() {
        return offerHistories;
    }

    public void setTrainingHistory(List<OfferHistory> offerHistory) {
        this.offerHistories = offerHistory;
    }

    public void addOfferHistory(OfferHistory offerHistory) {
        this.offerHistories.add(offerHistory);
    }

    @Override
    public String toString() {
        return "Trainer{" +
                "offerHistories=" + offerHistories +
                '}';
    }
}
