package data.function;

import grid.Point;

import java.util.List;

public class Function {

    private List<Double> coefficients;

    public Function(List<Double> coefficients) {
        this.coefficients = coefficients;
    }

    public List<Double> getCoefficients() {
        return coefficients;
        }

    public void addCoefficients(Double coefficient) {
        coefficients.add(coefficient);
        }

    public Double getValue(Point point) {
        Double value = 0.0;
        for (int i = 0; i < point.getPoint().length; i++) {
            value += getCoefficients().get(i) * point.getPoint()[i];
        }
        return value;
    }

}