package factory;

import factory.product.ICar;
import factory.factory.BENZFactory;
import factory.factory.BWMFactory;

/**
 * 描述: 调用产品工厂
 * 参考文献: https://juejin.im/post/6844903877246992392
 *
 * @author lijinhong
 * @date 20.9.15
 */
public abstract class Main {
    public static void main(String[] args) {
        BENZFactory benzFactory = new BENZFactory();
        ICar benzCar = benzFactory.createCar();
        benzCar.getName();
        benzCar.getSite();

        BWMFactory bwmFactory = new BWMFactory();
        ICar bwmCar = bwmFactory.createCar();
        bwmCar.getName();
        bwmCar.getSite();
    }
}
