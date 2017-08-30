package math.simulation.random_simplicial_complex;

public class EuclideanSpace {
    public static double distance(double[] x, double[] y) throws DimensionErrorException {
        if( x.length != y.length) {
            throw new DimensionErrorException();
        }
        double squaredLength = 0;
        for( int d = 0; d < x.length; d++) {
            squaredLength += (x[d] - y[d]) * (x[d] - y[d]);
        }
        return Math.sqrt(squaredLength);
    }
}
