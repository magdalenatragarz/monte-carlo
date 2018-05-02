package data.function;

import java.util.List;

public class LimitationFunction extends Function {


    private String sign;
    private Double constance;

    public LimitationFunction(List<Double> coefficients, String sign, Double constance) {
        super(coefficients);
        this.sign = sign;
        this.constance = constance;
    }


    public String getSign() {
        return sign;
    }

    public Double getConstance() {
        return constance;
    }
}
