package 观察者模式.被观察者;

import 观察者模式.观察者.Impl.Person;

import java.util.ArrayList;
import java.util.List;

/**
 * 描述:
 *
 * @author lijinhong
 * @date 20.12.10
 */
public class XiaoMei {
    List<Person> list = new ArrayList<Person>();

    public XiaoMei() {
    }

    public void addPerson(Person person) {
        list.add(person);
    }

    //遍历list，把自己的通知发送给所有暗恋自己的人
    public void notifyPerson() {
        for (Person person : list) {
            person.doThings("今天家里就我一个人，你们过来吧，谁先过来谁就能得到我!");
        }
    }
}
