package kamenov.springkamenovnatnature.web;

import kamenov.springkamenovnatnature.entity.Product;
import kamenov.springkamenovnatnature.service.MarketplaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/marketplace")
@CrossOrigin(origins = "http://localhost:3001")
public class MarketplaceController {
    @Autowired
    private final MarketplaceService service;

    public MarketplaceController(MarketplaceService service) {
        this.service = service;
    }

    @GetMapping("/products")
    public List<Product> searchProducts(@RequestParam String name) {
        return service.searchProducts(name);
    }
}
