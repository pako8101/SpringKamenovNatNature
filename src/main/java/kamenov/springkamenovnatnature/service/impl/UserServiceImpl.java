package kamenov.springkamenovnatnature.service.impl;

import kamenov.springkamenovnatnature.entity.UserEntity;
import kamenov.springkamenovnatnature.entity.dto.RegisterDto;
import kamenov.springkamenovnatnature.repositories.UserRepository;
import kamenov.springkamenovnatnature.service.UserService;
import kamenov.springkamenovnatnature.user.AppUserDetails;
import kamenov.springkamenovnatnature.user.LoggedUser;
import kamenov.springkamenovnatnature.web.AuthController;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.function.Consumer;

@Service
public class UserServiceImpl implements UserService {

    private final ModelMapper modelMapper;
    private final LoggedUser loggedUser;
    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    private final UserDetailsService userDetailsService;
    @Autowired
    private final UserRepository userRepository;

    @Autowired
    private final PasswordEncoder passwordEncoder;


    public UserServiceImpl(ModelMapper modelMapper, LoggedUser loggedUser, UserDetailsService userDetailsService, UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.modelMapper = modelMapper;
        this.loggedUser = loggedUser;
        this.userDetailsService = userDetailsService;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }
//    @Override
//    public UserEntity registerUser(UserEntity user) {
//        // Криптиране на паролата
//        user.setPassword(passwordEncoder.encode(user.getPassword()));
//        user.setConfirmPassword(passwordEncoder.encode(user.getPassword()));
//        return userRepository.save(user);
//    }
    @Override
    public UserEntity  registerUser(RegisterDto userRegisterDto,
                                    Consumer<Authentication> successfulRegister) {
        UserEntity user = modelMapper.map(userRegisterDto, UserEntity.class);
        user.setPassword(passwordEncoder.encode(userRegisterDto.getPassword()));
        user.setConfirmPassword(passwordEncoder.encode(user.getPassword()));
        // user.getRoles().add(roleService.findByName(UserRoleEnum.USER));
//        if (userRegisterDto.getImage() == null || Objects.equals(userRegisterDto.getImage().getOriginalFilename(), "")) {
//            user.setImage(profileImageService.getDefaultProfileImage());
//
//        }
//        user.setImage(profileImageService.saveProfileImage(userRegisterDto.
//                getImage(), user));


        user. setFullName(userRegisterDto.getFullName()).
                setEmail(userRegisterDto.getEmail()).
                setUsername(userRegisterDto.getUsername()).
                setPassword(passwordEncoder.encode(userRegisterDto.getPassword())

                );

        userRepository.save(user);

        UserDetails userDetails =
                userDetailsService.loadUserByUsername(userRegisterDto.getUsername());

        Authentication authentication = new UsernamePasswordAuthenticationToken(
                userDetails,
                userDetails.getPassword(),
                userDetails.getAuthorities()
        );

        successfulRegister.accept(authentication);
        return user;
    }

//    @Override
//    public UserViewModel getUserProfile() {
//        UserEntity user = loggedUser.get();
//        return modelMapper.map(user,UserViewModel.class);
//    }
//
//    @Override
//    public UserViewModel findBId(Long id) {
//        return userRepository.findById(id).
//                map(user -> modelMapper.map(user,UserViewModel.class))
//                .orElse(null);
////        UserEntity user = loggedUser.get();
////        user = userRepository.findById(id).orElseThrow(NoSuchElementException::new);
////        return modelMapper.map(user,UserViewModel.class);
//    }

    @Override
    public UserEntity findByName(String username) {
        return userRepository.findByUsername(username)
                .orElse(null);
    }

    @Override
    public Optional<AppUserDetails> getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if ( authentication != null &&
                authentication.getPrincipal()
                        instanceof AppUserDetails appUserDetails) {
            return Optional.of(appUserDetails);
        }

        return Optional.empty();
    }

    @Override
    public UserEntity findByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElse(null);
    }
}
