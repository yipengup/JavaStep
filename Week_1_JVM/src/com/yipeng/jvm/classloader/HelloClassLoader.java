package com.yipeng.jvm.classloader;

import java.util.Base64;

/**
 * 自定义类加载器，用于加载经过加密之后的类
 * <p>
 * 实现类加载一般只需要重写 {@link java.lang.ClassLoader#findClass(java.lang.String)} 调用的loadClass逻辑上实现了双亲委派的逻辑
 *
 * @author yipeng
 */
public class HelloClassLoader extends ClassLoader {

    public static void main(String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException {
        // 加载并初始化Hello类
        // 输出 Hello Class Initialized
        new HelloClassLoader().findClass("com.yipeng.jvm.classloader.Hello").newInstance();
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        // com.yipeng.jvm.classloader.Hello 该类被base64 加密后的字符串
        String base64EncodeString = "yv66vgAAADQAHAoABgAOCQAPABAIABEKABIAEwcAFAcAFQEABjxpbml0PgEAAygpVgEABENvZGUB" +
                "AA9MaW5lTnVtYmVyVGFibGUBAAg8Y2xpbml0PgEAClNvdXJjZUZpbGUBAApIZWxsby5qYXZhDAAH" +
                "AAgHABYMABcAGAEAF0hlbGxvIENsYXNzIEluaXRpYWxpemVkBwAZDAAaABsBACBjb20veWlwZW5n" +
                "L2p2bS9jbGFzc2xvYWRlci9IZWxsbwEAEGphdmEvbGFuZy9PYmplY3QBABBqYXZhL2xhbmcvU3lz" +
                "dGVtAQADb3V0AQAVTGphdmEvaW8vUHJpbnRTdHJlYW07AQATamF2YS9pby9QcmludFN0cmVhbQEA" +
                "B3ByaW50bG4BABUoTGphdmEvbGFuZy9TdHJpbmc7KVYAIQAFAAYAAAAAAAIAAQAHAAgAAQAJAAAA" +
                "HQABAAEAAAAFKrcAAbEAAAABAAoAAAAGAAEAAAAMAAgACwAIAAEACQAAACUAAgAAAAAACbIAAhID" +
                "tgAEsQAAAAEACgAAAAoAAgAAABAACAARAAEADAAAAAIADQ==";

        byte[] bytes = Base64.getDecoder().decode(base64EncodeString);
        // 可以将字节码对应的类文件加载到JVM中
        return defineClass(name, bytes, 0, bytes.length);
    }


}
