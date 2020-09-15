package factory.factory;

import factory.product.BWMCar;
import factory.product.ICar;

/**
 * 描述:
 *
 * @author lijinhong
 * @date 20.9.15
 */
public class BWMFactory extends BWMCar implements IFactory {

    @Override
    public ICar createCar() {
        return new BWMCar();
    }
}
