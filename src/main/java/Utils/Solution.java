package Utils;

import java.util.List;

/**
 * Created by fox on 02/11/17.
 */
public class Solution {
    private List<Routes> solution;
    private Double fitness;

    public Double getFitness() {
        return fitness;
    }

    public List<Routes> getSolution() {
        return solution;
    }

    public void setFitness(Double fitness) {
        this.fitness = fitness;
    }

    public void setSolution(List<Routes> solution) {
        this.solution = solution;
    }
}
