import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Tt {
    public static void main(String[] args) {
        new Thread(()->{
            try {
                new Tick().sale(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }

}
class Tick{
    public void sale(int num) throws InterruptedException {
        Lock l=new ReentrantLock();
        Condition c=l.newCondition();
        TimeUnit.SECONDS.sleep(3);
        System.out.println("saleTick"+Thread.currentThread().getName()+  num--);
    }
}