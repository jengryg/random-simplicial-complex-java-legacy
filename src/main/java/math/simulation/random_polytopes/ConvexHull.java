package math.simulation.random_polytopes;

import math.simulation.common.SaveFileAbstract;

import java.io.*;
import java.util.*;

public class ConvexHull extends SaveFileAbstract {
    protected List<double[]> points = new ArrayList<>();

    public List<double[]> getPoints() {
        return points;
    }

    protected ArrayList<double[]> pointsHull = new ArrayList<>();

    public List<double[]> getPointsHull() {
        return pointsHull;
    }

    public ConvexHull(List<double[]> aPoints) {
        points = aPoints;
    }

    public ConvexHull(List<double[]> aPoints, List<double[]> aPointsHull) {
        points = aPoints;
        pointsHull = new ArrayList<>(aPointsHull);
    }

    public ConvexHull(String code) {
        setLoadCode(code);

        try {
            load();
        } catch (IOException aE) {
            aE.printStackTrace();
        }
    }

    public void printPoints() {
        if (points.size() == 0) {
            System.out.println("NO POINTS!");
        } else {
            System.out.println(String.format("%d POINTS ! ...", points.size()));
            for (int i = 0; i < points.size(); i++) {
                System.out.println(Arrays.toString(points.get(i)));
            }
            System.out.println();
        }
    }

    public void printPointsHull() {
        if (pointsHull.size() == 0) {
            System.out.println("NO HULL POINTS!");
        } else {
            System.out.println(String.format("%d HULL POINTS ! ...", pointsHull.size()));
            for (int i = 0; i < pointsHull.size(); i++) {
                System.out.println(Arrays.toString(pointsHull.get(i)));
            }
            System.out.println();
        }
    }

    public void generate() throws Exception {
        generateGrahamScan();
    }

    public void generateGrahamScan() {

        Point2DGraham[] pArray = new Point2DGraham[points.size()];

        for (int i = 0; i < points.size(); i++) {
            pArray[i] = new Point2DGraham(points.get(i)[0], points.get(i)[1]);
        }

        GrahamScan grahamScan = new GrahamScan(pArray);

        pointsHull.clear();
        for (Point2DGraham p : grahamScan.hull()) {
            pointsHull.add(new double[]{p.x(), p.y()});
        }
    }

    /**
     * GrahamScan
     */

    public void generateGrahamScanOld() throws IllegalAccessException {
        List<double[]> sortedPoints = new ArrayList<>(getSortedPointSet());

        if (sortedPoints.size() == 0) {
            System.out.println("NO HULL POINTS!");
        } else {
            System.out.println(String.format("%d HULL POINTS ! ...", sortedPoints.size()));
            for (int i = 0; i < sortedPoints.size(); i++) {
                System.out.println(Arrays.toString(sortedPoints.get(i)));
            }
            System.out.println();
        }

        if (sortedPoints.size() < 3) {
            throw new IllegalAccessException("convex hull of 2 or less points can not be created");
        }

        double[] pointStart = sortedPoints.get(0);
        double[] pointOver = sortedPoints.get(1);
        double[] pointEnd;

        boolean collinear = true;

        for (int i = 2; i < sortedPoints.size(); i++) {
            if (getOrientation(pointStart, pointOver, sortedPoints.get(i)) != Orientation.COLLINEAR) {
                collinear = false;
                break;
            }
        }

        if (collinear) throw new IllegalAccessException("convex hull of collinear points can not be created");

        Stack<double[]> pointStack = new Stack<>();

        pointStack.push(sortedPoints.get(0));
        pointStack.push(sortedPoints.get(1));

        for (int i = 2; i < sortedPoints.size(); i++) {
            pointEnd = sortedPoints.get(i);
            pointOver = pointStack.pop();
            pointStart = pointStack.peek();

            switch (getOrientation(pointStart, pointOver, pointEnd)) {
                case COUNTER_CLOCKWISE:
                    pointStack.push(pointOver);
                    pointStack.push(pointEnd);
                    break;
                case CLOCKWISE:
                    i--;
                    break;
                case COLLINEAR:
                    pointStack.push(pointEnd);
                    break;
            }
        }

        pointStack.push(sortedPoints.get(0));

        pointsHull = new ArrayList<>(pointStack);

    }

    protected Set<double[]> getSortedPointSet() {
        final double[] lowestPoint = getLowestPoint();

        TreeSet<double[]> set = new TreeSet<>(new Comparator<double[]>() {
            @Override
            public int compare(double[] x, double[] y) {
                if (x == y || (x[0] == y[0] && x[1] == y[1])) {
                    return 0;
                }

                double[] vecX = {x[0] - lowestPoint[0], x[1] - lowestPoint[0]};
                double[] vecY = {y[0] - lowestPoint[0], y[1] - lowestPoint[1]};

                double angleX = Math.atan2(vecX[1], vecX[0]);
                double angleY = Math.atan2(vecY[1], vecY[0]);

                if (angleX < angleY) {
                    return -1;
                } else if (angleX > angleY) {
                    return 1;
                } else {
                    double sqLengthX = vecX[0] * vecX[0] + vecX[1] * vecX[1];
                    double sqLengthY = vecY[0] * vecY[0] + vecY[1] * vecY[1];

                    if (sqLengthX < sqLengthY) {
                        return -1;
                    } else {
                        return 1;
                    }
                }
            }
        });

        set.addAll(points);

        return set;
    }

    protected double[] getLowestPoint() {
        double[] lowestPoint = points.get(0);

        for (int i = 1; i < points.size(); i++) {
            if (points.get(i)[1] < lowestPoint[0] || (points.get(i)[1] == lowestPoint[1] && points.get(i)[0] < lowestPoint[0])) {
                lowestPoint = points.get(i);
            }
        }

        return lowestPoint;
    }

    protected enum Orientation {
        CLOCKWISE, COUNTER_CLOCKWISE, COLLINEAR
    }

    protected Orientation getOrientation(double[] aPointStart, double[] aPointOver, double[] aPointEnd) {

        System.out.println(Arrays.toString(aPointStart));
        System.out.println(Arrays.toString(aPointOver));
        System.out.println(Arrays.toString(aPointEnd));

        System.out.println(String.format("(%f - %f) * (%f - %f) - (%f - %f) * (%f - %f)", aPointOver[0], aPointStart[0], aPointEnd[1], aPointStart[1], aPointOver[1], aPointStart[1], aPointEnd[0], aPointStart[0]));

        double det = (aPointOver[0] - aPointStart[0]) * (aPointEnd[1] - aPointStart[1]) - (aPointOver[1] - aPointStart[1]) * (aPointEnd[0] - aPointStart[0]);

        System.out.println(det);

        if (det > 0) {
            return Orientation.COUNTER_CLOCKWISE;
        } else if (det < 0) {
            return Orientation.CLOCKWISE;
        } else {
            return Orientation.COLLINEAR;
        }
    }


    @SuppressWarnings("unchecked")
    public void load() throws IOException {
        FileInputStream fi;
        ObjectInputStream oi;

        fi = new FileInputStream(String.format("%s.%s", getFullLoadName(), "pointsHull"));
        oi = new ObjectInputStream(fi);
        try {
            pointsHull = (ArrayList<double[]>) oi.readObject();
        } catch (ClassNotFoundException aE) {
            aE.printStackTrace();
        }
        oi.close();
        fi.close();

        fi = new FileInputStream(String.format("%s.%s", getFullLoadName(), "points"));
        oi = new ObjectInputStream(fi);
        try {
            points = (ArrayList<double[]>) oi.readObject();
        } catch (ClassNotFoundException aE) {
            aE.printStackTrace();
        }
        oi.close();
        fi.close();

    }

    public void save() throws IOException {
        FileOutputStream fo;
        ObjectOutputStream oo;

        fo = new FileOutputStream(String.format("%s.%s", getFullSaveName(), "pointsHull"));
        oo = new ObjectOutputStream(fo);
        oo.writeObject(pointsHull);
        oo.close();
        fo.close();

        fo = new FileOutputStream(String.format("%s.%s", getFullSaveName(), "points"));
        oo = new ObjectOutputStream(fo);
        oo.writeObject(points);
        oo.close();
        fo.close();
    }
}
