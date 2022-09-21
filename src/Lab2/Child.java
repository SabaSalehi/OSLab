package Lab2;

public class Child {
    private final int id;
    private final Parent mom;
    private final Parent dad;

    public Child(int id, Parent mom, Parent dad) {
        this.id = id;
        this.mom = mom;
        this.dad = dad;
    }

    public void askParentsPermission() {
        // Some children prefer asking their moms for permission!
        if (id % 2 == 0) {
            mom.askPermission(id);
        } else {
            dad.askPermission(id);
        }
    }
}
