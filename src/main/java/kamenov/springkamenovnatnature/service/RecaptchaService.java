package kamenov.springkamenovnatnature.service;

public interface RecaptchaService {
    boolean validateRecaptcha(String recaptchaToken);
}
