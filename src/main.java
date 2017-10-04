import java.util.Arrays;

/**
 * Created by fox on 26/09/17.
 */

public class main {
    public static int iterations = 1000;

    public static int workHours = 6;

    public static int max_allowed_trucks = 10;
    public static int trucks = 0;
    public static int truckLoad = 300;
    public static int pointsNumber = 10;
    public static Routes[] nodes = new Routes[pointsNumber];
    public static double[][] matrix;
    public static Routes[] finalSolution = new Routes[pointsNumber];

    public static void main (String[] args) {
        Timer timer = new Timer();
        timer.start();
        Routes.initialize_garbage_trucks(1);
        Routes.initialize_collect_points();

        int i = 0;
        Routes[] randomSolution;
        double randomFitness;

        double[] solutionNew;
        double newSolutionFitness = 0.0;

        while (i <= iterations) {
            randomSolution = Routes.generate_random_solution();
            randomFitness = ObjetiveFunction.calculate_fitness(randomSolution);
            if (newSolutionFitness < randomFitness) {
                 finalSolution = randomSolution;
            }
            i++;
        }


        timer.stop();
        System.out.print("Tempo para Execução (Segundos): " + timer.getTime());
        System.out.println();
        for (int index = 0; index < finalSolution.length; index++) {
            if (index == 0) {
                System.out.print("Solução Final: ");
            }
            System.out.print(finalSolution[index].getCollectPoint() + " ");
        }


    }



//    public static void build_distance_matrix () {
//        double distance = 0;
//        double[][] tempDistance = new double[matrix.length][matrix.length];
//        for (int i = 0; i < matrix.length; i ++) {
//            for (int j = 0; j < matrix.length; j ++) {
//                distance = ObjetiveFunction.calculate_distance(matrix[i][0], matrix[j][0], matrix[i][1], matrix[j][1]);
//                tempDistance[i][j] = distance;
//            }
//        }
//        distanceMatrix = new double[tempDistance.length][tempDistance[0].length];
//        distanceMatrix = tempDistance;
//
//        for (int i = 0; i < distanceMatrix.length; i ++) {
//            for (int j = 0; j < distanceMatrix[0].length; j++) {
//                System.out.print(distanceMatrix[i][j] + " ");
//            }
//            System.out.println();
//        }
//    }


}
