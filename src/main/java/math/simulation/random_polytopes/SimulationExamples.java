package math.simulation.random_polytopes;

import math.simulation.common.PoissonPointProcessSVG;
import math.simulation.common.SaveFileAbstract;
import org.apache.batik.svggen.SVGGraphics2D;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static math.simulation.common.SVGGenerator.*;

public class SimulationExamples {

    public static void convexHullEasyExample() throws Exception {
        List<double[]> points = new ArrayList<>();

        points.add(new double[]{0, 0});
        points.add(new double[]{7, 4});
        points.add(new double[]{6,0});
        points.add(new double[]{5,-1});
        points.add(new double[]{5,1});

        ConvexHullSVG ch = new ConvexHullSVG(points);

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

    public static void randomPolytopeInBall() throws Exception {
        PoissonPointProcessSVG poissonPointProcessSVG = new PoissonPointProcessSVG(new int[]{8, 6}, 8);
        poissonPointProcessSVG.generate();
        poissonPointProcessSVG.printPoints();
        poissonPointProcessSVG.save();

        ConvexHullSVG convexHullSVG = new ConvexHullSVG(poissonPointProcessSVG.getPoints());
        convexHullSVG.generate();
        convexHullSVG.printPointsHull();

        SVGGraphics2D svgGraphics2D[] = new SVGGraphics2D[]{
                getSVGGraphics2D(), // 0 GRID
                getSVGGraphics2D(), // 1 POISSON NUMBERS
                getSVGGraphics2D(), // 2 POINTS IN GRIND
                getSVGGraphics2D(), // 3 ONLY POINTS
                getSVGGraphics2D(), // 4 KONVEX BODY K
                getSVGGraphics2D(), // 5 REMOVE POINTS NOT IN K
                getSVGGraphics2D(), // 6 CONVEX HULL OF POINTS IN K
                getSVGGraphics2D(), // 7 REMOVE POINT IN CONVEX HULL
                getSVGGraphics2D(), // 8 SHOW ONLY POLYTOPE
        };

        convexHullSVG.drawHullPolygon(svgGraphics2D[6], Color.RED);
        convexHullSVG.drawHullPolygon(svgGraphics2D[7], Color.RED);
        convexHullSVG.drawHullPolygon(svgGraphics2D[8], Color.RED);


        poissonPointProcessSVG.drawGrid(svgGraphics2D[0], Color.GRAY);
        poissonPointProcessSVG.drawGrid(svgGraphics2D[1], Color.GRAY);
        poissonPointProcessSVG.drawGrid(svgGraphics2D[2], Color.GRAY);
        poissonPointProcessSVG.drawGrid(svgGraphics2D[3]);
        poissonPointProcessSVG.drawGrid(svgGraphics2D[4]);
        poissonPointProcessSVG.drawGrid(svgGraphics2D[5]);
        poissonPointProcessSVG.drawGrid(svgGraphics2D[6]);
        poissonPointProcessSVG.drawGrid(svgGraphics2D[7]);

        poissonPointProcessSVG.writePoissonNumbers(svgGraphics2D[1], Color.BLUE);
        poissonPointProcessSVG.writePoissonNumbers(svgGraphics2D[2], Color.BLUE);




        for (int i = 0; i < svgGraphics2D.length; i++) {
            saveSVGGraphics2D(svgGraphics2D[i], String.format("%s.step_%d", convexHullSVG.getFullSaveName(), i));
        }

        System.out.println(String.format("intensity=%f", poissonPointProcessSVG.getIntensity()));
        System.out.println(String.format("f_0 =%d", convexHullSVG.getPointsHull().size()));
    }

}
