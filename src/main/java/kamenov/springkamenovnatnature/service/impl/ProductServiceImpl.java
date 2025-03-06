package kamenov.springkamenovnatnature.service.impl;

import kamenov.springkamenovnatnature.entity.Product;
import kamenov.springkamenovnatnature.repositories.ProductRepository;
import kamenov.springkamenovnatnature.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {



        private final ProductRepository productRepository;
    @Autowired
    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }
@Override
    public List<Product> getAllProducts() {
            return productRepository.findAll();
        }
@Override
        public Product addProduct(Product product) {
            return productRepository.save(product);
        }
@Override
        public void deleteProduct(Long id) {
            productRepository.deleteById(id);
        }

}
