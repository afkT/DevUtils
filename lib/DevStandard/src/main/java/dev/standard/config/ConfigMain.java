package dev.standard.config;

import java.io.File;

import dev.utils.DevFinal;
import dev.utils.common.FileUtils;

/**
 * detail: 配置读取 Main 方法
 * @author Ttt
 */
final class ConfigMain {

    public static void main(String[] args) {
        File depsJSONFile = new File("file/json/deps.json");
        // 读取数据
        String json = FileUtils.readFile(depsJSONFile);
        // 转实体类
        DepsJsonBean depsJsonBean = DepsJsonBean.get(json);
        // 获取 Gradle Dependencies 全部依赖信息
        String dependencies = depsJsonBean.getAllDependencies(DepsJsonBean.IMPLEMENTATION);
        // 保存文件
        FileUtils.saveFile(
                new File("file/json/deps.gradle"),
                String.format("dependencies {%s%s}", dependencies, DevFinal.NEW_LINE_STR).getBytes()
        );
        System.out.println(dependencies);
    }
}