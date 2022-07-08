package storage;

import beans.offer.Offer;
import beans.sportfacility.SportFacility;
import beans.users.Customer;
import beans.users.Subscription;
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
import java.util.ArrayList;
import java.util.List;

public class CustomerStorage {
    private static CustomerStorage instance = null;

    Gson gson = new GsonBuilder()
            .setExclusionStrategies(new CustomerStorage.CustomerExcluder())
            .setPrettyPrinting()
            .serializeNulls()
            .create();

    public static CustomerStorage getInstance() {
        if (instance == null) {
            instance = new CustomerStorage();
        }
        return instance;
    }

    private CustomerStorage() {
    }

    public Customer getById(int id) {
        List<Customer> customers = getAll();
        for (Customer customer:customers) {
            if(customer.getId()==id) return customer;
        }
        return null;
    }

    public Customer addCustomer(Customer customer) {
        List<Customer> customers = getAll();
        try(FileWriter writer =new FileWriter("./storage/customers.json")){
            customers.add(customer);
            gson.toJson(customers, writer);
            return customer;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void editCustomer(Customer customer) {
        List<Customer> customers = getAll();
        for(Customer cus:customers) {
            if(cus.getId() == customer.getId()) {
                cus.setUsername(customer.getUsername());
                cus.setPassword(customer.getPassword());
                cus.setName(customer.getName());
                cus.setSurname(customer.getSurname());
                cus.setGender(customer.isGender());
                cus.setBirthDate(customer.getBirthDate());
                cus.setRole(customer.getRole());
                cus.setSubscription(customer.getSubscription());
                cus.setVisitedFacilities(customer.getVisitedFacilities());
                cus.setType(customer.getType());
                break;
            }
        }
        save(customers);
    }

    public List<Customer> getAll() {
        List<Customer> allCustomers = new ArrayList<>();
        try {
            Reader reader = Files.newBufferedReader(Paths.get("./storage/customers.json"));
            allCustomers = gson.fromJson(reader, new TypeToken<List<Customer>>() {}.getType());
            reader.close();
            return allCustomers;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Customer> getAllNotDeleted() {
        List<Customer> customers = new ArrayList<>();
        for (var customer : getAll()) {
            if(!customer.isDeleted()) {
                customers.add(customer);
            }
        }
        return customers;
    }

    public Customer getCustomerByUsername(String username) {
        for(var customer : getAllNotDeleted()) {
            if(username.equals(customer.getUsername())) {
                return customer;
            }
        }
        return null;
    }

    private void save(List<Customer> customers) {
        try(FileWriter writer =new FileWriter("./storage/customers.json")){
            gson.toJson(customers, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private static class CustomerExcluder implements ExclusionStrategy {

        @Override
        public boolean shouldSkipField(FieldAttributes fieldAttributes) {
            if (fieldAttributes.getDeclaringClass() == SportFacility.class && !fieldAttributes.getName().equals("id") ) {
                return true;
            } else if (fieldAttributes.getDeclaringClass() == Subscription.class && !fieldAttributes.getName().equals("id")) {
                return true;
            }else{
                return false;
            }

        }

        @Override
        public boolean shouldSkipClass(Class<?> aClass) {
            return false;
        }
    }

}
