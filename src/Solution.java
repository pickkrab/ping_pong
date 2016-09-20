class Solution {
    private static final Object lock = new Object();
    private static boolean ready = true;

    public static void main(String[] args) {
        Thread thread1 = new Thread(() -> {
            while (ready) {
                System.out.println("Ping");
                synchronized (lock) {
                    try {
                        ready = false;
                        lock.notify();
                        lock.wait();
                    } catch (InterruptedException e) {
                        throw new RuntimeException("Thread1 is interrupt");
                    }
                }
            }
        });

        Thread thread2 = new Thread(() -> {
            while (!ready) {
                System.out.println("Pong");
                synchronized (lock) {
                    try {
                        ready = true;
                        lock.notify();
                        lock.wait();
                    } catch (InterruptedException e) {
                        throw new RuntimeException("Thread2 is interrupt");
                    }
                }
            }
        });

        thread1.start();
        thread2.start();
    }
}
