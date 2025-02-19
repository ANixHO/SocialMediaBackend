package org.socialmedia.dto;

public class LoginResponseDTO {

    private String jwt;
    private String id;
    private String username;

    public LoginResponseDTO(String id, String username, String jwt) {
        this.id = id;
        this.username = username;
        this.jwt = jwt;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getJwt() {
        return jwt;
    }

    public void setJwt(String jwt) {
        this.jwt = jwt;
    }
}
