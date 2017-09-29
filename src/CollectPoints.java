/**
 * Created by fox on 26/09/17.
 */
public class CollectPoints {
    private double x;
    private double y;
    private double garbage;
    private double collectTime; // use seconds
    private int collectPoint;

    public CollectPoints (double xPos, double yPos, double garbageLoad, double time, int collectNumber) {
        x = xPos;
        y = yPos;
        garbage = garbageLoad;
        collectTime = time;
        collectPoint = collectNumber;
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

    public static double[][] build_matrix (CollectPoints[] collectPoints) {
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
