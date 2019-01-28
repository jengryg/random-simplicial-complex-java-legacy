package math.simulation.random_polytopes;

import math.simulation.common.SaveFileAbstract;

public class Simulation {

    public static void main(String[] args) throws Exception {

        long startTime = System.nanoTime();

        simulation();

        System.out.println(String.format("Calculation Time %f ms", (System.nanoTime() - startTime) / 1000000.0));

        System.out.println(String.format("SaveCode = %s", SaveFileAbstract.getSaveCode()));

    }

    public static void simulation() throws Exception {
        SimulationExamples.randomPolytopeInBallIntensitySimulation();
    }
}