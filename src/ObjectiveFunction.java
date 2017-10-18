/**
 * Created by fox on 27/09/17.
 */
public class ObjectiveFunction {

    //calcular o custo do trajeto + se coletou todos os lixos estabelecidos + penalizar se estourou 6 horas +
    private static double medVel = 10.0; //km/h
    private static double gasCost = 3.98; //valor da gasolina


    public ObjectiveFunction () {
    }

    public static double worst_distance_cost () {
        return (main.workHours * medVel) * gasCost;
    }

    public static double calculate_distance_cost (double posX1, double posX2, double posY1, double posY2) {
        double distance = Math.sqrt(Math.pow(posX1 - posX2, 2) + (Math.pow(posY1 - posY2, 2)));
        return distance * gasCost;
    }

    public static double calculate_travel_time (double distance) {
        medVel = medVel * 3.6;
        return medVel * distance;
    }

    public static double calculate_fitness (Routes[] solution) {
        double x1, x2, y1, y2, distance = 0, garbageTotal = 0, collectTotalTime = 0, totalTimeCost = 0, totalDistanceCost = 0, fitness = 0, trucksQty = 0;
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
        }

        return fitness;
    }
}
