package com.ms.common.mbg;

import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class MybatisGenerator {

    private static final String DATASOURCE_URL = "jdbc:mysql://localhost:3306/moga_mall_ums?useUnicode=true&&chatacterEncoding=utf-8&&useSSL=false";

    private static final String OUTPUT_DIR = "D:\\WorkStationJava\\在线商城\\moga-mall\\user-service\\src\\main\\java";

    private static final String USERNAME = "root";

    private static final String PASSWORD = "802324";

    public static void main(String[] args) {
        FastAutoGenerator.create(DATASOURCE_URL, USERNAME, PASSWORD)
                // 全局配置
                .globalConfig((scanner, builder) -> builder.author(scanner.apply("请输入作者名称？")).fileOverride().disableOpenDir().enableSwagger().outputDir(OUTPUT_DIR))
                // 包配置
                .packageConfig((scanner, builder) -> builder.parent(scanner.apply("请输入包名？")))
                // 策略配置
                .strategyConfig((scanner, builder) -> builder.addInclude(getTables(scanner.apply("请输入表名，多个英文逗号分隔？所有输入 all"))).addFieldPrefix("ums_", "")
                        .controllerBuilder().enableRestStyle().enableHyphenStyle()
                        .entityBuilder().enableLombok()
                        .mapperBuilder().enableMapperAnnotation().build())
                .templateEngine(new FreemarkerTemplateEngine())
                /*
                    模板引擎配置，默认 Velocity 可选模板引擎 Beetl 或 Freemarker
                   .templateEngine(new BeetlTemplateEngine())
                   .templateEngine(new FreemarkerTemplateEngine())
                 */
                .execute();
    }

    // 处理 all 情况
    protected static List<String> getTables(String tables) {
        return "all".equals(tables) ? Collections.emptyList() : Arrays.asList(tables.split(","));
    }
}
