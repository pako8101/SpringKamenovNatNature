package kamenov.springkamenovnatnature.web;

import kamenov.springkamenovnatnature.entity.RecyclingCenter;
import kamenov.springkamenovnatnature.service.RecyclingCenterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/recycling")
@CrossOrigin(origins = "http://localhost:3000")
public class RecyclingCenterController {
    @Autowired
    private final RecyclingCenterService service;

    public RecyclingCenterController(RecyclingCenterService service) {
        this.service = service;
    }

    @GetMapping("/centers")
    public List<RecyclingCenter> getCentersByCity(@RequestParam String city) {
        return service.getCentersByCity(city);
    }
}




