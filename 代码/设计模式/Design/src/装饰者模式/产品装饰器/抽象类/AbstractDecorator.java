package 装饰者模式.产品装饰器.抽象类;

import 装饰者模式.产品.抽象类.ABattercake;

/**
 * 抽象装饰类
 * 需要注意的是，抽象装饰类通过成员属性的方式将 煎饼抽象类组合进来，同时也继承了煎饼抽象类，且这里定义了新的业务方法 doSomething()
 */
public abstract class AbstractDecorator extends ABattercake {
    private ABattercake aBattercake;

    public AbstractDecorator(ABattercake aBattercake) {
        this.aBattercake = aBattercake;
    }
    
    protected abstract void doSomething();

    @Override
    public String getDesc() {
        return this.aBattercake.getDesc();
    }
    @Override
    public int cost() {
        return this.aBattercake.cost();
    }
}
