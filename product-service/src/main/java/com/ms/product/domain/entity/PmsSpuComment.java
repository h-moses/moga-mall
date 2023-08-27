package com.ms.product.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 商品评价
 * </p>
 *
 * @author ms
 * @since 2023-07-01
 */
@Getter
@Setter
@TableName("pms_spu_comment")
@ApiModel(value = "PmsSpuComment对象", description = "商品评价")
public class PmsSpuComment implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("id")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty("sku_id")
    private Long skuId;

    @ApiModelProperty("spu_id")
    private Long spuId;

    @ApiModelProperty("商品名字")
    private String spuName;

    @ApiModelProperty("会员昵称")
    private String memberNickName;

    @ApiModelProperty("星级")
    private Boolean star;

    @ApiModelProperty("会员ip")
    private String memberIp;

    @ApiModelProperty("创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty("显示状态[0-不显示，1-显示]")
    private Boolean showStatus;

    @ApiModelProperty("购买时属性组合")
    private String spuAttributes;

    @ApiModelProperty("点赞数")
    private Integer likesCount;

    @ApiModelProperty("回复数")
    private Integer replyCount;

    @ApiModelProperty("评论图片/视频[json数据；[{type:文件类型,url:资源路径}]]")
    private String resources;

    @ApiModelProperty("内容")
    private String content;

    @ApiModelProperty("用户头像")
    private String memberIcon;

    @ApiModelProperty("评论类型[0 - 对商品的直接评论，1 - 对评论的回复]")
    private Integer commentType;


}
