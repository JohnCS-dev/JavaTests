/**
 * Created by iis on 12/1/2020.
 */

class Store
{
    private int counter = 0;
    public synchronized void get()
    {
        while (counter < 1) {
            try {
                wait();
            } catch (InterruptedException e) {}
        }
        counter--;
        System.out.println("-1 : товар забрали");
        System.out.println(
                "\tколичество товара на складе : " + counter);
        notify();
    }
    public synchronized void put() {
        while (counter >= 3) {
            try {
                wait();
            }catch (InterruptedException e) {}
        }
        counter++;
        System.out.println("+1 : товар добавили");
        System.out.println(
                "\tколичество товара на складе : " + counter);
        notify();
    }
}

class Producer implements Runnable
{
    Store store;
    Producer(Store store) {
        this.store=store;
    }
    @Override
    public void run() {
        for (int i = 1; i < 6; i++) {
            store.put();
        }
    }
}

class Consumer implements Runnable
{
    Store store;
    Consumer(Store store) {
        this.store=store;
    }
    @Override
    public void run(){
        for (int i = 1; i < 6; i++) {
            store.get();
        }
    }
}

public class Trade
{
    public static void main(String[] args)
    {
        System.out.println("Args... " + args.length);
        for (String arg: args) {
            System.out.println("---> " + arg + " <---");
        }

        Store     store    = new Store();
        Producer  producer = new Producer(store);
        Consumer  consumer = new Consumer(store);
        new Thread(producer).start();
        new Thread(consumer).start();
    }
}
