public class ThreadDemo {
    public static void main(String[] args) {
        Thread t1 = new PrintThread("Hello from Thread 1");
        Thread t2 = new PrintThread("Hi from Thread 2");
        t1.start();
        t2.start();
    }
}
class PrintThread extends Thread {
    private String msg;
    PrintThread(String msg) { this.msg = msg; }
    public void run() {
        for (int i = 0; i < 5; i++) {
            System.out.println(msg);
            try { Thread.sleep(100); } catch (InterruptedException e) {}
        }
    }
}

/*
Hello from Thread 1
Hi from Thread 2
Hello from Thread 1
Hi from Thread 2
Hello from Thread 1
Hi from Thread 2
Hello from Thread 1
Hi from Thread 2
Hello from Thread 1
Hi from Thread 2
*/
