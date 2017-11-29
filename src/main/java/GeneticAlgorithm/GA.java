package GeneticAlgorithm;

import Utils.Routes;
import org.apache.commons.math3.genetics.*;

import java.util.*;

import static Utils.Constants.generations;
import static Utils.Constants.nodes;

public class GA {
    public static final int    POPULATION_SIZE   = 10000;
    public static final double CROSSOVER_RATE    = 0.9;
    public static final double MUTATION_RATE     = 0.3;
    public static final double ELITISM_RATE      = 0.4;
    public static final int    TOURNAMENT_ARITY  = 2;

    public static void main(String[] args) {
        long startTime = System.currentTimeMillis();
        GeneticAlgorithm ga = new GeneticAlgorithm(new OnePointCrossover<Routes>(), CROSSOVER_RATE,
                new RouteMutation(), MUTATION_RATE,
                new TournamentSelection(TOURNAMENT_ARITY));

        Population initial = getInitialPopulation();

        StoppingCondition stoppingCondition = new StoppingCondition() {
            int generation = 0;

            public boolean isSatisfied(Population population) {
                Chromosome fittestChromosome = population.getFittestChromosome();

                if (generation == 1 || generation % 10 == 0) {
                    System.out.println("Generation " + generation + ": " + fittestChromosome.toString());
                }
                generation++;

                double fitness = fittestChromosome.fitness();
                if (generation == generations) {
                    System.out.println(fitness);
                    return true;
                } else {
                    System.out.println(fitness);
                    return false;
                }
            }
        };

        System.out.println("Starting evolution...");

        Population finalPopulation = ga.evolve(initial, stoppingCondition);

        long endTime = System.currentTimeMillis();

        Chromosome best = finalPopulation.getFittestChromosome();
        System.out.println("Generation" + ga.getGenerationsEvolved() + ": " + best.toString());
        System.out.println("Total execution time: " + (endTime - startTime) + "ms");
    }

    private static Population getInitialPopulation() {
        List<Chromosome> popList = new LinkedList<Chromosome>();
        Routes.initialize_collect_points(true);

        for(int i = 0; i < POPULATION_SIZE; i++) {
            popList.add(new RouteChromosome(nodes));
        }
        return new ElitisticListPopulation(popList, 2 * popList.size(), ELITISM_RATE);
    }
}