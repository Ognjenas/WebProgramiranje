package storage;

import beans.users.Manager;
import beans.users.Role;
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
            new Gson().toJson(managers, writer);
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
            new Gson().toJson(managers, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
