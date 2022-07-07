package services;

import beans.users.*;
import dto.users.MakeUserDto;
import dto.users.PromoCodeCreateDto;
import dto.users.PromoCodeShowDto;
import storage.ManagerStorage;
import storage.PromoCodeStorage;
import storage.TrainerStorage;
import storage.UserStorage;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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
        TrainerStorage.getInstance().add(new Trainer(user, new ArrayList<>()));
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
        ManagerStorage.getInstance().add(new Manager(user, null));
        return true;
    }

    public PromoCodeShowDto getValidPromoCodes() {
        System.out.println("USO U SERVIS");
        List<PromoCode> list = new ArrayList<>();
        for (PromoCode promo : PromoCodeStorage.getInstance().getAllActive(LocalDate.now())) {
            list.add(promo);
            System.out.println(promo);
        }
        if(list.isEmpty()) return null;
        return new PromoCodeShowDto(list);
    }

    public boolean createPromoCode(PromoCodeCreateDto dto){
        PromoCode promoCode=new PromoCode(dto.getCode(),dto.getDiscount(),LocalDate.now(),dto.getEndDate(),dto.getUsageTimes());
        PromoCodeStorage.getInstance().add(promoCode);
        return true;
    }
}
