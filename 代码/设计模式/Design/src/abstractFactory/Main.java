package abstractFactory;


import abstractFactory.factory.AbstractRestaurant;

/**
 * 描述:
 *
 * @date 20.9.16
 */
public class Main {
    public static void main(String[] args) {
        AbstractRestaurant a = AbstractRestaurant.createFactory("a");
        a.haveDinner();
        System.out.println("----------------------");
        AbstractRestaurant b = AbstractRestaurant.createFactory("b");
        b.haveDinner();
        System.out.println("----------------------");
        AbstractRestaurant c = AbstractRestaurant.createFactory("c");
        c.haveDinner();
    }
}
