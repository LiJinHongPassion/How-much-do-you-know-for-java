package abstractFactory.product.tool;

/**
 * 描述:
 *
 * @date 20.9.16
 */
public class Knife implements ITool {
    @Override
    public void printName() {
        System.out.println("刀");
    }
}
