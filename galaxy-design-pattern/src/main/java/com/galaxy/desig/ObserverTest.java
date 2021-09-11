package com.galaxy.desig;

import java.util.Vector;

/**
 * 观察者模式
 *
 * 定义对象间一种一对多的依赖关系，使得每当一个对象改变状态，则所有依赖于它的对象都会得到通知并被自动更新。
 * @author galaxy
 * @since 2021/9/11 15:28
 */
public class ObserverTest {
    public static void main(String[] args) {
        Subject subject = new ConcreteSubject();
        ConcreteObserver1 observer1 = new ConcreteObserver1();
        ConcreteObserver2 observer2 = new ConcreteObserver2();
        subject.addObserver(observer1);
        subject.addObserver(observer2);
        subject.notifyObserver();
    }
}
abstract class Subject{
    protected Vector<Observer> observers = new Vector<>();

    public void addObserver(Observer o){
        this.observers.add(o);
    }

    abstract void notifyObserver();
}
//具体目标
class ConcreteSubject extends Subject {
    @Override
    public void notifyObserver() {
        System.out.println("具体目标发生改变...");
        System.out.println("--------------");
        for (Observer obs : observers) {
            obs.response();
        }
    }
}
//抽象观察者
interface Observer {
    void response(); //反应
}
//具体观察者1
class ConcreteObserver1 implements Observer {
    public void response() {
        System.out.println("具体观察者1作出反应！");
    }
}
//具体观察者1
class ConcreteObserver2 implements Observer {
    public void response() {
        System.out.println("具体观察者2作出反应！");
    }
}
