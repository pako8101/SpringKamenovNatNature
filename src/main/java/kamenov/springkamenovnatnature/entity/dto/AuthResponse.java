package kamenov.springkamenovnatnature.entity.dto;

import kamenov.springkamenovnatnature.entity.UserEntity;

public class AuthResponse {
    public String token;
    public String username;
    public AuthResponse(String token, UserEntity username) {
        this.token = token;
        this.username = String.valueOf(username);
    }

    public String getToken() {
        return token;
    }

    public AuthResponse setToken(String token) {
        this.token = token;
        return this;
    }

    public String getUsername() {
        return username;
    }

    public AuthResponse setUsername(String username) {
        this.username = username;
        return this;
    }
}
