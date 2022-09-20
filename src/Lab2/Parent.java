package Lab2;

import java.util.ArrayList;

public class Parent {
    private ArrayList<Integer> grantedPermission;
    private Parent partner;

    // TODO: Take care of synchronization!
    public boolean askPermission(int childId) {
        // TODO: Implement this
        // Note down the ID of every child that asks for permission, a parent
        // should never give permission to the same child twice!
        // Before giving permission to the child, the parent should also first
        // consult his/her partner (by calling askPermissionAsParent)
        return false;
    }

    // TODO: Take care of synchronization!
    // When a partner wants to confirm that he/she should give permission to a child,
    // he/she would call this method on his/her partner. As long as permission has never been
    // given before to the same child, the partner should always approve the request.
    public boolean askPermissionAsParent(int childId) {
        // TODO: Implement this
        return false;
    }
}
