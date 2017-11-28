import java.util.ArrayList;
import java.util.List;

/**
 * Created by fox on 27/09/17.
 */
public class ObjectiveFunction {

    //calcular o custo do trajeto + se coletou todos os lixos estabelecidos + penalizar se estourou 6 horas + descartar solucao se nao coletou todo lixo
    private static double medVel = 10.0; //km/h
    private static double gasCost = 3.98; //valor da gasolina

    private static int solutionIndex = 0;


    public ObjectiveFunction () {
    }

    public static double worst_distance_cost () {
        return (main.workHours * medVel) * gasCost;
    }

    public static double calculate_total_garbage () {
        double totalGarbage = 0;
        for (int i=0; i < main.nodes.size(); i++) {
            totalGarbage += main.nodes.get(i).getGarbage();
        }
        return totalGarbage;
    }

    public static double calculate_distance_cost (double posX1, double posX2, double posY1, double posY2) {
        double distance = Math.sqrt(Math.pow(posX1 - posX2, 2) + (Math.pow(posY1 - posY2, 2)));
        return distance * gasCost;
    }

    public static double calculate_fitness (Solution solution) {
        List<Routes> routes = solution.getSolution();
        List<Routes> tempRoutes = new ArrayList<Routes>();
        double fitness = 0d;
        int j = 0;
        int i = 0;
        while (j < routes.size()) {
            tempRoutes.clear();
            if (i>0) {
                i++;
            }
            if (i <= routes.size()) {
                while (routes.get(i).getSeparator() == null) {
                    tempRoutes.add(routes.get(i));
                    i++;
                }
                fitness += calculate_fitness_value(tempRoutes);
            }
            j++;

        }
        return fitness;
    }

    public static double calculate_fitness_value (List<Routes> rt) {
        double x1, x2, y1, y2, garbageTotal = 0, totalTimeCost = 0, totalDistanceCost = 0, fitness = 0, trucksQty = 0;
        int size = rt.size();
        for (int i = 0; i < size; i++) {
            if (i == size - 1) {
                x1 = rt.get(i).getX();
                x2 = 0;
                y1 = rt.get(i).getY();
                y2 = 0;
            } else {
                x1 = rt.get(i).getX();
                x2 = rt.get(i).getX();
                y1 = rt.get(i).getY();
                y2 = rt.get(i).getY();
            }
            totalDistanceCost = totalDistanceCost + (calculate_distance_cost(x1, x2, y1, y2) / worst_distance_cost());
            garbageTotal = garbageTotal + rt.get(i).getGarbage();
//            totalTimeCost = totalTimeCost + calculate_travel_time(calculate_distance_cost(x1, x2, y1, y2));
        }

        totalDistanceCost = totalDistanceCost / rt.size();

        trucksQty = garbageTotal / main.truckLoad;

        fitness = totalDistanceCost + (garbageTotal / calculate_total_garbage()) + trucksQty;
        return fitness;
    }

    public static Boolean check_best_solution_by_fitness (Solution solution) {
        double oldFitness = main.finalSolution.getFitness();
        if (solution.getFitness() > oldFitness) {
            main.finalSolution = solution;
            return true;
        } else {
            return false;
        }
    }
}
