package com.company.PC;

/**
 * 描述: 消费者
 *
 * @author codeant
 * @date 20.8.18
 */
public class Main {
    static ProductQueue productQueue = new ProductQueue(100);
    public static void main(String[] args) {
        new Thread(Main::consumption).start();
        new Thread(Main::provider).start();
    }

    public static void provider(){
        System.out.println("-----------------消费者启动------------------");
        while (true){
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            new Thread( ()->{
                int temp = (int) (Math.random()*100);
                Product<Integer> product = new Product<>("codeant" + temp, temp);
                productQueue.production(product);
                System.out.println("生产 -- " + product);
            }).start();
        }
    }

    public static void consumption(){
        System.out.println("-----------------生产者启动------------------");
        while (true){
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            new Thread( ()->{
                int temp = (int) (Math.random()*100);
                Product<Integer> product = (Product<Integer>) productQueue.consumption();
                System.out.println("消费 -- " + product);
            }).start();
        }
    }
}
