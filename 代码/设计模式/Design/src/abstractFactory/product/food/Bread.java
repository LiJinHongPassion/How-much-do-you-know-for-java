package abstractFactory.product.food;

/**
 * 描述:
 *
 * @date 20.9.16
 */
public class Bread implements IFood {

    @Override
    public void printName() {
        System.out.println("面包");
    }
}
