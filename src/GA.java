import java.util.ArrayList;

public class GA {

    public static void genetic_algorithm () {
        int i = 0;
        Routes[] newRoute;
        double newRouteFitness;

        ArrayList<Routes[]> routesList = new ArrayList<>(10);

        for (int j = 0; j < 11; j ++) {
            routesList.add(Routes.generate_random_solution());
        }

        main.finalSolution = Routes.generate_random_solution();
        main.finalFitness = ObjectiveFunction.calculate_fitness(main.finalSolution);

        while(i <= main.iterations) {

        }

    }
}
