package com.galaxy.desig;

/**
 * 工厂模式
 * 定义一个用于创建对象的接口，让子类决定实例化哪一个类。工厂 方法使一个类的实例化延迟到其子类。
 *
 * jdbc连接数据库，硬件访问，降低对象的产生和销毁
 * @author galaxy
 * @since 2021/9/11 14:28
 */
public class FactoryTest {
    public static void main(String[] args) {
        FruitFactory fruitFactory = new FruitFactory();
        Fruit apple = fruitFactory.produce("Apple");
        Fruit banana = fruitFactory.produce("Banana");
        Fruit error = fruitFactory.produce("error");
        apple.print();
        banana.print();
        error.print();
    }
}
class FruitFactory{
    public Fruit produce(String type){
        if (type.equals("Apple")){
            return new Apple();
        }else if (type.equals("Banana")){
            return new Banana();
        }else {
            System.out.println("type error");
        }
        return null;
    }
}

interface Fruit{
    void print();
}
class Apple implements Fruit{
    @Override
    public void print() {
        System.out.println("Apple");
    }
}
class Banana implements Fruit{
    @Override
    public void print() {
        System.out.println("Banana");
    }
}
