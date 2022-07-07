package dto.users;

import beans.users.PromoCode;

import java.util.List;

public class PromoCodeShowDto {
    private List<PromoCode> allPromoCodes;

    public PromoCodeShowDto(){
    }

    public PromoCodeShowDto(List<PromoCode> allPromoCodes) {
        this.allPromoCodes = allPromoCodes;
    }

    public List<PromoCode> getAllPromoCodes() {
        return allPromoCodes;
    }

    public void setAllPromoCodes(List<PromoCode> allPromoCodes) {
        this.allPromoCodes = allPromoCodes;
    }


}
