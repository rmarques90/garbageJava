package GeneticAlgorithm;

import Utils.Routes;
import org.apache.commons.math3.exception.MathIllegalArgumentException;
import org.apache.commons.math3.genetics.Chromosome;
import org.apache.commons.math3.genetics.GeneticAlgorithm;
import org.apache.commons.math3.genetics.MutationPolicy;

import java.util.ArrayList;
import java.util.List;

public class RouteMutation implements MutationPolicy {

    public RouteMutation() {
    }

    public Chromosome mutate(Chromosome chromosome) throws MathIllegalArgumentException {
        if (!(chromosome instanceof RouteChromosome)) {
            throw new IllegalArgumentException();
        }

        RouteChromosome routes = (RouteChromosome) chromosome;
        List<Routes> routesList = routes.getRoutesRepresentation();

        int mutationIndex = GeneticAlgorithm.getRandomGenerator().nextInt(routesList.size());

        List<Routes> mutatedList = new ArrayList<Routes>(routesList);
        Routes rt = new Routes(2,2,2,2);
        mutatedList.set(mutationIndex, rt);

        return new RouteChromosome(mutatedList);
    }
}
