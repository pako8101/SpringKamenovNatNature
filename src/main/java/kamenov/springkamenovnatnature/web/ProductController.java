package kamenov.springkamenovnatnature.web;

import kamenov.springkamenovnatnature.entity.Product;
import kamenov.springkamenovnatnature.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@CrossOrigin(origins = "http://localhost:3001")
public class ProductController {


    private final ProductRepository productRepository;
    @Autowired
    public ProductController(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @GetMapping
    public List<Product> getProducts() {
        return productRepository.findAll();
    }
}
