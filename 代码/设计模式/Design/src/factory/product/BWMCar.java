package factory.product;

/**
 * 描述:
 *
 * @author lijinhong
 * @date 20.9.15
 */
public class BWMCar implements ICar {
    @Override
    public void getName() {
        System.out.println("这是宝马");
    }

    @Override
    public void getSite() {
        System.out.println("2人座位");
    }
}
