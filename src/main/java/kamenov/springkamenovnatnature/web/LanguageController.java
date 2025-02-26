package kamenov.springkamenovnatnature.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.web.bind.annotation.*;

import java.util.Locale;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:3001")
public class LanguageController {

    @Autowired
    private MessageSource messageSource;

    @GetMapping("/welcome")
    public String getWelcomeMessage(@RequestHeader(name = "Accept-Language", required = false) Locale locale) {
        return messageSource.getMessage("welcome", null, locale);
    }
}
