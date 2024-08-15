package math.simulation.random_polytopes;

import math.simulation.common.BallRestrictionSVG;
import math.simulation.common.PoissonPointProcessSVG;
import math.simulation.common.SaveFileAbstract;
import org.apache.batik.svggen.SVGGraphics2D;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static math.simulation.common.SVGGenerator.getSVGGraphics2D;
import static math.simulation.common.SVGGenerator.saveSVGGraphics2D;

public class SimulationExamples {

    public static void convexHullEasyExample() throws Exception {
        List<double[]> points = new ArrayList<>();

        points.add(new double[]{0, 0});
        points.add(new double[]{7, 4});
        points.add(new double[]{6, 0});
        points.add(new double[]{5, -1});
        points.add(new double[]{5, 1});

        ConvexHullSVG ch = new ConvexHullSVG(points);

        ch.printPoints();

        ch.generate();

        ch.printPointsHull();
    }

    public static void randomPolytopeInBallWithIntensityQuick(double intensity) throws Exception {
        PoissonPointProcessSVG poissonPointProcessSVG = new PoissonPointProcessSVG(new int[]{8, 6}, intensity);
        poissonPointProcessSVG.generate();
        //poissonPointProcessSVG.printPoints();

        BallRestrictionSVG ballRestrictionSVG = new BallRestrictionSVG(new double[]{4, 3}, 2);
        poissonPointProcessSVG.restrictTo(ballRestrictionSVG);

        ConvexHullSVG convexHullSVG = new ConvexHullSVG(poissonPointProcessSVG.getRestrictedPoints());
        convexHullSVG.generate();
        convexHullSVG.printPointsHull();

        SVGGraphics2D svgGraphics2D[] = new SVGGraphics2D[]{
                getSVGGraphics2D(), // 0 REMOVE POINT IN CONVEX HULL
                getSVGGraphics2D(), // 1 SHOW ONLY POLYTOPE
        };

        ballRestrictionSVG.drawRestriction(svgGraphics2D[0], Color.RED);

        poissonPointProcessSVG.drawGrid(svgGraphics2D[0]);
        poissonPointProcessSVG.drawGrid(svgGraphics2D[1]);

        convexHullSVG.drawHullPolygon(svgGraphics2D[0], Color.BLUE);
        convexHullSVG.drawHullPoints(svgGraphics2D[0], Color.BLACK);

        convexHullSVG.drawHullPolygon(svgGraphics2D[1], Color.BLUE);
        convexHullSVG.drawHullPoints(svgGraphics2D[1], Color.BLACK);


        for (int i = 0; i < svgGraphics2D.length; i++) {
            saveSVGGraphics2D(svgGraphics2D[i], String.format("%s.f_0=%d.step_%d", convexHullSVG.getFullSaveName(), convexHullSVG.getPointsHull().size(), i));
        }

        System.out.println(String.format("intensity=%f", poissonPointProcessSVG.getIntensity()));
        System.out.println(String.format("f_0 =%d", convexHullSVG.getPointsHull().size()));

    }

    public static void randomPolytopeInBallWithIntensity(double intensity) throws Exception {
        PoissonPointProcessSVG poissonPointProcessSVG = new PoissonPointProcessSVG(new int[]{8, 6}, intensity);
        poissonPointProcessSVG.generate();
        poissonPointProcessSVG.printPoints();
        poissonPointProcessSVG.save();

        BallRestrictionSVG ballRestrictionSVG = new BallRestrictionSVG(new double[]{4, 3}, 2);
        poissonPointProcessSVG.restrictTo(ballRestrictionSVG);

        ConvexHullSVG convexHullSVG = new ConvexHullSVG(poissonPointProcessSVG.getRestrictedPoints());
        convexHullSVG.generate();
        convexHullSVG.printPointsHull();

        SVGGraphics2D svgGraphics2D[] = new SVGGraphics2D[]{
                getSVGGraphics2D(), // 0 CONVEX BODY K
                getSVGGraphics2D(), // 1 GRID
                getSVGGraphics2D(), // 2 POISSON NUMBERS
                getSVGGraphics2D(), // 3 POINTS IN GRIND
                getSVGGraphics2D(), // 4 ONLY POINTS
                getSVGGraphics2D(), // 5 REMOVE POINTS NOT IN K
                getSVGGraphics2D(), // 6 CONVEX HULL OF POINTS IN K
                getSVGGraphics2D(), // 7 REMOVE POINT IN CONVEX HULL
                getSVGGraphics2D(), // 8 SHOW ONLY POLYTOPE
        };


        ballRestrictionSVG.drawRestriction(svgGraphics2D[0], Color.RED);
        ballRestrictionSVG.drawRestriction(svgGraphics2D[1], Color.RED);
        ballRestrictionSVG.drawRestriction(svgGraphics2D[2], Color.RED);
        ballRestrictionSVG.drawRestriction(svgGraphics2D[3], Color.RED);
        ballRestrictionSVG.drawRestriction(svgGraphics2D[4], Color.RED);
        ballRestrictionSVG.drawRestriction(svgGraphics2D[5], Color.RED);
        ballRestrictionSVG.drawRestriction(svgGraphics2D[6], Color.RED);
        ballRestrictionSVG.drawRestriction(svgGraphics2D[7], Color.RED);

        poissonPointProcessSVG.drawGrid(svgGraphics2D[0]);
        poissonPointProcessSVG.drawGrid(svgGraphics2D[1], Color.GRAY);
        poissonPointProcessSVG.drawGrid(svgGraphics2D[2], Color.GRAY);
        poissonPointProcessSVG.drawGrid(svgGraphics2D[3], Color.GRAY);
        poissonPointProcessSVG.drawGrid(svgGraphics2D[4]);
        poissonPointProcessSVG.drawGrid(svgGraphics2D[5]);
        poissonPointProcessSVG.drawGrid(svgGraphics2D[6]);
        poissonPointProcessSVG.drawGrid(svgGraphics2D[7]);
        poissonPointProcessSVG.drawGrid(svgGraphics2D[8]);

        poissonPointProcessSVG.writePoissonNumbers(svgGraphics2D[2], Color.BLUE);
        poissonPointProcessSVG.writePoissonNumbers(svgGraphics2D[3], Color.BLUE);

        poissonPointProcessSVG.drawPoints(svgGraphics2D[3], Color.BLACK);
        poissonPointProcessSVG.drawPoints(svgGraphics2D[4], Color.BLACK);


        poissonPointProcessSVG.drawRestrictedPoints(svgGraphics2D[5], Color.BLACK);

        convexHullSVG.drawHullPolygon(svgGraphics2D[6], Color.BLUE);
        poissonPointProcessSVG.drawRestrictedPoints(svgGraphics2D[6], Color.BLACK);

        convexHullSVG.drawHullPolygon(svgGraphics2D[7], Color.BLUE);
        convexHullSVG.drawHullPoints(svgGraphics2D[7], Color.BLACK);

        convexHullSVG.drawHullPolygon(svgGraphics2D[8], Color.BLUE);
        convexHullSVG.drawHullPoints(svgGraphics2D[8], Color.BLACK);


        for (int i = 0; i < svgGraphics2D.length; i++) {
            saveSVGGraphics2D(svgGraphics2D[i], String.format("%s.f_0=%d.step_%d", convexHullSVG.getFullSaveName(), convexHullSVG.getPointsHull().size(), i));
        }

        System.out.println(String.format("intensity=%f", poissonPointProcessSVG.getIntensity()));
        System.out.println(String.format("f_0 =%d", convexHullSVG.getPointsHull().size()));
    }

    public static void randomPolytopeInBallIntensitySimulation() throws Exception {

        int maxExponent = 20;

        double[] intensities = new double[maxExponent+1];

        for( int i = 0; i <= maxExponent; i++) {
            intensities[i] = Math.pow(2.0, (double) i);
        }

        String nameBase = SaveFileAbstract.generateNameBase();

        for (int i = 0; i < intensities.length; i++) {
            SaveFileAbstract.setSaveCode(String.format("%s-%f", nameBase, intensities[i]));
            randomPolytopeInBallWithIntensityQuick(intensities[i]);
        }
    }

    public static void randomPolytopeInBallGeneration() throws Exception {
        double intensity = 1.0;

        String nameBase = SaveFileAbstract.generateNameBase();
        SaveFileAbstract.setSaveCode(String.format("%s-%f", nameBase, intensity));
        randomPolytopeInBallWithIntensity(intensity);
    }

}
