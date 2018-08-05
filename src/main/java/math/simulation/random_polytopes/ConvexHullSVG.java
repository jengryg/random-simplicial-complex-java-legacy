package math.simulation.random_polytopes;

import org.apache.batik.svggen.SVGGraphics2D;

import java.awt.*;
import java.util.List;

import static math.simulation.common.SVGGenerator.*;
import static math.simulation.common.SVGGenerator.TRANSPARENT;
import static math.simulation.common.SVGGenerator.drawSimplex;

public class ConvexHullSVG extends ConvexHull {
    public ConvexHullSVG(List<double[]> aPoints) {
        super(aPoints);
    }

    public ConvexHullSVG(List<double[]> aPoints, List<double[]> aPointsHull) {
        super(aPoints, aPointsHull);
    }

    public ConvexHullSVG(String code) {
        super(code);
    }

    public void drawPoints(SVGGraphics2D aSVGGraphics2D) {
        drawPoints(aSVGGraphics2D, TRANSPARENT);
    }

    public void drawPoints(SVGGraphics2D aSVGGraphics2D, Color aColor) {
        aSVGGraphics2D.setColor(aColor);
        for (int i = 0; i < points.size(); i++) {
            drawCircle(aSVGGraphics2D, points.get(i), 0.05);
        }
    }

    public void drawHullPoints(SVGGraphics2D aSVGGraphics2D) {
        drawHullPoints(aSVGGraphics2D, TRANSPARENT);
    }

    public void drawHullPoints(SVGGraphics2D aSVGGraphics2D, Color aColor) {
        aSVGGraphics2D.setColor(aColor);
        for (int i = 0; i < pointsHull.size(); i++) {
            drawCircle(aSVGGraphics2D, pointsHull.get(i), 0.05);
        }
    }

    public void drawHullPolygon(SVGGraphics2D aSVGGraphics2D, Color aColor) {
        aSVGGraphics2D.setColor(aColor);
        drawSimplex(aSVGGraphics2D, pointsHull);
    }

}
