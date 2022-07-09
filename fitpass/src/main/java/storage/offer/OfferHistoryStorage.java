package storage.offer;

import beans.offer.Offer;
import beans.offer.OfferHistory;
import beans.users.Customer;
import beans.users.Trainer;
import beans.users.User;
import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class OfferHistoryStorage {
    private static OfferHistoryStorage instance = null;

    public static OfferHistoryStorage getInstance() {
        if (instance == null) {
            instance = new OfferHistoryStorage();
        }
        return instance;
    }

    Gson gson = new GsonBuilder()
            .setExclusionStrategies(new TrainerAndCustomerExcluder())
            .setPrettyPrinting()
            .serializeNulls()
            .create();

    private OfferHistoryStorage() {
    }

    public OfferHistory getById(int id) {
        return getAll().stream().filter(oh -> !oh.isDeleted()).filter(oh -> oh.getId() == id).findFirst().orElse(null);
    }

    public List<OfferHistory> getNotDeleted() {
        return getAll().stream().filter(oh -> !oh.isDeleted()).collect(Collectors.toList());
    }

    public List<OfferHistory> getAll() {
        List<OfferHistory> allOffersHistories = new ArrayList<>();

        try {
            Reader reader = Files.newBufferedReader(Paths.get("./storage/offerHistories.json"));
            allOffersHistories = gson.fromJson(reader, new TypeToken<List<OfferHistory>>() {
            }.getType());
            reader.close();
            return allOffersHistories;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return allOffersHistories;
    }

    private int getId() {
        return getAll().size();
    }

    public OfferHistory add(OfferHistory offerHistory) {
        List<OfferHistory> all = getAll();
        offerHistory.setId(getId());

        try (FileWriter writer = new FileWriter("./storage/offerHistories.json")) {
            all.add(offerHistory);
            gson.toJson(all, writer);
            return offerHistory;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<OfferHistory> getBySportFacilityId(int sportFacilityId) {
        return getAll().stream()
                .filter(oh -> !oh.isDeleted() && oh.getSportFacility().getId() == sportFacilityId)
                .collect(Collectors.toList());
    }

    public List<OfferHistory> getByCustomerId(int customerId) {
        return getAll().stream()
                .filter(oh -> !oh.isDeleted() && oh.getCustomer().getId() == customerId && oh.getCheckIn().isAfter(LocalDateTime.now()))
                .collect(Collectors.toList());
    }

    public List<OfferHistory> getByTrainerId(int trainerId) {
        return getAll().stream()
                .filter(oh -> !oh.isDeleted() && oh.getTrainer().getId() == trainerId && oh.getCheckIn().isAfter(LocalDateTime.now()))
                .collect(Collectors.toList());
    }

    public List<OfferHistory> getByDateAndOfferId(LocalDate date, int offerId) {
        return getAll().stream()
                .filter(oh -> !oh.isDeleted() && oh.getOffer().getId() == offerId && oh.getCheckIn().toLocalDate().isEqual(date))
                .collect(Collectors.toList());
    }

    public List<OfferHistory> getByDateAndTrainerId(LocalDate date, int trainerId) {
        return getAll().stream()
                .filter(oh -> !oh.isDeleted() && oh.getTrainer().getId() == trainerId && oh.getCheckIn().toLocalDate().isEqual(date))
                .collect(Collectors.toList());
    }
    public List<OfferHistory> getByDateAndCustomerId(LocalDate date, int customerId) {
        return getAll().stream()
                .filter(oh -> !oh.isDeleted() && oh.getCustomer().getId() == customerId && oh.getCheckIn().toLocalDate().isEqual(date))
                .collect(Collectors.toList());
    }

    public OfferHistory update(OfferHistory offerHistory) {
        List<OfferHistory> offerHistories = getAll();
        for(int i = 0; i < offerHistories.size(); i++) {
            if(offerHistories.get(i).getId() == offerHistory.getId()) {
                offerHistories.set(i, offerHistory);
                save(offerHistories);
                return offerHistory;
            }
        }
        return null;
    }

    private void save(List<OfferHistory> offerHistory) {
        try(FileWriter writer =new FileWriter("./storage/offerHistories.json")){
            gson.toJson(offerHistory, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static class TrainerAndCustomerExcluder implements ExclusionStrategy {

        @Override
        public boolean shouldSkipField(FieldAttributes fieldAttributes) {
            if (fieldAttributes.getDeclaringClass() == Offer.class && !fieldAttributes.getName().equals("id")) {
                return true;
            } else if (fieldAttributes.getDeclaringClass() == Trainer.class && !fieldAttributes.getName().equals("id")) {
                return true;
            } else if (fieldAttributes.getDeclaringClass() == Customer.class && !fieldAttributes.getName().equals("id")) {
                return true;
            } else if (fieldAttributes.getDeclaringClass() == User.class && !fieldAttributes.getName().equals("id")) {
                return true;
            } else {
                return false;
            }
        }

        @Override
        public boolean shouldSkipClass(Class<?> aClass) {
            return false;
        }
    }
}
