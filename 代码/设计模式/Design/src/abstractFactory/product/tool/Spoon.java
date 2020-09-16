package abstractFactory.product.tool;

/**
 * 描述:
 *
 * @date 20.9.16
 */
public class Spoon implements ITool {
    @Override
    public void printName() {
        System.out.println("勺子");
    }
}
