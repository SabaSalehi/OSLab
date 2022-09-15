package Lab1;

public class Lab1 {
    static final int NUM_ITERATIONS = 100000;

    public static void main(String[] args) {
        // TODO: Increment the counter in a separate thread
        Counter counter = new Counter();
        for (int i = 0; i < NUM_ITERATIONS; i++)
            counter.increment();

        // TODO: Set up two threads to increment the counter, will the result be
        // coherent?

        System.out.println("Counter value: " + counter.getValue());
    }

    static class Counter {
        int c = 0;

        Counter() {

        }

        public void increment() {
            c++;
        }

        public int getValue() {
            return c;
        }
    }

    static class Incrementer extends Thread {
        Counter c;

        Incrementer(Counter c) {
            this.c = c;
        }

        @Override
        public void run() {
            // TODO: Increment the counter here instead of in the main thread
        }
    }
}
