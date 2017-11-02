import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.lang.reflect.Array;
import java.util.Collections;
import java.util.Random;

/**
 * Created by fox on 26/09/17.
 */
public class Routes {
    private double x;
    private double y;
    private double garbage;
    private int collectPoint;

    public Routes(double posX, double posY, double garbageLoad, int point) {
        x = posX;
        y = posY;
        garbage = garbageLoad;
        collectPoint = point;
    }

    public double getX () {
        return x;
    }

    public double getY () {
        return y;
    }

    public double getGarbage () {
        return garbage;
    }

    public int getCollectPoint() {
        return collectPoint;
    }

    public static Routes[] generate_random_solution () {
        int size = main.pointsNumber;
        Routes[] randomSolution = new Routes[size];
        Random random = new Random();
        for (int i = 0; i < size; i++) {
            if (i == (main.pointsNumber - 1) || i == 0) {
                randomSolution[i] = new Routes(0, 0, 0, 0);
            } else {
                randomSolution[i] = new Routes(i*random.nextInt(30), i*random.nextInt(30), i*random.nextInt(10), i);
            }
        }
        //shuffle the array
        Random rgen = new Random();  // Random number generator

        for (int i=1; i < (randomSolution.length - 1); i++) {
            int randomPosition = rgen.nextInt((randomSolution.length - 2) + 1 - 1) + 1;
            Routes temp = randomSolution[i];
            randomSolution[i] = randomSolution[randomPosition];
            randomSolution[randomPosition] = temp;
        }
        return randomSolution;
    }

    public static void initialize_garbage_trucks (int qty) {
        if (qty <= main.max_allowed_trucks) {
            main.trucks = qty;
        } else {
            System.out.println("Número máximo de caminhões excedido.");
            System.exit(1);
        }
    }

    public static void initialize_collect_points(Boolean csvFile) {
        if (csvFile) {
            String line;

            BufferedReader stream;
            String[] splitted;
            int index = 0;

            try {
                stream = new BufferedReader(new FileReader(main.routesFilePath));
                while ((stream.readLine()) != null) {
                    main.pointsNumber++;
                }
                stream = new BufferedReader(new FileReader(main.routesFilePath));
                main.nodes = new Routes[main.pointsNumber];
                while ((line = stream.readLine()) != null) {
                    splitted = line.split(";");
                    main.nodes[index] = new Routes(
                            Double.parseDouble(splitted[0]),
                            Double.parseDouble(splitted[1]),
                            Double.parseDouble(splitted[2]),
                            Integer.parseInt(splitted[3]));
                    index++;
                }
            } catch (Exception e) {
                System.out.println(e);
            }
            main.pointsNumber = main.nodes.length;
            main.matrix = Routes.build_matrix(main.nodes);
        } else {
            Random random = new Random();
            main.nodes = new Routes[10];
            main.pointsNumber = main.nodes.length;
            for (int i = 0; i < main.nodes.length; i++){
                main.nodes[i] = new Routes(i*random.nextInt(30), i*random.nextInt(30), i*random.nextInt(10), i);
            }
            main.matrix = Routes.build_matrix(main.nodes);
        }
    }

    public static double[][] build_matrix (Routes[] collectPoints) {
        double[][] matrix = new double[collectPoints.length][5];
        for (int i = 0; i < collectPoints.length; i++) {
            matrix[i][0] = collectPoints[i].x;
            matrix[i][1] = collectPoints[i].y;
            matrix[i][2] = collectPoints[i].garbage;
            matrix[i][3] = collectPoints[i].collectPoint;
        }
        return matrix;
    }

//        public static void build_distance_matrix () {
//        double distance = 0;
//        double[][] tempDistance = new double[main.matrix.length][main.matrix.length];
//        for (int i = 0; i < main.matrix.length; i ++) {
//            for (int j = 0; j < main.matrix.length; j ++) {
//                distance = ObjectiveFunction.calculate_distance(main.matrix[i][0], main.matrix[j][0], main.matrix[i][1], main.matrix[j][1]);
//                tempDistance[i][j] = distance;
//            }
//        }
//        main.distanceMatrix = new double[tempDistance.length][tempDistance[0].length];
//        main.distanceMatrix = tempDistance;
//
//        for (int i = 0; i < main.distanceMatrix.length; i ++) {
//            for (int j = 0; j < main.distanceMatrix[0].length; j++) {
//                System.out.print(main.distanceMatrix[i][j] + " ");
//            }
//            System.out.println();
//        }
//    }
}
