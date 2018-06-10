package math.simulation.random_simplicial_complex;

import math.simulation.common.DimensionErrorException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CechComplex extends GeometricSimplicialComplexAbstract {
    public CechComplex(List<double[]> aVertices, double aDistance) {
        super(aVertices, aDistance);
    }

    public CechComplex(String code) {
        super(code);
    }

    public CechComplex(List<List<int[]>> aSimplicies, List<double[]> aVertices, double aDistance) {
        super(aSimplicies, aVertices, aDistance);
    }

    public void generateOneSkeleton() throws DimensionErrorException {
        simplicies.clear();
        generateAdjacencyMatrix();

        List<int[]> fixedDimensionSimplex;

        // 0-dim
        fixedDimensionSimplex = new ArrayList<>();
        for (int i = 0; i < vertices.size(); i++) {
            fixedDimensionSimplex.add(new int[]{i});
            System.out.println(String.format("Found 0 dimensional Simplex: %s", Arrays.toString(new int[]{i})));
        }
        simplicies.add(fixedDimensionSimplex);
        System.out.println(String.format("FOUND %d SIMPLICIES OF DIMENSION %d", fixedDimensionSimplex.size(), 0));
        try {
            saveFixedDimensionSimplex(simplicies.size() - 1);
        } catch (IOException aE) {
            aE.printStackTrace();
            System.exit(0);
        }

        // 1-dim
        fixedDimensionSimplex = new ArrayList<>();
        for (int i = 0; i < vertices.size(); i++) {
            for (int j = i + 1; j < vertices.size(); j++) {
                if (adjacencyMatrix[i][j]) {
                    fixedDimensionSimplex.add(new int[]{i, j});
                    System.out.println(String.format("Found 1 dimensional Simplex: %s", Arrays.toString(new int[]{i, j})));
                }
            }
        }
        simplicies.add(fixedDimensionSimplex);
        System.out.println(String.format("FOUND %d SIMPLICIES OF DIMENSION %d", fixedDimensionSimplex.size(), 1));
        try {
            saveFixedDimensionSimplex(simplicies.size() - 1);
        } catch (IOException aE) {
            aE.printStackTrace();
            System.exit(0);
        }
    }

    @Override
    public void generate() throws DimensionErrorException {
        System.exit(0);
    }
}
