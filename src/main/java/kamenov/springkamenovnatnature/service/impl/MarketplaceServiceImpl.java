package kamenov.springkamenovnatnature.service.impl;

import kamenov.springkamenovnatnature.entity.Product;
import kamenov.springkamenovnatnature.repositories.MarketplaceRepository;
import kamenov.springkamenovnatnature.service.MarketplaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MarketplaceServiceImpl implements MarketplaceService {
    @Autowired
    private final MarketplaceRepository repository;

    public MarketplaceServiceImpl(MarketplaceRepository repository) {
        this.repository = repository;
    }
@Override
    public List<Product> searchProducts(String name) {
        return repository.findByNameContaining(name);
    }
}
