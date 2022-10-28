# Sleeping barber problem
The objective of this lab is to code a solution to the **Sleeping barber problem**.

A hypothetical barbershop has one barber, one barber chair, and a waiting room with n chairs (n may be 0) for waiting customers. The following rules apply:

- If there are no customers, the barber falls asleep in the chair
- A customer must wake the barber if he is asleep
- If a customer arrives while the barber is working, the customer leaves if all chairs are occupied and sits in an empty chair if it's available
- When the barber finishes a haircut, he inspects the waiting room to see if there are any waiting customers and falls asleep if there are none

We have 3 classes,

1. A **monitor** class to coordinate everything.

2. A **barber** class that is basically just a thread that loops forever. In each iteration, the barber simply tries to 'get' a next customer by calling the monitor's **getNextCustomer** method.

3. A **customer** class that calls the monitor's **getHaircut** method once to get his haircut.

For this lab, you are required to implement the two methods in the monitor class. You will need to use condition variables, more details may be found [here](https://docs.oracle.com/javase/7/docs/api/java/util/concurrent/locks/Condition.html).

The pseudocode for the solution may be found [here](https://www.cs.cornell.edu/courses/cs414/2001SU/barber.htm) and also on the last page of [this PDF](https://moodle.concordia.ca/moodle/pluginfile.php/5703928/mod_resource/content/1/05_Tutorial%205.pdf).