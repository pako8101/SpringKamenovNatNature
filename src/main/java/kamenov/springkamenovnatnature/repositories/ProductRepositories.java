package kamenov.springkamenovnatnature.repositories;

import kamenov.springkamenovnatnature.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepositories extends JpaRepository<Product, Long> {
}
