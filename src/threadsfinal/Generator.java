/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package threadsfinal;

import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author errol
 */
/**
 * A Java class Generator that inherits from the Runnable interface. Override
 * and implement the classes run() method in which the thread runs every 5
 * milliseconds and fills the Integer array with 10 random numbers of integer
 * type. The thread should only fill the array if it is empty otherwise it must
 * wait. (1.5 pts)
 */
public class Generator implements Runnable {

    private final Simulation simulation;
    private final Random random;

    public Generator(Simulation simulation) {
        this.simulation = simulation;
        this.random = new Random();
    }

    @Override
    public void run() {
        while (true) {
            synchronized (simulation) {
                if (simulation.isArrayEmpty()) {
                    ArrayList<Integer> tempArray = new ArrayList<>();

                    for (int i = 0; i < 10; i++) {
                        tempArray.add(random.nextInt(100));
                    }

                    simulation.fillArray(tempArray);
                    simulation.print();
                    simulation.notifyAll();
                } else {
                    try {
                        simulation.wait();
                    } catch (InterruptedException e) {
                        System.out.println("Generator thread interrupted: " + e.getMessage());
                        Thread.currentThread().interrupt();
                        break;
                    }
                }
            }
        }
    }

}
