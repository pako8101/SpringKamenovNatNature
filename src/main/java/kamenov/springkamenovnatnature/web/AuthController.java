package kamenov.springkamenovnatnature.web;


import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import kamenov.springkamenovnatnature.entity.UserEntity;
import kamenov.springkamenovnatnature.entity.dto.AuthResponse;
import kamenov.springkamenovnatnature.entity.dto.LoginDto;
import kamenov.springkamenovnatnature.entity.dto.RegisterDto;
import kamenov.springkamenovnatnature.repositories.UserRepository;
import kamenov.springkamenovnatnature.service.JwtService;
import kamenov.springkamenovnatnature.service.RecaptchaService;
import kamenov.springkamenovnatnature.service.UserService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextHolderStrategy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:3000")
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final UserRepository userRepository;
    private final SecurityContextRepository securityContextRepository;
    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);
    private final RecaptchaService recaptchaService;
    private final ModelMapper modelMapper;
    private final JwtService jwtService;

    @Autowired
    public AuthController(AuthenticationManager authenticationManager, UserService userService, UserRepository userRepository, SecurityContextRepository securityContextRepository, RecaptchaService recaptchaService, ModelMapper modelMapper, JwtService jwtService) {
        this.authenticationManager = authenticationManager;
        this.userService = userService;
        this.userRepository = userRepository;
        this.securityContextRepository = securityContextRepository;
        this.recaptchaService = recaptchaService;
        this.modelMapper = modelMapper;
        this.jwtService = jwtService;
    }


//    @PostMapping("/register")
//    public ResponseEntity<?> registerUser(@Valid @RequestBody RegisterDto request) {
//        // Валидиране на reCAPTCHA
//        boolean recaptchaValid = recaptchaService.validateRecaptcha(request.recaptchaToken);
//        if (!recaptchaValid) {
//            return ResponseEntity.badRequest().body("Invalid reCAPTCHA");
//        }
//        // Проверка дали потребителят съществува (може да се добави и проверка за email)
//        if (userService.findByName(request.getUsername()) != null) {
//            return ResponseEntity.badRequest().body("Username already exists");
//        }
//        if (userService.findByEmail(request.getEmail()) != null) {
//            return ResponseEntity.badRequest().body("Username already exists");
//        }
//        UserEntity newUser = new UserEntity(request.getUsername(),
//                request.getFullName(),
//                request.getEmail(),
//                request.getPassword(),
//                request.getConfirmPassword());
//
//        userService.registerUser(newUser);
//
//        return ResponseEntity.ok("User registered successfully");
//    }

    @GetMapping("/me")
    public ResponseEntity<?> getCurrentUser(@AuthenticationPrincipal UserDetails userDetails) {
        if (userDetails == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not authenticated");
        }
        Map<String, String> response = new HashMap<>();
        response.put("username", userDetails.getUsername());
        // Добавяне на ролята от authorities
        String role = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .findFirst()
                .orElse("USER"); // По подразбиране "USER", ако няма роли
        response.put("role", role.replace("ROLE_", "")); // Премахва "ROLE_" префикса, ако има
        return ResponseEntity.ok(response);
    }

//@GetMapping("/me")
//public ResponseEntity<?> getCurrentUser(@AuthenticationPrincipal UserDetails userDetails) {
//    if (userDetails == null) {
//        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not authenticated");
//    }

//    Map<String, String> response = new HashMap<>();
//    response.put("username", userDetails.getUsername());
//    return ResponseEntity.ok(response);
//}
    @PostMapping("/register")
    public ResponseEntity<?> registerPost(@Valid @RequestBody RegisterDto userRegisterDto,
                               HttpServletRequest request,
                               HttpServletResponse response,
                               Model model,
                               BindingResult bindingResult,
                               RedirectAttributes redirectAttributes) {
        if (userService.findByName(userRegisterDto.getUsername()) != null) {
            return ResponseEntity.badRequest().body("Username already exists");
        }
        if (userService.findByEmail(userRegisterDto.getEmail()) != null) {
            return ResponseEntity.badRequest().body("Username already exists");
        }

        if (bindingResult.hasErrors() || !userRegisterDto.getPassword()
                .equals(userRegisterDto.getConfirmPassword())) {

            redirectAttributes.addFlashAttribute("registerDto",
                    userRegisterDto);
            redirectAttributes.addFlashAttribute(
                    "org.springframework.validation.BindingResult." +
                            "registerDto", bindingResult);

            return ResponseEntity.badRequest().body("Wrong password mismatch");
        }

        boolean recaptchaValid = recaptchaService.validateRecaptcha(userRegisterDto.recaptchaToken);
        if (!recaptchaValid) {
            return ResponseEntity.badRequest().body("Invalid reCAPTCHA");
        }
        UserEntity user =
                userService.registerUser(userRegisterDto, successfulAuth -> {
                    SecurityContextHolderStrategy strategy = SecurityContextHolder.getContextHolderStrategy();

                    SecurityContext context = strategy.createEmptyContext();
                    context.setAuthentication(successfulAuth);

                    strategy.setContext(context);
                    securityContextRepository.saveContext(context, request, response);

                });
        Cookie cookie = new Cookie("jwt", jwtService.generateToken(user));
        cookie.setPath("/");
        cookie.setMaxAge(60 * 60 * 24);
        cookie.setHttpOnly(true);
        response.addCookie(cookie);
        String token = jwtService.generateToken(user);
        // Връщане на токена в JSON
        Map<String, String> responseBody = new HashMap<>();
        responseBody.put("token", token);
        responseBody.put("username", user.getUsername());
        return ResponseEntity.ok(responseBody);
//        model.addAttribute("message", "Registration successful");
//
//        return ResponseEntity.ok("User registered successfully");
    }

    @ModelAttribute
    public LoginDto loginDto() {
        return new LoginDto();
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@Valid @RequestBody LoginDto request) {
        try {
            Authentication auth = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
            );
            UserDetails userDetails = (UserDetails) auth.getPrincipal();
            String token = jwtService.generateToken(request.getUsername());
            Map<String, String> response = new HashMap<>();
            response.put("token", token);
            response.put("username", request.getUsername().toString());
            String role = userDetails.getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority)
                    .findFirst()
                    .orElse("USER");
            response.put("role", role.replace("ROLE_", ""));
            return ResponseEntity.ok(response);
        } catch (AuthenticationException e) {
            return ResponseEntity.status(401).body("Incorrect username or password");
        }
    }

//    @PostMapping("/login")
//    public ResponseEntity<?> loginUser(@Valid @RequestBody LoginDto request) {
//        try {
//            authenticationManager.authenticate(
//                    new UsernamePasswordAuthenticationToken(request.getUsername(),
//                            request.getPassword())
//            );
//        } catch (AuthenticationException e) {
//            return ResponseEntity.status(401).body("Incorrect username or password");
//        }
//        String token = jwtService.generateToken(request.getUsername());
//        return ResponseEntity.ok(new AuthResponse(token, request.getUsername()));
//    }

    @PostMapping("/login-error")
    public String onFailedLogin(@ModelAttribute(UsernamePasswordAuthenticationFilter
            .SPRING_SECURITY_FORM_USERNAME_KEY) String username,
                                RedirectAttributes redirectAttributes) {

        redirectAttributes.addFlashAttribute(UsernamePasswordAuthenticationFilter.SPRING_SECURITY_FORM_USERNAME_KEY, username);
        redirectAttributes.addFlashAttribute("bad_credentials", true);


//        redirectAttributes.addFlashAttribute("username", username);
        return "redirect:/api/auth/login";
//        if (userServiceModel == null) {
//            redirectAttributes.addFlashAttribute("userLoginBindingModel", userLoginBindingModel());
//            redirectAttributes.addFlashAttribute("isFound", false);
//            return "redirect:login";
//        }

    }


}
