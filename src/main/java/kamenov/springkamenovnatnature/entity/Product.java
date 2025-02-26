package kamenov.springkamenovnatnature.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.PositiveOrZero;

@Entity
@Table(name = "products")
public class Product extends BaseEntity {
    @Column(name = "name",nullable = false,unique = true)
    private String name;
    @Column(columnDefinition = "TEXT")
    private String description;
    @PositiveOrZero
    @Column(nullable = false)
    private double price;

    public Product() {
    }

    public String getName() {
        return name;
    }

    public Product setName(String name) {
        this.name = name;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public Product setDescription(String description) {
        this.description = description;
        return this;
    }

    public double getPrice() {
        return price;
    }

    public Product setPrice(double price) {
        this.price = price;
        return this;
    }
}
