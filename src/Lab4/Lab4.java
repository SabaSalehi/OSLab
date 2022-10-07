package Lab4;

public class Lab4 {
    final static int N = 100; // number of speakers

    //TODO: make sure to use the appropriate semaphores so that the speakers give their speech in increasing order of their id

    public static void main(String args[]) {
//        Seminar seminar = new Seminar();
        for (int i = 1; i <= N; i++) {
            new Speaker(i).start();
        }
    }

    static class Speaker extends Thread {
        int id;
        Speaker(int id) {
            this.id = id;
        }

        @Override
        public void run() {
            //signing in
            System.out.println(String.format("Added %dst speaker's name to the list.", this.id));

            //TODO: wait for all other speakers to arrive

            //giving speech
            System.out.println(String.format("This is the %dst speaker giving their speech.", this.id));
        }
    }
}
