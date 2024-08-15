package math.simulation.random_simplicial_complex;

import math.simulation.common.PoissonPointProcess;
import math.simulation.common.PoissonPointProcessSVG;
import math.simulation.common.SaveFileAbstract;
import org.apache.batik.svggen.SVGGraphics2D;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static math.simulation.common.SVGGenerator.*;

public class SimulationExamples {

    public static void theLonelyComplex() throws Exception {
        PoissonPointProcessSVG poissonPointProcessSVG = new PoissonPointProcessSVG("2017-11-28-00-24-58");
        poissonPointProcessSVG.printPoints();

        VietorisRipsComplexSVG vietorisRipsComplexSVG = new VietorisRipsComplexSVG(poissonPointProcessSVG.getPoints(), 0.999 * 0.9 * PoissonPointProcess.PERCOLATION);
        vietorisRipsComplexSVG.generateOneSkeleton();
        vietorisRipsComplexSVG.printSimplicies();
        vietorisRipsComplexSVG.generateComponents();
        vietorisRipsComplexSVG.recalculateComponentPointCount();
        vietorisRipsComplexSVG.recalculateComponentIndexOfLargest();
        vietorisRipsComplexSVG.save();

        SVGGraphics2D svgGraphics2D[] = new SVGGraphics2D[]{
                getSVGGraphics2D(), // 0 GRID
                getSVGGraphics2D(), // 1 POISSON NUMBERS
                getSVGGraphics2D(), // 2 POINTS IN GRIND
                getSVGGraphics2D(), // 3 ONLY POINTS
                getSVGGraphics2D(), // 4 CIRCLES AROUND POINTS
                getSVGGraphics2D(), // 5 CIRLCES AND 1-SKELETON
                getSVGGraphics2D(), // 6 1-SKELETON ONLY
                getSVGGraphics2D(), // 7 GIANT COMPONENT
        };

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

        vietorisRipsComplexSVG.drawCircles(svgGraphics2D[0]);
        vietorisRipsComplexSVG.drawCircles(svgGraphics2D[1]);
        vietorisRipsComplexSVG.drawCircles(svgGraphics2D[2]);
        vietorisRipsComplexSVG.drawCircles(svgGraphics2D[3]);
        vietorisRipsComplexSVG.drawCircles(svgGraphics2D[4], new Color(255, 255, 0, 128));
        vietorisRipsComplexSVG.drawCircles(svgGraphics2D[5], new Color(255, 255, 0, 128));
        vietorisRipsComplexSVG.drawCircles(svgGraphics2D[6]);
        vietorisRipsComplexSVG.drawCircles(svgGraphics2D[7]);

        vietorisRipsComplexSVG.drawSimplicesOfDimension(svgGraphics2D[5], Color.BLUE, 1);
        vietorisRipsComplexSVG.drawSimplicesOfDimension(svgGraphics2D[6], Color.BLUE, 1);
        for (int i = 0; i < vietorisRipsComplexSVG.getComponentCount(); i++) {

            if (vietorisRipsComplexSVG.getComponentIndexOfLargest() == i) {
                svgGraphics2D[7].setStroke(new BasicStroke(3));
                vietorisRipsComplexSVG.drawSimpliciesOfDimensionAndComponent(svgGraphics2D[7], Color.RED, 1, i);
                svgGraphics2D[7].setStroke(new BasicStroke());
            } else {
                vietorisRipsComplexSVG.drawSimpliciesOfDimensionAndComponent(svgGraphics2D[7], Color.BLUE, 1, i);
            }
        }
        poissonPointProcessSVG.drawPoints(svgGraphics2D[0]);
        poissonPointProcessSVG.drawPoints(svgGraphics2D[1]);
        poissonPointProcessSVG.drawPoints(svgGraphics2D[2], Color.BLACK);
        poissonPointProcessSVG.drawPoints(svgGraphics2D[3], Color.BLACK);
        poissonPointProcessSVG.drawPoints(svgGraphics2D[4], Color.BLACK);
        poissonPointProcessSVG.drawPoints(svgGraphics2D[5], Color.BLACK);
        poissonPointProcessSVG.drawPoints(svgGraphics2D[6], Color.BLACK);
        poissonPointProcessSVG.drawPoints(svgGraphics2D[7], Color.BLACK);

        for (int i = 0; i < svgGraphics2D.length; i++) {
            saveSVGGraphics2D(svgGraphics2D[i], String.format("%s.step_%d", vietorisRipsComplexSVG.getFullSaveName(), i));
        }

        System.out.println(String.format("intensity=%f", poissonPointProcessSVG.getIntensity()));
        System.out.println(String.format("distance=%f", vietorisRipsComplexSVG.getDistance()));
        System.out.println(String.format("points=%d", poissonPointProcessSVG.getPoints().size()));

        System.out.println(String.format("componentCount=%d", vietorisRipsComplexSVG.getComponentCount()));
    }

    public static void searchForTriangles() throws Exception {
        PoissonPointProcessSVG poissonPointProcessSVG = new PoissonPointProcessSVG("2017-11-28-00-24-58");
        poissonPointProcessSVG.printPoints();
        poissonPointProcessSVG.save();

        VietorisRipsComplexSVG vietorisRipsComplexSVG = new VietorisRipsComplexSVG("2017-11-28-00-24-58");

        vietorisRipsComplexSVG.generateOneSkeleton();
        vietorisRipsComplexSVG.printSimplicies();
        vietorisRipsComplexSVG.generateComponents();
        vietorisRipsComplexSVG.recalculateComponentPointCount();
        vietorisRipsComplexSVG.recalculateComponentIndexOfLargest();
        vietorisRipsComplexSVG.recalculateDegrees();
        vietorisRipsComplexSVG.recalculateFVector();
        vietorisRipsComplexSVG.save();

        vietorisRipsComplexSVG.findTriangles();
        vietorisRipsComplexSVG.printTriangles();

        List<SVGGraphics2D> svgGraphics2DList = new ArrayList<>();

        /*
            // GIANT COMPONENT
         */
        svgGraphics2DList.add(0, getSVGGraphics2D());

        poissonPointProcessSVG.drawGrid(svgGraphics2DList.get(0));
        vietorisRipsComplexSVG.drawCircles(svgGraphics2DList.get(0));

        for (int i = 0; i < vietorisRipsComplexSVG.getComponentCount(); i++) {

            if (vietorisRipsComplexSVG.getComponentIndexOfLargest() == i) {
                svgGraphics2DList.get(0).setStroke(new BasicStroke(3));
                vietorisRipsComplexSVG.drawSimpliciesOfDimensionAndComponent(svgGraphics2DList.get(0), Color.RED, 1, i);
                svgGraphics2DList.get(0).setStroke(new BasicStroke());
            } else {
                vietorisRipsComplexSVG.drawSimpliciesOfDimensionAndComponent(svgGraphics2DList.get(0), Color.BLUE, 1, i);
            }
        }

        poissonPointProcessSVG.drawPoints(svgGraphics2DList.get(0), Color.BLACK);


        /*
            // TRIANGLE SHOWCASE
         */
        svgGraphics2DList.add(1, getSVGGraphics2D());
        svgGraphics2DList.add(2, getSVGGraphics2D());
        svgGraphics2DList.add(3, getSVGGraphics2D());

        poissonPointProcessSVG.drawGrid(svgGraphics2DList.get(1));
        vietorisRipsComplexSVG.drawCircles(svgGraphics2DList.get(1));
        poissonPointProcessSVG.drawGrid(svgGraphics2DList.get(2));
        vietorisRipsComplexSVG.drawCircles(svgGraphics2DList.get(2));
        poissonPointProcessSVG.drawGrid(svgGraphics2DList.get(3));
        vietorisRipsComplexSVG.drawCircles(svgGraphics2DList.get(3));

        int numberOfIsolatedTriangles = 0;
        int numberOfAttachedTriangles = 0;
        int numberOfOtherTriangles = 0;

        for (int i = 0; i < vietorisRipsComplexSVG.getTriangles().size(); i++) {

            ArrayList<double[]> vertices = new ArrayList<>();


            int[] triangle = vietorisRipsComplexSVG.getTriangles().get(i);

            for (int j = 0; j < 3; j++) {
                vertices.add(vietorisRipsComplexSVG.getVertices().get(triangle[j]));
            }

            if (triangle[3] == 0) {
                // isolated
                svgGraphics2DList.get(1).setColor(Color.BLACK);
                svgGraphics2DList.get(1).setStroke(new BasicStroke(10));
                drawContainerBox(svgGraphics2DList.get(1), vertices);
                svgGraphics2DList.get(1).setStroke(new BasicStroke());
                numberOfIsolatedTriangles++;
            } else {
                // not isolated
                if (vietorisRipsComplexSVG.getComponentPointsMap()[triangle[0]] == vietorisRipsComplexSVG.getComponentIndexOfLargest()) {
                    // attached to giant component
                    svgGraphics2DList.get(2).setColor(Color.BLACK);
                    svgGraphics2DList.get(2).setStroke(new BasicStroke(10));
                    drawContainerBox(svgGraphics2DList.get(2), vertices);
                    svgGraphics2DList.get(2).setStroke(new BasicStroke());
                    numberOfAttachedTriangles++;
                } else {
                    // attached to some other component
                    svgGraphics2DList.get(3).setColor(Color.BLACK);
                    svgGraphics2DList.get(3).setStroke(new BasicStroke(10));
                    drawContainerBox(svgGraphics2DList.get(3), vertices);
                    svgGraphics2DList.get(3).setStroke(new BasicStroke());
                    numberOfOtherTriangles++;
                }
            }
        }

        for (int i = 0; i < vietorisRipsComplexSVG.getComponentCount(); i++) {

            if (vietorisRipsComplexSVG.getComponentIndexOfLargest() == i) {
                svgGraphics2DList.get(1).setStroke(new BasicStroke(3));
                svgGraphics2DList.get(2).setStroke(new BasicStroke(3));
                svgGraphics2DList.get(3).setStroke(new BasicStroke(3));
                vietorisRipsComplexSVG.drawSimpliciesOfDimensionAndComponent(svgGraphics2DList.get(1), Color.RED, 1, i);
                vietorisRipsComplexSVG.drawSimpliciesOfDimensionAndComponent(svgGraphics2DList.get(2), Color.RED, 1, i);
                vietorisRipsComplexSVG.drawSimpliciesOfDimensionAndComponent(svgGraphics2DList.get(3), Color.RED, 1, i);
                svgGraphics2DList.get(1).setStroke(new BasicStroke());
                svgGraphics2DList.get(2).setStroke(new BasicStroke());
                svgGraphics2DList.get(3).setStroke(new BasicStroke());
            } else {
                vietorisRipsComplexSVG.drawSimpliciesOfDimensionAndComponent(svgGraphics2DList.get(1), Color.BLUE, 1, i);
                vietorisRipsComplexSVG.drawSimpliciesOfDimensionAndComponent(svgGraphics2DList.get(2), Color.BLUE, 1, i);
                vietorisRipsComplexSVG.drawSimpliciesOfDimensionAndComponent(svgGraphics2DList.get(3), Color.BLUE, 1, i);
            }
        }

        poissonPointProcessSVG.drawPoints(svgGraphics2DList.get(1), Color.BLACK);
        poissonPointProcessSVG.drawPoints(svgGraphics2DList.get(2), Color.BLACK);
        poissonPointProcessSVG.drawPoints(svgGraphics2DList.get(3), Color.BLACK);

        for (int i = 0; i < svgGraphics2DList.size(); i++) {
            saveSVGGraphics2D(svgGraphics2DList.get(i), String.format("%s.step_%d", vietorisRipsComplexSVG.getFullSaveName(), i));
        }

    }

    public static void theGiantBeast() throws Exception {
        PoissonPointProcessSVG poissonPointProcessSVG = new PoissonPointProcessSVG(new int[]{128, 96}, 1);
        poissonPointProcessSVG.generate();
        poissonPointProcessSVG.printPoints();
        poissonPointProcessSVG.save();

        VietorisRipsComplexSVG vietorisRipsComplexSVG = new VietorisRipsComplexSVG(poissonPointProcessSVG.getPoints(), 0.999 * PoissonPointProcess.PERCOLATION);
        vietorisRipsComplexSVG.generateOneSkeleton();
        vietorisRipsComplexSVG.printSimplicies();
        vietorisRipsComplexSVG.generateComponents();
        vietorisRipsComplexSVG.recalculateComponentPointCount();
        vietorisRipsComplexSVG.recalculateComponentIndexOfLargest();
        vietorisRipsComplexSVG.save();

        SVGGraphics2D svgGraphics2D[] = new SVGGraphics2D[]{
                getSVGGraphics2D(), // 0 GRID
                getSVGGraphics2D(), // 1 POISSON NUMBERS
                getSVGGraphics2D(), // 2 POINTS IN GRIND
                getSVGGraphics2D(), // 3 ONLY POINTS
                getSVGGraphics2D(), // 4 CIRCLES AROUND POINTS
                getSVGGraphics2D(), // 5 CIRLCES AND 1-SKELETON
                getSVGGraphics2D(), // 6 1-SKELETON ONLY
                getSVGGraphics2D(), // 7 GIANT COMPONENT
        };

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

        vietorisRipsComplexSVG.drawCircles(svgGraphics2D[0]);
        vietorisRipsComplexSVG.drawCircles(svgGraphics2D[1]);
        vietorisRipsComplexSVG.drawCircles(svgGraphics2D[2]);
        vietorisRipsComplexSVG.drawCircles(svgGraphics2D[3]);
        vietorisRipsComplexSVG.drawCircles(svgGraphics2D[4], new Color(255, 255, 0, 128));
        vietorisRipsComplexSVG.drawCircles(svgGraphics2D[5], new Color(255, 255, 0, 128));
        vietorisRipsComplexSVG.drawCircles(svgGraphics2D[6]);
        vietorisRipsComplexSVG.drawCircles(svgGraphics2D[7]);

        vietorisRipsComplexSVG.drawSimplicesOfDimension(svgGraphics2D[5], Color.BLUE, 1);
        vietorisRipsComplexSVG.drawSimplicesOfDimension(svgGraphics2D[6], Color.BLUE, 1);
        for (int i = 0; i < vietorisRipsComplexSVG.getComponentCount(); i++) {

            if (vietorisRipsComplexSVG.getComponentIndexOfLargest() == i) {
                svgGraphics2D[7].setStroke(new BasicStroke(3));
                vietorisRipsComplexSVG.drawSimpliciesOfDimensionAndComponent(svgGraphics2D[7], Color.RED, 1, i);
                svgGraphics2D[7].setStroke(new BasicStroke());
            } else {
                vietorisRipsComplexSVG.drawSimpliciesOfDimensionAndComponent(svgGraphics2D[7], Color.BLUE, 1, i);
            }
        }
        poissonPointProcessSVG.drawPoints(svgGraphics2D[0]);
        poissonPointProcessSVG.drawPoints(svgGraphics2D[1]);
        poissonPointProcessSVG.drawPoints(svgGraphics2D[2], Color.BLACK);
        poissonPointProcessSVG.drawPoints(svgGraphics2D[3], Color.BLACK);
        poissonPointProcessSVG.drawPoints(svgGraphics2D[4], Color.BLACK);
        poissonPointProcessSVG.drawPoints(svgGraphics2D[5], Color.BLACK);
        poissonPointProcessSVG.drawPoints(svgGraphics2D[6], Color.BLACK);
        poissonPointProcessSVG.drawPoints(svgGraphics2D[7], Color.BLACK);

        for (int i = 0; i < svgGraphics2D.length; i++) {
            saveSVGGraphics2D(svgGraphics2D[i], String.format("%s.step_%d", vietorisRipsComplexSVG.getFullSaveName(), i));
        }

        System.out.println(String.format("intensity=%f", poissonPointProcessSVG.getIntensity()));
        System.out.println(String.format("distance=%f", vietorisRipsComplexSVG.getDistance()));
        System.out.println(String.format("points=%d", poissonPointProcessSVG.getPoints().size()));

        System.out.println(String.format("componentCount=%d", vietorisRipsComplexSVG.getComponentCount()));
    }



    public static void randomizedFullVietorisRipsComplex() throws Exception {
        PoissonPointProcessSVG poissonPointProcessSVG = new PoissonPointProcessSVG("2017-11-28-22-09-23");
        poissonPointProcessSVG.save();

        VietorisRipsComplexSVG vietorisRipsComplexSVG = new VietorisRipsComplexSVG(poissonPointProcessSVG.getPoints(), 1);
        vietorisRipsComplexSVG.generate();
        vietorisRipsComplexSVG.printAdjacencyMatrix();
        vietorisRipsComplexSVG.save();
        vietorisRipsComplexSVG.svgStepConstruction();

    }

    public static void onlyOnePointComplex() throws Exception {
        SaveFileAbstract.setSaveCode("ABSTRACT_EXAMPLES_0");
        List<List<int[]>> simplices = new ArrayList<>();

        List<int[]> dim0 = new ArrayList<>();

        dim0.add(new int[]{0});

        simplices.add(dim0);


        List<double[]> points = new ArrayList<>();

        points.add(new double[]{0.1, 0.8});

        VietorisRipsComplexSVG vietorisRipsComplexSVG = new VietorisRipsComplexSVG(simplices, points, 1.5);

        vietorisRipsComplexSVG.svgStepConstruction();
        vietorisRipsComplexSVG.save();
    }

    public static void onlyOneEdgeComplex() throws Exception {
        SaveFileAbstract.setSaveCode("ABSTRACT_EXAMPLES_1");

        List<List<int[]>> simplices = new ArrayList<>();

        List<int[]> dim0 = new ArrayList<>();

        dim0.add(new int[]{0});
        dim0.add(new int[]{1});

        simplices.add(dim0);

        List<int[]> dim1 = new ArrayList<>();

        dim1.add(new int[]{0, 1});

        simplices.add(dim1);

        List<double[]> points = new ArrayList<>();

        points.add(new double[]{0.1, 0.8});
        points.add(new double[]{0.6, 0.8});

        VietorisRipsComplexSVG vietorisRipsComplexSVG = new VietorisRipsComplexSVG(simplices, points, 1.5);

        vietorisRipsComplexSVG.svgStepConstruction();
        vietorisRipsComplexSVG.save();
    }

    public static void onlyOneEdgeAndAPointComplex() throws Exception {
        SaveFileAbstract.setSaveCode("ABSTRACT_EXAMPLES_2");

        List<List<int[]>> simplices = new ArrayList<>();

        List<int[]> dim0 = new ArrayList<>();

        dim0.add(new int[]{0});
        dim0.add(new int[]{1});
        dim0.add(new int[]{2});

        simplices.add(dim0);

        List<int[]> dim1 = new ArrayList<>();

        dim1.add(new int[]{0, 1});

        simplices.add(dim1);

        List<double[]> points = new ArrayList<>();

        points.add(new double[]{0.1, 0.8});
        points.add(new double[]{0.6, 0.8});
        points.add(new double[]{0.1, 0.3});

        VietorisRipsComplexSVG vietorisRipsComplexSVG = new VietorisRipsComplexSVG(simplices, points, 1.5);

        vietorisRipsComplexSVG.svgStepConstruction();
        vietorisRipsComplexSVG.save();
    }

    public static void onlyOneFilledTriangle() throws Exception {
        SaveFileAbstract.setSaveCode("ABSTRACT_EXAMPLES_3");

        List<List<int[]>> simplices = new ArrayList<>();

        List<int[]> dim0 = new ArrayList<>();

        dim0.add(new int[]{0});
        dim0.add(new int[]{1});
        dim0.add(new int[]{2});

        simplices.add(dim0);

        List<int[]> dim1 = new ArrayList<>();

        dim1.add(new int[]{0, 1});
        dim1.add(new int[]{0, 2});
        dim1.add(new int[]{1, 2});

        simplices.add(dim1);

        List<int[]> dim2 = new ArrayList<>();

        dim2.add(new int[]{0, 1, 2});

        simplices.add(dim2);

        List<double[]> points = new ArrayList<>();

        points.add(new double[]{0.1, 0.8});
        points.add(new double[]{0.6, 0.8});
        points.add(new double[]{0.1, 0.3});

        VietorisRipsComplexSVG vietorisRipsComplexSVG = new VietorisRipsComplexSVG(simplices, points, 1.5);

        vietorisRipsComplexSVG.svgStepConstruction();
        vietorisRipsComplexSVG.save();
    }

    public static void thankYouComplex() throws Exception {
        List<double[]> vertices = new ArrayList<>();

        vertices.add(new double[]{1, 1});
        vertices.add(new double[]{2, 1});
        vertices.add(new double[]{3, 1});
        vertices.add(new double[]{2, 2});
        vertices.add(new double[]{2, 3});

        vertices.add(new double[]{4, 1});
        vertices.add(new double[]{5, 1});
        vertices.add(new double[]{4, 2});
        vertices.add(new double[]{5, 2});
        vertices.add(new double[]{4, 3});
        vertices.add(new double[]{5, 3});

        vertices.add(new double[]{6, 1});
        vertices.add(new double[]{7, 1});
        vertices.add(new double[]{6, 2});
        vertices.add(new double[]{7, 2});
        vertices.add(new double[]{6, 3});
        vertices.add(new double[]{7, 3});

        vertices.add(new double[]{8, 1});
        vertices.add(new double[]{9, 1});
        vertices.add(new double[]{8, 3});
        vertices.add(new double[]{9, 3});

        vertices.add(new double[]{10, 1});
        vertices.add(new double[]{11, 1});
        vertices.add(new double[]{10, 2});
        vertices.add(new double[]{10, 3});
        vertices.add(new double[]{11, 3});

        vertices.add(new double[]{14,1});
        vertices.add(new double[]{15,2});
        vertices.add(new double[]{15,3});
        vertices.add(new double[]{16,1});

        vertices.add(new double[]{17,1});
        vertices.add(new double[]{18,1});
        vertices.add(new double[]{17,3});
        vertices.add(new double[]{18,3});

        vertices.add(new double[]{19,1});
        vertices.add(new double[]{19,3});
        vertices.add(new double[]{20,1});
        vertices.add(new double[]{20,3});

        List<List<int[]>> simplices = new ArrayList<>();

        List<int[]> dim0 = new ArrayList<>();

        for (int i = 0; i < vertices.size(); i++) {
            dim0.add(new int[]{i});
        }

        simplices.add(dim0);

        List<int[]> dim1 = new ArrayList<>();

        dim1.add(new int[]{0, 1});
        dim1.add(new int[]{1, 2});
        dim1.add(new int[]{1, 3});
        dim1.add(new int[]{1, 4});

        dim1.add(new int[]{5, 7});
        dim1.add(new int[]{7, 8});
        dim1.add(new int[]{7, 9});
        dim1.add(new int[]{6, 8});
        dim1.add(new int[]{8, 10});

        dim1.add(new int[]{11, 12});
        dim1.add(new int[]{11, 13});
        dim1.add(new int[]{13, 14});
        dim1.add(new int[]{13, 15});
        dim1.add(new int[]{12, 14});
        dim1.add(new int[]{14, 16});

        dim1.add(new int[]{17, 19});
        dim1.add(new int[]{18, 20});
        dim1.add(new int[]{17, 20});

        dim1.add(new int[]{21, 23});
        dim1.add(new int[]{23, 24});
        dim1.add(new int[]{23, 22});
        dim1.add(new int[]{23, 25});

        dim1.add(new int[]{26, 27});
        dim1.add(new int[]{27, 29});
        dim1.add(new int[]{27, 28});

        dim1.add(new int[]{30, 31});
        dim1.add(new int[]{32, 33});
        dim1.add(new int[]{30, 32});
        dim1.add(new int[]{31, 33});

        dim1.add(new int[]{34, 35});
        dim1.add(new int[]{36, 37});
        dim1.add(new int[]{35, 37});

        simplices.add(dim1);

        SVGGraphics2D svgGraphics2D = getSVGGraphics2D();

        svgGraphics2D.setStroke(new BasicStroke(10));

        VietorisRipsComplexSVG vietorisRipsComplexSVG = new VietorisRipsComplexSVG(simplices, vertices, 0);

        //vietorisRipsComplexSVG.writePointNumbers(svgGraphics2D, Color.BLACK);

        vietorisRipsComplexSVG.drawSimplicesOfDimension(svgGraphics2D, Color.RED, 1);
        vietorisRipsComplexSVG.drawSimplicesOfDimension(svgGraphics2D, Color.RED, 2);
        vietorisRipsComplexSVG.drawPoints(svgGraphics2D, Color.BLACK);

        saveSVGGraphics2D(svgGraphics2D, String.format("%s", vietorisRipsComplexSVG.getFullSaveName()));

        vietorisRipsComplexSVG.printSimplicies();
        vietorisRipsComplexSVG.save();

    }
}