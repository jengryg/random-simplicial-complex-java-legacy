package math.simulation.random_simplicial_complex;

import org.apache.batik.svggen.SVGGraphics2D;

import java.awt.*;
import java.util.List;

import static math.simulation.common.SVGGenerator.*;
import static math.simulation.common.SVGGenerator.TRANSPARENT;
import static math.simulation.common.SVGGenerator.drawSimplex;

public class CechComplexSVG extends CechComplex {

    public CechComplexSVG(List<double[]> aVertices, double aDistance) {
        super(aVertices, aDistance);
    }

    public CechComplexSVG(List<List<int[]>> aSimplicies, List<double[]> aVertices, double aDistance) {
        super(aSimplicies, aVertices, aDistance);
    }

    public CechComplexSVG(String code) {
        super(code);
    }

    public void svgStepConstruction() {
        SVGGraphics2D svgGraphics2D[] = new SVGGraphics2D[]{
                getSVGGraphics2D(),
                getSVGGraphics2D(),
                getSVGGraphics2D(),
                getSVGGraphics2D(),
                getSVGGraphics2D()
        };

        drawCircles(svgGraphics2D[0]);
        drawCircles(svgGraphics2D[1], new Color(255, 255, 0, 128));
        drawCircles(svgGraphics2D[2], new Color(255, 255, 0, 128));
        drawCircles(svgGraphics2D[3], new Color(255, 255, 0, 128));
        drawCircles(svgGraphics2D[4]);


        for (int i = 2; i < simplicies.size(); i++) {
            drawSimplicesOfDimension(svgGraphics2D[0], i);
            drawSimplicesOfDimension(svgGraphics2D[1], i);
            drawSimplicesOfDimension(svgGraphics2D[2], i);
            drawSimplicesOfDimension(svgGraphics2D[3], new Color(0, 0, 255, 128), i);
            drawSimplicesOfDimension(svgGraphics2D[4], new Color(0, 0, 255, 128), i);
        }

        drawEdges(svgGraphics2D[0]);
        drawEdges(svgGraphics2D[1]);
        drawEdges(svgGraphics2D[2], Color.RED);
        drawEdges(svgGraphics2D[3], Color.RED);
        drawEdges(svgGraphics2D[4], Color.RED);

        drawPoints(svgGraphics2D[0], Color.BLACK);
        drawPoints(svgGraphics2D[1], Color.BLACK);
        drawPoints(svgGraphics2D[2], Color.BLACK);
        drawPoints(svgGraphics2D[3], Color.BLACK);
        drawPoints(svgGraphics2D[4], Color.BLACK);

        writePointNumbers(svgGraphics2D[0], Color.BLACK);
        writePointNumbers(svgGraphics2D[1], Color.BLACK);
        writePointNumbers(svgGraphics2D[2], Color.BLACK);
        writePointNumbers(svgGraphics2D[3], Color.BLACK);
        writePointNumbers(svgGraphics2D[4], Color.BLACK);

        for (int i = 0; i < svgGraphics2D.length; i++) {
            saveSVGGraphics2D(svgGraphics2D[i], String.format("%s.step_%d", getFullSaveName(), i));
        }
    }

    public void drawPoints(SVGGraphics2D aSVGGraphics2D) {
        drawPoints(aSVGGraphics2D, TRANSPARENT);
    }

    public void drawPoints(SVGGraphics2D aSVGGraphics2D, Color aColor) {
        aSVGGraphics2D.setColor(aColor);
        for (int i = 0; i < vertices.size(); i++) {
            drawFilledCircle(aSVGGraphics2D, vertices.get(i), 0.05);
        }
    }

    public void drawEdges(SVGGraphics2D aSVGGraphics2D) {
        drawEdges(aSVGGraphics2D, TRANSPARENT);
    }

    public void drawEdges(SVGGraphics2D aSVGGraphics2D, Color aColor) {
        aSVGGraphics2D.setStroke(new BasicStroke(3));
        aSVGGraphics2D.setColor(aColor);
        for (int i = 0; i < simplicies.get(1).size(); i++) {
            drawEdge(aSVGGraphics2D, vertices.get(simplicies.get(1).get(i)[0]), vertices.get(simplicies.get(1).get(i)[1]));
        }
        aSVGGraphics2D.setStroke(new BasicStroke());
    }

    public void writePointNumbers(SVGGraphics2D aSVGGraphics2D) {
        writePointNumbers(aSVGGraphics2D, TRANSPARENT);
    }

    public void writePointNumbers(SVGGraphics2D aSVGGraphics2D, Color aColor) {
        Font font = new Font("TimesRoman", Font.PLAIN, 26);
        aSVGGraphics2D.setFont(font);
        for (int i = 0; i < vertices.size(); i++) {
            aSVGGraphics2D.drawString(String.valueOf(i), convertCord(vertices.get(i)[0] - 0.05 / 2 + 0.05), convertCord(vertices.get(i)[1] - 0.05 / 2 - 0.05));
        }
    }

    public void drawCircles(SVGGraphics2D aSVGGraphics2D) {
        drawCircles(aSVGGraphics2D, TRANSPARENT);
    }

    public void drawCircles(SVGGraphics2D aSVGGraphics2D, Color aColor) {
        aSVGGraphics2D.setColor(aColor);
        for (int i = 0; i < vertices.size(); i++) {
            drawFilledCircle(aSVGGraphics2D, vertices.get(i), distance / 2);
        }
    }

    public void drawSimplicesOfDimension(SVGGraphics2D aSVGGraphics2D, int dimension) {
        drawSimplicesOfDimension(aSVGGraphics2D, TRANSPARENT, dimension);
    }

    public void drawSimplicesOfDimension(SVGGraphics2D aSVGGraphics2D, Color aColor, int dimension) {
        aSVGGraphics2D.setColor(aColor);
        for (int i = 0; i < simplicies.get(dimension).size(); i++) {
            drawSimplex(aSVGGraphics2D, mapSimplexToGeometry(simplicies.get(dimension).get(i)));
        }
    }

}
