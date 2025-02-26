package kamenov.springkamenovnatnature.repositories;

import kamenov.springkamenovnatnature.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MarketplaceRepository extends JpaRepository<Product,Long> {

        List<Product> findByNameContaining(String name);

}
