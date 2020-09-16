package abstractFactory.factory;

import abstractFactory.product.food.Milk;
import abstractFactory.product.tool.Spoon;

/**
 * 描述:
 *
 * @author lijinhong
 * @date 20.9.16
 */
public class CFactory implements AbstractRestaurant {
    @Override
    public void haveDinner() {
        new Milk().printName();
        new Spoon().printName();
    }
}
