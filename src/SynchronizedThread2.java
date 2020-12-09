/**
 * Created by iis on 12/1/2020.
 */

class CommonObject2
{
    private int counter = 0;
    synchronized public void incCounter() {
        counter++;
        System.out.printf("'%s' - %d\n",
                Thread.currentThread().getName(),
                counter);
    }
}

class CounterThread2 implements Runnable
{
    CommonObject2 res;
    CounterThread2(CommonObject2 res)
    {
        this.res = res;
    }
    @Override
    public void run()
    {
        for (int i = 0; i < 4; i++){
            res.incCounter();
            try {
                Thread.sleep(100);
            }
            catch(InterruptedException e){
                System.out.printf("'%s' interrupted\n",
                         Thread.currentThread().getName());
            }
        }
    }
}
public class SynchronizedThread2
{
    public static void main(String[] args) {
        System.out.println("---> SynchronizedThread2 <---");
        CommonObject2 commonObject= new CommonObject2();
        for (int i = 0; i < 5; i++) {
            Thread t;
            t = new Thread(new CounterThread2(commonObject));
            t.setName("Поток " + i);
            t.start();
        }
    }
}
