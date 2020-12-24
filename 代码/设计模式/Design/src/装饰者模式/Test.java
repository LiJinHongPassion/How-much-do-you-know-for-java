package 装饰者模式;

import 装饰者模式.产品.抽象类.ABattercake;
import 装饰者模式.产品.Battercake;
import 装饰者模式.产品装饰器.EggDecorator;
import 装饰者模式.产品装饰器.SausageDecorator;

public class Test {
    public static void main(String[] args) {
        ABattercake aBattercake = new Battercake();
        System.out.println(aBattercake.getDesc() + ", 销售价格: " + aBattercake.cost());


        //在aBattercake基础上，加一个鸡蛋
        EggDecorator eggDecorator = new EggDecorator(aBattercake);
        System.out.println(eggDecorator.getDesc() + ", 销售价格: " + eggDecorator.cost());

        //eggDecorator，加一个鸡蛋
        SausageDecorator sausageDecorator = new SausageDecorator(eggDecorator);
        System.out.println(sausageDecorator.getDesc() + ", 销售价格: " + sausageDecorator.cost());
    }
}