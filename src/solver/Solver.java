package solver;

import data.DataReader;
import data.Goal;
import data.function.LimitationFunction;
import grid.Grid;
import grid.Point;
import threads.SolverThread;

import java.util.*;

public class Solver {

    private static final Double TOLERANCE = 0.0001;
    private static final Double ALPHA = 0.8;
    private static final Integer THREADS = 4;

    private int dimension;

    private Double[] upperLimitOfValues;
    private Double[] lowerLimitOfValues;

    private Point currentlyBest;

    private DataReader dataReader;


    /***********************************************/

    public Solver(DataReader dataReader, Double initialLowerLimit, Double initialUpperLimit) {


        this.dataReader = dataReader;
        this.dimension = dataReader.getDimension();

        this.lowerLimitOfValues = new Double[this.dimension];
        this.upperLimitOfValues = new Double[this.dimension];

        for (int i = 0; i < dimension; i++) {
            lowerLimitOfValues[i] = initialLowerLimit;
            upperLimitOfValues[i] = initialUpperLimit;
        }
        currentlyBest = new Point(lowerLimitOfValues);


    }


    public Point solve() {
        Point optimalPoint;

        optimalPoint = findOptimalPoint();
        currentlyBest = optimalPoint;
        Double currentlyBestValue = getGoalValue(optimalPoint);


        changeLimits(optimalPoint.getPoint());
        optimalPoint = findOptimalPoint();

        Double nextBest = getGoalValue(optimalPoint);

        while (Math.abs(currentlyBestValue - nextBest) > TOLERANCE) {
            currentlyBestValue = nextBest;
            changeLimits(optimalPoint.getPoint());
            optimalPoint = findOptimalPoint();
            nextBest = getGoalValue(optimalPoint);

        }
        return optimalPoint;
    }


    private Point findOptimalPoint() {
        Map<Double, Point> meetRequirements = new HashMap<>();
        Grid grid = new Grid(dimension, upperLimitOfValues, lowerLimitOfValues);
        List<Double> values;

        SolverThread[] threads = new SolverThread[THREADS];

        for (int i = 0; i < THREADS; i++) {
            Integer start = (grid.getPoints().size() / THREADS) * i;
            Integer stop = (grid.getPoints().size() / THREADS) * i + (grid.getPoints().size() / THREADS);

            threads[i] = new SolverThread(
                    this,
                    grid,
                    meetRequirements,
                    start,
                    stop);

            threads[i].run();
        }

        for (int i = 0; i < THREADS; i++) {
            try {
                threads[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }


        values = createProperlySortedValuesList(meetRequirements);

        if (dataReader.getGoal() == Goal.MAX) {
            if (getGoalValue(currentlyBest) > getGoalValue(meetRequirements.get(values.get(0)))) {
                return currentlyBest;
            }
        } else {
            if (getGoalValue(currentlyBest) < getGoalValue(meetRequirements.get(values.get(0)))) {
                return currentlyBest;
            }
        }
        return meetRequirements.get(values.get(0));
    }


    public boolean meetAllRequirements(Point point) {
        boolean flag = true;
        for (LimitationFunction limitationFunction : dataReader.getLimitationFunctions()) {
            if (!meetRequirement(limitationFunction, point)) flag = false;
        }
        return flag;

    }


    private boolean meetRequirement(LimitationFunction limitationFunction, Point point) {
        Double value = limitationFunction.getValue(point);
        switch (limitationFunction.getSign()) {
            case (">="):
                return (value >= limitationFunction.getConstance());
            case ("<="):
                return (value <= limitationFunction.getConstance());
            case ("=="):
                return (Objects.equals(value, limitationFunction.getConstance()));
            default:
                return false;
        }
    }


    private List<Double> createProperlySortedValuesList(Map<Double, Point> pointsWithValues) {
        List<Double> values = new ArrayList<>();

        for (Map.Entry<Double, Point> pointAndValueSet : pointsWithValues.entrySet()) {
            values.add(pointAndValueSet.getKey());
        }
        Collections.sort(values);
        if (dataReader.getGoal() == Goal.MAX) Collections.reverse(values);
        return values;
    }


    private void changeLimits(Double[] optimalPoint) {
        Double newRadius = (Math.abs(upperLimitOfValues[0] - lowerLimitOfValues[0]) * ALPHA) / 2;

        for (int i = 0; i < dimension; i++) {
            upperLimitOfValues[i] = optimalPoint[i] + newRadius;
            if (optimalPoint[i] - newRadius < 0) {
                lowerLimitOfValues[i] = 0.0;
            } else {
                lowerLimitOfValues[i] = optimalPoint[i] - newRadius;
            }
        }
    }


    public Double getGoalValue(Point point) {
        return dataReader.getGoalFunction().getValue(point);
    }


}
