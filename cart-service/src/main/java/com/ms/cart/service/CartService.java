package com.ms.cart.service;

import com.ms.cart.vo.Cart;
import com.ms.cart.vo.CartItem;

import java.util.concurrent.ExecutionException;

public interface CartService {

    Boolean addProduct(Long productId, Integer quantity) throws ExecutionException, InterruptedException;

    CartItem queryProduct(Long productId);

    Cart queryCart();

    Boolean checkProduct(Long productId, Integer status);

    void clearCart();

    void updateProduct(Long productId, Integer quantity);

    Long deleteProduct(Long productId);
}
