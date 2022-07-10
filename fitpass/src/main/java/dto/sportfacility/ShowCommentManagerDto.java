package dto.sportfacility;

import beans.sportfacility.CommentStatus;

public class ShowCommentManagerDto {
    private int id;
    private String customerUsername;
    private String text;
    private CommentStatus status;
    private int grade;

    public ShowCommentManagerDto(int id, String customerUsername, String text, CommentStatus status, int grade) {
        this.id = id;
        this.customerUsername = customerUsername;
        this.text = text;
        this.status = status;
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

    public CommentStatus getStatus() {
        return status;
    }

    public void setStatus(CommentStatus status) {
        this.status = status;
    }

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }
}
