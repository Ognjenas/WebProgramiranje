package utilities;

import java.time.LocalTime;

public class WorkingHours {
    private LocalTime startWorkingDays;
    private LocalTime endWorkingDays;
    private LocalTime startSaturday;
    private LocalTime endSaturday;
    private LocalTime startSunday;
    private LocalTime endSunday;

    public WorkingHours() {
    }

    public WorkingHours(LocalTime startWorkingDays, LocalTime endWorkingDays, LocalTime startSaturday, LocalTime endSaturday, LocalTime startSunday, LocalTime endSunday) {
        this.startWorkingDays = startWorkingDays;
        this.endWorkingDays = endWorkingDays;
        this.startSaturday = startSaturday;
        this.endSaturday = endSaturday;
        this.startSunday = startSunday;
        this.endSunday = endSunday;
    }

    public LocalTime getStartWorkingDays() {
        return startWorkingDays;
    }

    public void setStartWorkingDays(LocalTime startWorkingDays) {
        this.startWorkingDays = startWorkingDays;
    }

    public LocalTime getEndWorkingDays() {
        return endWorkingDays;
    }

    public void setEndWorkingDays(LocalTime endWorkingDays) {
        this.endWorkingDays = endWorkingDays;
    }

    public LocalTime getStartSaturday() {
        return startSaturday;
    }

    public void setStartSaturday(LocalTime startSaturday) {
        this.startSaturday = startSaturday;
    }

    public LocalTime getEndSaturday() {
        return endSaturday;
    }

    public void setEndSaturday(LocalTime endSaturday) {
        this.endSaturday = endSaturday;
    }

    public LocalTime getStartSunday() {
        return startSunday;
    }

    public void setStartSunday(LocalTime startSunday) {
        this.startSunday = startSunday;
    }

    public LocalTime getEndSunday() {
        return endSunday;
    }

    public void setEndSunday(LocalTime endSunday) {
        this.endSunday = endSunday;
    }
}
