/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package threadsfinal;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

/**
 *
 * @author errol
 */
/**
 * A private Integer ArrayList or similar data structure to an array type to
 * store Integers. (0.25 pts) A public synchronized print method to the
 * Simulation class to print the contents of the array in a single line with one
 * whitespace between each number. Call this print method after each thread does
 * its job once. (1.5 pts) A public method called runsim( ) in which one
 * instance of each of the 3 thread types above (1a, 1b, and 1c) are created.
 * The threads must then be executed using the Thread Executor service as we
 * studied in class. Let the simulation run such that each thread runs
 * continuously for 1, 2 and 5 mins. (1.5 pts) Design a thread synchronization
 * mechanism using the book examples that ensures that the 3 threads execute
 * sequentially in a synchronized manner one after the other - i.e., first the
 * generator thread runs, then the sorter thread runs and then finally the drain
 * thread, after which the cycle repeats - make sure to demonstrate this
 * sequence is occurring using print statements. (1.5 pts)
 */
public class Simulation {

    private final List<Integer> arrayList;
    private boolean isSorted = false;

    public Simulation() {
        this.arrayList = new ArrayList<>();
    }

    public synchronized void print() {
        System.out.print("Array contents: ");
        for (int i = 0; i < arrayList.size(); i++) {
            System.out.print(arrayList.get(i));
            if (i < arrayList.size() - 1) {
                System.out.print(" ");
            }
        }
        System.out.println();
    }

    public synchronized List<Integer> getArray() {
        return arrayList;
    }

    public synchronized int getArraySize() {
        return arrayList.size();
    }

    public synchronized void fillArray(ArrayList<Integer> newArray) {
        arrayList.clear();
        arrayList.addAll(newArray);
        isSorted = false;
    }

    public synchronized void clearArray() {
        arrayList.clear();
        isSorted = false;
    }

    public synchronized boolean isArrayEmpty() {
        return arrayList.isEmpty();
    }

    public synchronized boolean isSorted() {
        return isSorted;
    }

    public synchronized void setSorted(boolean sorted) {
        isSorted = sorted;
    }

    public void runsim(int duration) {
        System.out.println("Running simulation for " + duration + " minute(s).");

        ExecutorService executorService = Executors.newFixedThreadPool(3);

        Generator generator = new Generator(this);
        Sorter sorter = new Sorter(this);
        Drainer drainer = new Drainer(this);

        executorService.execute(generator);
        executorService.execute(sorter);
        executorService.execute(drainer);

        try {
            if (!executorService.awaitTermination(duration, TimeUnit.MINUTES)) {
                executorService.shutdownNow();
                System.out.println("Simulation ran for " + duration + " minutes(s).");
            }
        } catch (InterruptedException e) {
            System.out.println("Simulation interrupted: " + e.getMessage());
        }
    }

}
