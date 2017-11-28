import org.apache.commons.math3.genetics.AbstractListChromosome;
import org.apache.commons.math3.genetics.InvalidRepresentationException;

import java.util.List;

public class RouteChromosome extends AbstractListChromosome<Routes> {

    public RouteChromosome(List<Routes> rp) {
        super(rp);
    }

    @Override
    protected void checkValidity(List<Routes> rp) throws InvalidRepresentationException {
        //do nothing
    }

    @Override
    public AbstractListChromosome<Routes> newFixedLengthChromosome(List<Routes> rp) {
        return new RouteChromosome(rp);
    }

    public List<Routes> getRoutesRepresentation() {
        return getRepresentation();
    }

    public double fitness() {
        List<Routes> rt = getRoutesRepresentation();
        return ObjectiveFunction.calculate_fitness(rt);
    }

    @Override
    public String toString() {
        List<Routes> rt = getRoutesRepresentation();
        String finalString = "";
        for (int i = 0; i < rt.size()-1; i++ ) {
            if (rt.get(i).getSeparator() == null) {
                finalString = finalString + " " + rt.get(i).getCollectPoint();
            } else {
                finalString = finalString + " " + rt.get(i).getSeparator();
            }
        }
        return String.format("(f=%s - sol: %s)", getFitness(), finalString);
    }
}
