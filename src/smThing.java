import java.util.ArrayList;

public class smThing {
    private ArrayList<ArrayList<SimpleMovie>> movies;
    private String actor1;
    private String actor2;

    private int i;
    private int j;

    public smThing(ArrayList<ArrayList<SimpleMovie>> m, String a1, String a2, int i, int j) {
        movies = m;
        actor1 = a1;
        actor2 = a2;
        this.i = i;
        this.j = j;
    }

    public ArrayList<ArrayList<SimpleMovie>> getMovies() {
        return movies;
    }
    public String getActor1() {
        return actor1;
    }

    public String getActor2() {
        return actor2;
    }

    public int getI() {
        return i;
    }

    public int getJ() {
        return j;
    }
}
