package com.galaxy.desig;

/**
 * 单例模式
 * 确保某一个类只有一个实例，而且自行实例化并向整个系统提供这 个实例。
 *
 * @author galaxy
 * @since 2021/8/22 16:33
 */
public class SingleTest {
    public static void main(String[] args) {
        Singleton s1 = Singleton.getInstance();
        Singleton s2 = Singleton.getInstance();
        System.out.println(s1 == s2);

        System.out.println(SingleEnum.STUDENT);
    }
}

/**
 * DCL单例
 */
class Singleton{
    private volatile static Singleton singleton = null;

    private Singleton(){}

    public static Singleton getInstance(){
        if (singleton == null) {
            synchronized (Singleton.class) {
                if (singleton == null) {
                    singleton = new Singleton();
                }
            }
        }
        return singleton;
    }
}
enum SingleEnum{

    STUDENT("学生", 18);

    private final String name;
    private final Integer age;

    SingleEnum(String name, Integer age){
        this.name = name;
        this.age = age;
    }

    @Override
    public String toString() {
        return "SingleEnum{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}
