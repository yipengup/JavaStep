package com.yipeng.jvm.classloader;

import java.lang.reflect.Field;

/**
 * 查看 反射中 declareField 和 Field 的区别
 * <p>
 * Field: 只能获取到本类或者父类中public字段
 * <p>
 * declareField: 只能获取到本类中字段
 * <p>
 * 要想获取到父类中某个非public属性，直接使用父类的class对象即可。
 *
 * @author yipeng
 */
public class FieldAndDeclareField {

    public static void main(String[] args) throws Exception {
        Dog dog = new Dog();
        dog.setName("xixi");
        dog.setAddress("haha");
        dog.sex = "男";

        Class<? extends Dog> aClass = dog.getClass();
        // Field nameField = aClass.getField("name"); // throw NoSuchFieldException 无法获取到非public字段

        Field sexField = aClass.getField("sex");
        sexField.setAccessible(true);
        // sex = 男
        System.out.println("sex = " + sexField.get(dog)); // 只能获取到 public 字段

        Field nameField = aClass.getDeclaredField("name");
        nameField.setAccessible(true);
        // name = xixi
        System.out.println("name = " + nameField.get(dog)); // 只能获取到本类中字段

        // Field declaredField = aClass.getDeclaredField("address"); // throw NoSuchFieldException 无法获取到非本类中的字段

        // 获取到父类中非public属性
        Field addressField = Animal.class.getDeclaredField("address");
        addressField.setAccessible(true);
        System.out.println("address = " + addressField.get(dog));
    }


    private static class Animal {
        private String address;

        public String sex;

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }
    }

    private static class Dog extends Animal {
        private String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return "Dog{" +
                    "name='" + name + '\'' +
                    ", address='" + getAddress() + '\'' +
                    '}';
        }
    }


}



