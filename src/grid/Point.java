package grid;

public class Point {

    private Double[] point;

    public Double[] getPoint() {
        return point;
    }

    public Point(Double[] point) {
        this.point = point;
    }

    @Override
    public String toString() {
        StringBuilder pointBuilder = new StringBuilder();
        for (int i=0;i<point.length;i++){
            pointBuilder.append("x"+(i+1)+" = "+point[i]+", ");
        }
        return pointBuilder.toString();
    }
}
