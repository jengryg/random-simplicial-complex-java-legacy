package math.simulation.random_simplicial_complex;

import org.apache.commons.math3.util.CombinatoricsUtils;

import java.io.*;
import java.util.*;

/**
 * calculates the Vietoris Rips Complex with distance delta out of PoissonPointProcess
 * WARNING: THE GENERATION ALGORYTHM SEEMS TO HAVE A HYPEREXPONENTIAL COMPUTATION TIME IN THE NUMBER OF GIVEN POINTS
 *
 * @TODO Optimization:
 * Try to form Higher Dimensional Simplicies Only with Other Facets of Same Dimension that are laying in a small neighbourhood.
 * @TODO alternative Algo:
 * Iterate directly over all possible sets of points and check the distances for all k choose 2.
 */
public class VietorisRipsComplex extends GeometricSimplicialComplexAbstract {

    public VietorisRipsComplex(List<double[]> aVertices, double aDistance) {
        super(aVertices, aDistance);
    }

    public VietorisRipsComplex(String code) {
        super(code);
    }

    public VietorisRipsComplex(List<List<int[]>> aSimplicies, List<double[]> aVertices, double aDistance) {
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
        simplicies.clear();
        generateAdjacencyMatrix();


    }

    public void generate_ineff() throws DimensionErrorException {
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

        int findDimension = 2;

        int numberOfOneDimensionLowerSimplices = fixedDimensionSimplex.size();

        int incPosition;
        boolean isSimplex;

        int numberOfVertices = vertices.size();

        while (findDimension < numberOfVertices && numberOfOneDimensionLowerSimplices >= findDimension) {
            System.out.println(String.format("SEARCHING FOR SIMPLEXES OF DIMENSION %d", findDimension));

            fixedDimensionSimplex = new ArrayList<>();

            int[] index = new int[findDimension + 1];

            for (int i = 0; i < index.length; i++) {
                index[i] = i; // init with increasing series
            }


            do {
                System.out.println(String.format("GENERATING: %s", Arrays.toString(index)));
                // start generation
                isSimplex = true;
                for (int i = 0; i < index.length && isSimplex; i++) {
                    for (int j = i + 1; j < index.length && isSimplex; j++) {
                        System.out.println(String.format("%d <-> %d = %d", index[i], index[j], adjacencyMatrix[index[i]][index[j]] ? 1 : 0));
                        if (!adjacencyMatrix[index[i]][index[j]]) {
                            isSimplex = false;
                        }
                    }
                }

                if (isSimplex) {
                    fixedDimensionSimplex.add(index.clone());
                    System.out.println(String.format("Found %d Dimensional Simplex given by %s", findDimension, Arrays.toString(index)));
                }


                // end generation

                incPosition = index.length - 1; // we start to try increment the rightmost entry

                while (incPosition >= 0 && index[incPosition] == numberOfVertices - 1 + incPosition - findDimension) {
                    // incPosition must be >= 0, because 0 is leftmost entry
                    // we want strongly monotony increasing indices, so we want all (a_i), a_0 < a_1 < ... < a_k, and a_i \in {0,...,n-1}
                    incPosition--;
                }
                if (incPosition >= 0) {
                    index[incPosition]++;
                    for (int j = incPosition + 1; j < index.length; j++) {
                        index[j] = index[j - 1] + 1;
                    }
                }
            } while (incPosition >= 0);

            simplicies.add(fixedDimensionSimplex);

            numberOfOneDimensionLowerSimplices = fixedDimensionSimplex.size();
            System.out.println(String.format("FOUND %d SIMPLICIES OF DIMENSION %d", numberOfOneDimensionLowerSimplices, findDimension));
            try {
                saveFixedDimensionSimplex(simplicies.size() - 1);
            } catch (IOException aE) {
                aE.printStackTrace();
                System.exit(0);
            }
            findDimension++;
        }

        recalculateFVector();
    }

    //@Override
    public void generate2() throws DimensionErrorException {
        simplicies.clear();

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
        for (int i = 0; i < simplicies.get(0).size(); i++) {
            for (int j = i + 1; j < simplicies.get(0).size(); j++) {
                if (EuclideanSpace.distance(vertices.get(i), vertices.get(j)) < distance) {
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

        int findDimension = 2;

        int numberOfOneDimensionLowerFacets = simplicies.get(1).size();

        HashSet<String> testSet;

        int[] face;

        int incPosition;

        while (findDimension <= numberOfOneDimensionLowerFacets) {

            for (int i = 0; i < simplicies.get(findDimension - 1).size(); i++) {
                System.out.println(Arrays.toString(simplicies.get(findDimension - 1).get(i)));
            }

            System.out.println(String.format("SEARCHING FOR SIMPLEXES OF DIMENSION %d", findDimension));

            // start pre-calculation for simplex test
            int numberOfDimensionMinusTwoFacets = (int) CombinatoricsUtils.binomialCoefficient(findDimension + 1, findDimension - 1);
            // end pre-calculation

            fixedDimensionSimplex = new ArrayList<>();

            int[] index = new int[findDimension + 1];

            for (int i = 0; i < index.length; i++) {
                index[i] = i; // init with increasing series
            }

            do {
                System.out.println(String.format("GENERATING: %s", Arrays.toString(index)));
                // start generation
                testSet = new HashSet<>();
                System.out.println("TESTING SIMPLEX:");
                boolean failed = false;
                for (int faceId = 0; faceId < index.length && !failed; faceId++) {
                    face = simplicies.get(findDimension - 1).get(index[faceId]);
                    System.out.println(String.format("Face Given by %s", Arrays.toString(face)));
                    for (int subFaceId = 0; subFaceId < face.length; subFaceId++) {
                        testSet.add(Arrays.toString(simplicies.get(findDimension - 2).get(face[subFaceId])));
                        //System.out.println(String.format("SubFace Given by %s", Arrays.toString(simplicies.get(findDimension-2).get(face[subFaceId]))));
                        if (testSet.size() > numberOfDimensionMinusTwoFacets) {
                            System.out.println(String.format("TestSet Size is now: %d", testSet.size()));
                            failed = true;
                            break;
                        }
                    }
                }
                System.out.println(String.format("compare NumberOfDimensionMinusTwoFacets: %d vs. %d", testSet.size(), numberOfDimensionMinusTwoFacets));
                System.out.println(Arrays.toString(testSet.toArray()));
                if (testSet.size() == numberOfDimensionMinusTwoFacets) {
                    fixedDimensionSimplex.add(index.clone());
                    System.out.println(String.format("Found %d Dimensional Simplex given by %s", findDimension, Arrays.toString(index)));
                }
                // end generation

                incPosition = index.length - 1; // we start to try increment the rightmost entry

                while (incPosition >= 0 && index[incPosition] == numberOfOneDimensionLowerFacets - 1 + incPosition - findDimension) {
                    // incPosition must be >= 0, because 0 is leftmost entry
                    // we want strongly monotony increasing indices, so we want all (a_i), a_0 < a_1 < ... < a_k, and a_i \in {0,...,n-1}
                    incPosition--;
                }
                if (incPosition >= 0) {
                    index[incPosition]++;
                    for (int j = incPosition + 1; j < index.length; j++) {
                        index[j] = index[j - 1] + 1;
                    }
                }
            } while (incPosition >= 0);


            simplicies.add(fixedDimensionSimplex);

            numberOfOneDimensionLowerFacets = fixedDimensionSimplex.size();
            System.out.println(String.format("FOUND %d SIMPLICIES OF DIMENSION %d", numberOfOneDimensionLowerFacets, findDimension));
            try {
                saveFixedDimensionSimplex(simplicies.size() - 1);
            } catch (IOException aE) {
                aE.printStackTrace();
                System.exit(0);
            }
            findDimension++;
        }
        System.out.println();
    }
}
