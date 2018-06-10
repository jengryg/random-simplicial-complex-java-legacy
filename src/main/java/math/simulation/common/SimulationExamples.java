package math.simulation.common;

public class SimulationExamples {

    public static void generationOfPoissonPointProcess() throws Exception {
        PoissonPointProcessSVG poissonPointProcessSVG = new PoissonPointProcessSVG(new int[]{8, 6}, 1);
        poissonPointProcessSVG.generate();
        poissonPointProcessSVG.save();
        poissonPointProcessSVG.svgStepConstruction();
    }

    public static void intensityIncreasingPoissonPointProcess() throws Exception {

        double[] intensities = new double[]{2.0, 4.0, 8.0, 16.0, 32.0, 0.5, 0.1};

        String nameBase = SaveFileAbstract.generateNameBase();

        PoissonPointProcessSVG[] poissonPointProcessSVG = new PoissonPointProcessSVG[intensities.length];

        for (int i = 0; i < poissonPointProcessSVG.length; i++) {
            SaveFileAbstract.setSaveCode(String.format("%s-%f", nameBase, intensities[i]));

            poissonPointProcessSVG[i] = new PoissonPointProcessSVG(new int[]{8, 6}, intensities[i]);
            poissonPointProcessSVG[i].generate();
            poissonPointProcessSVG[i].save();
            poissonPointProcessSVG[i].svgStepConstruction();
        }
    }
}
