import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * Created by fox on 26/09/17.
 */
public class Routes {
    private double x;
    private double y;
    private double garbage;
    private int collectPoint;
    private String separator;

    public Routes(double posX, double posY, double garbageLoad, int point) {
        x = posX;
        y = posY;
        garbage = garbageLoad;
        collectPoint = point;
    }

    public Routes(String separator) {
        this.separator = separator;
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    public void setGarbage(double garbage) {
        this.garbage = garbage;
    }

    public void setCollectPoint(int collectPoint) {
        this.collectPoint = collectPoint;
    }

    public String getSeparator() {
        return separator;
    }

    public void setSeparator(String separator) {
        this.separator = separator;
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

    public static List<Routes> generate_random_solution () {
        int size = main.pointsNumber + main.trucks;
        List<Routes> randomSolution = new ArrayList<Routes>();
        Random random = new Random();
        for (int i = 0; i < size; i++) {
            if (i >= main.pointsNumber && i < size) {
                randomSolution.add(new Routes("t"));
            } else {
                if (i == (main.pointsNumber - 1) || i == 0) {
                    randomSolution.add(new Routes(0, 0, 0, 0));
                } else {
                    randomSolution.add(new Routes(i*random.nextInt(30), i*random.nextInt(30), i*random.nextInt(10), i));
                }
            }
        }
        //shuffle the array
        Collections.shuffle(randomSolution);
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

            try {
                stream = new BufferedReader(new FileReader(main.routesFilePath));
                while ((stream.readLine()) != null) {
                    main.pointsNumber++;
                }
                stream = new BufferedReader(new FileReader(main.routesFilePath));
                main.nodes = new ArrayList<Routes>();
                while ((line = stream.readLine()) != null) {
                    splitted = line.split(";");
                    main.nodes.add(new Routes(
                            Double.parseDouble(splitted[0]),
                            Double.parseDouble(splitted[1]),
                            Double.parseDouble(splitted[2]),
                            Integer.parseInt(splitted[3])));
                }
            } catch (Exception e) {
                System.out.println(e);
            }
            main.pointsNumber = main.nodes.size();
            main.matrix = Routes.build_matrix(main.nodes);
        } else {
            Random random = new Random();
            main.nodes = new ArrayList<Routes>();
            main.pointsNumber = 10;
            for (int i = 0; i < main.pointsNumber; i++){
                main.nodes.add(new Routes(i*random.nextInt(30), i*random.nextInt(30), i*random.nextInt(10), i));
            }
            main.matrix = Routes.build_matrix(main.nodes);
        }
    }

    public static double[][] build_matrix (List<Routes> collectPoints) {
        double[][] matrix = new double[collectPoints.size()][5];
        for (int i = 0; i < collectPoints.size(); i++) {
            matrix[i][0] = collectPoints.get(i).x;
            matrix[i][1] = collectPoints.get(i).y;
            matrix[i][2] = collectPoints.get(i).garbage;
            matrix[i][3] = collectPoints.get(i).collectPoint;
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
