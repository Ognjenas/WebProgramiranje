package dto.offer;

import java.util.List;

public class OffersToChooseDto {
    private List<ChooseOfferDto> offers;

    public OffersToChooseDto(List<ChooseOfferDto> offers) {
        this.offers = offers;
    }

    public List<ChooseOfferDto> getOffers() {
        return offers;
    }

    public void setOffers(List<ChooseOfferDto> offers) {
        this.offers = offers;
    }
}
