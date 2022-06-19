package dto;

import java.util.List;

public class AllFacilitiesDto {
    private List<SportFacilityDto> allFacilities;

    public AllFacilitiesDto() {
    }

    public AllFacilitiesDto(List<SportFacilityDto> allFacilities) {
        this.allFacilities = allFacilities;
    }

    public List<SportFacilityDto> getAllFacilities() {
        return allFacilities;
    }

    public void setAllFacilities(List<SportFacilityDto> allFacilities) {
        this.allFacilities = allFacilities;
    }
}
