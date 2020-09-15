package factory.factory;

import factory.product.BENZCar;
import factory.product.ICar;

/**
 * 描述: 产品工厂
 *
 * @author lijinhong
 * @date 20.9.15
 */
public class BENZFactory extends BENZCar implements IFactory {

    @Override
    public ICar createCar() {
        return new BENZCar();
    }
}
