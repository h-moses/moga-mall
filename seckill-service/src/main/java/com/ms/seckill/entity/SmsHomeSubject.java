package com.ms.seckill.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 首页专题表【jd首页下面很多专题，每个专题链接新的页面，展示专题商品信息】
 * </p>
 *
 * @author ms
 * @since 2023-10-24
 */
@Getter
@Setter
@TableName("sms_home_subject")
@ApiModel(value = "SmsHomeSubject对象", description = "首页专题表【jd首页下面很多专题，每个专题链接新的页面，展示专题商品信息】")
public class SmsHomeSubject implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("id")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty("专题名字")
    private String name;

    @ApiModelProperty("专题标题")
    private String title;

    @ApiModelProperty("专题副标题")
    private String subTitle;

    @ApiModelProperty("显示状态")
    private Boolean status;

    @ApiModelProperty("详情连接")
    private String url;

    @ApiModelProperty("排序")
    private Integer sort;

    @ApiModelProperty("专题图片地址")
    private String img;


}
