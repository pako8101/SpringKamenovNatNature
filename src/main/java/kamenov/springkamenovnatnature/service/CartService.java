package kamenov.springkamenovnatnature.service;

import kamenov.springkamenovnatnature.entity.CartItem;
import kamenov.springkamenovnatnature.entity.UserEntity;

import java.util.List;

public interface CartService {
    List<CartItem> getCartItems(UserEntity user);

    void addToCart(Long productId, int quantity, UserEntity user);

    void removeFromCart(Long cartItemId, UserEntity user);

    void clearCart(UserEntity user);
}
