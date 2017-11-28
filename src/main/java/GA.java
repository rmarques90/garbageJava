import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.math3.exception.util.LocalizedFormats;
import org.apache.commons.math3.genetics.*;
import org.apache.commons.math3.util.FastMath;
import org.apache.commons.math3.util.Precision;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

//https://www.programcreek.com/java-api-examples/index.php?source_dir=CARMA-master/SIMULATION/eu.quanticol.ms/libs/commons-math3-3.4.1-src/src/userguide/java/org/apache/commons/math3/userguide/genetics/HelloWorldExample.java
public class GA {
    public static final int    POPULATION_SIZE   = 10000;
    public static final double CROSSOVER_RATE    = 0.9;
    public static final double MUTATION_RATE     = 0.03;
    public static final double ELITISM_RATE      = 0.1;
    public static final int    TOURNAMENT_ARITY  = 2;

    public static void main(String[] args) {
        long startTime = System.currentTimeMillis();
        GeneticAlgorithm ga = new GeneticAlgorithm(new OnePointCrossover<Routes>(), CROSSOVER_RATE,
                new RandomMutation(), MUTATION_RATE,
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
                if (generation == 300) {
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
        List<Routes> rt = new ArrayList<Routes>();

        for(int i = 0; i < POPULATION_SIZE; i++) {
            for (int j = 0; j < 60; j++) {
                rt.add(new Routes(1,1,1,1));
            }
            popList.add(new RouteChromosome(rt));
            rt.clear();
        }
        return new ElitisticListPopulation(popList, 2 * popList.size(), ELITISM_RATE);
    }

    private static class RandomMutation implements MutationPolicy {
        public Chromosome mutate(Chromosome original) {
            if (!(original instanceof RouteChromosome)) {
                throw new IllegalArgumentException();
            }

            RouteChromosome strChromosome = (RouteChromosome) original;
            List<Routes> rts = strChromosome.getRoutesRepresentation();

            int mutationIndex = GeneticAlgorithm.getRandomGenerator().nextInt(rts.size());

            List<Routes> mutatedChromosome = new ArrayList<Routes>(rts);
            Routes routes = new Routes(3,2,4,1);
            mutatedChromosome.set(mutationIndex, routes);

            return strChromosome.newFixedLengthChromosome(mutatedChromosome);
        }
    }
}