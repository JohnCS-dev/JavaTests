/**
 * Created by iis on 12/1/2020.
 */
class CommonObject
{
    int counter = 1;
}

class CounterThread implements Runnable
{
    CommonObject res;
    CounterThread(CommonObject res)
    {
        this.res = res;
    }
    @Override
    public void run()
    {
      synchronized(res) {
//        res.counter = 1;
        for (int i = 0; i < 4; i++){
            System.out.printf("'%s' - %d\n",
                    Thread.currentThread().getName(),
                    res.counter);
            res.counter++;
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
}
public class SynchronizedThread
{
    public static void main(String[] args) {
        CommonObject commonObject= new CommonObject();
        for (int i = 0; i < 5; i++) {
            Thread t;
            t = new Thread(new CounterThread(commonObject));
            t.setName("Поток " + i);
            t.start();
        }
    }
}