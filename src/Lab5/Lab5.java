package Lab5;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Lab5 {

  public static void main(String args[]) throws InterruptedException {
    Barber barber = new Barber();
    barber.start();

    // Start 10 customers
    List<Thread> threads = IntStream
      .range(0, 10)
      .boxed()
      .map(
        i -> {
          Thread t = new Customer(i + 1);
          t.start();
          return t;
        }
      )
      .collect(Collectors.toList());

    for (Thread t : threads) {
      t.join();
    }

    // This is deprecated but whatever
    barber.stop();
    return;
  }

  public static class Barber extends Thread {

    @Override
    public void run() {
      System.out.println("<BARBER> Started.");
      while (true) {
        SleepingBarberMonitor.getNextCustomer();

        // Perform haircut
        try {
          Thread.sleep(100);
          System.out.println("<BARBER> Cut customer's hair.");
        } catch (InterruptedException e) {
          throw new RuntimeException(e);
        }
      }
    }
  }

  public static class Customer extends Thread {

    private int id;

    public Customer(int id) {
      this.id = id;
    }

    @Override
    public void run() {
      if (SleepingBarberMonitor.getHaircut()) {
        System.out.printf("<CUSTOMER %d> Got my haircut!\n", id);
      } else {
        System.out.printf("<CUSTOMER %d> Maybe some other time :(\n", id);
      }
    }
  }

  public static class SleepingBarberMonitor {

    private static final int MAX_CUSTOMERS = 5;

    // Number of waiting customers
    private static int numberCustomers = 0;

    // TODO: Define the two condition vars here
    // (https://docs.oracle.com/javase/7/docs/api/java/util/concurrent/locks/Condition.html)
    // Signal this condition when the barber is ready to see the next customer
    // Signal this condition upon the arrival of a customer

    // Barber calls this to wait for next customer
    public static void getNextCustomer() {
      // TODO:
      // 1. If there are currently no customers, wait for the right condition.
      // 2. Decrement the # of waiting customers
      // 3. Signal the appropriate condition to inform a customer that the barber is ready
    }

    // This methods returns true if the customer successfully got a haircut,
    // it returns false otherwise (if the customer decided not to wait).
    public static boolean getHaircut() {
      // TODO:
      // 1. Check if the customer should wait or leave
      // 2. Increment the number of waiting customers
      // 3. Signal the arrival of a new customer (Should only the first customer do this?
      //    Or it doesn't matter?)
      // 4. Wait for the barber to be ready for you
      // 5. We're done :)

      return false;
    }
  }
}
