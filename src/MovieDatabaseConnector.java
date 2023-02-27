import java.util.ArrayList;
import java.util.Arrays;

public class MovieDatabaseConnector {
        private String actor1 = "";
        private String actor2 = "";

        private ArrayList<SimpleMovie> allMovies;
        private ArrayList<SimpleMovie> allMoviesNotChanged;



        private String path;
        private String addToPath;
        private int count = -1;
        private long startTime;
        private long done;
        public MovieDatabaseConnector(String actor1, String actor2, ArrayList<SimpleMovie> movies) {
            this.actor1 = actor1;
            this.actor2 = actor2;
            path = "";
            addToPath = "";
            allMoviesNotChanged = movies;
            reset();

        }
        public int run() {
            startTime = System.currentTimeMillis();
            int x = find();
            done = (System.currentTimeMillis()-startTime);
            reset();
            return x;
        }

        public String getPath() {
            return path;
        }
        public long getTime() {return done;}


        public int find() {


            ArrayList<ArrayList<SimpleMovie>> nextMovies =  getActorMovies(actor1,actor2);
            if (nextMovies.size()==0) { //actor 1 and actor 2 were in the same movie
                path = addToPath;
                return 1;
            }

            if (connection(nextMovies.get(0), nextMovies.get(1))) { //actor 1 and 2 were both in a movie with a certain actor
                path = actor2+addToPath+(" -> "+actor1);
                return 2;
            }






            ArrayList<String> usedActors1 = new ArrayList<>();
            ArrayList<String> usedActors2 = new ArrayList<>();

            ArrayList<smThing> saveThis = new ArrayList<>();

            ArrayList<ArrayList<SimpleMovie>> ogNextMovies = new ArrayList<>();

            ogNextMovies.addAll(nextMovies);

//           ArrayList<ArrayList<SimpleMovie>> ogNextMovies = (ArrayList) nextMovies.clone();;

            for (int i = 0; i<ogNextMovies.get(0).size(); i++) {
                for (int j = 0; j<ogNextMovies.get(1).size(); j++) {

                    ArrayList<String> a1 = ogNextMovies.get(0).get(i).getActors();
                    ArrayList<String> a2 = ogNextMovies.get(1).get(j).getActors();


                    for (int a = 0; a < a1.size(); a++) {
                        String act1 = a1.get(a);
                        if (!(usedActors1.contains(act1))) {
                            usedActors1.add(act1);
                            for (int b = 0; b < a2.size(); b++) {
                                String act2 = a2.get(b);
                                if (!(usedActors2.contains(act2))) {
                                    usedActors2.add(act2);

                                    nextMovies = getActorMoviesFast(act1, act2);
                                    saveThis.add(new smThing(nextMovies, act1, act2, i, j));

                                    if (nextMovies.size()==0) {
                                        path = actor2 + (" -> " + ogNextMovies.get(1).get(j).getTitle()) + addToPath +  (" -> " + ogNextMovies.get(0).get(i).getTitle())+ (" -> "+actor1);
                                        return 3;
                                    }

                                    if (connection(nextMovies.get(0), nextMovies.get(1))) {
                                          System.out.println("Found link of 4. Searching for shorter link of 3...");
                                          path = (actor2+" -> "+ogNextMovies.get(1).get(j).getTitle()+" -> "+a2.get(b)+addToPath+" -> "+a1.get(a)+" -> "+ogNextMovies.get(0).get(i).getTitle()+" -> "+actor1);
                                          System.out.println(path+"\n");
                                          count = 4;
                                    }
                                }
                            }
                        }
                    }
                }
            }


            if (count==4) {
                return count;
            }






            ArrayList<String> usedActors1p2 = new ArrayList<>();
            ArrayList<String> usedActors2p2 = new ArrayList<>();

            ArrayList<ArrayList<SimpleMovie>> nextMovies2 = new ArrayList<>();

            nextMovies.addAll(ogNextMovies);



            for (int r = 0; r<saveThis.size(); r++) {
                nextMovies = saveThis.get(r).getMovies();
                String act1 = saveThis.get(r).getActor1();
                String act2 = saveThis.get(r).getActor2();
                int i = saveThis.get(r).getI();
                int j = saveThis.get(r).getJ();

                for (int x = 0; x<nextMovies.get(0).size(); x++) {
                    for (int y = 0; y<nextMovies.get(1).size(); y++) {

                        ArrayList<String> a1p2 = nextMovies.get(0).get(x).getActors();
                        ArrayList<String> a2p2 = nextMovies.get(1).get(y).getActors();

                        for (String act1p2 : a1p2) { //----go through unique actors in movies--------
                            if (!(usedActors1p2.contains(act1p2))) {
                                usedActors1p2.add(act1p2);
                                for (String act2p2 : a2p2) {
                                    if (!(usedActors2p2.contains(act2p2))) {
                                        usedActors2p2.add(act2p2);

                                        nextMovies2 = getActorMoviesFast(act1p2, act2p2); //list of movies----------------------


//                                                            if (nextMovies2.size() == 0) {
//                                                                path = actor2 + (" -> " + ogNextMovies.get(1).get(j).getTitle()) + addToPath +  (" -> " + ogNextMovies.get(0).get(i).getTitle())+ (" -> "+actor1);
//                                                                System.out.println();
//                                                                return 5;
//                                                            }
                                        if (connection(nextMovies2.get(0), nextMovies2.get(1))) {
                                            if (act1.equals(act1p2)) {
                                                path = (actor2+" -> "+ogNextMovies.get(1).get(j).getTitle()+" -> "+act2+" -> "+nextMovies.get(1).get(y).getTitle()+" -> "+act2p2+addToPath+" -> "+act1+" -> "+ogNextMovies.get(0).get(i).getTitle()+" -> "+actor1);
                                                return 5;
                                            }
                                            if (act2.equals(act2p2)) {
                                                path = (actor2+" -> "+ogNextMovies.get(1).get(j).getTitle()+" -> " +act2p2+addToPath+" -> "+act1+" -> "+ogNextMovies.get(0).get(i).getTitle()+" -> "+actor1);
                                                return 5;
                                            }
                                            System.out.println("Found link of 6. Searching for shorter link of 5...");
                                            path = (actor2+" -> "+ogNextMovies.get(1).get(j).getTitle()+" -> "+act2+" -> "+nextMovies.get(1).get(y).getTitle()+" -> "+act2p2+addToPath+" -> "+act1p2+" -> "+nextMovies.get(0).get(x).getTitle()+" -> "+act1+" -> "+ogNextMovies.get(0).get(i).getTitle()+" -> "+actor1);
                                            System.out.println(path+"\n");


                                            count = 6;
                                            return 6;
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
//                            }
//                        }
//                    }
//                }
//            }

            return count;

        }

        private void reset() {
            allMovies = new ArrayList<>();
            for (SimpleMovie sm: allMoviesNotChanged) {
                allMovies.add(sm);
            }
        }

            private boolean connection(ArrayList<SimpleMovie> m1, ArrayList<SimpleMovie> m2) {
                for (SimpleMovie movie: m1) {
                    for (SimpleMovie movie2: m2) {
                        for (String actor: movie2.getActors()) {
                            if (!(actor.equals(""))) {
                                if (movie.getActors().contains(actor)) {
                                    addToPath = " -> "+movie2.getTitle()+" -> "+actor + " -> "+movie.getTitle();
                                    return true;
                                }
                            }
                        }
                    }
                }
                return false;
            }

            private ArrayList<ArrayList<SimpleMovie>> getActorMoviesFast(String a1, String a2) {

                ArrayList<SimpleMovie> A1movies = new ArrayList<>();
                ArrayList<SimpleMovie> A2movies = new ArrayList<>();
                for (int i = 0; i<allMovies.size(); i++) {
                    SimpleMovie movie = allMovies.get(i);
                    ArrayList<String> actors = movie.getActors();

                    if (actors.contains(a1)) {
                        A1movies.add(movie);
                        if (actors.contains(a2)) {
                            addToPath=(" -> "+a2+" -> "+movie.getTitle()+" -> "+a1);
                            return new ArrayList<>();
                        }
                    }
                    if (actors.contains(a2)) {
                        A2movies.add(movie);
                    }

                }
                ArrayList<ArrayList<SimpleMovie>> combine = new ArrayList<>();
                combine.add(A1movies);
                combine.add(A2movies);
                return combine;
            }
            private ArrayList<ArrayList<SimpleMovie>> getActorMovies(String a1, String a2) {
                ArrayList<SimpleMovie> A1movies = new ArrayList<>();
                ArrayList<SimpleMovie> A2movies = new ArrayList<>();
                for (int i = 0; i<allMovies.size(); i++) {
                    SimpleMovie movie = allMovies.get(i);
                    ArrayList<String> actors = movie.getActors();
                    if (actors.contains(a1)) {
                        A1movies.add(movie);
                        allMovies.remove(movie);
                        if (actors.contains(a2)) {
                            addToPath=(a2+" -> "+movie.getTitle()+" -> "+a1);
                            return new ArrayList<>();
                        }
                    }
                    if (actors.contains(a2)) {
                        A2movies.add(movie);
                        allMovies.remove(movie);
                    }

                }
                ArrayList<ArrayList<SimpleMovie>> combine = new ArrayList<>();
                combine.add(A1movies);
                combine.add(A2movies);
                return combine;
        }


}
