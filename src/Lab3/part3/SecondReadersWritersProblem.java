package Lab3.part3;

import Lab3.part2.FirstReadersWritersProblem;

import java.util.concurrent.Semaphore;
import java.util.concurrent.ThreadLocalRandom;

// No writer, once added to the queue, shall be kept waiting longer than absolutely necessary.
// Writers-preference :)
public class SecondReadersWritersProblem {
    static int readCount = 0;
    static int writeCount = 0;

    static Semaphore readMutex = new Semaphore(1);
    static Semaphore writeMutex = new Semaphore(1);
    static Semaphore readTry = new Semaphore(1);
    static Semaphore resource = new Semaphore(1);

    static int sharedVariable = 42;

    public static void main(String args[]) throws InterruptedException {
        new Reader().start();
        new Reader().start();

        new Writer().start();
        new Writer().start();
        new Writer().start();
        new Writer().start();
    }


    static class Writer extends Thread {
        public void run() {
            while (true) {
                try {
                    // Entry section
                    writeMutex.acquire(); // Get mutex to access writeCount
                    writeCount++;
                    if (writeCount == 1)
                        // If we are the first reader, we want to lock the readers out
                        readTry.acquire();
                    writeMutex.release();
                    // Ensure that we have the lock on the resource
                    // we don't want another writer to access the resource
                    // simultaneously
                    resource.acquire();

                    // Critical section
                    sharedVariable = (sharedVariable * 5 + 256) / 2;
                    System.out.println(String.format("<---Writer ---> Updated value to %d", sharedVariable));

                    // Exit section
                    resource.release();
                    writeMutex.acquire();
                    writeCount--;
                    if (writeCount == 0)
                        // If we are the last writer, make it now possible for readers to access the resource
                        readTry.release();
                    writeMutex.release();

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
                    // Entry section
                    readTry.acquire(); // Try to get permission to read
                    readMutex.acquire(); // Get mutex to access readCount
                    readCount++;
                    if (readCount == 1)
                        // Lock the resource if you're the first reader
                        resource.acquire();
                    readMutex.release(); // Release the lock of readCount
                    // Indicate that we're done adding ourselves as a reader
                    // Now another writer might come in and block all future readers
                    // or another reader might join in and start reading
                    readTry.release();

                    // Critical section
                    System.out.println(String.format("<---Reader ---> Read value: %d", sharedVariable));

                    // Exit section
                    readMutex.acquire(); // Obtain lock to modify readCount
                    readCount--;
                    if (readCount == 0)
                        // Release the resource if we are the last reader
                        resource.release();
                    readMutex.release(); // Release lock on readCount

                    Thread.sleep(ThreadLocalRandom.current().nextInt(0, 2000));
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}
