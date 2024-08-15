package math.simulation.common;

import org.apache.batik.svggen.SVGGraphics2D;

import java.awt.*;

import static math.simulation.common.SVGGenerator.drawCircle;

public class BallRestrictionSVG extends BallRestriction {

    public void drawRestriction(SVGGraphics2D aSVGGraphics2D, Color aColor) {
        aSVGGraphics2D.setColor(aColor);
        drawCircle(aSVGGraphics2D, center, radius);
    }

    @Override
    public double[] getCenter() {
        return super.getCenter();
    }

    @Override
    public double getRadius() {
        return super.getRadius();
    }

    public BallRestrictionSVG(double[] aCenter, double aRadius) {
        super(aCenter, aRadius);
    }

    @Override
    public boolean isContained(double[] point) throws DimensionErrorException {
        return super.isContained(point);
    }
}
