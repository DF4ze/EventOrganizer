package fr.ses10doigts.webApp2.security.model;

public enum ERole {

    ROLE_USER("User"), ROLE_MODERATOR("Moderator"), ROLE_ADMIN("Administrator");

    private String pretty;

    private ERole(String pretty) {
	this.pretty = pretty;
    }

    public String getPretty() {
	return pretty;
    }

}