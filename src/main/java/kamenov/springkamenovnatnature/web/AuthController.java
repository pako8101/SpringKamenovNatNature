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
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextHolderStrategy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:3001")
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

//    @GetMapping("/register")
//    public String registerForm(Model model) {
//        if (!model.containsAttribute("userRegisterDto")) {
//            model.addAttribute("userRegisterDto", new RegisterDto());
//        }
//        //  model.addAttribute("recaptchaSiteKey", recaptchaSiteKey);
//        return "register";
//    }
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody RegisterDto request) {
        // Валидиране на reCAPTCHA
        boolean recaptchaValid = recaptchaService.validateRecaptcha(request.recaptchaToken);
        if (!recaptchaValid) {
            return ResponseEntity.badRequest().body("Invalid reCAPTCHA");
        }
        // Проверка дали потребителят съществува (може да се добави и проверка за email)
        if (userService.findByName(request.getUsername()) != null) {
            return ResponseEntity.badRequest().body("Username already exists");
        }
        if (userService.findByEmail(request.getEmail()) != null) {
            return ResponseEntity.badRequest().body("Username already exists");
        }
        UserEntity newUser = new UserEntity(request.getUsername(),
                request.getFullName(),
                request.getEmail(),
                request.getPassword(),
                request.getConfirmPassword());

        userService.registerUser(newUser);

        return ResponseEntity.ok("User registered successfully");
    }

//    @PostMapping("/register")
//    public ResponseEntity<?> registerPost(@Valid @RequestBody RegisterDto userRegisterDto,
//                               HttpServletRequest request,
//                               HttpServletResponse response,
//                               Model model,
//                               BindingResult bindingResult,
//                               RedirectAttributes redirectAttributes) {
//        if (userService.findByName(userRegisterDto.getUsername()) != null) {
//            return ResponseEntity.badRequest().body("Username already exists");
//        }
//        if (userService.findByEmail(userRegisterDto.getEmail()) != null) {
//            return ResponseEntity.badRequest().body("Username already exists");
//        }
//
//        if (bindingResult.hasErrors() || !userRegisterDto.getPassword()
//                .equals(userRegisterDto.getConfirmPassword())) {
//
//            redirectAttributes.addFlashAttribute("registerDto",
//                    userRegisterDto);
//            redirectAttributes.addFlashAttribute(
//                    "org.springframework.validation.BindingResult." +
//                            "registerDto", bindingResult);
//
//            return ResponseEntity.badRequest().body("Wrong password mismatch");
//        }
////        if (!recaptchaService.validateRecaptcha(request.getRecaptchaToken())) {
////            return ResponseEntity.badRequest().body("Невалидна reCAPTCHA!");
////        }
//        boolean recaptchaValid = recaptchaService.validateRecaptcha(userRegisterDto.recaptchaToken);
//        if (!recaptchaValid) {
//            return ResponseEntity.badRequest().body("Invalid reCAPTCHA");
//        }
//        UserEntity user =
//                userService.registerUser(userRegisterDto, successfulAuth -> {
//                    SecurityContextHolderStrategy strategy = SecurityContextHolder.getContextHolderStrategy();
//
//                    SecurityContext context = strategy.createEmptyContext();
//                    context.setAuthentication(successfulAuth);
//
//                    strategy.setContext(context);
//                    securityContextRepository.saveContext(context, request, response);
//
//                });
//        Cookie cookie = new Cookie("jwt", jwtService.generateToken(user));
//        cookie.setPath("/");
//        cookie.setMaxAge(60 * 60 * 24);
//        cookie.setHttpOnly(true);
//        response.addCookie(cookie);
//
//        model.addAttribute("message", "Registration successful");
//
//        return ResponseEntity.ok("User registered successfully");
//    }

    @ModelAttribute
    public LoginDto loginDto() {
        return new LoginDto();
    }

    @GetMapping("/login")
    public String login(Model model) {
        if (!model.containsAttribute("isFound")) {
            model.addAttribute("isFound", true);
        }

        return "login";
    }
    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@Valid @RequestBody LoginDto request) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getUsername(),
                            request.getPassword())
            );
        } catch (AuthenticationException e) {
            return ResponseEntity.status(401).body("Incorrect username or password");
        }
        String token = jwtService.generateToken(request.getUsername());
        return ResponseEntity.ok(new AuthResponse(token, request.getUsername()));
    }

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
