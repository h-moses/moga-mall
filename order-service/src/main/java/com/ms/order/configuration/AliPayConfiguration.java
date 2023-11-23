package com.ms.order.configuration;

import com.alipay.easysdk.factory.Factory;
import com.alipay.easysdk.kernel.Config;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Slf4j
@Data
@Component
public class AliPayConfiguration {

    // 商户appid
    public static String APPID = "9021000129611901";
    // 私钥 pkcs8格式的
    public static String RSA_PRIVATE_KEY = "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQCO9gvjA+lQlXk1ejCRhyQUFJ/LqJJl5rjRD7QefpV1bBM7+34PF77KSGJ7vFb2GKxpxLcU5/uOnIk/MyhDXulapI77dP0fxWmeYjuJRwhjuiM6GvGVS1V+s2/BuSFJdQaAy1OlfYKY11D0KCCTyilgNxDqIiV4oCA0V1ToH/zge4FFnvMiQ4BlpskqhEy5dNJ1+cRCcHNO4tJ+FfEBrd+sjYF5E912YaaSWDNjSXGhQbYPw3Z9wm32T6qtXiNO6AZkDlXHIvIsanP2IRxjffLaYkNEBdj9SNagMFnCOHlr95G6d1DeI6jqaCG8FaJMvLl2OAkki+blRZ/7kCXpKQAPAgMBAAECggEAE0L3awjV4LOi9x+Vd6YbqFD3DruRrgUeFZzmCc3IWV/MX6KM43+xhxOloUJI/TNp135XpIt1C0vv+gHshNc+3aWaVir3B9bTWjGmD+LwMG+d23BR6GmUBVQr1JAa+gi4bZX3mS6F8A3irGLTsNXmKT6CGumq3svESwkROY1FxwDpZFr/NWtMWRsTLQ7Qv4vfanye/NNVakFUk5aZdoX/wKPiUPUxmKjw9AJTpVoANlf2FYwjTTlk8vgu5imhdHvjyDHJ3JlDH8anpzcE6c9WOtoLYOdZwkJPN9EYE9ywn+U9Bf/F9Iva8KjWn2pQ7THPPZY4/Oa2V3wd4kHCcVBuIQKBgQDDCwqVhHDScsMuhGIHvwNi9Zu0LiPTeffp1jq1M69YJY14jmQzGE/9F7yzfprTcKr1DGafl4z54uNyboCKwNXndq6NnXo1t16W671koKK8WMuOGN1oEVcHpU/HsWgobvCDkbS6M76YdWDfuOXQoQTDm+DjaKi4TLWB63mNfoO0NwKBgQC7pAqbpcCJheVHCC9v/fZOrAdGmbYmBNlQxMGh0v8Z6zUQBj4sqX2jxNkWuCkX1J75rYoojzZgSvBPuBhYTiId4Y7OLo4hJUQ9loSIeuQGfWfoZiopUpMT3IrfgTkFiLcgY2q+bvduYGR1jZFWpIHZOenyU9CIdzVy0yhCoHzW6QKBgQCGEn7yDJppb44P2dxxKrKvQdoIX3ctFbh/O04b5S1xUE8RQgBKxzmk5r5GPI89LUO3MxEQP2MfGwHyVVdzCd+APqr6mfe6GRSideY419tF0VwLj7gSo2jJzq8qvB63PJuENZKgNJWdw4IWB8miQi0+MVbDu9vO5JwYdL+XrhHmvQKBgQCQEpRSjiIXdcdlktNZmhXUsiGkA8HnzzFM4sYKCof0zrQfaauzfzAI3swQtbZXpdmqu6SpH80QgnmhvCh1JQjtCJMtnHFk4chTwY5hfHo4wXFW75rPv6dOSLgYhI/Tv/Vb2PnM3kb9aOW5nMlLTWSl9eG7Pr+PCHjxgVAYWEGCgQKBgDdW1IIF6D/FDTCFKYVtOfzFi3JeX337O2MiSl+HsLJTijWpfF0n+9bGJmXZdkFneRfdBoGCj3uiycmZxE/BNO4csQ1VCFUneWWKZEzllB6cbefdC8wNUM0JWOktc3SH7rGX7ae52W0s2ax0is6HcMiEmlWuUO0yJmxK0hFct++K";
    // 服务器异步通知页面路径 需http://或者https://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
    public static String NOTIFY_URL = "https://shrimp-accurate-equally.ngrok-free.app/api/order/pay/notify";
    // 页面跳转同步通知页面路径 需http://或者https://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问 商户可以自定义同步跳转地址
    public static String RETURN_URL = "https://shrimp-accurate-equally.ngrok-free.app/alipay.trade.wap.pay-JAVA-UTF-8/return_url.jsp";
    // 请求网关地址
    public static String URL = "openapi-sandbox.dl.alipaydev.com";
    // 编码
    public static String CHARSET = "UTF-8";
    // 返回格式
    public static String FORMAT = "json";
    // 支付宝公钥
    public static String ALIPAY_PUBLIC_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAhBXEiJh2mMyzkNdcgWnx5iuLwvbb1lmfeEjzpRvBKL6kXgd1ceLHaRhznTH1p1u6cP3IILDKNQfv9bquslDSOUXY4UcZLZfPoyon1f+gjwW26sbdAVN7PW1xKxtkwXfSOj628ef4oATMEZhzLNCrUetKeI+wi09rKovreDsIF/BIiyuv+fTBiO+QSeC9WqOsDU0yOjwff+7xSW165Zp7s0jKqkQeyFDNEgUoZri5/NLF4oYqdg/WMxVnVNS2cuGFgOLfb0D0bvNnzRJMKPoDlR4gDgRHidUXrJiOLpRvjyS2Mgo8bXQuufFo7YcS1uQUwlbfbmmkDbrfYSBxO35C2QIDAQAB";
    // 日志记录目录
    public static String log_path = "/log";
    // RSA2
    public static String SIGNTYPE = "RSA2";

    @PostConstruct
    public void init() {
        Config config = new Config();
        //#基础配置
        config.protocol = "https";  //协议
        config.gatewayHost = URL;    //支付宝网关
        config.signType = SIGNTYPE;   //签名方式
        config.encryptKey = "smIGGAMFZH9VNjupb7FwIQ==";
        //#业务配置
        config.appId = APPID;  //应用id
        config.merchantPrivateKey = RSA_PRIVATE_KEY;    // 应用私钥
        config.alipayPublicKey = ALIPAY_PUBLIC_KEY;    // 支付宝公钥
        config.notifyUrl = NOTIFY_URL;  // 回调地址
        Factory.setOptions(config);
        log.info("支付宝配置初始化完成");
    }
}

