package com.ms.cart.service.impl;

import com.alibaba.fastjson.JSON;
import com.ms.cart.feign.ProductFeign;
import com.ms.cart.interceptor.CartInterceptor;
import com.ms.cart.service.CartService;
import com.ms.cart.vo.Cart;
import com.ms.cart.vo.CartItem;
import com.ms.cart.vo.SkuInfo;
import com.ms.common.api.Response;
import com.ms.common.enums.BizStatusCode;
import com.ms.common.exception.BizException;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.stream.Collectors;

@Service
public class CartServiceImpl implements CartService {

    private static final String CART_PREFIX = "cart::";

    @Resource
    StringRedisTemplate redisTemplate;

    @Resource
    ProductFeign productFeign;

    @Resource
    ThreadPoolExecutor threadPoolExecutor;

    @Override
    public Boolean addProduct(Long productId, Integer quantity) throws ExecutionException, InterruptedException {
        String claim = CartInterceptor.THREAD_LOCAL_CART.get();
        if (StringUtils.hasText(claim)) {
            BoundHashOperations<String, Object, Object> operations = getHashOpsByKey();

            String product = (String) operations.get(productId.toString());
            if (StringUtils.hasText(product)) {
                CartItem cartItem = JSON.parseObject(product, CartItem.class);
                cartItem.setCount(cartItem.getCount() + quantity);
                operations.put(productId.toString(), JSON.toJSONString(cartItem));
            } else {
                final CartItem cartItem = new CartItem();
                // 远程查询商品信息
                CompletableFuture<Void> infoFuture = CompletableFuture.runAsync(() -> {
                    Response<SkuInfo> skuInfoResponse = productFeign.querySkuInfo(productId);
                    SkuInfo skuInfo = skuInfoResponse.getData();

                    cartItem.setSkuId(productId);
                    cartItem.setTitle(skuInfo.getSkuTitle());
                    cartItem.setImage(skuInfo.getSkuDefaultImg());
                    cartItem.setCount(1);
                    cartItem.setChecked(true);
                    cartItem.setPrice(skuInfo.getPrice());
                }, threadPoolExecutor);

                // 远程查询商品的组合信息
                CompletableFuture<Void> saleFuture = CompletableFuture.runAsync(() -> {
                    Response<List<String>> saleAttrResponse = productFeign.querySkuSaleAttr(productId);
                    cartItem.setSkuAttr(saleAttrResponse.getData());
                }, threadPoolExecutor);

                CompletableFuture<Void> future = CompletableFuture.allOf(infoFuture, saleFuture).thenRun(() -> {
                    operations.put(productId.toString(), JSON.toJSONString(cartItem));
                });

                future.get();
            }

            return Boolean.TRUE;
        }

        return Boolean.FALSE;
    }

    @Override
    public CartItem queryProduct(Long productId) {
        BoundHashOperations<String, Object, Object> operations = getHashOpsByKey();
        String data = (String) operations.get(productId.toString());
        return JSON.parseObject(data, CartItem.class);
    }

    /**
     * 查询购物车物品，不提供未登录时的购物车
     * @return 购物车详细信息
     */
    @Override
    public Cart queryCart() {
        Cart cart = new Cart();
        List<CartItem> cartItems = queryCartItem();
        cart.setCartItemList(cartItems);
        // 避免出现转换错误
        cart.setProductCount(0);
        cart.setTotalAmount(BigDecimal.valueOf(0));
        return cart;
    }

    @Override
    public Boolean checkProduct(Long productId, Integer status) {
        BoundHashOperations<String, Object, Object> operations = getHashOpsByKey();
        CartItem cartItem = queryProduct(productId);
        cartItem.setChecked(status == 1);
        operations.put(productId, JSON.toJSONString(cartItem));
        return Boolean.FALSE;
    }

    @Override
    public void clearCart() {
        String claim = CartInterceptor.THREAD_LOCAL_CART.get();
        String cartKey = CART_PREFIX + claim;
        redisTemplate.delete(cartKey);
    }

    @Override
    public void updateProduct(Long productId, Integer quantity) {
        CartItem cartItem = queryProduct(productId);
        cartItem.setCount(quantity);
        BoundHashOperations<String, Object, Object> operations = getHashOpsByKey();
        operations.put(productId.toString(), JSON.toJSONString(cartItem));
    }

    @Override
    public Long deleteProduct(Long productId) {
        CartItem cartItem = queryProduct(productId);
        if (null == cartItem) {
            throw new BizException(BizStatusCode.CART_ITEM_NOT_EXIST);
        }
        BoundHashOperations<String, Object, Object> operations = getHashOpsByKey();
        return operations.delete(productId.toString());
    }

    private List<CartItem> queryCartItem() {
        BoundHashOperations<String, Object, Object> operations = getHashOpsByKey();
        List<Object> values = operations.values();
        if (null != values && !values.isEmpty()) {
            return values.stream().map(obj -> {
                String item = (String) obj;
                return JSON.parseObject(item, CartItem.class);
            }).collect(Collectors.toList());
        }
        return null;
    }

    private BoundHashOperations<String, Object, Object> getHashOpsByKey() {
        String claim = CartInterceptor.THREAD_LOCAL_CART.get();
        String cartKey = CART_PREFIX + claim;
        return redisTemplate.boundHashOps(cartKey);
    }
}
