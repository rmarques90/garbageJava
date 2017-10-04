import java.util.Random;

/**
 * Created by fox on 26/09/17.
 */
public class Routes {
    private double x;
    private double y;
    private double garbage;
    private double collectTime; // use seconds
    private int collectPoint;

    public Routes(double posX, double posY, double garbageLoad, double timeToCollect, int point) {
        x = posX;
        y = posY;
        garbage = garbageLoad;
        collectTime = timeToCollect;
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

    public double getCollectTime () {
        return collectTime;
    }

    public int getCollectPoint() {
        return collectPoint;
    }

    public static Routes[] generate_random_solution () {
        int size = main.pointsNumber;
        Routes[] randomSolution = new Routes[size];
        Random random = new Random();
        for (int i = 0; i < size; i++) {
            if (i == (main.pointsNumber - 1)) {
                randomSolution[i] = new Routes(0, 0, 0, 0, i);
            } else {
                randomSolution[i] = new Routes(i*random.nextInt(30), i*random.nextInt(30), i*random.nextInt(10), i*random.nextInt(10), i);
            }
        }
        //shuffle the array
        Random rgen = new Random();  // Random number generator

        for (int i=0; i < randomSolution.length; i++) {
            int randomPosition = rgen.nextInt(randomSolution.length);
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

    public static void initialize_collect_points() {
        Random random = new Random();
        for (int i = 0; i < main.nodes.length; i++){
            main.nodes[i] = new Routes(i*random.nextInt(30), i*random.nextInt(30), i*random.nextInt(10), i*random.nextInt(10), i);
        }
        main.matrix = Routes.build_matrix(main.nodes);
    }

    public static double[][] build_matrix (Routes[] collectPoints) {
        double[][] matrix = new double[collectPoints.length][5];
        for (int i = 0; i < collectPoints.length; i++) {
            matrix[i][0] = collectPoints[i].x;
            matrix[i][1] = collectPoints[i].y;
            matrix[i][2] = collectPoints[i].garbage;
            matrix[i][3] = collectPoints[i].collectTime;
            matrix[i][4] = collectPoints[i].collectPoint;
        }
        return matrix;
    }

}
