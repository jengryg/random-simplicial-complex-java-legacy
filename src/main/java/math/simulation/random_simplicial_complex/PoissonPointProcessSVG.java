package math.simulation.random_simplicial_complex;

import org.apache.batik.svggen.SVGGraphics2D;

import java.awt.*;
import java.util.Arrays;

import static math.simulation.random_simplicial_complex.SVGGenerator.*;

public class PoissonPointProcessSVG extends PoissonPointProcess {

    public PoissonPointProcessSVG(int[] aLengths, double aIntensity) throws DimensionErrorException {
        super(aLengths, aIntensity);
        if (lengths.length != 2) {
            throw new DimensionErrorException();
        }
    }

    public PoissonPointProcessSVG(String code) throws DimensionErrorException {
        super(code);
        if (lengths.length != 2) {
            throw new DimensionErrorException();
        }
    }

    public void svgStepConstruction() {
        SVGGraphics2D svgGraphics2D[] = new SVGGraphics2D[]{
                getSVGGraphics2D(),
                getSVGGraphics2D(),
                getSVGGraphics2D(),
                getSVGGraphics2D()
        };

        drawGrid(svgGraphics2D[0], Color.GRAY);
        drawGrid(svgGraphics2D[1], Color.GRAY);
        drawGrid(svgGraphics2D[2], Color.GRAY);
        drawGrid(svgGraphics2D[3]);

        //writePoissonNumbers(svgGraphics2D[0]);
        writePoissonNumbers(svgGraphics2D[1], Color.BLUE);
        writePoissonNumbers(svgGraphics2D[2], Color.BLUE);
        //writePoissonNumbers(svgGraphics2D[3]);

        drawPoints(svgGraphics2D[0]);
        drawPoints(svgGraphics2D[1]);
        drawPoints(svgGraphics2D[2], Color.BLACK);
        drawPoints(svgGraphics2D[3], Color.BLACK);

        for (int i = 0; i < svgGraphics2D.length; i++) {
            saveSVGGraphics2D(svgGraphics2D[i], String.format("%s.step_%d", getFullSaveName(), i));
        }
    }


    public void drawGrid(SVGGraphics2D aSVGGraphics2D) {
        drawGrid(aSVGGraphics2D, TRANSPARENT);
    }

    public void drawGrid(SVGGraphics2D aSVGGraphics2D, Color aColor) {
        aSVGGraphics2D.setColor(aColor);
        for (int i = 1; i < lengths[0]; i++) {
            aSVGGraphics2D.drawLine(convertCord(i), convertCord(-0.1), convertCord(i), convertCord(lengths[1] + 0.1));
        }
        for (int j = 1; j < lengths[1]; j++) {
            aSVGGraphics2D.drawLine(convertCord(-0.1), convertCord(j), convertCord(lengths[0] + 0.1), convertCord(j));
        }
        aSVGGraphics2D.setColor(Color.black);
    }

    public void writePoissonNumbers(SVGGraphics2D aSVGGraphics2D) {
        writePoissonNumbers(aSVGGraphics2D, TRANSPARENT);
    }

    public void writePoissonNumbers(SVGGraphics2D aSVGGraphics2D, Color aColor) {
        aSVGGraphics2D.setColor(aColor);
        Font font = new Font("TimesRoman", Font.PLAIN, 26);
        aSVGGraphics2D.setFont(font);

        int[] block = new int[2];
        for (int j = 2; j >= 0; ) {
            // writing start
            int numberOfPoints = poissonNumbers.get(Arrays.toString(block));
            System.out.println(String.format("WRITING BLOCK: %s WITH NUMBER %d", Arrays.toString(block), numberOfPoints));

            aSVGGraphics2D.drawString(String.valueOf(numberOfPoints), convertCord(0.05 + block[0]), convertCord(0.1 + block[1] + 0.1));

            // writing end
            for (j = 1; j >= 0; j--) {
                if (block[j] < lengths[j] - 1) {
                    ++block[j];
                    break;
                }
                block[j] = 0;
            }
        }
        aSVGGraphics2D.setColor(Color.BLACK);
    }

    public void drawPoints(SVGGraphics2D aSVGGraphics2D) {
        drawPoints(aSVGGraphics2D, TRANSPARENT);
    }

    public void drawPoints(SVGGraphics2D aSVGGraphics2D, Color aColor) {
        aSVGGraphics2D.setColor(aColor);
        for (int i = 0; i < points.size(); i++) {
            drawCircle(aSVGGraphics2D, points.get(i), 0.03);
        }
    }

    public void writePointNumbers(SVGGraphics2D aSVGGraphics2D) {
        writePointNumbers(aSVGGraphics2D, TRANSPARENT);
    }

    public void writePointNumbers(SVGGraphics2D aSVGGraphics2D, Color aColor) {
        Font font = new Font("TimesRoman", Font.PLAIN, 26);
        aSVGGraphics2D.setFont(font);
        for (int i = 0; i < points.size(); i++) {
            aSVGGraphics2D.drawString(String.valueOf(i), convertCord(points.get(i)[0] - 0.03 / 2 + 0.05), convertCord(points.get(i)[1] - 0.03 / 2 - 0.05));
        }
    }

}
