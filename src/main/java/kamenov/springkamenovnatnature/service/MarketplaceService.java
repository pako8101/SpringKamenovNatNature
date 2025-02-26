package kamenov.springkamenovnatnature.service;

import kamenov.springkamenovnatnature.entity.Product;

import java.util.List;

public interface MarketplaceService {
    List<Product> searchProducts(String name);
}
