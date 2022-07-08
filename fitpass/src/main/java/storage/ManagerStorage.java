package storage;

import beans.sportfacility.SportFacility;
import beans.users.Manager;
import beans.users.Role;
import beans.users.Trainer;
import beans.users.User;
import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
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

    Gson gson = new GsonBuilder()
            .setExclusionStrategies(new FacilityExcluder())
            .setPrettyPrinting()
            .serializeNulls()
            .create();

    public List<Manager> getAll() {
        List<Manager> allManagers = new ArrayList<>();

        try {
            Reader reader = Files.newBufferedReader(Paths.get("./storage/managers.json"));
            allManagers = gson.fromJson(reader, new TypeToken<List<Manager>>() {}.getType());
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
            gson.toJson(managers, writer);
            return manager;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Manager getByUsername(String username) {
        return getAll().stream().filter(manager -> manager.getUsername().equals(username)).findFirst().orElse(null);
    }

    public Manager getById(int id){
        List<Manager> managers = getAll();
        for(Manager man:managers){
            if(man.getId()==id) return man;
        }
        return null;
    }

    public boolean update(Manager manager){
        List<Manager> managers = getAll();
        try(FileWriter writer =new FileWriter("./storage/managers.json")){
            for(Manager man:managers){
                if(man.getId()==manager.getId()){
                    man.setUsername(manager.getUsername());
                    man.setPassword(manager.getPassword());
                    man.setName(manager.getName());
                    man.setSurname(manager.getSurname());
                    man.setGender(manager.isGender());
                    man.setBirthDate(manager.getBirthDate());
                    man.setRole(manager.getRole());
                    man.setDeleted(manager.isDeleted());
                    man.setSportFacility(manager.getSportFacility());
                }
            }
            gson.toJson(managers, writer);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void editUser(User user) {
        List<Manager> managers = getAll();

        for(int i = 0; i<managers.size();i++) {
            if(managers.get(i).getId() == user.getId()) {
                managers.set(i,new Manager(user, managers.get(i).getSportFacility()));
                break;
            }
        }
        save(managers);
    }

    private void save(List<Manager> managers) {
        try(FileWriter writer =new FileWriter("./storage/managers.json")){
            gson.toJson(managers, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static class FacilityExcluder implements ExclusionStrategy {

        @Override
        public boolean shouldSkipField(FieldAttributes fieldAttributes) {
            return fieldAttributes.getDeclaringClass() == SportFacility.class && !fieldAttributes.getName().equals("id");
        }

        @Override
        public boolean shouldSkipClass(Class<?> aClass) {
            return false;
        }
    }
}
