/**
 * Created by fox on 04/10/17.
 */
public class SolveLocal {

    public static void grasp () {
        int i = 0;
        Routes[] newSolution;
        double newSolutionFitness;
        Solution solution = new Solution();

        main.finalSolution = Routes.generate_random_solution();
        main.finalFitness = ObjectiveFunction.calculate_fitness(main.finalSolution);

        while (i <= main.iterations) {
            newSolution = Routes.mutate_solution(main.finalSolution);
            newSolutionFitness = ObjectiveFunction.calculate_fitness(main.finalSolution);
            if (newSolutionFitness < main.finalFitness) {
                main.finalSolution = newSolution;
                main.finalFitness = newSolutionFitness;
            }
            i++;
        }
    }
}
