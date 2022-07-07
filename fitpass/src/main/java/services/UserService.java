package services;

import beans.users.*;
import dto.users.*;
import storage.*;
import utilities.ComparatorFactory;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.Random;

public class UserService {

    private static UserService instance = null;
    private static final UserStorage userStorage = UserStorage.getInstance();
    private static final TrainerStorage trainerStorage = TrainerStorage.getInstance();
    private static final ManagerStorage managerStorage = ManagerStorage.getInstance();
    private static final CustomerStorage customerStorage = CustomerStorage.getInstance();
    private static final SubscriptionStorage subscriptionStorage= SubscriptionStorage.getInstance();
    private static final PromoCodeStorage promoCodeStorage= PromoCodeStorage.getInstance();

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
        User addedUser=userStorage.addUser(new User(customerDto.getUsername(), customerDto.getPassword(), customerDto.getName(),
                customerDto.getSurname(), customerDto.isGender(), customerDto.getBirthDate(), Role.CUSTOMER));
        customerStorage.addCustomer(new Customer(addedUser,null,null, new CustomerType(CustomerRankType.NONE,0,0)));
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

    public AllUsersDto getSearchedUsers(String searchInput, String userRole, String userType) {
        List<UserDto> list=new ArrayList<>();
        for(User user:UserStorage.getInstance().getAllNotDeleted()){
            if(user.isSearched(searchInput,userRole,userType)) {
                list.add(makeUserDto(user));
            }
        }
        return new AllUsersDto(list);
    }

    public AllUsersDto getSortedAndSearchedUsers(String searchInput, String userRole, String userType, String columnIndex, String sortDir) {
        AllUsersDto searched=getSearchedUsers(searchInput,userRole,userType);
        List<UserDto> searchedUsers=searched.getUsers();

        if(Integer.parseInt(columnIndex)==0){
            Collections.sort(searchedUsers,new ComparatorFactory.UserCompareName());
        } else if (Integer.parseInt(columnIndex)==1) {
            Collections.sort(searchedUsers,new ComparatorFactory.UserCompareSurname());
        } else if (Integer.parseInt(columnIndex)==2) {
            Collections.sort(searchedUsers,new ComparatorFactory.UserCompareUsername());
        }

        if(sortDir.equals("desc")) Collections.reverse(searchedUsers);

        return new AllUsersDto(searchedUsers);
    }

    public boolean createSubscription(MakeSubscriptionDto dto) {
        Customer customer=customerStorage.getCustomerByUsername(dto.getUsername());
        Subscription subscription=makeSubscriptionInstance(dto,customer);
        subPromoCode(dto.getPromoCode());
        customer.setSubscription(subscription);
        customerStorage.editCustomer(customer);

        return true;
    }

    private void subPromoCode(String promoCode) {
        PromoCode code=PromoCodeStorage.getInstance().getByCode(promoCode);
        code.setUsageTimes(code.getUsageTimes()-1);
        PromoCodeStorage.getInstance().edit(code);
    }

    private static Subscription makeSubscriptionInstance(MakeSubscriptionDto dto, Customer customer){
        if(customer.getSubscription()!=null){
            Subscription previosuSubscription=subscriptionStorage.getById(customer.getSubscription().getId());
            previosuSubscription.setDeleted(true);
            previosuSubscription.setStatus(false);
            subscriptionStorage.edit(previosuSubscription);
        }

        String Id = generateRandomId(10);
        LocalDate startDate=LocalDate.now();
        LocalDate endDate;
        if(dto.getType()==SubscriptionType.MONTHLY){
            endDate=startDate.plusMonths(1);
        }else{
            endDate=startDate.plusYears(1);
        }



        return subscriptionStorage.add(new Subscription(Id,dto.getType(),startDate,endDate,dto.getPrice(),customer,true,dto.getDailyTrainings()));
    }

    private static String generateRandomId(int len) {
        String chars = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz!@#$%&";
        Random rnd = new Random();
        StringBuilder sb = new StringBuilder(len);
        for (int i = 0; i < len; i++)
            sb.append(chars.charAt(rnd.nextInt(chars.length())));
        return sb.toString();
    }

    public ShowSubscriptionDto getCurrentSubscription(String username) {
        Subscription subscription=subscriptionStorage.getActiveByCustomerId(customerStorage.getCustomerByUsername(username).getId());
        if(subscription==null) return null;
        return new ShowSubscriptionDto(subscription.getId(),subscription.getType(),subscription.getPayDate(),subscription.getValidUntil(), subscription.getDailyEnteringNumber());
    }

    public PromoCode checkPromoCode(String searchedCode) {
        for (PromoCode promoCode:promoCodeStorage.getAllActive(LocalDate.now())){
            if(promoCode.getCode().equals(searchedCode)) {
                System.out.println(promoCode);
                return promoCode;
            }
        }
        return null;
    }
}
