package com.ms.order.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

public class CartVo implements Serializable {

    private static final long serialVersionUID = 8969624409546284727L;

    List<CartItemVo> cartItemList;

    Integer productCount;

    BigDecimal totalAmount;

    public List<CartItemVo> getCartItemList() {
        return cartItemList;
    }

    public void setCartItemList(List<CartItemVo> cartItemList) {
        this.cartItemList = cartItemList;
    }

    public Integer getProductCount() {
        if (null != cartItemList && !cartItemList.isEmpty()) {
            for (CartItemVo cartItem : cartItemList) {
                productCount += cartItem.getCount();
            }
        }
        return productCount;
    }

    public BigDecimal getTotalAmount() {
        if (null != cartItemList && !cartItemList.isEmpty()) {
            for (CartItemVo cartItem : cartItemList) {
                totalAmount = totalAmount.add(cartItem.getTotalPrice());
            }
        }
        return totalAmount;
    }
}
