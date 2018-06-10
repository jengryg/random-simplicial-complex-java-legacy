package math.simulation.random_simplicial_complex;

import math.simulation.common.DimensionErrorException;
import math.simulation.common.SaveFileAbstract;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @TODO implement persitence of adj Matrix, componentPointsMap, componentPointCount, componentCount, componentIndexOfLargest, degrees
 */
abstract public class SimplicialComplexAbstract extends SaveFileAbstract {
    protected List<Integer> fVector = new ArrayList<>();
    protected List<List<int[]>> simplicies = new ArrayList<>();
    protected List<List<Integer>> componentMap = new ArrayList<>();
    protected List<List<List<int[]>>> componentSimplicies = new ArrayList<>();

    List<int[]> triangles = new ArrayList<>();

    protected boolean[][] adjacencyMatrix;

    protected int[] componentPointsMap;

    protected int[] componentPointCount;

    protected int componentCount = 0;

    protected int componentIndexOfLargest;

    protected List<Integer> degrees = new ArrayList<>();

    public List<List<int[]>> getSimplicies() {
        return simplicies;
    }

    public List<Integer> getfVector() {
        return fVector;
    }

    public boolean[][] getAdjacencyMatrix() {
        return adjacencyMatrix;
    }

    public int[] getComponentPointsMap() {
        return componentPointsMap;
    }

    public int[] getComponentPointCount() {
        return componentPointCount;
    }


    public int getComponentCount() {
        return componentCount;
    }

    public List<Integer> getDegrees() {
        return degrees;
    }

    public int getComponentIndexOfLargest() {
        return componentIndexOfLargest;
    }

    public List<List<Integer>> getComponentMap() {
        return componentMap;
    }

    public List<List<List<int[]>>> getComponentSimplicies() {
        return componentSimplicies;
    }

    public List<int[]> getTriangles() {
        return triangles;
    }

    public SimplicialComplexAbstract(String code) {
        setLoadCode(code);

        try {
            load();
        } catch (IOException aE) {
            aE.printStackTrace();
        }
    }

    protected SimplicialComplexAbstract() {

    }

    public SimplicialComplexAbstract(List<List<int[]>> aSimplicies) {
        simplicies = aSimplicies;
        recalculateFVector();
    }

    protected void recalculateFVector() {
        fVector.clear();
        for (int i = 0; i < simplicies.size(); i++) {
            System.out.println(String.format("fVector %d = %d", i, simplicies.get(i).size()));
            fVector.add(i, simplicies.get(i).size());
        }
    }

    @SuppressWarnings("uncecked")
    public void load() throws IOException {
        FileInputStream fi = new FileInputStream(String.format("%s.%s", getFullLoadName(), "f_vector"));
        ObjectInputStream oi = new ObjectInputStream(fi);
        try {
            fVector = (ArrayList<Integer>) oi.readObject();
        } catch (ClassNotFoundException aE) {
            aE.printStackTrace();
        }
        oi.close();
        fi.close();

        if (fVector.size() > 0) {
            for (int i = 0; i < fVector.size(); i++) {
                try {
                    loadFixedDimensionSimplex(i);
                } catch (IOException aE) {
                    aE.printStackTrace();
                }
            }
        }
    }

    public void save() throws IOException {
        FileOutputStream fo = new FileOutputStream(String.format("%s.%s", getFullSaveName(), "f_vector"));
        ObjectOutputStream oo = new ObjectOutputStream(fo);
        oo.writeObject(fVector);
        oo.close();
        fo.close();

        if (fVector.size() > 0) {
            for (int i = 0; i < fVector.size(); i++) {
                System.out.println(fVector.get(i));
                try {
                    saveFixedDimensionSimplex(i);
                } catch (IOException aE) {
                    aE.printStackTrace();
                }
            }
        }
    }

    @SuppressWarnings("unchecked")
    public void loadFixedDimensionSimplex(int index) throws IOException {
        FileInputStream fi = new FileInputStream(String.format("%s.%s.%d", getFullLoadName(), "fixed_dimension_simplex", index));
        ObjectInputStream oi = new ObjectInputStream(fi);
        try {
            List<int[]> fixedDimensionSimplex = (ArrayList<int[]>) oi.readObject();
            simplicies.add(index, fixedDimensionSimplex);
        } catch (ClassNotFoundException aE) {
            aE.printStackTrace();
        }
        oi.close();
        fi.close();
    }

    public void saveFixedDimensionSimplex(int index) throws IOException {
        FileOutputStream fo = new FileOutputStream(String.format("%s.%s.%d", getFullSaveName(), "fixed_dimension_simplex", index));
        ObjectOutputStream oo = new ObjectOutputStream(fo);
        oo.writeObject(simplicies.get(index));
        oo.close();
        fo.close();
    }

    public void printSimplicies() {
        for (int d = 0; d < simplicies.size(); d++) {
            System.out.println(String.format("%d dimensional Simplicies: %d", d, simplicies.get(d).size()));
            for (int i = 0; i < simplicies.get(d).size(); i++) {
                System.out.println(Arrays.toString(simplicies.get(d).get(i)));
            }
        }
    }


    public int getMaxDimension() {
        return fVector.size();
    }

    public abstract void generate() throws DimensionErrorException;

    public void generateAdjacencyMatrix() {
        adjacencyMatrix = new boolean[simplicies.get(0).size()][simplicies.get(0).size()];

        for (int i = 0; i < adjacencyMatrix.length; i++) {
            for (int j = 0; j < adjacencyMatrix.length; j++) {
                adjacencyMatrix[i][j] = false;
            }
        }

        int[] edge = new int[2];

        for (int edgeId = 0; edgeId < simplicies.get(1).size(); edgeId++) {
            edge = simplicies.get(1).get(edgeId); // edge is {x,y}
            adjacencyMatrix[edge[0]][edge[1]] = true; // x -> y
            adjacencyMatrix[edge[1]][edge[0]] = true; // y -> x
        }
    }

    public void generateComponents() {

        if (simplicies.get(0).size() != 0) {
            componentCount = 1;
            componentPointsMap = new int[simplicies.get(0).size()];


            boolean visited[] = new boolean[simplicies.get(0).size()];

            for (int i = 0; i < visited.length; i++) {
                visited[i] = false;
            }

            int remaining = visited.length;

            while (remaining > 0) {
                System.out.println(String.format("ComponentAlgorithm: starting component # %d", componentCount));
                int root = 0;
                while (visited[root]) { // visited points can not be root of a new component
                    root++; // find first no visited point
                }

                System.out.println(String.format("ComponentAlgorithm: root=%d", root));

                int current = root;
                boolean haveNext = true;
                while (haveNext) { // as long as we are not done with all vertices of this component
                    System.out.println(String.format("ComponentAlgorithm: current=%d", current));
                    visited[current] = true; // we visit the current point
                    remaining--;

                    for (int i = 0; i < adjacencyMatrix.length; i++) {
                        if (adjacencyMatrix[current][i]) { // if connected
                            System.out.println(String.format("ComponentAlgorithm: %d connected to %d", current, i));
                            componentPointsMap[i] = componentCount; // i belongs to component with componentCount as number
                        }
                    }

                    haveNext = false;

                    for (int i = 0; i < adjacencyMatrix.length; i++) {
                        if (componentPointsMap[i] == componentCount && !visited[i]) {
                            current = i;
                            haveNext = true;
                            break;
                        }
                    }
                }

                componentCount++;
            }
        }

        componentMap.clear();

        for (int d = 0; d < simplicies.size(); d++) {
            List<Integer> componentDimensionMap = new ArrayList<>();
            for (int i = 0; i < simplicies.get(d).size(); i++) {
                componentDimensionMap.add(i, componentPointsMap[simplicies.get(d).get(i)[0]]);
            }
            componentMap.add(d, componentDimensionMap);
        }

        for (int c = 0; c < componentCount; c++) {
            List<List<int[]>> component = new ArrayList<>();
            for (int d = 0; d < simplicies.size(); d++) {
                List<int[]> fixedDimensionSimplicies = new ArrayList<>();
                for (int i = 0; i < simplicies.get(d).size(); i++) {
                    if (c == componentMap.get(d).get(i)) {
                        fixedDimensionSimplicies.add(simplicies.get(d).get(i));
                    }
                }
                component.add(d, fixedDimensionSimplicies);
            }
            componentSimplicies.add(c, component);
        }
    }

    public void recalculateComponentPointCount() {
        componentPointCount = new int[componentCount];

        for (int i = 0; i < componentPointsMap.length; i++) {
            componentPointCount[componentPointsMap[i]]++;
        }
    }

    public void recalculateComponentIndexOfLargest() {
        if (componentPointCount == null || componentPointCount.length == 0) {
            componentIndexOfLargest = -1;
        } else {
            componentIndexOfLargest = 0;
            for (int i = 1; i < componentPointCount.length; i++) {
                if (componentPointCount[i] > componentPointCount[componentIndexOfLargest]) componentIndexOfLargest = i;
            }
        }
    }

    public void recalculateDegrees() {
        degrees.clear();
        int deg;
        for (int pointId = 0; pointId < simplicies.get(0).size(); pointId++) {
            deg = 0;
            for (int j = 0; j < adjacencyMatrix.length; j++) {
                if (adjacencyMatrix[pointId][j]) {
                    deg++;
                }
            }
            degrees.add(deg);
        }
    }

    public void findTriangles() {
        triangles.clear();
        for (int i = 0; i < adjacencyMatrix.length; i++) {
            if ((degrees.get(i) != 2 && degrees.get(i) != 3)) {
                continue;
            }
            for (int j = i + 1; j < adjacencyMatrix.length; j++) {
                if (!adjacencyMatrix[i][j] || (degrees.get(j) != 2 && degrees.get(j) != 3)) {
                    continue;
                }
                for (int k = j + 1; k < adjacencyMatrix.length; k++) {
                    if (!adjacencyMatrix[i][k] || !adjacencyMatrix[j][k] || (degrees.get(k) != 2 && degrees.get(k) != 3)) {
                        continue;
                    }
                    if (degrees.get(i) + degrees.get(j) + degrees.get(k) == 6) {
                        triangles.add(new int[]{i, j, k,0});
                        System.out.println(String.format("TSA: ISOLATED Triangle = %s", Arrays.toString(new int[]{i, j, k, 0})));
                    } else if (degrees.get(i) + degrees.get(j) + degrees.get(k) == 7) {
                        triangles.add(new int[]{i, j, k,1});
                        System.out.println(String.format("TSA: CONNECTED Triangle = %s", Arrays.toString(new int[]{i, j, k, 1})));
                    }
                }
            }
        }
    }

    public void printTriangles() {

        int[] triangle;

        for (int i = 0; i < triangles.size(); i++) {
            triangle = triangles.get(i);
            System.out.println(Arrays.toString(triangle));
            if (triangle[3] == 0) {
                System.out.println(String.format("Triangle %s is isolated", Arrays.toString(new int[]{triangle[0], triangle[1], triangle[2]})));
            } else {
                System.out.println(String.format("Triangle %s is connected", Arrays.toString(new int[]{triangle[0], triangle[1], triangle[2]})));
            }
        }
    }

    public void printAdjacencyMatrix() {
        for (int i = 0; i < adjacencyMatrix.length; i++) {
            System.out.println(Arrays.toString(adjacencyMatrix[i]));
        }
    }
}
