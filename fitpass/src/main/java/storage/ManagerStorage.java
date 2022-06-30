package storage;

import beans.users.Manager;
import beans.users.Trainer;
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

public class ManagerStorage {
    private static ManagerStorage instance = null;

    public static ManagerStorage getInstance() {
        if (instance == null) {
            instance = new ManagerStorage();
        }
        return instance;
    }

    private ManagerStorage() {
    }

    public List<Manager> getAll() {
        List<Manager> allManagers = new ArrayList<>();

        try {
            Reader reader = Files.newBufferedReader(Paths.get("./storage/managers.json"));
            allManagers = new Gson().fromJson(reader, new TypeToken<List<Manager>>() {}.getType());
            reader.close();
            return allManagers;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return allManagers;
    }

    public Manager add(Manager manager) {
        List<Manager> managers = getAll();
        try(FileWriter writer =new FileWriter("./storage/managers.json")){
            managers.add(manager);
            new Gson().toJson(managers, writer);
            return manager;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
