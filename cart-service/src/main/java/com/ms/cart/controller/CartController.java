package com.ms.cart.controller;

import com.ms.cart.service.CartService;
import com.ms.cart.vo.Cart;
import com.ms.cart.vo.CartItem;
import com.ms.common.api.Response;
import com.ms.common.enums.BizStatusCode;
import com.ms.common.enums.SysExceptionCode;
import com.ms.common.exception.SysException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.ExecutionException;

@Slf4j
@RestController
@RequestMapping("/cart")
public class CartController {

    @Resource
    private CartService cartService;

    @GetMapping("/queryCart")
    public Response<Cart> queryCart() {
        Cart cart = cartService.queryCart();
        return Response.SUCCESS(cart);
    }

    @GetMapping("/query/{productId}")
    public Response<CartItem> queryProduct(@PathVariable("productId") Long productId) {
        CartItem cartItem = cartService.queryProduct(productId);
        return Response.SUCCESS(cartItem);
    }

    @PostMapping("/add")
    public Response addProduct(@RequestParam("productId") Long productId,
                               @RequestParam("quantity") Integer quantity) {
        Boolean res;
        try {
            res = cartService.addProduct(productId, quantity);
        } catch (ExecutionException | InterruptedException e) {
            throw new SysException(SysExceptionCode.PRODUCT_ADD_TO_CART_ERROR);
        }
        if (Boolean.TRUE.equals(res)) {
            return Response.SUCCESS(BizStatusCode.GOODS_ADD_TO_CART);
        } else {
            return Response.ERROR(BizStatusCode.CART_ITEM_ALREADY_EXIST);
        }
    }

    @PutMapping("/check")
    public Response checkProduct(@RequestParam("productId") Long productId,
                                  @RequestParam("status") Integer status) {
        Boolean res = cartService.checkProduct(productId, status);
        if (Boolean.TRUE.equals(res)) {
            return Response.SUCCESS(BizStatusCode.SUCCESS);
        } else {
            return Response.ERROR(BizStatusCode.GOODS_CHECKED_INVALID);
        }
    }

    @PutMapping("/update")
    public Response updateProduct(@RequestParam("productId") Long productId,
                                  @RequestParam("quantity") Integer quantity) {
        cartService.updateProduct(productId, quantity);
        return Response.SUCCESS(BizStatusCode.SUCCESS);
    }

    @DeleteMapping("/delete")
    public Response deleteProduct(@RequestParam("productId") Long productId) {
        Long deleted = cartService.deleteProduct(productId);
        if (deleted > 0) {
            return Response.SUCCESS(BizStatusCode.SUCCESS);
        } else {
            return Response.ERROR(BizStatusCode.CART_ITEM_NOT_EXIST);
        }
    }

    @DeleteMapping("/clear")
    public Response clearCart() {
        cartService.clearCart();
        return Response.SUCCESS(BizStatusCode.SUCCESS);
    }

    @GetMapping("/quantity")
    public Response<Integer> queryQuantity() {
        return null;
    }

    @PostMapping("/settle")
    public Response settle(@RequestParam("productIds") List<Long> productIds) {
        // TODO
        return null;
    }
}
