package abstractFactory.factory;

import abstractFactory.product.food.Bread;
import abstractFactory.product.tool.Knife;

/**
 * 描述:
 *
 * @author lijinhong
 * @date 20.9.16
 */
public class AFactory implements AbstractRestaurant {

    @Override
    public void haveDinner() {
        new Bread().printName();
        new Knife().printName();
    }
}
