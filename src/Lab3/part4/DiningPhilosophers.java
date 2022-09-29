package Lab3.part4;

import java.util.List;
import java.util.concurrent.Semaphore;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.IntStream;

// TODO: 1. Run the program and see if you encounter a deadlock
//       2. Now make it work :)
public class DiningPhilosophers {
    final int NUM_PHILOSOPHERS = 5;
    final int NUM_FORKS = NUM_PHILOSOPHERS;

    List<Semaphore> forks = IntStream
            .range(0, NUM_PHILOSOPHERS)
            .boxed()
            .map(i -> new Semaphore(1))
            .toList(); // A mutex for each fork

    public static void main(String args[]) {
        new DiningPhilosophers().runSimulation();
    }

    public void runSimulation() {
        IntStream
                .range(0, NUM_PHILOSOPHERS)
                .boxed()
                .map(i -> {
                    Thread t = new Philosopher(i);
                    t.start();
                    return t;
                }).toArray();
    }


    class Philosopher extends Thread {
        private final int MAX_SLEEP_TIME = 500;
        private int id;

        Philosopher(int id) {
            this.id = id;
        }

        @Override
        public void run() {
            think();

            try {
                eat();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        private void print(String m) {
            System.out.println(String.format("<P%d> ", id + 1) + m);
        }

        private void think() {
            try {
                print("Start thinking");
                Thread.sleep(MAX_SLEEP_TIME);
                print("Done thinking");
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        private void eat() throws InterruptedException {
            print("Wants to start eating");

            // Pickup forks, eat, and release forks
            print("Trying to pick up left fork");
            pickupFork(leftForkId());

            Thread.sleep(ThreadLocalRandom.current().nextInt(0, MAX_SLEEP_TIME));

            print("Trying to pick up right fork");
            pickupFork(rightForkId());

            try {
                Thread.sleep(ThreadLocalRandom.current().nextInt(0, MAX_SLEEP_TIME));
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            print("Done eating");

            print("Releasing right fork");
            releaseFork(rightForkId());

            print("Releasing left fork");
            releaseFork(leftForkId());
        }

        private void pickupFork(int forkId) throws InterruptedException {
            forks.get(forkId).acquire();
        }

        private void releaseFork(int forkId) {
            forks.get(forkId).release();
        }

        private int leftForkId() {
            return id;
        }

        private int rightForkId() {
            return (id + 1) % NUM_FORKS;
        }
    }
}
