package Lab1;

public class ThreadEx1 extends Thread {
    private String name;

    ThreadEx1 (String name) {
        this.name = name;
    }

    public void run() {
        for (int i=0; i<100; i++) {
            System.out.println("I am " + name);
        }
    }
}