package abstractFactory.factory;

/**
 * 描述:
 *
 * @date 20.9.16
 */
public interface AbstractRestaurant {

    void haveDinner();

    public static AbstractRestaurant createFactory(String name){
        switch (name){
            case "a" : return new AFactory();
            case "b" : return new BFactory();
            case "c" : return new CFactory();
            default:return null;
        }
    }
}
