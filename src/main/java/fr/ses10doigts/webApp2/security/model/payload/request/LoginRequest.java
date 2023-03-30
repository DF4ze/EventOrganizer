package fr.ses10doigts.webApp2.security.model.payload.request;

import jakarta.validation.constraints.NotBlank;

public class LoginRequest {

    @NotBlank
    private String userName;

    @NotBlank
    private String password;

    public String getUserName() {
	return userName;
    }

    public void setUserName(String username) {
	this.userName = username;
    }

    public String getPassword() {
	return password;
    }

    public void setPassword(String password) {
	this.password = password;
    }
}