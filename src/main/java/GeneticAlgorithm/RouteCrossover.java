package GeneticAlgorithm;

import Utils.Routes;
import org.apache.commons.math3.exception.DimensionMismatchException;
import org.apache.commons.math3.exception.MathIllegalArgumentException;
import org.apache.commons.math3.exception.util.LocalizedFormats;
import org.apache.commons.math3.genetics.*;

import java.util.ArrayList;
import java.util.List;

public class RouteCrossover implements CrossoverPolicy {
    public RouteCrossover(){
    }

    public ChromosomePair crossover(Chromosome first, Chromosome second) throws MathIllegalArgumentException {
        if (first instanceof RouteChromosome && second instanceof RouteChromosome) {
            return this.crossover((RouteChromosome) first,(RouteChromosome) second);
        } else {
            throw new MathIllegalArgumentException(LocalizedFormats.INVALID_FIXED_LENGTH_CHROMOSOME, new Object[0]);
        }
    }

    private ChromosomePair crossover(RouteChromosome first, RouteChromosome second) throws DimensionMismatchException {
        int length = first.getLength();
        if (length != second.getLength()) {
            throw new DimensionMismatchException(second.getLength(), length);
        } else {
            List<Routes> parent1Rep = first.getRoutesRepresentation();
            List<Routes> parent2Rep = second.getRoutesRepresentation();
            ArrayList<Routes> child1Rep = new ArrayList(first.getLength());
            ArrayList<Routes> child2Rep = new ArrayList(second.getLength());
            int crossoverIndex = 1 + GeneticAlgorithm.getRandomGenerator().nextInt(length - 2);

            int i;
            for(i = 0; i < crossoverIndex; ++i) {
                child1Rep.add(parent1Rep.get(i));
                child2Rep.add(parent2Rep.get(i));
            }

            for(i = crossoverIndex; i < length; ++i) {
                child1Rep.add(parent2Rep.get(i));
                child2Rep.add(parent1Rep.get(i));
            }

            return new ChromosomePair(first.newFixedLengthChromosome(child1Rep), second.newFixedLengthChromosome(child2Rep));
        }
    }
}
