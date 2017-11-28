package Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fox on 27/09/17.
 */
public class ObjectiveFunction {

    //calcular o custo do trajeto + se coletou todos os lixos estabelecidos + penalizar se estourou 6 horas + descartar solucao se nao coletou todo lixo
    private static double medVel = 10.0; //km/h
    private static double gasCost = 3.98; //valor da gasolina


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

    public static double calculate_distance (double posX1, double posX2, double posY1, double posY2) {
        return Math.sqrt(Math.pow(posX1 - posX2, 2) + (Math.pow(posY1 - posY2, 2)));
    }

    public static double calculate_fitness (List<Routes> routes) {
        List<Routes> temp = new ArrayList<Routes>();
        double fitness = 0d;
        int finalIndex = 0;
        int index = 0;
        int trucksQty = 0;
        //percorrer lista e dividir em listas menores por caminhão
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
                fitness -= calculate_distance_fitness(temp);
                fitness -= calculate_garbage_fitness(temp);
                fitness -= calculate_distance_fitness(temp);
                trucksQty++;
            }
            temp.clear();
            finalIndex++;
        }
        fitness -= trucksQty / main.trucks;
        return fitness;
    }

    public static double calculate_distance_fitness (List<Routes> rt) {
        double x1, x2, y1, y2, distanceFitness = 0;
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
            distanceFitness += calculate_distance_cost(x1, x2, y1, y2) / worst_distance_cost();
        }
        return distanceFitness;
    }

    public static double calculate_garbage_fitness (List<Routes> rt) {
        double garbageFitness =0, totalGarbage=0;
        for (int i=0; i < rt.size(); i++) {
            totalGarbage += rt.get(i).getGarbage();
        }
        garbageFitness = totalGarbage / main.truckLoad;
        return garbageFitness;
    }

    public static double calculate_collect_time (List<Routes> rt) {
        double collectTimeFitness =0, totalCollectTime=0, totalDistance=0;
        for (int i=0; i < rt.size(); i++) {
            totalDistance += calculate_distance(rt.get(i).getX(), rt.get(i+1).getX(), rt.get(i).getY(), rt.get(i+1).getY());
        }
        totalCollectTime = totalDistance * medVel;
        return collectTimeFitness;
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
