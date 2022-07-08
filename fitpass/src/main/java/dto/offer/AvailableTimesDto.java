package dto.offer;

import java.util.List;

public class AvailableTimesDto {
    private List<String> times;

    public AvailableTimesDto(List<String> times) {
        this.times = times;
    }

    public List<String> getTimes() {
        return times;
    }

    public void setTimes(List<String> times) {
        this.times = times;
    }
}
