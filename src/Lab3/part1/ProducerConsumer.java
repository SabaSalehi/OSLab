package Lab3.part1;

import java.util.concurrent.Semaphore;

public class ProducerConsumer {
    static final int NUM_SLOTS = 10;

    static Semaphore mutex = new Semaphore(1);
    static Semaphore empty = new Semaphore(NUM_SLOTS);
    static Semaphore full = new Semaphore(0);

    static int in = 0; // Next slot to produce value at
    static int out = 0; // Next slot to consume value from
    static int buffer[] = new int[NUM_SLOTS];

    public static void main(String args[]) throws InterruptedException {
        new Producer(1).start();
        new Producer(2).start();

        Thread.sleep(4000);
        new Consumer(1).start();
        new Consumer(2).start();
    }

    static class Producer extends Thread {
        int next_item;
        int id;

        Producer(int id) {
            this.id = id;
            next_item = id * 100;
        }

        @Override
        public void run() {
            while (true) {
                try {
                    System.out.println(String.format("<---PRODUCER %d---> Waiting for an empty slot", id));
                    // Wait for a slot to be available
                    empty.acquire();

                    // Acquire the mutex so that two threads don't modify the
                    // shared variables at the same time
                    mutex.acquire();

                    buffer[in] = next_item;

                    System.out.println(String.format("<---PRODUCER %d---> Inserted item '%d' at slot %d", id, next_item, in));

                    next_item++;
                    in = (in + 1) % NUM_SLOTS;

                    mutex.release();

                    full.release(); // Signal that one slot is currently full

                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    static class Consumer extends Thread {
        int id;

        Consumer(int id) {
            this.id = id;
        }

        @Override
        public void run() {
            int item;

            try {
                while (true) {
                    // Wait for an item to be available
                    System.out.println(String.format("<***CONSUMER %d***> Waiting for an item to be available", id));
                    full.acquire();

                    // Acquire the mutex so that two threads don't modify the
                    // shared variables at the same time
                    mutex.acquire();

                    item = buffer[out];


                    System.out.println(String.format("<***CONSUMER %d***> Consumed item '%d' from slot %d", id, item, out));

                    out = (out + 1) % NUM_SLOTS;

                    mutex.release();

                    empty.release(); // Signal that one slot is currently empty

                    Thread.sleep(1500);
                }

            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
