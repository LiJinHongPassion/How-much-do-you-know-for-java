package 适配器模式.适配者;

public class CommonHeadset implements Headset {

    @Override
    public void listen() {
        System.out.println("使用3.5mm耳机享受音乐...");
    }
}