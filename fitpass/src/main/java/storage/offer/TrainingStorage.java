package storage.offer;

import beans.offer.Offer;
import beans.offer.Training;
import beans.sportfacility.SportFacility;
import beans.users.Manager;
import beans.users.Trainer;
import beans.users.User;
import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import storage.ManagerStorage;

import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class TrainingStorage {

    private static TrainingStorage instance = null;

    public static TrainingStorage getInstance() {
        if (instance == null) {
            instance = new TrainingStorage();
        }
        return instance;
    }

    Gson gson = new GsonBuilder()
            .setExclusionStrategies(new OfferExcluder())
            .setPrettyPrinting()
            .serializeNulls()
            .create();

    private TrainingStorage() {
    }

    public Training getById(int id) {
        return getAll().stream().filter(training -> training.getId() == id).findFirst().orElse(null);
    }

    public void deleteById(int id) {
        List<Training> trainings = getAll();
        for(var training : trainings) {
            if(training.getId() == id) {
                training.setId(-1);
                training.setDeleted(true);
            }
        }
        save(trainings);
    }

    public List<Training> getAll() {
        List<Training> allTraining = new ArrayList<>();

        try {
            Reader reader = Files.newBufferedReader(Paths.get("./storage/trainings.json"));
            allTraining = gson.fromJson(reader, new TypeToken<List<Training>>() {
            }.getType());
            reader.close();
            return allTraining;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return allTraining;
    }

    public List<Training> getAllNotDeleted() {
        List<Training> allTraining = new ArrayList<>();

        try {
            Reader reader = Files.newBufferedReader(Paths.get("./storage/trainings.json"));
            allTraining = gson.fromJson(reader, new TypeToken<List<Training>>() {
            }.getType());
            reader.close();
            return allTraining.stream().filter(training -> !training.isDeleted()).collect(Collectors.toList());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return allTraining;
    }

    public Training update(Training training) {
        List<Training> trainings = getAll();
        for(int i = 0; i < trainings.size(); i++) {
            if(trainings.get(i).getId() == training.getId()) {
                trainings.set(i, training);
                save(trainings);
                return training;
            }
        }
        return null;
    }

    private void save(List<Training> trainings) {
        try(FileWriter writer =new FileWriter("./storage/trainings.json")){
            gson.toJson(trainings, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Training add(Training training) {
        List<Training> trainings = getAll();
        try (FileWriter writer = new FileWriter("./storage/trainings.json")) {
            trainings.add(training);
            gson.toJson(trainings, writer);
            return training;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static class OfferExcluder implements ExclusionStrategy {

        @Override
        public boolean shouldSkipField(FieldAttributes fieldAttributes) {
            if (fieldAttributes.getDeclaringClass() == Offer.class && !fieldAttributes.getName().equals("id") && !fieldAttributes.getName().equals("isDeleted")) {
                return true;
            } else if (fieldAttributes.getDeclaringClass() == Trainer.class && !fieldAttributes.getName().equals("id")) {
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
