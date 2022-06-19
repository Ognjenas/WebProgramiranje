package dto.users;

public class UserInfoDto {
    private String username;
    private String role;

    public UserInfoDto(String username, String role) {
        this.username = username;
        this.role = role;
    }

    public UserInfoDto() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
