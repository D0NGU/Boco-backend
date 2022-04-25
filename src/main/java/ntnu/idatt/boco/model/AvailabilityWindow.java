package ntnu.idatt.boco.model;

import java.time.LocalDate;
import java.util.Objects;

/**
 * This class represents an availability window.
 */
public class AvailabilityWindow {
    private LocalDate from;
    private LocalDate to;

    public AvailabilityWindow() {}

    /**
     * Constructor for an availability window object.
     * @param from the start date of the availability window
     * @param to the end date of the availability window
     */
    public AvailabilityWindow(LocalDate from, LocalDate to) {
        this.from = from;
        this.to = to;
    }

    public LocalDate getFrom() {
        return from;
    }
    public LocalDate getTo() {
        return to;
    }

    public void setFrom(LocalDate from) {
        this.from = from;
    }
    public void setTo(LocalDate to) {
        this.to = to;
    }

    @Override
    public String toString() {
        return "AvailabilityWindow is from " + from + " to " + to;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AvailabilityWindow that = (AvailabilityWindow) o;
        return Objects.equals(from, that.from) && Objects.equals(to, that.to);
    }

    @Override
    public int hashCode() {
        return Objects.hash(from, to);
    }
}
