import java.math.BigInteger;
import java.util.concurrent.atomic.AtomicReference;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

class SequenceGenerator
{
    private static BigInteger           MULTIPLIER;
    private AtomicReference<BigInteger> element;

    public SequenceGenerator()
    {
        if (MULTIPLIER == null)
            MULTIPLIER = BigInteger.valueOf(2);
        element = new AtomicReference<BigInteger>(
                BigInteger.ONE);
    }
    public BigInteger next()
    {
        BigInteger value;
        BigInteger next;
        do {
            value = element.get();
            next = value.multiply(MULTIPLIER);
        } while (!element.compareAndSet(value, next));
        return value;
    }
}

class Sequence implements Runnable
{
    Thread thread;
    int id;
    int count;
    SequenceGenerator sg;
    ArrayList<BigInteger> sequence = new ArrayList<BigInteger>();
    boolean printed = false;

    Sequence(final int id, final int count,
             SequenceGenerator sg)
    {
        this.count = count;
        this.id    = id;
        this.sg    = sg;
        thread     = new Thread(this);

        System.out.println("Создан поток " + id);
        thread.start();
    }
    @Override
    public void run() {
        try {
            for (int i = 0; i < count; i++) {
                sequence.add(sg.next());
                Thread.sleep((long) (
                        (Math.random()*2 + 1)*30));
            }
        } catch (InterruptedException e) {
            System.out.println("Поток " + id
                    + " прерван");
        }
        System.out.println("Поток " + id + " завершён");
        printSequence();
    }
    public void printSequence()
    {
        if (printed)
            return;
        String tmp = "[";
        for (int i = 0; i < sequence.size(); i++) {
            if (i > 0)
                tmp += ", ";
            String nb = String.valueOf(sequence.get(i));
            while (nb.length() < 9)
                nb = " " + nb;
            tmp += nb;
        }
        tmp += "]";
        System.out.println("Последовательность потока "
                + id + " : " + tmp);
        printed = true;
    }
}

public class AtomicTest {
    public static void main(String[] args)
    {
        SequenceGenerator sg = new SequenceGenerator();
        List<Sequence> sequences = new ArrayList<Sequence>();
        for (int i = 0; i < 10; i++) {
            Sequence seq = new Sequence(i + 1, 3, sg);
            sequences.add(seq);
        }
        System.out.println("\nРасчет последовательностей\n");
        int summa;
        // Ожидания завершения потоков
        do {
            summa = 0;
            for (int i = 0; i < sequences.size(); i++) {
                if (!sequences.get(i).thread.isAlive()) {
                    sequences.get(i).printSequence();
                    summa++;
                }
            }
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {}
        } while (summa < sequences.size()) ;
        System.out.println("\n\nРабота потоков завершена");
        System.exit(0);
    }
}
