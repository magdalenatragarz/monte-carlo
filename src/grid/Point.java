package grid;

import java.util.List;

public class Point {

    private List<Double> point;

    public List<Double> getPoint() {
        return point;
    }

    public Point(List<Double> point) {
        this.point = point;
    }

    public String parseToBracketsForm(){
        StringBuilder builder = new StringBuilder();
        builder.append("{");
        for (Double coordinate : point ) {
            if ((point.indexOf(coordinate) != point.size()-1)){
                builder.append(coordinate + ",");
            }else{
                builder.append(coordinate + "}");
            }
        }
        return builder.toString();
    }

    @Override
    public String toString() {
        return "point=" + point +"\n";
    }
}
