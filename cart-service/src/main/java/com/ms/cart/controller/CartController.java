package com.ms.cart.controller;

import com.ms.cart.service.CartService;
import com.ms.cart.vo.Cart;
import com.ms.cart.vo.CartItem;
import com.ms.common.api.Response;
import com.ms.common.enums.BizStatusCode;
import com.ms.common.enums.SysExceptionCode;
import com.ms.common.exception.SysException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

@Api(tags = "购物车接口")
@Slf4j
@RestController
@RequestMapping("/cart")
public class CartController {

    @Resource
    private CartService cartService;

    @ApiOperation(value = "查询购物车")
    @GetMapping("/queryCart")
    public Response<Cart> queryCart() {
        Cart cart = cartService.queryCart();
        return Response.SUCCESS(cart);
    }

    @ApiOperation(value = "查询选中的购物项")
    @GetMapping("/queryCheckedItem")
    public Response<Cart> queryCheckedItem() {
        Cart cart = cartService.queryCart();
        cart.setCartItemList(cart.getCartItemList().stream().filter(CartItem::getChecked).collect(Collectors.toList()));
        return Response.SUCCESS(cart);
    }

    @ApiOperation(value = "根据商品ID查询购物项")
    @GetMapping("/query/{productId}")
    public Response<CartItem> queryProduct(@PathVariable("productId") Long productId) {
        CartItem cartItem = cartService.queryProduct(productId);
        return Response.SUCCESS(cartItem);
    }

    @ApiOperation(value = "添加购物项")
    @PostMapping("/add")
    public Response addProduct(@RequestParam("productId") Long productId,
                               @RequestParam("quantity") Integer quantity) {
        Boolean res;
        try {
            res = cartService.addProduct(productId, quantity);
        } catch (ExecutionException | InterruptedException e) {
            throw new RuntimeException(e.getMessage());
        }
        if (Boolean.TRUE.equals(res)) {
            return Response.SUCCESS(BizStatusCode.GOODS_ADD_TO_CART);
        } else {
            return Response.ERROR(BizStatusCode.CART_ITEM_ALREADY_EXIST);
        }
    }

    @ApiOperation(value = "修改购物项的选中状态")
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

    @ApiOperation(value = "修改购物项的购买数量")
    @PutMapping("/update")
    public Response updateProduct(@RequestParam("productId") Long productId,
                                  @RequestParam("quantity") Integer quantity) {
        cartService.updateProduct(productId, quantity);
        return Response.SUCCESS(BizStatusCode.SUCCESS);
    }

    @ApiOperation(value = "删除购物项")
    @DeleteMapping("/delete")
    public Response deleteProduct(@RequestParam("productId") Long productId) {
        Long deleted = cartService.deleteProduct(productId);
        if (deleted > 0) {
            return Response.SUCCESS(BizStatusCode.SUCCESS);
        } else {
            return Response.ERROR(BizStatusCode.CART_ITEM_NOT_EXIST);
        }
    }

    @ApiOperation(value = "清空购物车")
    @DeleteMapping("/clear")
    public Response clearCart() {
        cartService.clearCart();
        return Response.SUCCESS(BizStatusCode.SUCCESS);
    }

    @ApiOperation(value = "查询购物车的商品数量")
    @GetMapping("/quantity")
    public Response<Integer> queryQuantity() {
        return null;
    }

    @ApiOperation(value = "结算购物车")
    @PostMapping("/settle")
    public Response settle(@RequestParam("productIds") List<Long> productIds) {
        // TODO
        return null;
    }
}
