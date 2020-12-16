package 观察者模式.观察者;

import 观察者模式.观察者.Impl.Person;

//老王
public class LaoWang extends Person {
	public LaoWang(String name){
        setName(name);
    }
    public void doThings(String thing){
        System.out.print("通知："  + thing);
    }
}