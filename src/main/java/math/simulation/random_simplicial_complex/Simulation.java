package math.simulation.random_simplicial_complex;


import math.simulation.common.SaveFileAbstract;

public class Simulation {

    public static void main(String[] args) throws Exception {

        long startTime = System.nanoTime();

        simulation();

        System.out.println(String.format("Calculation Time %f ms", (System.nanoTime() - startTime) / 1000000.0));

        System.out.println(String.format("SaveCode = %s", SaveFileAbstract.getSaveCode()));

    }

    // NOTE: 2024-08-15 code was adjusted and model data from 2017-11-28 was added to repository to make these
    // simulations runnable again.

    public static void simulation() throws Exception {
//        SimulationExamples.theGiantBeast();
//        SimulationExamples.theLonelyComplex();
//        SimulationExamples.onlyOnePointComplex();
//        SimulationExamples.onlyOneEdgeComplex();
//        SimulationExamples.onlyOneEdgeAndAPointComplex();
//        SimulationExamples.onlyOneFilledTriangle();
//        SimulationExamples.searchForTriangles();
//        SimulationExamples.thankYouComplex();
    }
}
