package data.function;

import java.util.List;

public class GoalFunction extends Function {

    private List<Double> coefficients;

    public GoalFunction(List<Double> coefficients) {
        super(coefficients);
    }

}
