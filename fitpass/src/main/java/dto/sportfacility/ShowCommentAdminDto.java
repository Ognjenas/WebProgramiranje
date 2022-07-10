package dto.sportfacility;

import beans.sportfacility.CommentStatus;

public class ShowCommentAdminDto {
    private int id;
    private String customerUsername;
    private String facilityName;
    private String text;
    private CommentStatus status;
    private int grade;

    public ShowCommentAdminDto(int id, String customerUsername, String facilityName, String text, CommentStatus status, int grade) {
        this.id = id;
        this.customerUsername = customerUsername;
        this.facilityName = facilityName;
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

    public String getFacilityName() {
        return facilityName;
    }

    public void setFacilityName(String facilityName) {
        this.facilityName = facilityName;
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
