package storage;

import beans.users.Customer;
import beans.users.Subscription;
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
import java.util.ArrayList;
import java.util.List;

public class SubscriptionStorage {
    private static SubscriptionStorage instance = null;

    Gson gson = new GsonBuilder()
            .setExclusionStrategies(new SubscriptionStorage.CustomerExcluder())
            .setPrettyPrinting()
            .serializeNulls()
            .create();

    public static SubscriptionStorage getInstance() {
        if (instance == null) {
            instance = new SubscriptionStorage();
        }
        return instance;
    }

    private SubscriptionStorage() {
    }

    public Subscription getById(String id) {
        List<Subscription> subscriptions = getAll();
        for (Subscription sub:subscriptions) {
            if(sub.getId().equals(id)) return sub;
        }
        return null;
    }

    public Subscription add(Subscription subscription) {
        List<Subscription> subscriptions = getAll();
        try(FileWriter writer =new FileWriter("./storage/subscriptions.json")){
            subscriptions.add(subscription);
            gson.toJson(subscriptions, writer);
            return subscription;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void edit(Subscription subscription) {
        List<Subscription> subscriptions = getAll();
        for(Subscription sub:subscriptions) {
            if(sub.getId().equals(subscription.getId())) {
                sub.setType(subscription.getType());
                sub.setPayDate(subscription.getPayDate());
                sub.setValidUntil(subscription.getValidUntil());
                sub.setPrice(subscription.getPrice());
                sub.setCustomer(subscription.getCustomer());
                sub.setStatus(subscription.isStatus());
                sub.setDailyEnteringNumber(subscription.getDailyEnteringNumber());
                sub.setDeleted(subscription.isDeleted());
                sub.setOrderedAppointments(subscription.getOrderedAppointments());
                break;
            }
        }
        save(subscriptions);
    }

    public List<Subscription> getAll() {
        List<Subscription> allSubscriptions = new ArrayList<>();
        try {
            Reader reader = Files.newBufferedReader(Paths.get("./storage/subscriptions.json"));
            allSubscriptions = gson.fromJson(reader, new TypeToken<List<Subscription>>() {}.getType());
            reader.close();
            return allSubscriptions;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    public List<Subscription> getAllNotDeleted() {
        List<Subscription> subscriptions = new ArrayList<>();
        for (var subscription : getAll()) {
            if(!subscription.isDeleted()) {
                subscriptions.add(subscription);
            }
        }
        return subscriptions;
    }

    private void save(List<Subscription> subscriptions) {
        try(FileWriter writer =new FileWriter("./storage/subscriptions.json")){
            gson.toJson(subscriptions, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Subscription getActiveByCustomerId(int id){
        List<Subscription> subscriptions = getAll();
        for (Subscription sub:subscriptions) {
            if(sub.getCustomer().getId()== id && sub.isStatus()) return sub;
        }
        return null;
    }

    private static class CustomerExcluder implements ExclusionStrategy {

        @Override
        public boolean shouldSkipField(FieldAttributes fieldAttributes) {
            if (fieldAttributes.getDeclaringClass() == Customer.class && !fieldAttributes.getName().equals("id")) {
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
