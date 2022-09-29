package Lab3.part2;

import java.util.concurrent.Semaphore;
import java.util.concurrent.ThreadLocalRandom;

// No reader should be kept waiting unless a writer has already
// obtained permission to use the shared object
// Readers are prioritized!
public class FirstReadersWritersProblem {

    static Semaphore read_write = new Semaphore(1);
    static Semaphore mutex = new Semaphore(1); // Protects the readcount
    static int readCount = 0; // Keep track of how many active readers we have

    static int sharedVariable = 42; // The variable that readers and writers will share


    public static void main(String args[]) throws InterruptedException {
        new Reader().start();
        new Reader().start();
        new Reader().start();
        new Reader().start();
        new Reader().start();

        new Writer().start();
        new Writer().start();
    }

    static class Writer extends Thread {
        public void run() {
            while (true) {
                try {
                    // Try to acquire the read write lock, this can only be obtained
                    // if no one is currently reading/writing
                    read_write.acquire();

                    sharedVariable = (sharedVariable * 5 + 256) / 2;
                    System.out.println(String.format("<---Writer ---> Updated value to %d", sharedVariable));

                    read_write.release(); // Release the semaphore

                    Thread.sleep(ThreadLocalRandom.current().nextInt(0, 2000));
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    static class Reader extends Thread {
        public void run() {
            while (true) {
                try {
                    mutex.acquire(); // Obtain lock before accessing read count
                    readCount++;

                    if (readCount == 1) {
                        // If we are the first reader, obtain the readWrite semaphore
                        // so no writers can interfere with us
                        read_write.acquire();
                    }

                    mutex.release(); // We no longer need to modify the readCount, hence we release the lock

                    System.out.println(String.format("<---Reader ---> Read value %d", sharedVariable));

                    // We are done, we need to decrement the readCount
                    mutex.acquire();
                    readCount--;

                    if (readCount == 0) {
                        // If we are the last reader, we should signal the readwrite semaphore
                        // so that a writer can potentially start writing
                        read_write.release();
                    }


                    mutex.release();

                    Thread.sleep(ThreadLocalRandom.current().nextInt(0, 1000));
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}
