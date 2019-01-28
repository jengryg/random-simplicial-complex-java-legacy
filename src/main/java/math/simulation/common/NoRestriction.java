package math.simulation.common;

public class NoRestriction extends RestrictionAbstract {
    @Override
    public boolean isContained(double[] point) throws DimensionErrorException {
        return true;
    }
}
