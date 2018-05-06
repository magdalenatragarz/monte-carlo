import solver.Solver;
import data.DataReader;
import grid.Point;

public class Application {

    public static void main(String[] args) {
        DataReader reader = new DataReader();
        reader.readData();
        Solver solver = new Solver(reader, 0.0, 5000.0);
        Point point = solver.solve();
        System.out.println(point.toString());
        System.out.println(solver.getGoalValue(point));
    }

}
