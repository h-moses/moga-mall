package com.ms.cart.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

public class CartItem implements Serializable {
    private static final long serialVersionUID = 4881536341159583647L;

    Long skuId;

    Boolean checked;

    String title;

    String image;

    List<String> skuAttr;

    BigDecimal price;

    Integer count;

    BigDecimal totalPrice;

    public Long getSkuId() {
        return skuId;
    }

    public void setSkuId(Long skuId) {
        this.skuId = skuId;
    }

    public Boolean getChecked() {
        return checked;
    }

    public void setChecked(Boolean checked) {
        this.checked = checked;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public List<String> getSkuAttr() {
        return skuAttr;
    }

    public void setSkuAttr(List<String> skuAttr) {
        this.skuAttr = skuAttr;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public BigDecimal getTotalPrice() {
        return new BigDecimal(String.valueOf(price.multiply(new BigDecimal(count))));
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    @Override
    public String toString() {
        return "CartItem{" +
                "skuId=" + skuId +
                ", checked=" + checked +
                ", title='" + title + '\'' +
                ", image='" + image + '\'' +
                ", skuAttr=" + skuAttr +
                ", price=" + price +
                ", count=" + count +
                ", totalPrice=" + totalPrice +
                '}';
    }
}
