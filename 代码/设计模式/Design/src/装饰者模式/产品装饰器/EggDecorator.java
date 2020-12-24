package 装饰者模式.产品装饰器;

import 装饰者模式.产品.抽象类.ABattercake;
import 装饰者模式.产品装饰器.抽象类.AbstractDecorator;

/**
 * 鸡蛋装饰器，继承了抽象装饰类，鸡蛋装饰器在父类的基础上增加了一个鸡蛋，同时价格加上 1 块钱
 */
public class EggDecorator extends AbstractDecorator {
    public EggDecorator(ABattercake aBattercake) {
        super(aBattercake);
    }

    @Override
    protected void doSomething() {

    }

    @Override
    public String getDesc() {
        return super.getDesc() + " 加一个鸡蛋";
    }

    @Override
    public int cost() {
        return super.cost() + 1;
    }
    
    public void egg() {
        System.out.println("增加了一个鸡蛋");
    }
}
