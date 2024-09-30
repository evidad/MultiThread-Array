/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package threadsfinal;

/**
 *
 * @author errol
 */
/**
 * A Java class Drainer that inherits from the Runnable interface. Override and
 * implement the classes run() method in which the thread runs every 5
 * milliseconds and clears the array sorted by the Sorter thread. It must only
 * attempt to perform the clear operation after the filling was done by
 * Generator thread and sorting by the Sorter thread was done by the first 2
 * threads. (1.5 pts)
 */
public class Drainer implements Runnable {

    private final Simulation simulation;

    public Drainer(Simulation simulation) {
        this.simulation = simulation;
    }

    public synchronized void clearList() {
        simulation.clearArray();
        System.out.println("Array cleared.");
    }

    @Override
    public void run() {
        while (true) {
            synchronized (simulation) {
                while (simulation.getArraySize() != 10 || !simulation.isSorted()) {
                    try {
                        simulation.wait();
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }

                clearList();
                simulation.notifyAll();
            }

            try {
                Thread.sleep(5);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
    }
}
