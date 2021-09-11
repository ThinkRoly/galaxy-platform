package com.galaxy.desig;

/**
 * 原型模式
 * 用原型实例指定创建对象的种类，并且通过拷贝这些原型创建新的 对象。
 *
 * 原型模式是在内存二进制流的拷贝，要比直接new一个对象性能好很多，特别是 要在一个循环体内产生大量的对象时，原型模式可以更好地体现其优点。
 * 这既是它的优点也是缺点，直接在内存中拷贝，构造函数是不会执行
 *
 * 资源优化场景 类初始化需要消化非常多的资源，这个资源包括数据、硬件资源等。
 * 性能和安全要求的场景 通过new产生一个对象需要非常繁琐的数据准备或访问权限，则可以使用原型模式。
 * 一个对象多个修改者的场景 一个对象需要提供给其他对象访问，而且各个调用者可能都需要修改其值时，可 以考虑使用原型模式拷贝多个对象供调用者使用。
 * 浅拷贝和深拷贝：
 * 浅拷贝：Object类提供的方法clone只是拷贝本对象，其对象内部的数组、引用 对象等都不拷贝，还是指向原生对象的内部元素地址，这种拷贝就叫做浅拷贝， 其他的原始类型
 * 比如int、long、char、string（当做是原始类型）等都会被拷 贝。
 * 注意： 使用原型模式时，引用的成员变量必须满足两个条件才不会被拷贝：一 是类的成员变量，而不是方法内变量；二是必须是一个可变的引用对象，而不是 一个原始类型或
 * 不可变对象。
 * 深拷贝：对私有的类变量进行独立的拷贝
 * @author galaxy
 * @since 2021/9/11 15:18
 */
public class PrototypeTest {
    public static void main(String[] args) {
        Prototype prototype = new Prototype();
        prototype.setName("Jack");
        Prototype clone = (Prototype)prototype.clone();
        System.out.println(prototype);
        System.out.println(clone);
        System.out.println(prototype == clone);
    }
}
class Prototype implements Cloneable{
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    protected Object clone() {
        try {
            return super.clone();
        }catch (CloneNotSupportedException e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String toString() {
        return "Prototype{" +
                "name='" + name + '\'' +
                '}';
    }
}
