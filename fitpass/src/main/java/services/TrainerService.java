package services;

import beans.offer.Offer;
import beans.offer.OfferHistory;
import beans.offer.Training;
import beans.sportfacility.SportFacility;
import beans.users.Customer;
import beans.users.Subscription;
import beans.users.Trainer;
import dto.offerhistory.AllOrdersToShowDto;
import dto.offerhistory.OrderToShowDto;
import storage.*;
import storage.offer.OfferHistoryStorage;
import storage.offer.OfferStorage;
import storage.offer.TrainingStorage;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class TrainerService {
    private static TrainerService instance = null;
    private static final SportFacilityStorage sportFacilityStorage = SportFacilityStorage.getInstance();
    private static final OfferStorage offerStorage = OfferStorage.getInstance();
    private static final TrainingStorage trainingStorage = TrainingStorage.getInstance();
    private static final UserStorage userStorage = UserStorage.getInstance();
    private static final TrainerStorage trainerStorage = TrainerStorage.getInstance();
    private static final OfferHistoryStorage offerHistoryStorage = OfferHistoryStorage.getInstance();
    private static final CustomerStorage customerStorage = CustomerStorage.getInstance();
    private static final SubscriptionStorage subscriptionStorage = SubscriptionStorage.getInstance();

    public static TrainerService getInstance() {
        if (instance == null) {
            instance = new TrainerService();
        }
        return instance;
    }


    public TrainerService() {

    }

    public AllOrdersToShowDto getTrainingsForTrainer(String username) {
        Trainer trainer = trainerStorage.getByUsername(username);
        List<OrderToShowDto> orders = new ArrayList<>();
        for (var order : offerHistoryStorage.getByTrainerId(trainer.getId())) {
            Offer offer = offerStorage.getById(order.getOffer().getId());
            SportFacility sportFacility = sportFacilityStorage.getById(offer.getSportFacility().getId());
            Training training = trainingStorage.getById(offer.getId());
            orders.add(new OrderToShowDto(order.getId(), offer.getName(), sportFacility.getName(), training.getTrainingType().toString(),
                    order.getCheckIn().toString()));
        }
        return new AllOrdersToShowDto(orders);
    }

    public boolean cancelTraining(int trainingId, String username) {
        OfferHistory offerHistory = offerHistoryStorage.getById(trainingId);
        if (offerHistory.getCheckIn().minusDays(2).compareTo(LocalDateTime.now()) < 0) {
            return false;
        }
        offerHistory.setDeleted(true);
        offerHistoryStorage.update(offerHistory);
        deleteOfferFromTrainer(username, offerHistory);
        setOrderedAppointmentsInSubscription(offerHistory.getCustomer().getId());
        return true;
    }

    private boolean deleteOfferFromTrainer(String username, OfferHistory offerHistory) {
        Trainer trainer = trainerStorage.getByUsername(username);
        boolean flag = trainer.removeOfferHistory(offerHistory);
        trainerStorage.editTrainer(trainer);
        return flag;
    }

    private void setOrderedAppointmentsInSubscription(int customerId) {
        Subscription subscription = subscriptionStorage.getActiveByCustomerId(customerId);
        subscription.setOrderedAppointments(subscription.getOrderedAppointments()-1);
        subscriptionStorage.edit(subscription);
    }
}
