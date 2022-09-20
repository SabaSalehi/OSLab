package Lab2;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Lab2 {
    private static final int NUM_CHILDREN = 100;

    public static void main(String[] args) {
        Parent mom = new Parent();
        Parent dad = new Parent();

        mom.setPartner(dad);
        dad.setPartner(mom);

        // List of children
        List<Child> children = IntStream
                .range(1, NUM_CHILDREN + 1)
                .boxed()
                .map(id -> new Child(id, mom, dad))
                .toList();
        
        System.out.println("Asking for permission!");

        // TODO: Fire up a thread for each child and ask for their parent's
        // permission to hang out with their friends!

        System.out.println("Done!");
    }
}
