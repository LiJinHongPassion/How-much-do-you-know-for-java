package abstractFactory.factory;

import abstractFactory.product.food.Meat;
import abstractFactory.product.tool.Fork;

public class BFactory implements AbstractRestaurant {

    @Override
    public void haveDinner() {
        new Meat().printName();
        new Fork().printName();
    }
}