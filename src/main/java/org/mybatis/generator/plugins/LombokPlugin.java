package org.mybatis.generator.plugins;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.Plugin;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.java.Interface;
import org.mybatis.generator.api.dom.java.Method;
import org.mybatis.generator.api.dom.java.TopLevelClass;

/**
 * mybatis-generator-core扩展lombok的支持
 * <p>
 * 根据自己情况进行修改
 *
 * @author hager.w
 */
public class LombokPlugin extends PluginAdapter {
    private boolean useBuilderAnnotation = false;

    private String author = "admin";

    @Override
    public void setProperties(Properties properties) {
        super.setProperties(properties);
        this.useBuilderAnnotation = Boolean.valueOf(properties.getProperty("useBuilderAnnotation")).booleanValue();
        this.author = properties.getProperty("author");
    }

    public boolean validate(List<String> list) {
        return true;
    }

    @Override
    public boolean modelBaseRecordClassGenerated(TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        topLevelClass.addImportedType("lombok.Data");
        if (this.useBuilderAnnotation) {
            topLevelClass.addImportedType("lombok.Builder");
        }
        topLevelClass.addImportedType("lombok.NoArgsConstructor");
        topLevelClass.addImportedType("lombok.AllArgsConstructor");

        topLevelClass.addAnnotation("@Data");
        if (this.useBuilderAnnotation) {
            topLevelClass.addAnnotation("@Builder");
        }
        topLevelClass.addAnnotation("@NoArgsConstructor");
        topLevelClass.addAnnotation("@AllArgsConstructor");

        topLevelClass.addJavaDocLine("/**");
        topLevelClass.addJavaDocLine("* @author " + this.author);
        topLevelClass.addJavaDocLine("* @Date " + date2Str(new Date()));
        topLevelClass.addJavaDocLine("*/");

        return true;
    }

    @Override
    public boolean clientGenerated(Interface interfaze, TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        interfaze.addJavaDocLine("/**");
        interfaze.addJavaDocLine("* Created on " + date2Str(new Date()));
        interfaze.addJavaDocLine("*/");
        return true;
    }

    @Override
    public boolean modelSetterMethodGenerated(Method method, TopLevelClass topLevelClass, IntrospectedColumn introspectedColumn, IntrospectedTable introspectedTable, Plugin.ModelClassType modelClassType) {
        return false;
    }

    @Override
    public boolean modelGetterMethodGenerated(Method method, TopLevelClass topLevelClass, IntrospectedColumn introspectedColumn, IntrospectedTable introspectedTable, Plugin.ModelClassType modelClassType) {
        return false;
    }

    private String date2Str(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        return sdf.format(date);
    }
}