package 观察者模式.观察者.Impl;

public abstract class Person{
    private String name;
    
    public abstract void doThings(String thing);

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}