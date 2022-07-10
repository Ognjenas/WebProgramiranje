package services;

import beans.sportfacility.Comment;
import beans.sportfacility.CommentStatus;
import beans.sportfacility.SportFacility;
import beans.users.*;
import dto.sportfacility.AllCommentsAdminDto;
import dto.sportfacility.ShowCommentAdminDto;
import dto.users.MakeUserDto;
import dto.users.PromoCodeCreateDto;
import dto.users.PromoCodeShowDto;
import storage.*;

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

    public AllCommentsAdminDto getUnapprovedComments() {
        List<Comment> comments= CommentStorage.getInstance().getAllSubmitted();
        List<ShowCommentAdminDto> list=new ArrayList<>();
        for(Comment comment:comments){
            Customer customer=CustomerStorage.getInstance().getById(comment.getCustomer().getId());
            SportFacility sportFacility=SportFacilityStorage.getInstance().getById(comment.getFacility().getId());
            list.add(new ShowCommentAdminDto(comment.getId(),customer.getUsername(),sportFacility.getName(),
                    comment.getText(),comment.getStatus(),comment.getGrade()));
        }
        if(list.isEmpty()) return null;
        return new AllCommentsAdminDto(list);

    }

    public AllCommentsAdminDto getFinishedComments() {
        List<Comment> comments= CommentStorage.getInstance().getAllConfirmedAndRejectedAdmin();
        List<ShowCommentAdminDto> list=new ArrayList<>();
        for(Comment comment:comments){
            Customer customer=CustomerStorage.getInstance().getById(comment.getCustomer().getId());
            SportFacility sportFacility=SportFacilityStorage.getInstance().getById(comment.getFacility().getId());
            list.add(new ShowCommentAdminDto(comment.getId(),customer.getUsername(),sportFacility.getName(),
                    comment.getText(),comment.getStatus(),comment.getGrade()));
        }
        if(list.isEmpty()) return null;
        return new AllCommentsAdminDto(list);
    }

    public void confirmComment(int id) {
        Comment comment=CommentStorage.getInstance().getById(id);
        comment.setStatus(CommentStatus.CONFIRMED);
        CommentStorage.getInstance().edit(comment);
    }

    public void rejectComment(int id) {
        Comment comment=CommentStorage.getInstance().getById(id);
        comment.setStatus(CommentStatus.REJECTED);
        CommentStorage.getInstance().edit(comment);
    }
}
