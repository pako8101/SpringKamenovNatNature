package kamenov.springkamenovnatnature.entity.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import kamenov.springkamenovnatnature.entity.UserEntity;
import kamenov.springkamenovnatnature.validation.uniqUsername.UniqueUsername;

public class LoginDto {
    @Size(min = 3,max = 20,message = "Username must be between 3 and 20 symbols!")
    @NotBlank(message = "Username must not be empty!")
    @UniqueUsername
    private UserEntity username;
    @Size(min = 3,max = 20,message = "Password must be between 3 and 20 symbols!")
    @NotBlank(message = "Password must not be empty!")
    private String password;

    public LoginDto() {
    }

    public @Size(min = 3, max = 20, message = "Username must be between 3 and 20 symbols!") @NotNull(message = "Username must not be empty!") UserEntity getUsername() {
        return username;
    }

    public LoginDto setUsername(@Size(min = 3, max = 20, message = "Username must be between 3 and 20 symbols!") @NotNull(message = "Username must not be empty!") UserEntity username) {
        this.username = username;
        return this;
    }

    public @Size(min = 3, max = 20, message = "Password must be between 3 and 20 symbols!") @NotNull(message = "Password must not be empty!") String getPassword() {
        return password;
    }

    public LoginDto setPassword(@Size(min = 3, max = 20, message = "Password must be between 3 and 20 symbols!") @NotNull(message = "Password must not be empty!") String password) {
        this.password = password;
        return this;
    }
}
