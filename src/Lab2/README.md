# Lab 2 - Deadlocks

How does a deadlock occur? Imagine that we have two locks (the below shall be presented in pseudocode).
```Java
Lock lock1
Lock lock2
```

And now imagine that we have two threads that look something like this
```Java
void thread1() {
    obtainLock(lock1);
    obtainLock(lock2);
    
    // Do meaningful work here!

    releaseLock(lock2);
    releaseLock(lock1);
}

void thread2() {
    obtainLock(lock2);
    obtainLock(lock1);
    
    // Do meaningful work here!

    releaseLock(lock1);
    releaseLock(lock2);
}

```

It is trivial to see how the threads may end up competing to obtain the same lock, and when this occurs we have a **deadlock**.


Also consider reading up more on deadlocks [here](https://www.tutorialspoint.com/java/java_thread_deadlock.htm). Now that we've obtained a sufficient understanding of deadlocks, we shall attempt to produce one.

## Programming Problem
We shall be modeling a day in the lives of a family with lots of children. 
The children would all like to go out with their friends, however, in order to do so they must each ask their parents for permission.

Each child has an ID, and depending on his ID he might either prefer to ask his mom or dad for permission. The parents on the other hand will always seek the approval of their partners before allowing a child to go out.

### Task 1
Study all the code that has been given to you.

### Task 2
Implement the `askPermission` method in the `Parent` class. Each parent should keep track of a list of children that have asked for permission. A parent will not grant permission to the same child twice.  **Is synchronisation necessary here?** Before granting permission to a child, a parent always checks with his/her partner by invoking his/her partner's `askPermissionAsParent` method.

### Task 3
Implement the `askPermissionAsParent` method in the `Parent` class. This method is relatively simple, the `Parent` verifies that permission has never been given to the same child prior to this before approving the request.

### Task 4
Implement the `main` method in the `Lab2` class. Here, you should use a Thread to ask for permission on behalf of each `Child`. Once you are done, execute the program and see if a deadlock occurs. If it does, can you figure out why it happened? Can you propose solutions to the problem? 