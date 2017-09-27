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


        System.out.print("ok");


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
            nodes[i] = new CollectPoints(i*(random.nextInt(30)), i*(random.nextInt(30)), i*(random.nextInt(60)), i*(random.nextInt(5)));
        }
        matrix = CollectPoints.build_matrix(nodes);

        System.out.println();
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                System.out.print(matrix[i][j] + " ");
            }
            System.out.println();
        }
    }

    public static void build_distance_matrix () {
        double distance = 0;
        double[][] tempDistance = new double[matrix.length][matrix.length];
        for (int i = 0; i < matrix.length; i ++) {
            for (int j = 0; j < matrix.length; j ++) {
                distance = Math.sqrt(Math.pow(matrix[i][0] - matrix[j][0], 2) + (Math.pow(matrix[i][1] - matrix[j][1], 2)));
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



}
