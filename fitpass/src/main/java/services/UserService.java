package services;

import beans.users.Role;
import beans.users.User;
import dto.users.*;
import storage.ManagerStorage;
import storage.TrainerStorage;
import storage.UserStorage;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

public class UserService {

    private static UserService instance = null;
    private static final UserStorage userStorage = UserStorage.getInstance();
    private static final TrainerStorage trainerStorage = TrainerStorage.getInstance();
    private static final ManagerStorage managerStorage = ManagerStorage.getInstance();

    public static UserService getInstance() {
        if (instance == null) {
            instance = new UserService();
        }
        return instance;
    }

    private UserService() {

    }

    public boolean registerCustomer(RegisterCustomerDto customerDto) {
        List<User> users = userStorage.getAll();
        for (var user : users) {
            if (user.getUsername().equals(customerDto.getUsername())) {
                return false;
            }
        }
        userStorage.addUser(new User(customerDto.getUsername(), customerDto.getPassword(), customerDto.getName(),
                customerDto.getSurname(), customerDto.isGender(), customerDto.getBirthDate(), Role.CUSTOMER));
        return true;
    }

    public User checkLogin(LoginDto loginDto) {
        List<User> users = userStorage.getAll();
        for (var user : users) {
            if (user.getUsername().equals(loginDto.getUsername()) && user.getPassword().equals(loginDto.getPassword())) {
                return user;
            }
        }
        return null;
    }

    public User getByUsername(String username) {
        List<User> users = userStorage.getAll();
        for (var user : users) {
            if (user.getUsername().equals(username)) {
                return user;
            }
        }
        return null;
    }

    public UserInfoDto getUserInfo(String username) {
        User user = userStorage.getUserByUsername(username);
        return new UserInfoDto(user.getUsername(), user.getRole().toString());
    }

    public EditUserDto getUser(String username) {
        User user = userStorage.getUserByUsername(username);
        return makeEditUserDto(user);
    }

    public AllUsersDto getAllUsers() {
        return new AllUsersDto(userStorage
                .getAllNotDeleted()
                .stream()
                .map(this::makeUserDto)
                .collect(Collectors.toList()));
    }

    public User editUser(EditUserDto editUserDto) {
        User user = userStorage.getById(Integer.parseInt(editUserDto.getId()));
        if (user == null || !user.getRole().toString().equals(editUserDto.getRole())) {
            return null;
        }

        user.setUsername(editUserDto.getUsername());
        user.setPassword(editUserDto.getPassword());
        user.setName(editUserDto.getName());
        user.setName(editUserDto.getName());
        userStorage.editUser(user);

        switch (editUserDto.getRole()) {
            case "TRAINER":
                trainerStorage.editUser(user);
                return user;
            case "MANAGER":
                managerStorage.editUser(user);
                return user;
            default:
                return user;
        }
    }

    private UserDto makeUserDto(User user) {
        String birth = user.getBirthDate().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        String gender = user.isGender() ? "MUSKO" : "ZENSKO";
        return new UserDto(user.getName(), user.getSurname(), user.getUsername(), user.getRole().toString(), gender, birth);
    }

    private EditUserDto makeEditUserDto(User user) {
        return new EditUserDto(String.valueOf(user.getId()), user.getName(), user.getSurname(), user.getUsername(), user.getPassword(), user.getRole().toString());
    }
}
