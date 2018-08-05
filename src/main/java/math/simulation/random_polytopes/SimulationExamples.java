package math.simulation.random_polytopes;

import math.simulation.common.PoissonPointProcessSVG;
import math.simulation.common.SaveFileAbstract;

import java.util.ArrayList;
import java.util.List;

public class SimulationExamples {

    public static void convexHullEasyExample() throws Exception {
        List<double[]> points = new ArrayList<>();

        points.add(new double[]{0, 0});
        points.add(new double[]{7, 4});
        points.add(new double[]{6,0});
        points.add(new double[]{5,-1});
        points.add(new double[]{5,1});

        ConvexHull ch = new ConvexHull(points);

        ch.printPoints();

        ch.generate();

        ch.printPointsHull();
    }

    public static void randomPolytope() throws Exception {
        PoissonPointProcessSVG poissonPointProcessSVG = new PoissonPointProcessSVG(new int[]{8, 6}, 1);
        poissonPointProcessSVG.generate();
        poissonPointProcessSVG.save();
        poissonPointProcessSVG.svgStepConstruction();


    }

}
