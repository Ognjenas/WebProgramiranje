package services;

import beans.offer.*;
import beans.sportfacility.SportFacility;
import beans.users.Customer;
import beans.users.Subscription;
import beans.users.Trainer;
import dto.offer.AvailableTimesDto;
import dto.offer.ReserveOfferDto;
import dto.offerhistory.AllOrdersToShowDto;
import dto.offerhistory.OrderToShowDto;
import storage.CustomerStorage;
import storage.SportFacilityStorage;
import storage.SubscriptionStorage;
import storage.TrainerStorage;
import storage.offer.OfferHistoryStorage;
import storage.offer.OfferStorage;
import storage.offer.TrainingStorage;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class CustomerService {

    private static CustomerService instance = null;
    private final SubscriptionStorage subscriptionStorage = SubscriptionStorage.getInstance();
    private final CustomerStorage customerStorage = CustomerStorage.getInstance();
    private final OfferStorage offerStorage = OfferStorage.getInstance();
    private final TrainingStorage trainingStorage = TrainingStorage.getInstance();
    private final TrainerStorage trainerStorage = TrainerStorage.getInstance();
    private final OfferHistoryStorage offerHistoryStorage = OfferHistoryStorage.getInstance();
    private final SportFacilityStorage sportFacilityStorage = SportFacilityStorage.getInstance();

    public static CustomerService getInstance() {
        if (instance == null) {
            instance = new CustomerService();
        }
        return instance;
    }

    private CustomerService() {

    }

    public boolean reserveOffer(ReserveOfferDto reserveOfferDto, String username) {
        Customer customer = customerStorage.getCustomerByUsername(username);
        Subscription subscription = subscriptionStorage.getActiveByCustomerId(customer.getId());

        if (subscription == null) {
            return false;
        }

        if (checkIfCustomerCanOrderAppointment(reserveOfferDto.getDate(), customer, subscription)) {
            Offer offer = offerStorage.getById(reserveOfferDto.getOfferId());
            OfferHistory offerHistory = new OfferHistory();
            offerHistory.setOffer(offer);
            offerHistory.setCustomer(customer);
            offerHistory.setCheckIn(LocalDateTime.of(reserveOfferDto.getDate(), LocalTime.parse(reserveOfferDto.getTime())));
            offerHistory.setSportFacility(new SportFacility(offer.getSportFacility().getId()));
            offerHistory.setDeleted(false);
            Trainer trainer;
            if(!offer.getType().equals(OfferType.TRAINING)) {
                trainer = new Trainer();
                trainer.setId(-1);
                offerHistory.setTrainer(trainer);
                offerHistoryStorage.add(offerHistory);
            } else {
                Training training = trainingStorage.getById(offer.getId());
                trainer = trainerStorage.getById(training.getBelongingTrainer().getId());
                offerHistory.setTrainer(trainer);
                offerHistory = offerHistoryStorage.add(offerHistory);
                trainer.addOfferHistory(offerHistory);
                trainerStorage.editTrainer(trainer);
            }
            subscription.setOrderedAppointments(subscription.getOrderedAppointments()+1);
            subscriptionStorage.edit(subscription);
            return true;
        }
        return false;
    }

    private boolean checkIfCustomerCanOrderAppointment(LocalDate date, Customer customer, Subscription subscription) {
        List<OfferHistory> histories = offerHistoryStorage.getByDateAndCustomerId(date, customer.getId());
        return histories.size() <= subscription.getDailyEnteringNumber();
    }

    public AvailableTimesDto getAvailableTimesForOfferAndDate(int offerId, LocalDate date, String username) {
        Offer offer = offerStorage.getById(offerId);
        List<String> times = new ArrayList<>();
        List<OfferHistory> offerHistories = offerHistoryStorage.getByDateAndOfferId(date, offerId);
        Customer customer = customerStorage.getCustomerByUsername(username);
        offerHistories.addAll(offerHistoryStorage.getByDateAndCustomerId(date, customer.getId()));
        if (offer.getType().equals(OfferType.TRAINING)) {
            Training training = trainingStorage.getById(offerId);
            if (!training.getTrainingType().equals(TrainingType.GROUP)) {
                offerHistories.addAll(offerHistoryStorage.getByDateAndTrainerId(date, training.getBelongingTrainer().getId()));
            }
        }

        for(LocalTime iter = LocalTime.parse("07:00:00"); iter.isBefore(LocalTime.parse("21:00:00"));iter = iter.plus(Duration.ofMinutes(30))) {
            if(checkIfAvailableTime(iter, offerHistories, offer.getDuration())) {
                times.add(iter.format(DateTimeFormatter.ISO_LOCAL_TIME));
            }
        }
        return new AvailableTimesDto(times);
    }

    public AllOrdersToShowDto getTrainings(String username) {
        Customer customer = customerStorage.getCustomerByUsername(username);
        List<OfferHistory> offerHistories = offerHistoryStorage.getByCustomerId(customer.getId());
        List<OrderToShowDto> orders = new ArrayList<>();
        for (var order : offerHistories) {
            Offer offer = offerStorage.getById(order.getOffer().getId());
            SportFacility sportFacility = sportFacilityStorage.getById(offer.getSportFacility().getId());
            orders.add(new OrderToShowDto(order.getId(), offer.getName(), sportFacility.getName(), offer.getType().toString(),
                    order.getCheckIn().toString()));
        }
        return new AllOrdersToShowDto(orders);
    }

    private boolean checkIfAvailableTime(LocalTime time, List<OfferHistory> offerHistories, Duration duration) {
        for(var offerHistory : offerHistories) {
            if((offerHistory.getCheckIn().toLocalTime().isAfter(time) && offerHistory.getCheckIn().toLocalTime().isBefore(time.plus(duration))) ||
                    (time.compareTo(offerHistory.getCheckIn().toLocalTime()) >= 0 && time.compareTo(offerHistory.getCheckIn().toLocalTime().plus(duration)) < 0)){
                return false;
            }
        }
        return true;
    }
}
