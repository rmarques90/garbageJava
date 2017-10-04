/**
 * Created by fox on 04/10/17.
 */
public class SolveLocal {

    public void grasp () {
        int i = 0;
        Routes[] newSolution;
        double newSolutionFitness;

        main.finalSolution = Routes.generate_random_solution();
        main.finalFitness = ObjetiveFunction.calculate_fitness(main.finalSolution);

        while (i <= main.iterations) {
            newSolution = Routes.get_new_neighbor(main.finalSolution);
            newSolutionFitness = ObjetiveFunction.calculate_fitness(main.finalSolution);
            if (newSolutionFitness < main.finalFitness) {
                main.finalSolution = newSolution;
                main.finalFitness = newSolutionFitness;
            }
            i++;
        }
    }
}
