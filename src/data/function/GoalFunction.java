package data.function;

import java.util.List;

public class GoalFunction implements Function {

    private List<Double> coefficients;

    public GoalFunction(List<Double> coefficients) {
        this.coefficients = coefficients;
    }

    @Override
    public List<Double> getCoefficients() {
        return coefficients;
    }

    @Override
    public void addCoefficients(Double coefficient) {
        coefficients.add(coefficient);
    }
}
