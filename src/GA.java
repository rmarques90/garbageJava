import java.util.Random;

public class GA {

    public static Routes[] mutate_solution (Routes[] route) {
        Random rgen = new Random();  // Random number generator

        for (int i=1; i < (route.length-1); i++) {
            int randomPosition = rgen.nextInt((route.length - 2) + 1 - 1) + 1;
            Routes temp = route[i];
            route[i] = route[randomPosition];
            route[randomPosition] = temp;
        }

        return route;
    }


}
