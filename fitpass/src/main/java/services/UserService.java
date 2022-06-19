package services;

import beans.users.Role;
import beans.users.User;
import dto.users.LoginDto;
import dto.users.RegisterCustomerDto;
import dto.users.UserInfoDto;
import storage.UserStorage;

import java.util.List;

public class UserService {

    private static UserService instance = null;

    public static UserService getInstance() {
        if(instance == null) {
            instance = new UserService();
        }
        return instance;
    }

    private UserService() {

    }

    public boolean registerCustomer(RegisterCustomerDto customerDto) {
        UserStorage storage = UserStorage.getInstance();
        List<User> users = storage.getAll();
        for(var user : users) {
            if(user.getUsername().equals(customerDto.getUsername())) {
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
        for(var user : users) {
            if(user.getUsername().equals(loginDto.getUsername()) && user.getPassword().equals(loginDto.getPassword())) {
                return user;
            }
        }
        return null;
    }

    public UserInfoDto getUserInfo(String username) {
        User user = UserStorage.getInstance().getUserByUsername(username);
        return new UserInfoDto(user.getUsername(), user.getRole().toString());
    }
}
