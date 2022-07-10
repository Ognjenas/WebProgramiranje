package beans.sportfacility;

import beans.users.Customer;

public class Comment {
    private int id;
    private CommentStatus status;
    private SportFacility facility;
    private Customer customer;
    private String text;
    private int grade;

    public Comment(int id, CommentStatus status, SportFacility facility, Customer customer, String text, int grade) {
        this.id = id;
        this.status = status;
        this.facility = facility;
        this.customer = customer;
        this.text = text;
        this.grade = grade;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public CommentStatus getStatus() {
        return status;
    }

    public void setStatus(CommentStatus status) {
        this.status = status;
    }

    public SportFacility getFacility() {
        return facility;
    }

    public void setFacility(SportFacility facility) {
        this.facility = facility;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }
}
