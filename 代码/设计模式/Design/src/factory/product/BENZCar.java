package factory.product;

/**
 * 描述:
 *
 * @author lijinhong
 * @date 20.9.15
 */
public class BENZCar implements ICar {

    @Override
    public void getName() {
        System.out.println("这是奔驰");
    }

    @Override
    public void getSite() {
        System.out.println("4人座位");
    }
}
