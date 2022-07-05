package dto.offer;

import java.util.List;

public class OffersToShowDto {
    private List<ShortOfferDto> offers;

    public OffersToShowDto() {
    }

    public OffersToShowDto(List<ShortOfferDto> offers) {
        this.offers = offers;
    }

    public List<ShortOfferDto> getOffers() {
        return offers;
    }

    public void setOffers(List<ShortOfferDto> offers) {
        this.offers = offers;
    }
}
