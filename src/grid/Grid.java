package grid;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Grid {

    private static final int NUMBER_OF_POINTS = 50000;

    private Double[] upperLimit;
    private Double[] lowerLimit;

    private int dimension;

    private List<Point> points;


    public Grid(int dimension, Double[] initialUpperLimit, Double[] initialLowerLimit) {
        this.dimension = dimension;
        this.lowerLimit = initialLowerLimit;
        this.upperLimit = initialUpperLimit;
        this.points = createPoints();
    }


    private Double[] generatePoint() {
        Double[] point = new Double[dimension];
        Random randomizer = new Random();
        for (int i = 0; i < dimension; i++) {
            point[i] = (upperLimit[i] - lowerLimit[i]) * randomizer.nextDouble();
        }
        return point;
    }

    private List<Point> createPoints() {
        List<Point> points = new ArrayList<>();

        for (int i = 0; i < NUMBER_OF_POINTS; i++) {
            points.add(new Point(generatePoint()));
        }
        this.points = points;
        return points;
    }

    public List<Point> getPoints() {
        return points;
    }

}
