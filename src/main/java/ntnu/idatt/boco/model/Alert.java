package ntnu.idatt.boco.model;

import java.time.LocalDate;

/**
 * This class represents an alert
 */
public class Alert {
    private int alertId;
    private String description;
    private LocalDate date;
    private int optionalId;
    private int userId;

    public Alert() {}

    /**
     * Constructor for an alert object.
     * @param alertId the unique id of the alert
     * @param description a description of the alert
     * @param date the date the alert was created
     * @param optionalId an optional id to link the alert to a product/listing
     * @param userId the id of the user the alert is targeted at
     */
    public Alert(int alertId, String description, LocalDate date, int optionalId, int userId) {
        this.alertId = alertId;
        this.description = description;
        this.date = date;
        this.optionalId = optionalId;
        this.userId = userId;
    }

    /**
     * Constructor for an alert object.
     * @param alertId the unique id of the alert
     * @param description a description of the alert
     * @param date the date the alert was created
     * @param userId the id of the user the alert is targeted at
     */
    public Alert(int alertId, String description, LocalDate date, int userId) {
        this.alertId = alertId;
        this.description = description;
        this.date = date;
        this.userId = userId;
    }

    public int getAlertId() { return alertId; }
    public String getDescription() { return description; }
    public LocalDate getDate() { return date; }
    public int getOptionalId() { return optionalId; }
    public int getUserId() { return userId; }

    public void setAlert(int alertId) { this.alertId = alertId; }
    public void setDescription(String description) { this.description = description; }
    public void setDate(LocalDate date) { this.date = date; }
    public void setOptionalId(int optionalId) { this.optionalId = optionalId; }
    public void setUserId(int userId) { this.userId = userId; }

    @Override
    public String toString() {
        return "Alert{" +
                "alertId=" + alertId +
                ", description='" + description + '\'' +
                ", date=" + date +
                ", optionalId=" + optionalId +
                ", userId=" + userId +
                '}';
    }
}
