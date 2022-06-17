package beans.users;

import java.time.LocalDate;

public class User {
    private int id;
    private String username;
    private String password;
    private String name;
    private String surname;
    private boolean sex;
    private LocalDate birthDate;
    private Role role;

    public User() {
    }

    public User(String username, String password, String name, String surname, boolean sex, LocalDate birthDate, Role role) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.surname = surname;
        this.sex = sex;
        this.birthDate = birthDate;
        this.role = role;
    }

    public User(int id, String username, String password, String name, String surname, boolean sex, LocalDate birthDate, Role role) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.name = name;
        this.surname = surname;
        this.sex = sex;
        this.birthDate = birthDate;
        this.role = role;
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

    public boolean isSex() {
        return sex;
    }

    public void setSex(boolean sex) {
        this.sex = sex;
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

    @Override
    public String toString() {
        return this.id + "|" + this.username + "|" + this.password + "|" + this.name + "|" + this.surname + "|" +
                this.sex + "|" + this.birthDate.toString() + "|" + role.toString() + "\n";
    }
}
