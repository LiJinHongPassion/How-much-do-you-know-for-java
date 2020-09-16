package abstractFactory.product.tool;

/**
 * 描述:
 *
 * @date 20.9.16
 */
public class Fork implements ITool {
    @Override
    public void printName() {
        System.out.println("叉");
    }
}
