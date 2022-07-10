package dto.sportfacility;

public class CreateCommentDto {
    private String text;
    private int grade;
    private String username;
    private int facId;

    public CreateCommentDto(String text, int grade, String username, int facId) {
        this.text = text;
        this.grade = grade;
        this.username = username;
        this.facId = facId;
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getFacId() {
        return facId;
    }

    public void setFacId(int facId) {
        this.facId = facId;
    }
}
