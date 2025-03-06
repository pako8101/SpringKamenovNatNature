package kamenov.springkamenovnatnature.service;

import kamenov.springkamenovnatnature.entity.Product;

import java.util.List;

public interface ProductService {
    List<Product> getAllProducts();

    Product addProduct(Product product);

    void deleteProduct(Long id);
}
