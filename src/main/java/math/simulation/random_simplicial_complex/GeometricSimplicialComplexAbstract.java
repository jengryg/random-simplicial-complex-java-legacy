package math.simulation.random_simplicial_complex;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

abstract public class GeometricSimplicialComplexAbstract extends SimplicialComplexAbstract {
    protected List<double[]> vertices;

    protected double distance;

    public List<double[]> getVertices() {
        return vertices;
    }

    public double getDistance() {
        return distance;
    }

    public GeometricSimplicialComplexAbstract(List<double[]> aVertices, double aDistance) {
        vertices = aVertices;
        distance = aDistance;
    }

    public GeometricSimplicialComplexAbstract(String code) {
        super(code);
        try {
            load();
        } catch (IOException aE) {
            aE.printStackTrace();
        }
    }

    public GeometricSimplicialComplexAbstract(List<List<int[]>> aSimplicies, List<double[]> aVertices, double aDistance) {
        super(aSimplicies);
        vertices = aVertices;
        distance = aDistance;
    }

    public void generateAdjacencyMatrix() {
        adjacencyMatrix = new boolean[vertices.size()][vertices.size()];

        for (int i = 0; i < adjacencyMatrix.length; i++) {
            for (int j = i + 1; j < adjacencyMatrix.length; j++) {
                try {
                    if (EuclideanSpace.distance(vertices.get(i), vertices.get(j)) < distance) {
                        adjacencyMatrix[i][j] = true;
                        adjacencyMatrix[j][i] = true;
                    }
                } catch (DimensionErrorException aE) {
                    aE.printStackTrace();
                    System.exit(0);
                }
            }
        }
    }

    protected ArrayList<double[]> mapSimplexToGeometry(int[] abstractSimplex) {
        ArrayList<double[]> geometricRealization = new ArrayList<>();
        for (int i = 0; i < abstractSimplex.length; i++) {
            geometricRealization.add(vertices.get(abstractSimplex[i]));
        }
        return geometricRealization;
    }

    @Override
    @SuppressWarnings("unchecked")
    public void load() throws IOException {
        super.load();

        FileInputStream fi;
        ObjectInputStream oi;

        fi = new FileInputStream(String.format("%s.%s", getFullLoadName(), "properties"));
        oi = new ObjectInputStream(fi);
        try {
            Properties properties = (Properties) oi.readObject();
            distance = Double.parseDouble(properties.getProperty("distance", "0.0"));
        } catch (ClassNotFoundException aE) {
            aE.printStackTrace();
        }
        oi.close();
        fi.close();

        fi = new FileInputStream(String.format("%s.%s", getFullLoadName(), "vertices"));
        oi = new ObjectInputStream(fi);
        try {
            vertices = (List<double[]>) oi.readObject();
        } catch (ClassNotFoundException aE) {
            aE.printStackTrace();
        }
        oi.close();
        fi.close();
    }

    @Override
    public void save() throws IOException {
        super.save();

        FileOutputStream fo;
        ObjectOutputStream oo;

        fo = new FileOutputStream(String.format("%s.%s", getFullSaveName(), "properties"));
        oo = new ObjectOutputStream(fo);
        Properties properties = new Properties();
        properties.setProperty("distance", String.valueOf(distance));
        oo.writeObject(properties);
        oo.close();
        fo.close();

        fo = new FileOutputStream(String.format("%s.%s", getFullSaveName(), "vertices"));
        oo = new ObjectOutputStream(fo);
        oo.writeObject(vertices);
        oo.close();
        fo.close();
    }
}
