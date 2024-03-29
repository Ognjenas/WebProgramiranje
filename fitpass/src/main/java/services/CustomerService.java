package services;

import beans.offer.*;
import beans.sportfacility.Comment;
import beans.sportfacility.CommentStatus;
import beans.sportfacility.SportFacility;
import beans.users.Customer;
import beans.users.CustomerType;
import beans.users.Subscription;
import beans.users.Trainer;
import dto.offer.AvailableTimesDto;
import dto.offer.ReserveOfferDto;
import dto.offerhistory.AllOrdersToShowDto;
import dto.offerhistory.OrderToShowDto;
import dto.sportfacility.CreateCommentDto;
import storage.*;
import storage.offer.OfferHistoryStorage;
import storage.offer.OfferStorage;
import storage.offer.TrainingStorage;
import utilities.ComparatorFactory;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
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
    private final CommentStorage commentStorage = CommentStorage.getInstance();
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
            addVisitedFacilityToCustomer(customer,new SportFacility(offer.getSportFacility().getId()));
            subscription.setOrderedAppointments(subscription.getOrderedAppointments()+1);
            subscriptionStorage.edit(subscription);
            return true;
        }
        return false;
    }

    private void addVisitedFacilityToCustomer(Customer customer,SportFacility sportFacility) {
        List<SportFacility> visitedFacilities=customer.getVisitedFacilities();
        if(visitedFacilities!=null) {
            for (SportFacility visited : visitedFacilities) {
                if (visited.getId() == sportFacility.getId()) return;
            }
            visitedFacilities.add(sportFacility);
            customer.setVisitedFacilities(visitedFacilities);
            customerStorage.editCustomer(customer);
            return;
        }
        List<SportFacility> newList=new ArrayList<>();
        newList.add(sportFacility);
        customer.setVisitedFacilities(newList);
        customerStorage.editCustomer(customer);
    }

    private boolean checkIfCustomerCanOrderAppointment(LocalDate date, Customer customer, Subscription subscription) {
        List<OfferHistory> histories = offerHistoryStorage.getByDateAndCustomerId(date, customer.getId());
        return histories.size() <= subscription.getDailyEnteringNumber();
    }

    public AvailableTimesDto getAvailableTimesForOfferAndDate(int offerId, LocalDate date, String username) {
        Offer offer = offerStorage.getById(offerId);
        SportFacility facility= sportFacilityStorage.getById(offer.getSportFacility().getId());
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
        LocalTime iter;
        LocalTime to;
        if(date.getDayOfWeek()== DayOfWeek.SATURDAY){
            iter=facility.getOpenTime().getStartSaturday();
            to=facility.getOpenTime().getEndSaturday();
        }else if(date.getDayOfWeek()==DayOfWeek.SUNDAY){
            iter=facility.getOpenTime().getStartSunday();
            to=facility.getOpenTime().getEndSunday();
        }else{
            iter=facility.getOpenTime().getStartWorkingDays();
            to=facility.getOpenTime().getEndWorkingDays();
        }

        for( ; iter.isBefore(to);iter = iter.plus(Duration.ofMinutes(30))) {
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
                    order.getCheckIn(),offer.getPrice()));
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

    public AllOrdersToShowDto searchTrainings(String facName, String price, String facType, String trainingType, String sortType,
                                              String sortDir, String fromDate, String toDate, String username) {
        Customer customer = customerStorage.getCustomerByUsername(username);
        List<OfferHistory> offerHistories = offerHistoryStorage.getByCustomerId(customer.getId());
        List<OrderToShowDto> orders = new ArrayList<>();

        for (var order : offerHistories) {
            if (order.isActiveDate(fromDate, toDate)) {
                Offer offer = offerStorage.getById(order.getOffer().getId());
                SportFacility sportFacility = sportFacilityStorage.getById(offer.getSportFacility().getId());
                if (offer.searchedTypeAndPriceRange(trainingType,price) && sportFacility.searchedNameAndType(facName, facType)) {
                    orders.add(new OrderToShowDto(order.getId(), offer.getName(), sportFacility.getName(), offer.getType().toString(),
                            order.getCheckIn(),offer.getPrice()));
                }

            }
        }
        orders=sortList(orders,sortType,sortDir);
        return new AllOrdersToShowDto(orders);
    }

    private List<OrderToShowDto> sortList(List<OrderToShowDto> orders,String sortType, String sortDir) {

        if (sortType.equals("0")) {
            Collections.sort(orders,new ComparatorFactory.OrderCompareName());
        }else if(sortType.equals("1")){
            Collections.sort(orders,new ComparatorFactory.OrderComparePrice());
        }else if(sortType.equals("2")){
            Collections.sort(orders,new ComparatorFactory.OrderCompareDate());
        }

         if(sortDir.equals("desc")) Collections.reverse(orders);
        return orders;
    }

    public boolean createComment(CreateCommentDto commentDto) {
        SportFacility sportFacility=sportFacilityStorage.getById(commentDto.getFacId());
        Customer customer=customerStorage.getCustomerByUsername(commentDto.getUsername());
        Comment comment=new Comment(-1, CommentStatus.SUBMITTED,sportFacility,customer,commentDto.getText(),commentDto.getGrade());
        commentStorage.add(comment);
        return true;
    }

    public CustomerType getCustomerType(String username) {
        Customer customer=customerStorage.getCustomerByUsername(username);
        return customer.getType();
    }
}
