package kamenov.springkamenovnatnature.service;

import kamenov.springkamenovnatnature.entity.UserEntity;
import kamenov.springkamenovnatnature.entity.dto.RegisterDto;
import kamenov.springkamenovnatnature.user.AppUserDetails;
import org.springframework.security.core.Authentication;

import java.util.Optional;
import java.util.function.Consumer;

public interface UserService {
    UserEntity registerUser(RegisterDto userRegisterDto,
                            Consumer<Authentication> successfulRegister);



    //UserEntity registerUser(UserEntity user);

    UserEntity findByName(String username);

    Optional<AppUserDetails> getCurrentUser();

    UserEntity findByEmail(String email);
}
