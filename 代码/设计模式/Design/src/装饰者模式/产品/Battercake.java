package 装饰者模式.产品;

import 装饰者模式.产品.抽象类.ABattercake;

/**
 * 煎饼类，继承了煎饼抽象类，一个煎饼 8 块钱
 */
public class Battercake extends ABattercake {
    @Override
    public String getDesc() {
        return "煎饼";
    }
    @Override
    public int cost() {
        return 8;
    }
}
