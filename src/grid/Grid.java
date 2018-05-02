package grid;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

public class Grid {

    private static final int NUMBER_OF_POINTS = 1000;

    private Double upperLimit = 40000.0;
    private Double lowerLimit = 0.0;

    private int dimension;

    private List<Point> points;



    public Grid(int dimension) {
        this.dimension = dimension;
        this.points = createPoints();
    }

    public void setUpperLimit(Double upperLimit) {
        this.upperLimit = upperLimit;
    }

    public void setLowerLimit(Double lowerLimit) {
        this.lowerLimit = lowerLimit;
    }

    /*
        Double generate(){
            r
        }*/

    public List<Point> createPoints(Point centre) {
        //System.out.println("RADIUS: "+radius);
        points.clear();
        double start, stop;
        for (int i = 0; i < NUMBER_OF_POINTS; i++) {
            List<Double> pointVector = new ArrayList<>();
            for (int j = 0; j < dimension; j++) {
                /*if ((start = centre.getPoint().get(j) - radius) < 0) {
                    start = 0;
                }*/
                start = centre.getPoint().get(j)*0.75;
                stop = centre.getPoint().get(j)*1.25;
                pointVector.add(ThreadLocalRandom.current().nextDouble(start, stop));
            }
            Point point = new Point(pointVector);
            points.add(point);
        }
        return points;
    }

    public List<Point> createPoints() {
        List<Point> points = new ArrayList<>();
        Random randomizer = new Random();
        for (int i = 0; i < NUMBER_OF_POINTS; i++) {
            points.add(new Point(randomizer.doubles(dimension, lowerLimit, upperLimit).boxed().collect(Collectors.toList())));
        }
        return points;
    }


    public List<Point> getPoints() {
        return points;
    }

}
