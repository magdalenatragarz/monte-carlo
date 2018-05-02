package data;

import data.function.GoalFunction;
import data.function.LimitationFunction;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class DataReader {

    private List<LimitationFunction> limitationFunctions;
    private GoalFunction goalFunction;
    private Goal goal;
    int dimension;

    public DataReader() {

    }

    public void readData(){
        sayHello();

        limitationFunctions = readLimitationFunctions();
        goalFunction = readGoalFunction();
        normalizeFunctions();
        goal = readGoal();
        dimension = readDimension();
    }

    public int getDimension() {
        return dimension;
    }

    private void sayHello(){
        String communicate = "========Monte Carlo========\n" +
                "Linear Programming \n \n \n";
        System.out.println(communicate);
    }


    private int readDimension(){
        return goalFunction.getCoefficients().size();
    }

    private List<LimitationFunction> readLimitationFunctions(){
        Scanner scannerFunction = new Scanner(System.in);
        Scanner scannerIngridients = new Scanner(System.in);

        List<LimitationFunction> limitationFunctions = new ArrayList<>();
        System.out.println("ENTER to start.");
        String functionLine = scannerFunction.nextLine();
        while (!functionLine.equals("done")) {
            System.out.println("Enter set of limitation functions coefficients, separate them using comma, type 'done' to finish");
            String coefficients = scannerFunction.nextLine();
            functionLine = coefficients;
            if (functionLine.equals("done")) continue;
            System.out.println("Enter sign.");
            String sign = scannerFunction.nextLine();
            System.out.println("Enter constance.");
            Double constance = scannerFunction.nextDouble();
            limitationFunctions.add(new LimitationFunction(coefficientsParser(coefficients),sign.replaceAll(" ",""),constance));
            functionLine = scannerFunction.nextLine();
        }
        return limitationFunctions;
    }

    private int findMaximumSizedFunction(){
        int max = 0;
        for (LimitationFunction limitationFunction : limitationFunctions) {
            if(limitationFunction.getCoefficients().size()>max) max = limitationFunction.getCoefficients().size();
        }
        if (goalFunction.getCoefficients().size() > max) max = goalFunction.getCoefficients().size();
        return max;
    }

    private void normalizeFunctions(){
        int max = findMaximumSizedFunction();
        for (LimitationFunction limitationFunction : limitationFunctions) {
            while (limitationFunction.getCoefficients().size()!=max){
                limitationFunction.addCoefficients(0.0);
            }
        }
        while (goalFunction.getCoefficients().size()!=max){
            goalFunction.addCoefficients(0.0);
        }
    }

    private List<Double> coefficientsParser(String coefficientsString){
        String [] coefficientsArray = coefficientsString.split(",");
        List<Double> coefficients = new ArrayList<>();
        for (String aCoefficientsArray : coefficientsArray) {
            coefficients.add(Double.parseDouble(aCoefficientsArray));
        }
        return coefficients;
    }

    private GoalFunction readGoalFunction(){
        System.out.println("Enter Coefficients of Goal Function");
        Scanner scanner = new Scanner(System.in);
        String goalString = scanner.nextLine();
        return new GoalFunction(coefficientsParser(goalString));
    }

    private Goal readGoal() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Tell me if goal function should be minimized or maximized, type min if it should be minimized and max if it should be maximized");
        String line = scanner.nextLine();
        if (line.equals("min")) {
            return Goal.MIN;
        }
        return Goal.MAX;
    }

    @Override
    public String toString() {

        StringBuilder dataBuilder = new StringBuilder();
        dataBuilder.append("LIMITATION FUNCTIONS:\n");
        for (LimitationFunction limitationFunction : limitationFunctions) {
            dataBuilder.append(limitationFunction.getCoefficients().toString())
                    .append("\n")
                    .append(limitationFunction.getSign())
                    .append("\n")
                    .append(limitationFunction.getConstance())
                    .append("\n\n\n");
        }

        dataBuilder.append("GOAL FUNCTION: \n");
        dataBuilder.append(goalFunction.getCoefficients().toString()).append("\n");
        dataBuilder.append(goal).append("\n");

        return dataBuilder.toString();
    }

    public List<LimitationFunction> getLimitationFunctions() {
        return limitationFunctions;
    }

    public GoalFunction getGoalFunction() {
        return goalFunction;
    }

    public Goal getGoal() {
        return goal;
    }


}
