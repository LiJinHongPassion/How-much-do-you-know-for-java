package 装饰者模式.产品装饰器;

import 装饰者模式.产品.抽象类.ABattercake;
import 装饰者模式.产品装饰器.抽象类.AbstractDecorator;

/**
 * 香肠装饰器，与鸡蛋装饰器类似，继承了抽象装饰类，给在父类的基础上加上一根香肠，同时价格增加 2 块钱
 */
public class SausageDecorator extends AbstractDecorator {
    public SausageDecorator(ABattercake aBattercake) {
        super(aBattercake);
    }
    @Override
    protected void doSomething() {

    }

    @Override
    public String getDesc() {
        return super.getDesc() + " 加一根香肠";
    }
    @Override
    public int cost() {
        return super.cost() + 2;
    }
}
