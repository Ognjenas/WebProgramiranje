package dto.sportfacility;

import java.util.List;

public class AllFreeManagersDto {
    private List<FreeManagerDto> freeManagers;

    public AllFreeManagersDto(List<FreeManagerDto> freeManagers) {
        this.freeManagers = freeManagers;
    }

    public List<FreeManagerDto> getFreeManagers() {
        return freeManagers;
    }

    public void setFreeManagers(List<FreeManagerDto> freeManagers) {
        this.freeManagers = freeManagers;
    }

}
