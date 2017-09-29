import java.util.Random;

/**
 * Created by fox on 26/09/17.
 */

public class main {
    public static int iterations = 1000;

    public static int workHours = 6;

    public static int max_allowed_trucks = 10;
    public static int trucks = 0;
    public static int truckLoad = 300;
    public static int pointsNumber = 50;
    public static CollectPoints nodes[] = new CollectPoints[pointsNumber];
    public static double[][] matrix;
    public static double[][] distanceMatrix;

    public static void main (String[] args) {
        initialize_garbage_trucks(6);
        initialize_collect_points();
        build_distance_matrix();

        //GRASP
        int i = 0;
        double solution = 0;
        double solutionNew = 0;
        double finalSolution = 0;
        while (i <= iterations) {
            solution = generate_random_solution();
            solutionNew = local_search(solution);
            if (solutionNew < solution) {
                 finalSolution = solutionNew;
            }
            i++;
        }


        System.out.print(finalSolution);


    }

    public static void initialize_garbage_trucks (int qty) {
        if (qty <= max_allowed_trucks) {
            trucks = qty;
        } else {
            System.out.println("Número máximo de caminhões excedido.");
        }
    }

    public static void initialize_collect_points() {
        Random random = new Random();
        for (int i = 0; i < nodes.length; i++){
            nodes[i] = new CollectPoints(i*(random.nextInt(30)), i*(random.nextInt(30)), i*(random.nextInt(60)), i*(random.nextInt(5)), i);
        }
        matrix = CollectPoints.build_matrix(nodes);

        System.out.println();
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                System.out.print(matrix[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println();
    }

    public static void build_distance_matrix () {
        double distance = 0;
        double[][] tempDistance = new double[matrix.length][matrix.length];
        for (int i = 0; i < matrix.length; i ++) {
            for (int j = 0; j < matrix.length; j ++) {
                distance = calculate_distance(matrix[i][0], matrix[j][0], matrix[i][1], matrix[j][1]);
                tempDistance[i][j] = distance;
            }
        }
        distanceMatrix = new double[tempDistance.length][tempDistance[0].length];
        distanceMatrix = tempDistance;

        for (int i = 0; i < distanceMatrix.length; i ++) {
            for (int j = 0; j < distanceMatrix[0].length; j++) {
                System.out.print(distanceMatrix[i][j] + " ");
            }
            System.out.println();
        }
    }

    public static double calculate_distance (double posX1, double posX2, double posY1, double posY2) {
        return Math.sqrt(Math.pow(posX1 - posX2, 2) + (Math.pow(posY1 - posY2, 2)));
    }

    public static double local_search (double solution) {
        return 0;
    }
    //GRASP
    public static double generate_random_solution () {
        return 0;
    }
}
