package data.function;

import java.util.List;

public class LimitationFunction implements Function {

    private List<Double> coefficients;
    private String sign;
    private Double constance;

    public LimitationFunction(List<Double> coefficients, String sign, Double constance) {
        this.coefficients = coefficients;
        this.sign = sign;
        this.constance = constance;
    }

    @Override
    public List<Double> getCoefficients() {
        return coefficients;
    }

    @Override
    public void addCoefficients(Double coefficient) {
        coefficients.add(coefficient);
    }

    public String getSign() {
        return sign;
    }

    public Double getConstance() {
        return constance;
    }
}
