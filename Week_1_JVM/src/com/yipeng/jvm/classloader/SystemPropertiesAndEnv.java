package com.yipeng.jvm.classloader;

import java.util.Map;
import java.util.Properties;

/**
 * 系统变量和环境变量的区别
 * <p>
 * environment properties (环境变量) 是系统级的环境变量，系统中当前所有的进程共享
 * <p>
 * system properties （系统变量）是Java应用程序自身指定的变量，只有当前进程才能获取到， 一般通过启动参数 -D进行指定
 *
 * @author yipeng
 */
public class SystemPropertiesAndEnv {

    /**
     * 通过 -D 指定系统变量， 查看是否能得到
     *
     * @param args 命令行参数
     */
    public static void main(String[] args) {
        // 系统级环境变量
        Map<String, String> envMap = System.getenv();
        System.out.println("environment==================================");
        envMap.forEach((key, value) -> {
            System.out.printf("%s => %s%n", key, value);
        });

        // 当前进程系统变量
        Properties properties = System.getProperties();
        System.out.println("system properties==================================");
        properties.list(System.out);

        while (true) {
            
        }
    }
}
