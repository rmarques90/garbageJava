package GeneticAlgorithm;

import Utils.Routes;
import org.apache.commons.math3.genetics.*;

import java.util.*;

//https://www.programcreek.com/java-api-examples/index.php?source_dir=CARMA-master/SIMULATION/eu.quanticol.ms/libs/commons-math3-3.4.1-src/src/userguide/java/org/apache/commons/math3/userguide/genetics/HelloWorldExample.java
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
                if (fitness < 4 && generation > 100) {
                    System.out.println(fitness);
                    return true;
                } else {
                    System.out.println(fitness);
                    return false;
                }
            }
        };

//        StoppingCondition stoppingCondition = new FixedGenerationCount(3000);

        System.out.println("Starting evolution...");

        Population finalPopulation = ga.evolve(initial, stoppingCondition);

        long endTime = System.currentTimeMillis();

        Chromosome best = finalPopulation.getFittestChromosome();
        System.out.println("Generation" + ga.getGenerationsEvolved() + ": " + best.toString());
        System.out.println("Total execution time: " + (endTime - startTime) + "ms");
    }

    private static Population getInitialPopulation() {
        List<Chromosome> popList = new LinkedList<Chromosome>();
        List<Routes> rt = new ArrayList<Routes>();
        Random random = new Random();

        for(int i = 0; i < POPULATION_SIZE; i++) {
            for (int j = 0; j < 60; j++) {
                rt.add(new Routes(random.nextInt(10),random.nextInt(10),random.nextInt(20),j));
            }
            popList.add(new RouteChromosome(rt));
            rt.clear();
        }
        return new ElitisticListPopulation(popList, 2 * popList.size(), ELITISM_RATE);
    }
}