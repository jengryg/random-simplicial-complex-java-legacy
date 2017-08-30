package math.simulation.random_simplicial_complex;

import org.apache.commons.math3.distribution.PoissonDistribution;
import org.apache.commons.math3.distribution.UniformRealDistribution;

import java.io.*;
import java.util.*;

public class PoissonPointProcess extends SaveFileAbstract {
    /*
        for intensity 1
        set a = pi * rho^2
        and define a_c as the critical threshold for percolation then
        4.508 <= a_c <= 4.515
     */
    public static final double PERCOLATION = Math.sqrt(4.515 / Math.PI);

    protected int[] lengths;
    protected double intensity;
    protected List<double[]> points = new ArrayList<>();
    protected HashMap<String, Integer> poissonNumbers = new HashMap<>();

    public int[] getLengths() {
        return lengths;
    }

    public double getIntensity() {
        return intensity;
    }

    public List<double[]> getPoints() {
        return points;
    }

    public HashMap<String, Integer> getPoissonNumbers() {
        return poissonNumbers;
    }

    public PoissonPointProcess(int[] aLengths, double aIntensity) throws DimensionErrorException {
        if (aLengths.length <= 0) {
            throw new DimensionErrorException();
        }
        lengths = aLengths;
        intensity = aIntensity;
    }

    public PoissonPointProcess(String code) throws DimensionErrorException {
        setLoadCode(code);

        try {
            load();
        } catch (IOException aE) {
            aE.printStackTrace();
        }

        if (lengths.length <= 0) {
            throw new DimensionErrorException();
        }
    }

    public void generate() {
        PoissonDistribution poissonDistribution = new PoissonDistribution(intensity);
        UniformRealDistribution uniformRealDistribution = new UniformRealDistribution();

        int numberOfPoints;

        int[] block = new int[lengths.length];

        for (int j = lengths.length; j >= 0; ) {
            // generating start
            System.out.println(String.format("GENERATING: %s", Arrays.toString(block)));
            numberOfPoints = poissonDistribution.sample();
            poissonNumbers.put(Arrays.toString(block), numberOfPoints);
            while (numberOfPoints > 0) {
                double point[] = new double[lengths.length];
                for (int d = 0; d < lengths.length; d++) {
                    point[d] = uniformRealDistribution.sample() + block[d];
                }
                this.points.add(point);
                numberOfPoints--;
            }
            // generating end
            for (j = lengths.length - 1; j >= 0; j--) {
                if (block[j] < lengths[j] - 1) {
                    ++block[j];
                    break;
                }
                block[j] = 0;
            }
        }
    }

    public void printPoints() {
        if (points.size() == 0) {
            System.out.println("NO POINTS ARE LOADED!");
        } else {
            System.out.println(String.format("%d POINTS ARE LOADED! ...", points.size()));
            for (int i = 0; i < points.size(); i++) {
                System.out.println(Arrays.toString(points.get(i)));
            }
            System.out.println();
        }
    }

    @SuppressWarnings("unchecked")
    public void load() throws IOException {
        FileInputStream fi;
        ObjectInputStream oi;

        fi = new FileInputStream(String.format("%s.%s", getFullLoadName(), "properties"));
        oi = new ObjectInputStream(fi);
        try {
            Properties properties = (Properties) oi.readObject();
            intensity = Double.parseDouble(properties.getProperty("intensity", "0.0"));
        } catch (ClassNotFoundException aE) {
            aE.printStackTrace();
        }
        oi.close();
        fi.close();

        fi = new FileInputStream(String.format("%s.%s", getFullLoadName(), "lengths"));
        oi = new ObjectInputStream(fi);
        try {
            lengths = (int[]) oi.readObject();
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

        fi = new FileInputStream(String.format("%s.%s", getFullLoadName(), "poisson_numbers"));
        oi = new ObjectInputStream(fi);
        try {
            poissonNumbers = (HashMap<String, Integer>) oi.readObject();
        } catch (ClassNotFoundException aE) {
            aE.printStackTrace();
        }
        oi.close();
        fi.close();

    }

    public void save() throws IOException {
        FileOutputStream fo;
        ObjectOutputStream oo;

        fo = new FileOutputStream(String.format("%s.%s", getFullSaveName(), "properties"));
        oo = new ObjectOutputStream(fo);
        Properties properties = new Properties();
        properties.setProperty("intensity", String.valueOf(intensity));
        oo.writeObject(properties);
        oo.close();
        fo.close();

        fo = new FileOutputStream(String.format("%s.%s", getFullSaveName(), "lengths"));
        oo = new ObjectOutputStream(fo);
        oo.writeObject(lengths);
        oo.close();
        fo.close();

        fo = new FileOutputStream(String.format("%s.%s", getFullSaveName(), "points"));
        oo = new ObjectOutputStream(fo);
        oo.writeObject(points);
        oo.close();
        fo.close();

        fo = new FileOutputStream(String.format("%s.%s", getFullSaveName(), "poisson_numbers"));
        oo = new ObjectOutputStream(fo);
        oo.writeObject(poissonNumbers);
        oo.close();
        fo.close();

    }
}
