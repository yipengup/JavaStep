package com.yipeng.jvm.classloader;

/**
 * 自定义类, 获取到二进制信息，将其加密，然后利用自定义类加载器加载加密后的编码，使得类加载器获取到该类
 * <p>
 * 首先需要将类文件编译成字节码文件
 * <p>
 * 加密方式: 采用命令行命令 base64 对当前文件进行编码就行
 *
 * @author yipeng
 */
public class Hello {

    static {
        // 初始化时执行静态代码快
        System.out.println("Hello Class Initialized");
    }

}
