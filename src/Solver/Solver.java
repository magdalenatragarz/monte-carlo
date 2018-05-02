package Solver;

import data.DataReader;
import data.Goal;
import data.function.Function;
import data.function.LimitationFunction;
import grid.Grid;
import grid.Point;

import java.util.*;

public class Solver {

    private static final Double TOLERANCE = 0.0001;

    private DataReader dataReader;
    private Grid grid;

    public Solver(DataReader dataReader) {
        //this.dataReader = new DataReader();
        this.dataReader = dataReader;
        this.grid = new Grid(dataReader.getDimension());
        //System.out.println(grid.getPoints().toString());
    }

    private Double getValue(Function function, Point point) {
        Double value = 0.0;
        for (int i = 0; i < dataReader.getDimension(); i++) {
            value += function.getCoefficients().get(i) * point.getPoint().get(i);
        }
        return value;
    }


    private boolean meetRequirement(LimitationFunction limitationFunction, Point point) {
        Double value = getValue(limitationFunction, point);
        switch (limitationFunction.getSign()) {
            case (">="):
                return (value >= limitationFunction.getConstance());
            case ("<="):
                return (value <= limitationFunction.getConstance());
            case ("=="):
                return (value == limitationFunction.getConstance());
            default:
                return false;
        }
    }

    private boolean meetAllRequirements(Point point) {
        boolean flag = true;
        for (LimitationFunction limitationFunction : dataReader.getLimitationFunctions()) {
            if (!meetRequirement(limitationFunction, point)) flag = false;
        }
        //if(flag==true) System.out.println(flag);
        return flag;

    }


    private Map<Double, Point> findThoseWhichMeetAllRequirements() {
        Map<Double, Point> meetRequirements = new HashMap<>();
        for (Point point : grid.getPoints()) {
            if (meetAllRequirements(point)) {
                meetRequirements.put(getValue(dataReader.getGoalFunction(), point), point);
            }
        }
        return meetRequirements;
    }

    private double createNewRadius(Point theBest, Point middle) {
        Double radius = 0.0;
        for (int i = 0; i < dataReader.getDimension(); i++) {
            radius += Math.pow(theBest.getPoint().get(i) + middle.getPoint().get(i), 2);
        }
        return Math.sqrt(radius);
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

    private Map.Entry<Double, Point> findOptimum() {
        Map<Double, Point> valuesAndPoints = findThoseWhichMeetAllRequirements();
        List<Double> values = createProperlySortedValuesList(valuesAndPoints);

        Point thBest = valuesAndPoints.get(values.get(0));
        Point middle = valuesAndPoints.get(values.get(values.size() / 5));

        //Double radius = createNewRadius(thBest, middle);

        //grid.setLowerLimit();
        grid.createPoints(thBest);

        return new AbstractMap.SimpleEntry<>(values.get(0), thBest);
    }


    public Map.Entry<Double, Point> solve() {
        Map.Entry<Double, Point> currentlyBest = findOptimum();
        Map.Entry<Double, Point> nextBest = findOptimum();

        while (Math.abs(currentlyBest.getKey() - nextBest.getKey()) > TOLERANCE) {
            currentlyBest = nextBest;
            nextBest = findOptimum();
        }
        return currentlyBest;
    }


}
