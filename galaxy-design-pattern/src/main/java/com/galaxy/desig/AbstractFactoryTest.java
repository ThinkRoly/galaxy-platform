package com.galaxy.desig;

/**
 * 抽象工厂模式
 * 为创建一组相关或相互依赖的对象提供一个接口，而且无须指 定它们的具体类。
 * 相对于工厂模式，我们可以新增产品类（只需要实现产品接口），只需要同时新 增一个工厂类，客户端就可以轻松调用新产品的代码。
 *
 * 一个对象族（或是一组没有任何关系的对象）都有相同的约束。 涉及不同操作系统的时候，都可以考虑使用抽象工厂模式
 * @author galaxy
 * @since 2021/9/11 14:48
 */
public class AbstractFactoryTest {
    public static void main(String[] args) {
        Fruit apple = new AppleFactory().get();
        Fruit banana = new BananaFactory().get();
        apple.print();
        banana.print();
    }
}
interface Produce{
    Fruit get();
}
class AppleFactory implements Produce{

    @Override
    public Fruit get() {
        return new Apple();
    }
}
class BananaFactory implements Produce{

    @Override
    public Fruit get() {
        return new Banana();
    }
}
