package storage;

import beans.users.Role;
import beans.users.User;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class UserStorage {

    private static UserStorage instance = null;

    public static UserStorage getInstance() {
        if (instance == null) {
            instance = new UserStorage();
        }
        return instance;
    }

    private UserStorage() {
    }

    public List<User> getAll() {
        List<User> users = new ArrayList<>();
        try {
            File file = new File("./storage/users.txt");
            Scanner myReader = new Scanner(file);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                String[] separatedData = data.split("\\|");
                System.out.println(separatedData[0].getClass());
                Role role = Role.valueOf(Role.class,separatedData[7]);
                int id = Integer.parseInt(separatedData[0]);
                users.add(new User(id, separatedData[1], separatedData[2],
                        separatedData[3], separatedData[4], Boolean.parseBoolean(separatedData[5]),
                        LocalDate.parse(separatedData[6]), role));
                System.out.println(users.get(0).getName());
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return users;
    }
}
