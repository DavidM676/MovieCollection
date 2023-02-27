import java.util.ArrayList;
import java.util.Scanner;
public class Main {
    public static void printLine() {
        System.out.println("----------------------------------------------");
    }
    public static void main(String[] args) {

        ArrayList<SimpleMovie> movies = MovieDatabaseBuilder.getMovieDB("src/movie_data");



        String actor = "";
        while (!(actor.equals("q"))) {
            Scanner scanner = new Scanner(System.in);
            System.out.print("Enter an actor's name or (q) to quit: ");
            actor = scanner.nextLine();
            if (actor.equals("q")) {
                continue;
            }

            MovieDatabaseConnector mdc = new MovieDatabaseConnector("Kevin Bacon", actor, movies);
            int bn = mdc.run();
            if (bn == -1) {
                System.out.println("No Results Found");
                continue;
            }

            printLine();
            System.out.println(mdc.getPath());
            System.out.println("Bacon Number of: "+bn);
            System.out.println("Completed in: "+mdc.getTime()+"ms");
            printLine();


        }



    }



}