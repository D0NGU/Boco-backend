package ntnu.idatt.boco.model;

import java.time.LocalDate;

/**
 * This class represents an alert
 */
public class Alert {
    private int alertId;
    private String description;
    private LocalDate alertDate;
    private boolean hasSeen;
    private int optionalId;
    private int userId;

    public Alert() {}

    /**
     * Constructor for an alert object.
     * @param alertId the unique id of the alert
     * @param description a description of the alert
     * @param alertDate the date the alert was created
     * @param hasSeen true is alert has been seen, false otherwise
     * @param optionalId an optional id to link the alert to a product/listing
     * @param userId the id of the user the alert is targeted at
     */
    public Alert(int alertId, String description, LocalDate alertDate, boolean hasSeen, int optionalId, int userId) {
        this.alertId = alertId;
        this.description = description;
        this.alertDate = alertDate;
        this.hasSeen = hasSeen;
        this.optionalId = optionalId;
        this.userId = userId;
    }

    /**
     * Constructor for an alert object.
     * @param alertId the unique id of the alert
     * @param description a description of the alert
     * @param alertDate the date the alert was created
     * @param hasSeen true is alert has been seen, false otherwise
     * @param userId the id of the user the alert is targeted at
     */
    public Alert(int alertId, String description, LocalDate alertDate, boolean hasSeen, int userId) {
        this.alertId = alertId;
        this.description = description;
        this.alertDate = alertDate;
        this.hasSeen = hasSeen;
        this.userId = userId;
    }

    public int getAlertId() { return alertId; }
    public String getDescription() { return description; }
    public LocalDate getAlertDate() { return alertDate; }
    public int getOptionalId() { return optionalId; }
    public int getUserId() { return userId; }

    public void setAlert(int alertId) { this.alertId = alertId; }
    public void setDescription(String description) { this.description = description; }
    public void setAlertDate(LocalDate alertDate) { this.alertDate = alertDate; }
    public void setOptionalId(int optionalId) { this.optionalId = optionalId; }
    public void setUserId(int userId) { this.userId = userId; }

    @Override
    public String toString() {
        return "Alert{" +
                "alertId=" + alertId +
                ", description='" + description + '\'' +
                ", alertDate=" + alertDate +
                ", optionalId=" + optionalId +
                ", userId=" + userId +
                '}';
    }
}
