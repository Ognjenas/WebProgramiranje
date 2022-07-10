package dto.sportfacility;

import beans.sportfacility.CommentStatus;
import beans.sportfacility.SportFacility;
import beans.users.Customer;

public class ShowCommentDto {
    private int id;
    private String customerUsername;
    private String text;
    private int grade;

    public ShowCommentDto(int id, String customerUsername, String text, int grade) {
        this.id = id;
        this.customerUsername = customerUsername;
        this.text = text;
        this.grade = grade;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCustomerUsername() {
        return customerUsername;
    }

    public void setCustomerUsername(String customerUsername) {
        this.customerUsername = customerUsername;
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
