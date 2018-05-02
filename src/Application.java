import Solver.Solver;
import data.DataReader;
import grid.Point;

import java.net.Socket;
import java.util.Map;

public class Application {

    public static void main(String[] args) {
        DataReader reader = new DataReader();
        reader.readData();
        Solver solver = new Solver(reader);
        System.out.println("to tu w solwerze");
        Map.Entry<Double, Point> best = solver.solve();

        System.out.println(best.getKey().toString());
        System.out.println(best.getValue().toString());
    }

}
