package codesquad.dto;

import codesquad.domain.User;

public class UserDto {
    private String userId;
    private String password;
    private String currentPassword;
    private String name;
    private String email;

    public UserDto(String userId, String password, String currentPassword, String name, String email) {
        this.userId = userId;
        this.password = password;
        this.currentPassword = currentPassword;
        this.name = name;
        this.email = email;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getCurrentPassword() {
        return currentPassword;
    }

    public void setCurrentPassword(String currentPassword) {
        this.currentPassword = currentPassword;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public User toEntity() {
        return new User(userId, password, name, email);
    }
}
