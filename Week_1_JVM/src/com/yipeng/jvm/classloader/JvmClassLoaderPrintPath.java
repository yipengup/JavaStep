package com.yipeng.jvm.classloader;

import sun.misc.Launcher;
import sun.misc.URLClassPath;

import java.lang.reflect.Field;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Objects;

/**
 * 打印类加载器加载的Jar包
 *
 * @author yipeng
 */
public class JvmClassLoaderPrintPath {

    public static void main(String[] args) {
        // 获取到启动类加载器加载的所有的jar包路径
        URL[] urLs = Launcher.getBootstrapClassPath().getURLs();
        System.out.println("启动类加载器");
        for (URL urL : urLs) {
            // 以字符串的形式返回url
            System.out.println(" ==> " + urL.toExternalForm());
        }

        // 扩展类加载器
        printClassLoader("扩展类加载器", JvmClassLoaderPrintPath.class.getClassLoader().getParent());

        // 应用类加载器
        printClassLoader("应用类加载器", JvmClassLoaderPrintPath.class.getClassLoader());
    }

    private static void printClassLoader(String name, ClassLoader classLoader) {
        if (Objects.nonNull(classLoader)) {
            System.out.println(name + " ClassLoader -> " + classLoader);
            printURLForClassLoader(classLoader);
        } else {
            System.out.println(name + " ClassLoader -> null");
        }
    }

    /**
     * 打印指定类加载加载的URL信息
     *
     * @param classLoader 类加载器
     */
    private static void printURLForClassLoader(ClassLoader classLoader) {
        // 获取到类加载器加载Jar的地址（URLClassPath）
        URLClassPath ucp = (URLClassPath) insightField(classLoader, "ucp");
        if (Objects.nonNull(ucp)) {
            URL[] urLs = ucp.getURLs();
            for (URL urL : urLs) {
                // 以字符串的形式返回url
                System.out.println(" ==> " + urL.toExternalForm());
            }
        }
    }

    private static Object insightField(Object obj, String fName) {

        try {
            Field f;
            // 理论上所有的类加载器都是URLClassLoader的子类
            if (obj instanceof URLClassLoader) {
                f = URLClassLoader.class.getDeclaredField(fName);
            } else {
                f = obj.getClass().getDeclaredField(fName);
            }
            f.setAccessible(true);
            return f.get(obj);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
            return null;
        }
    }


}
