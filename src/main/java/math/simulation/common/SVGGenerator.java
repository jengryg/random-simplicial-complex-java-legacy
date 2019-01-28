package math.simulation.common;

import org.apache.batik.dom.GenericDOMImplementation;
import org.apache.batik.svggen.SVGGraphics2D;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;

import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;


public class SVGGenerator {
    public static Color TRANSPARENT = new Color(255, 255, 255, 0);

    public static SVGGraphics2D getSVGGraphics2D() {
        // Get a DOMImplementation.
        DOMImplementation domImpl =
                GenericDOMImplementation.getDOMImplementation();

        // Create an instance of org.w3c.dom.Document.
        String svgNS = "http://www.w3.org/2000/svg";
        Document document = domImpl.createDocument(svgNS, "svg", null);

        // Create an instance of the SVG Generator.
        SVGGraphics2D svgGenerator = new SVGGraphics2D(document);

        return svgGenerator;
    }

    public static void saveSVGGraphics2D(SVGGraphics2D aSVGGraphics2D, String aSaveFile) {
        // Finally, stream out SVG to the standard output using
        // UTF-8 encoding.
        boolean useCSS = false; // we want to use CSS style attributes

        try {
            File file = new File(String.format("%s.svg", aSaveFile));
            FileOutputStream fos = new FileOutputStream(file);

            if (!file.exists()) {
                file.createNewFile();
            }

            Writer out = new OutputStreamWriter(fos, "UTF-8");
            aSVGGraphics2D.stream(out, useCSS);

            fos.flush();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(0);
        }
    }

    public static int convertCord(double x) {
        return (int) Math.round(x * 100);
    }

    public static void drawFilledCircle(SVGGraphics2D aSVGGraphics2D, double[] aCenter, double aRadius) {
        aSVGGraphics2D.fillOval(convertCord(aCenter[0] - aRadius), convertCord(aCenter[1] - aRadius), convertCord(2 * aRadius), convertCord(2 * aRadius));
    }

    public static void drawCircle(SVGGraphics2D aSVGGraphics2D, double[] aCenter, double aRadius) {
        aSVGGraphics2D.drawOval(convertCord(aCenter[0] - aRadius), convertCord(aCenter[1] - aRadius), convertCord(2 * aRadius), convertCord(2 * aRadius));
    }

    public static void drawSimplex(SVGGraphics2D aSVGGraphics2D, ArrayList<double[]> vertices) {

        int[] xPoints = new int[vertices.size()];
        int[] yPoints = new int[vertices.size()];

        for (int i = 0; i < vertices.size(); i++) {
            xPoints[i] = convertCord(vertices.get(i)[0]);
            yPoints[i] = convertCord(vertices.get(i)[1]);
        }

        if (xPoints.length == 2) {
            aSVGGraphics2D.drawLine(xPoints[0], yPoints[0], xPoints[1], yPoints[1]);
        } else if (xPoints.length >= 3) {
            aSVGGraphics2D.fillPolygon(xPoints, yPoints, xPoints.length);
        }
    }

    public static void drawEdge(SVGGraphics2D aSVGGraphics2D, double[] x, double[] y) {
        if (x.length == 2 && y.length == 2) {
            aSVGGraphics2D.drawLine(convertCord(x[0]), convertCord(x[1]), convertCord(y[0]), convertCord(y[1]));
        }
    }

    public static void drawContainerBox(SVGGraphics2D aSVGGraphics2D, ArrayList<double[]> vertices) {

        for( int i = 0; i < vertices.size(); i++) {
            System.out.println(Arrays.toString(vertices.get(i)));
        }


        double maxX = 0;
        double maxY = 0;

        for (int i = 0; i < vertices.size(); i++) {
            maxX = Math.max(maxX, vertices.get(i)[0]);
            maxY = Math.max(maxY, vertices.get(i)[1]);
        }

        double minX = maxX;
        double minY = maxY;

        for( int i = 0; i < vertices.size(); i++) {
            minX = Math.min(minX, vertices.get(i)[0]);
            minY = Math.min(minY, vertices.get(i)[1]);
        }



        System.out.println(String.format("RECT: minX = %f, minY = %f, maxX = %f, maxY = %f", minX, minY, maxX, maxY));


        aSVGGraphics2D.setColor(new Color(255,255,0, 192));
        aSVGGraphics2D.fillRect(
                convertCord(Math.floor(minX)),
                convertCord(Math.floor(minY)),
                convertCord(Math.ceil(maxX) - Math.floor(minX)),
                convertCord(Math.ceil(maxY) - Math.floor(minY))
        );
        aSVGGraphics2D.setColor(Color.BLACK);
        aSVGGraphics2D.drawRect(
                convertCord(Math.floor(minX)),
                convertCord(Math.floor(minY)),
                convertCord(Math.ceil(maxX) - Math.floor(minX)),
                convertCord(Math.ceil(maxY) - Math.floor(minY))
        );
    }
}
