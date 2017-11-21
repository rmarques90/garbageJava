/**
 * Created by fox on 02/11/17.
 */
public class Solution {
    private Routes[] solution;
    private Double fitness;

    public Double getFitness() {
        return fitness;
    }

    public Routes[] getSolution() {
        return solution;
    }

    public void setFitness(Double fitness) {
        this.fitness = fitness;
    }

    public void setSolution(Routes[] solution) {
        this.solution = solution;
    }
}
