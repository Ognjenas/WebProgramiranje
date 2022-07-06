package storage;

import beans.users.Role;
import beans.users.User;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class UserStorage {

    private static UserStorage instance = null;

    Gson gson = new GsonBuilder()
            .setPrettyPrinting()
            .serializeNulls()
            .create();

    public static UserStorage getInstance() {
        if (instance == null) {
            instance = new UserStorage();
        }
        return instance;
    }

    private UserStorage() {
    }

    public User getById(int id) {
        return getAll().stream().filter(usr -> usr.getId() == id).findFirst().orElse(null);
    }

    public List<User> getAll() {
        List<User> allUsers = new ArrayList<>();

        try {
            Reader reader = Files.newBufferedReader(Paths.get("./storage/users.json"));
            allUsers =gson.fromJson(reader, new TypeToken<List<User>>() {}.getType());
            reader.close();
//            allUsers.add(new User("","","","",true,LocalDate.now(),Role.CUSTOMER));
            return allUsers;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return allUsers;
    }

    public List<User> getAllTrainers() {
        return getAll().stream().filter(user -> user.getRole().equals(Role.TRAINER)).collect(Collectors.toList());
    }

    public List<User> getAllNotDeleted() {
        List<User> users = new ArrayList<>();
        for (var user : getAll()) {
            if(!user.isDeleted()) {
                users.add(user);
            }
        }
        return users;
    }

    private int getId() {
        List<User> all = getAll();
        return all.size();
    }

    public User getUserByUsername(String username) {
        for(var user : getAll()) {
            if(username.equals(user.getUsername())) {
                return user;
            }
        }
        return null;
    }

    public void editUser(User user) {
        List<User> users = getAll();
        for(int i = 0; i<users.size();i++) {
            if(users.get(i).getId() == user.getId()) {
                users.set(i,user);
                break;
            }
        }
        save(users);
    }

    public User addUser(User user) {
        List<User> users = getAll();
        user.setId(getId());

        try(FileWriter writer =new FileWriter("./storage/users.json")){

            users.add(user);
            gson.toJson(users, writer);
            writer.close();
            return user;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void save(List<User> users) {
        try(FileWriter writer =new FileWriter("./storage/users.json")){
            gson.toJson(users, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
