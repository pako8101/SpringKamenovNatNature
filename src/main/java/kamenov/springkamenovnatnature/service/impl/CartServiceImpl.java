package kamenov.springkamenovnatnature.service.impl;

import kamenov.springkamenovnatnature.entity.CartItem;
import kamenov.springkamenovnatnature.entity.Product;
import kamenov.springkamenovnatnature.entity.UserEntity;
import kamenov.springkamenovnatnature.repositories.CartItemRepository;
import kamenov.springkamenovnatnature.repositories.ProductRepository;
import kamenov.springkamenovnatnature.repositories.UserRepository;
import kamenov.springkamenovnatnature.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartServiceImpl implements CartService {

        private final CartItemRepository cartItemRepository;
        private final ProductRepository productRepository;
        private final UserRepository userRepository;
    @Autowired
    public CartServiceImpl(CartItemRepository cartItemRepository, ProductRepository productRepository, UserRepository userRepository) {
        this.cartItemRepository = cartItemRepository;
        this.productRepository = productRepository;
        this.userRepository = userRepository;
    }
@Override
    public List<CartItem> getCartItems(UserEntity user) {
            return cartItemRepository.findByUser(user);
        }
    @Override
        public void addToCart(Long productId, int quantity, UserEntity user) {
            Product product = productRepository.findById(productId)
                    .orElseThrow(() -> new RuntimeException("Product not found"));
            CartItem cartItem = cartItemRepository.findByProductAndUser(product, user).get(1);

            cartItem.setProduct(product);
            cartItem.setQuantity(cartItem.getQuantity() + quantity);
            cartItem.setUser(user);
            cartItemRepository.save(cartItem);
        }
    @Override
        public void removeFromCart(Long cartItemId, UserEntity user) {
            CartItem cartItem = cartItemRepository.findById(cartItemId)
                    .orElseThrow(() -> new RuntimeException("Cart item not found"));
            if (cartItem.getUser().equals(user)) {
                cartItemRepository.delete(cartItem);
            }
        }
@Override
        public void clearCart(UserEntity user) {
            cartItemRepository.deleteByUser(user);
        }
    }


