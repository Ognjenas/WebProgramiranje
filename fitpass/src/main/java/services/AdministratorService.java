package services;

import beans.users.Manager;
import beans.users.Role;
import beans.users.Trainer;
import beans.users.User;
import dto.users.MakeUserDto;
import storage.ManagerStorage;
import storage.TrainerStorage;
import storage.UserStorage;

import java.util.ArrayList;

public class AdministratorService {

    private static AdministratorService instance = null;

    public static AdministratorService getInstance() {
        if (instance == null) {
            instance = new AdministratorService();
        }
        return instance;
    }

    private AdministratorService() {

    }

    public boolean makeTrainer(MakeUserDto makeUserDto) {
        if(UserStorage.getInstance().getUserByUsername(makeUserDto.getUsername()) != null) {
            return false;
        }
        User user = UserStorage.getInstance().addUser(new User(makeUserDto.getUsername(),
                makeUserDto.getPassword(),
                makeUserDto.getName(),
                makeUserDto.getSurname(),
                makeUserDto.isGender(),
                makeUserDto.getBirthDate(),
                Role.valueOf(makeUserDto.getRole())));
        TrainerStorage.getInstance().add(new Trainer(user, null));
        return true;
    }

    public boolean makeManager(MakeUserDto makeUserDto) {
        if(UserStorage.getInstance().getUserByUsername(makeUserDto.getUsername()) != null) {
            return false;
        }
        User user = UserStorage.getInstance().addUser(new User(makeUserDto.getUsername(),
                makeUserDto.getPassword(),
                makeUserDto.getName(),
                makeUserDto.getSurname(),
                makeUserDto.isGender(),
                makeUserDto.getBirthDate(),
                Role.valueOf(makeUserDto.getRole())));
        ManagerStorage.getInstance().add(new Manager(user, new ArrayList<>()));
        return true;
    }
}
