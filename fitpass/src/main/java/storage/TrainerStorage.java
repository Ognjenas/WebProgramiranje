package storage;

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
import java.util.ArrayList;
import java.util.List;

public class TrainerStorage {

    private static TrainerStorage instance = null;

    Gson gson = new GsonBuilder()
            .setExclusionStrategies(new OfferHistoryExcluder())
            .setPrettyPrinting()
            .serializeNulls()
            .create();
    public static TrainerStorage getInstance() {
        if (instance == null) {
            instance = new TrainerStorage();
        }
        return instance;
    }

    private TrainerStorage() {
    }

    public Trainer getById(int id) {
        return getAll().stream().filter(trainer-> trainer.getId() == id).findFirst().orElse(null);
    }

    public Trainer getByUsername(String username) {
        return getAll().stream().filter(trainer-> trainer.getUsername().equals(username)).findFirst().orElse(null);
    }

    public List<Trainer> getAll() {
        List<Trainer> allTrainers = new ArrayList<>();

        try {
            Reader reader = Files.newBufferedReader(Paths.get("./storage/trainers.json"));
            allTrainers = gson.fromJson(reader, new TypeToken<List<Trainer>>() {}.getType());
            reader.close();
            return allTrainers;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return allTrainers;
    }

    public Trainer add(Trainer trainer) {
        List<Trainer> trainers = getAll();
        try(FileWriter writer =new FileWriter("./storage/trainers.json")){
            trainers.add(trainer);
            gson.toJson(trainers, writer);
            return trainer;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void editTrainer(Trainer trainer) {
        List<Trainer> trainers = getAll();

        for(int i = 0; i<trainers.size();i++) {
            if(trainers.get(i).getId() == trainer.getId()) {
                trainers.set(i,trainer);
                break;
            }
        }
        save(trainers);
    }

    public void editUser(User user) {
        List<Trainer> trainers = getAll();

        for(int i = 0; i<trainers.size();i++) {
            if(trainers.get(i).getId() == user.getId()) {
                trainers.set(i,new Trainer(user, trainers.get(i).getTrainingHistory()));
                break;
            }
        }
        save(trainers);
    }

    private void save(List<Trainer> trainers) {
        try(FileWriter writer =new FileWriter("./storage/trainers.json")){
            gson.toJson(trainers, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static class OfferHistoryExcluder implements ExclusionStrategy {

        @Override
        public boolean shouldSkipField(FieldAttributes fieldAttributes) {
            if (fieldAttributes.getDeclaringClass() == OfferHistory.class && !fieldAttributes.getName().equals("id")) {
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
