package com.ms.cart.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

public class Cart implements Serializable {
    private static final long serialVersionUID = 8969624409546284727L;

    List<CartItem> cartItemList;

    Integer productCount;

    BigDecimal totalAmount;

    public List<CartItem> getCartItemList() {
        return cartItemList;
    }

    public void setCartItemList(List<CartItem> cartItemList) {
        this.cartItemList = cartItemList;
    }

    public void setProductCount(Integer productCount) {
        this.productCount = productCount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Integer getProductCount() {
        if (null != cartItemList && !cartItemList.isEmpty()) {
            for (CartItem cartItem : cartItemList) {
                productCount += cartItem.getCount();
            }
        }
        return productCount;
    }

    public BigDecimal getTotalAmount() {
        if (null != cartItemList && !cartItemList.isEmpty()) {
            for (CartItem cartItem : cartItemList) {
                totalAmount = totalAmount.add(cartItem.getTotalPrice());
            }
        }
        return totalAmount;
    }
}
