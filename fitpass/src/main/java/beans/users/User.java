package beans.users;

import java.time.LocalDate;

public class User {
    private int id;
    private String username;
    private String password;
    private String name;
    private String surname;
    private boolean gender;
    private LocalDate birthDate;
    private Role role;
    private boolean isDeleted;

    public User() {
    }

    public User(String username, String password, String name, String surname, boolean gender, LocalDate birthDate, Role role) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.surname = surname;
        this.gender = gender;
        this.birthDate = birthDate;
        this.role = role;
        this.isDeleted = false;
    }

    public User(int id, String username, String password, String name, String surname, boolean gender, LocalDate birthDate, Role role) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.name = name;
        this.surname = surname;
        this.gender = gender;
        this.birthDate = birthDate;
        this.role = role;
        this.isDeleted = false;
    }



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public boolean isGender() {
        return gender;
    }

    public void setGender(boolean gender) {
        this.gender = gender;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }

    @Override
    public String toString() {
        return this.id + "|" + this.username + "|" + this.password + "|" + this.name + "|" + this.surname + "|" +
                this.gender + "|" + this.birthDate.toString() + "|" + role.toString() + "\n";
    }

    public boolean isSearched(String searchInput, String userRole, String userType) {
        //TREBA RESITI USERTYPE
        return (this.name.toLowerCase().contains(searchInput.toLowerCase()) || this.surname.toLowerCase().contains(searchInput.toLowerCase()) ||
                this.username.toLowerCase().contains(searchInput.toLowerCase())) && this.role.toString().toLowerCase().contains(userRole.toLowerCase());
    }
}
