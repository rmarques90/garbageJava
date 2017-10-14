/**
 * Created by fox on 27/09/17.
 */
public class ObjetiveFunction {

    private static double medVel = 5.0; //km/h

    public ObjetiveFunction () {
    }

    public static double calculate_distance (double posX1, double posX2, double posY1, double posY2) {
        return Math.sqrt(Math.pow(posX1 - posX2, 2) + (Math.pow(posY1 - posY2, 2)));
    }

    public static double calculate_travel_time (double distance) {
        medVel = medVel * 3.6;
        return medVel * distance;
    }

    public static double calculate_fitness (Routes[] solution) {
        double x1, x2, y1, y2, distance = 0, garbageTotal = 0, collectTotalTime = 0, totalTimeCost = 0, totalDistanceCost = 0;
        int size = solution.length;
        for (int i = 0; i < size; i++) {
            if (i == size - 1) {
                x1 = solution[i].getX();
                x2 = 0;
                y1 = solution[i].getY();
                y2 = 0;
            } else {
                x1 = solution[i].getX();
                x2 = solution[i+1].getX();
                y1 = solution[i].getY();
                y2 = solution[i+1].getY();
            }
            garbageTotal = garbageTotal + (solution[i].getGarbage());

            distance = distance + (calculate_distance(x1, x2, y1, y2));

            collectTotalTime = collectTotalTime + (solution[i].getCollectTime());

            //calculate the costs
            totalDistanceCost = distance * 0.70;
            totalTimeCost = (collectTotalTime * 0.12) + (calculate_travel_time(totalDistanceCost));

        }
        return totalDistanceCost + totalTimeCost;
    }
}
