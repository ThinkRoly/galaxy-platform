package com.galaxy.desig;

/**
 * 建造者模式
 * 将一个复杂对象的构建与它的表示分离，使得同样的构建过程可 以创建不同的表示。
 * @author galaxy
 * @since 2021/9/11 14:56
 */
public class BuildTest {
    public static void main(String[] args) {
        Food food = new Food.Builder().setName("大米").bulid();
        System.out.println(food);
    }
}
class Food{
    private final String name;


    public Food(Builder builder){
        this.name = builder.name;
    }

    @Override
    public String toString() {
        return "Food{" +
                "name='" + name + '\'' +
                '}';
    }
    static class Builder{
        String name = "";

        public Builder setName(String name){
            this.name = name;
            return this;
        }

        public Food bulid(){
            return new Food(this);
        }
    }
}
