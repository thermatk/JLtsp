/*************************************************************************
 *  YOU DO NOT NEED TO MODIFY THIS FILE
 *
 *  Compilation:  javac TSPTimer.java
 *  Execution:    java TSPTimer N
 *  Dependencies: Tour.java Point.java
 *
 *  Time the two heuristics by generated random instances of size N.
 *
 *  % java TSPTimer N
 *
 *************************************************************************/

public class TSPTimer {

    public static void main(String[] args) {
        int SIZE = 600;
        int N = Integer.parseInt(args[0]);

        // generate data and run nearest insertion heuristic
        Stopwatch timer1 = new Stopwatch();
        Tour tour1 = new Tour();
        for (int i = 0; i < N; i++) {
            double x = Math.random() * SIZE;
            double y = Math.random() * SIZE;
            Point p = new Point(x, y);
            tour1.insertNearest(p);
        }
        double elapsed1 = timer1.elapsedTime();
        System.out.println("Tour distance = " + tour1.distance());
        System.out.println("Nearest insertion:  " + elapsed1 + " seconds");
        System.out.println();


        // generate data and run smallest insertion heuristic
        Stopwatch timer2 = new Stopwatch();
        Tour tour2 = new Tour();
        for (int i = 0; i < N; i++) {
            double x = Math.random() * SIZE;
            double y = Math.random() * SIZE;
            Point p = new Point(x, y);
            tour2.insertSmallest(p);
        }
        double elapsed2 = timer2.elapsedTime();
        System.out.println("Tour distance = " + tour2.distance());
        System.out.println("Smallest insertion:  " + elapsed2 + " seconds");



    }

}
