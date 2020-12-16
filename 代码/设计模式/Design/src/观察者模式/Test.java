package 观察者模式;

import 观察者模式.被观察者.XiaoMei;
import 观察者模式.观察者.Impl.Person;
import 观察者模式.观察者.LaoWang;
import 观察者模式.观察者.LaoLi;

/**
 * 描述: 测试类
 *
 * @author lijinhong
 * @date 20.12.10
 */
public class Test {
    //实例化所有对象
    private static Person laoWang = new LaoWang("老王");
    private static Person laoLi = new LaoLi("老李");
    private static XiaoMei xiaoMei = new XiaoMei();

    public static void main(String[] args) {
        //绑定观察者
        xiaoMei.addPerson(laoLi);
        xiaoMei.addPerson(laoWang);

        //通知
        xiaoMei.notifyPerson();
    }
}
