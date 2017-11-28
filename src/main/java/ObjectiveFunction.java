import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        List<Routes> temp = new ArrayList<Routes>();
        double fitness = 0d;
        int initialIndex = 0;
        int finalIndex = 0;
        int index = 0;
        while (finalIndex < routes.size()-1 && index < routes.size()-1) {
            if (index==0 && routes.get(index).getSeparator() != null) {
                index++;
            }
            if (index > 0 && routes.get(index).getSeparator() != null) {
                index++;
            }
            while (routes.get(index).getSeparator() == null && index < routes.size()-1) {
                temp.add(routes.get(index));
                index++;
            }
            if (temp.size() > 0) {
                fitness += calculate_fitness_value(temp);
            }
            temp.clear();
            finalIndex++;
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
                x2 = rt.get(i+1).getX();
                y1 = rt.get(i).getY();
                y2 = rt.get(i+1).getY();
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
