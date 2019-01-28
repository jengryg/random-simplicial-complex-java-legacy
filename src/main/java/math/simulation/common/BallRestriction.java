package math.simulation.common;

public class BallRestriction extends RestrictionAbstract{
    protected double[] center;
    protected double radius;
    protected double radiusSquared;

    public double[] getCenter() {
        return center;
    }

    public double getRadius() {
        return radius;
    }

    public BallRestriction(double[] aCenter, double aRadius) {
        center = aCenter;
        radius = aRadius;
        radiusSquared = radius * radius;
    }

    @Override
    public boolean isContained(double[] point) throws DimensionErrorException {
        if (center.length != point.length) throw new DimensionErrorException();
        double distSquared = 0;
        for (int i = 0; i < center.length; i++) {
            distSquared += (point[i] - center[i]) * (point[i] - center[i]);
        }
        if (distSquared > radiusSquared) {
            return false;
        } else {
            return true;
        }
    }
}
