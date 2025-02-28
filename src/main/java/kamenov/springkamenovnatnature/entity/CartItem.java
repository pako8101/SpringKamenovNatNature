package kamenov.springkamenovnatnature.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "cart_item")
public class CartItem extends BaseEntity{


    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;
@NotNull
    private int quantity;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    public CartItem() {
    }

    public Product getProduct() {
        return product;
    }

    public CartItem setProduct(Product product) {
        this.product = product;
        return this;
    }

    public int getQuantity() {
        return quantity;
    }

    public CartItem setQuantity(int quantity) {
        this.quantity = quantity;
        return this;
    }

    public UserEntity getUser() {
        return user;
    }

    public CartItem setUser(UserEntity user) {
        this.user = user;
        return this;
    }
}
