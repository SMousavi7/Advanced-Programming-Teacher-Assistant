import java.util.ArrayList;

class producer implements Runnable{
    ArrayList q;
    producer(ArrayList q){
        this.q = q;
    }
    @Override
    public void run() {
        while (true){
            synchronized (q){
                System.out.println("thread "+ Thread.currentThread().getName() + " is producing...");
                int temp = (int) (Math.random() * 10);
                q.add(temp);
                System.out.println("thread " + Thread.currentThread().getName() + " produced " + temp + " and finished.");
                System.out.println(q.size());
                System.out.println("-------------------------------------------------------------");
                q.notify();
                try {
                    Thread.currentThread().sleep(500);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}

class consumer implements Runnable {
    ArrayList q;

    consumer(ArrayList q){
        this.q = q;
    }
    @Override
    public void run() {

            while(true) {
                synchronized (q) {
                    System.out.println("thread " + Thread.currentThread().getName() + " is consuming...");
                    while (q.size() == 0) {
                        try {
                            q.wait();
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    int temp = (int) q.remove(0);
                    System.out.println("thread " + Thread.currentThread().getName() + " has consumed " + temp + " and finished");
                    System.out.println(q.size());
                    System.out.println("-----------------------------------------------------------------");
                    try {
                        Thread.currentThread().sleep(500);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
    }
}

public class ProduceConsumer {
    public static void main(String[] args) throws InterruptedException {
        ArrayList queue = new ArrayList<Integer>();
        producer p1 = new producer(queue);
        producer p2 = new producer(queue);
        producer p3 = new producer(queue);
        consumer c1 = new consumer(queue);
        consumer c2 = new consumer(queue);
        consumer c3 = new consumer(queue);

        Thread t1 = new Thread(p1, "producer_1");
        Thread t2 = new Thread(p2, "producer_2");
        Thread t3 = new Thread(p3, "producer_3");
        Thread t4 = new Thread(c1, "consumer_1");
        Thread t5 = new Thread(c2, "consumer_2");
        Thread t6 = new Thread(c3, "consumer_3");

        t1.start();
        t2.start();
        t3.start();
        t4.start();
        t5.start();
        t6.start();
    }
}
