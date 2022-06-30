package dto.users;

import java.util.List;

public class AllUsersDto {
    private List<UserDto> users;

    public AllUsersDto() {
    }

    public AllUsersDto(List<UserDto> users) {
        this.users = users;
    }

    public List<UserDto> getUsers() {
        return users;
    }

    public void setUsers(List<UserDto> users) {
        this.users = users;
    }
}
