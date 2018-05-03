package threads;

import grid.Grid;
import grid.Point;
import solver.Solver;

import java.util.HashMap;
import java.util.Map;

public class SolverThread extends Thread{

    private Solver solver;
    private Integer startIndex;
    private Integer stopIndex;
    private Grid grid;

    private Map<Double, Point> result;

    public SolverThread(Solver solver, Grid grid, Map<Double,Point> result, Integer startIndex, Integer stopIndex) {
        this.result = result;
        this.solver = solver;
        this.grid = grid;
        this.startIndex = startIndex;
        this.stopIndex = stopIndex;
        System.out.println("jestem wontkiem");
    }

    @Override
    public void run() {
        for (int i=startIndex;i<stopIndex;i++){
            if (solver.meetAllRequirements(grid.getPoints().get(i))){
                result.put(solver.getGoalValue(grid.getPoints().get(i)),grid.getPoints().get(i));
            }
        }
    }
}
