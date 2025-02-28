package kamenov.springkamenovnatnature.validation.uniqueEmail;


import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import kamenov.springkamenovnatnature.repositories.UserRepository;

public class UniqueEmailValidator implements ConstraintValidator<UniqueEmail, String> {

    private final UserRepository userRepository;
    private String message;

    public UniqueEmailValidator(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void initialize(UniqueEmail constraintAnnotation) {
        this.message = constraintAnnotation.message();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return userRepository.findByEmail(value).isEmpty();
    }
}
