package ntnu.idatt.boco.model;

/**
 * This class represents a contact form sent in by a user
 */
public class ContactForm {
    private int contactFormId;
    private String fname;
    private String lname;
    private String email;
    private String comment;
    private int userId;

    /**
     * Constructor for creating a contact form object
     * @param contactFormId the unique if of the contact form
     * @param fname the first name of the user who sent in the contact form
     * @param lname the last name of the user who sent in the contact form
     * @param email the email address of the user who sent in the contact form
     * @param comment the comment sent in by the user
     * @param userId the id of the user
     */
    public ContactForm(int contactFormId, String fname, String lname, String email, String comment, int userId) {
        this.contactFormId = contactFormId;
        this.fname = fname;
        this.lname = lname;
        this.email = email;
        this.comment = comment;
        this.userId = userId;
    }

    public int getContactFormId() { return contactFormId; }
    public String getFname() { return fname; }
    public String getLname() { return lname; }
    public String getEmail() { return email; }
    public String getComment() { return comment; }
    public int getUserId() { return userId; }

    public void setContactFormId(int contactFormId) { this.contactFormId = contactFormId; }
    public void setFname(String fname) { this.fname = fname; }
    public void setLname(String lname) { this.lname = lname; }
    public void setEmail(String email) { this.email = email; }
    public void setComment(String comment) { this.comment = comment; }
    public void setUserId(int userId) { this.userId = userId; }
}
