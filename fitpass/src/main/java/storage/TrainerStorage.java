package storage;

import beans.users.Trainer;
import beans.users.User;
import com.google.gson.Gson;
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

    public List<Trainer> getAll() {
        List<Trainer> allTrainers = new ArrayList<>();

        try {
            Reader reader = Files.newBufferedReader(Paths.get("./storage/trainers.json"));
            allTrainers = new Gson().fromJson(reader, new TypeToken<List<Trainer>>() {}.getType());
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
            new Gson().toJson(trainers, writer);
            return trainer;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
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
            new Gson().toJson(trainers, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
