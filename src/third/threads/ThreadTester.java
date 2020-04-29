package third.threads;

public class ThreadTester {
    public static void main(String[] args) {
        Runnable pr = new PrintMe();
        Thread t1 = new Thread(pr);
        Thread t2 = new Thread(pr);
        Thread t3 = new Thread(pr);

        t1.setName("Thread 1 - Nike");
        t2.setName("Thread 2 - Adidas");
        t3.setName("Thread 3 - New balance");

        t3.setPriority(Thread.MAX_PRIORITY);
        t1.setPriority(Thread.MIN_PRIORITY);

        t1.start();
        t2.start();
        t3.start();
    }
}
