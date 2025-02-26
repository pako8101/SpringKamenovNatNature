package kamenov.springkamenovnatnature.entity.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class RegisterDto {
    @NotBlank
    @Size(min = 3, max = 20)
    //@UniqueUsername
    private String username;
    @NotBlank
    @Size(min = 2, max = 200)
    private String fullName;
    @Email
    @NotBlank
    //@UniqueEmail
    private String email;

    @NotBlank
    @Size(min = 4, max = 20)
    private String password;

    @NotBlank
    @Size(min =4, max = 20)
    private String confirmPassword;
    public String recaptchaToken;
    public RegisterDto() {
    }

    public RegisterDto(RegisterDto map) {

    }

    public String getRecaptchaToken() {
        return recaptchaToken;
    }

    public RegisterDto setRecaptchaToken(String recaptchaToken) {
        this.recaptchaToken = recaptchaToken;
        return this;
    }

    public @NotBlank @Size(min = 2, max = 200) String getFullName() {
        return fullName;
    }

    public RegisterDto setFullName(@NotBlank @Size(min = 2, max = 200) String fullName) {
        this.fullName = fullName;
        return this;
    }

    public @NotBlank @Size(min = 3, max = 20) String getUsername() {
        return username;
    }

    public RegisterDto setUsername(@NotBlank @Size(min = 3, max = 20) String username) {
        this.username = username;
        return this;
    }

    public @Email @NotBlank String getEmail() {
        return email;
    }

    public RegisterDto setEmail(@Email @NotBlank String email) {
        this.email = email;
        return this;
    }

    public @NotBlank @Size(min = 4, max = 20) String getPassword() {
        return password;
    }

    public RegisterDto setPassword(@NotBlank @Size(min = 4, max = 20) String password) {
        this.password = password;
        return this;
    }

    public @NotBlank @Size(min = 4, max = 20) String getConfirmPassword() {
        return confirmPassword;
    }

    public RegisterDto setConfirmPassword(@NotBlank @Size(min = 4, max = 20) String confirmPassword) {
        this.confirmPassword = confirmPassword;
        return this;
    }
}
