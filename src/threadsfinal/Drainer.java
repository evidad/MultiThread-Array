/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package threadsfinal;

/**
 *
 * @author errol
 */
public class Drainer implements Runnable {

    private final Simulation simulation;

    public Drainer(Simulation simulation) {
        this.simulation = simulation;  // Simulation object that holds the array
    }

    public synchronized void clearList() {
        simulation.clearArray(); // Clears the list in the Simulation object
        System.out.println("Array cleared.");
    }

    @Override
    public void run() {
        while (true) {
            synchronized (simulation) {
                // Wait until the array has been filled and sorted
                while (simulation.getArraySize() != 10 || !simulation.isSorted()) {
                    try {
                        simulation.wait();  // Wait until both conditions are met
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }

                // Once the array is sorted and filled, clear it
                clearList();

                // Notify all threads that the array has been cleared
                simulation.notifyAll();
            }

            // Sleep for 5 milliseconds between iterations
            try {
                Thread.sleep(5);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
    }
}
