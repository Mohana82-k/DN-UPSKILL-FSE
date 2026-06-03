public class VirtualThreadsDemo {
    public static void main(String[] args) throws InterruptedException {
        long start = System.currentTimeMillis();
        Thread[] threads = new Thread[100_000];
        for (int i = 0; i < threads.length; i++) {
            int id = i;
            threads[i] = Thread.startVirtualThread(() -> {
                System.out.println("Thread " + id + " running");
            });
        }
        for (Thread t : threads) t.join();
        long end = System.currentTimeMillis();
        System.out.println("100k virtual threads finished in " + (end - start) + " ms");
    }
}
/*
Thread 0 running
Thread 1 running
Thread 99998 running
Thread 99999 running
100k virtual threads finished in 150 ms
*/
