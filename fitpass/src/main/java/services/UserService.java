package services;

import beans.users.Role;
import beans.users.User;
import dto.users.*;
import storage.UserStorage;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

public class UserService {

    private static UserService instance = null;

    public static UserService getInstance() {
        if (instance == null) {
            instance = new UserService();
        }
        return instance;
    }

    private UserService() {

    }

    public boolean registerCustomer(RegisterCustomerDto customerDto) {
        UserStorage storage = UserStorage.getInstance();
        List<User> users = storage.getAll();
        for (var user : users) {
            if (user.getUsername().equals(customerDto.getUsername())) {
                return false;
            }
        }
        storage.addUser(new User(customerDto.getUsername(), customerDto.getPassword(), customerDto.getName(),
                customerDto.getSurname(), customerDto.isGender(), customerDto.getBirthDate(), Role.CUSTOMER));
        return true;
    }

    public User checkLogin(LoginDto loginDto) {
        UserStorage storage = UserStorage.getInstance();
        List<User> users = storage.getAll();
        for (var user : users) {
            if (user.getUsername().equals(loginDto.getUsername()) && user.getPassword().equals(loginDto.getPassword())) {
                return user;
            }
        }
        return null;
    }

    public User getByUsername(String username) {
        UserStorage storage = UserStorage.getInstance();
        List<User> users = storage.getAll();
        for (var user : users) {
            if (user.getUsername().equals(username)) {
                return user;
            }
        }
        return null;
    }

    public UserInfoDto getUserInfo(String username) {
        User user = UserStorage.getInstance().getUserByUsername(username);
        return new UserInfoDto(user.getUsername(), user.getRole().toString());
    }

    public AllUsersDto getAllUsers() {
        return new AllUsersDto(UserStorage.getInstance()
                .getAllNotDeleted()
                .stream()
                .map(this::makeUserDto)
                .collect(Collectors.toList()));
    }
    


    private UserDto makeUserDto(User user) {
        String birth = user.getBirthDate().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        String gender = user.isGender() ? "MUSKO" : "ZENSKO";
        return new UserDto(user.getName(), user.getSurname(), user.getUsername(), user.getRole().toString(), gender, birth);
    }
}
