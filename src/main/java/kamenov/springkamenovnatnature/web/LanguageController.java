package kamenov.springkamenovnatnature.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.web.bind.annotation.*;

import java.util.Locale;

@RestController
@RequestMapping("/auth/api")
@CrossOrigin(origins = "http://localhost:3000")
public class LanguageController {

    @Autowired
    private MessageSource messageSource;

    @GetMapping("/welcome")
    public String getWelcomeMessage(@RequestHeader(name = "Accept-Language", required = false) Locale locale) {
        return messageSource.getMessage("welcome", null, locale);
    }
//    @CrossOrigin(origins = "http://localhost:3001")
//    @GetMapping("/api/welcome")
//    public String welcome(@RequestHeader("Accept-Language") String language) {
//        if ("bg".equals(language)) {
//            return "Добре дошъл!";
//        }
//        return "Welcome!";
//    }
}
