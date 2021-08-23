package com.galaxy.desig.single;

/**
 * 单例模式
 * @author galaxy
 * @since 2021/8/22 16:33
 */
public class SingleTest {
    public static void main(String[] args) {
        Singleton s1 = Singleton.getInstance();
        Singleton s2 = Singleton.getInstance();
        System.out.println(s1 == s2);
    }
}
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
