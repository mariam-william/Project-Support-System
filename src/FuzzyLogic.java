import java.util.ArrayList;
import java.util.Collections;

public class FuzzyLogic {
    private ArrayList<Variable> inputVars = new ArrayList<>();
    private ArrayList<Variable> outputVars = new ArrayList<>();
    private ArrayList<String> rules = new ArrayList<>();
    private ArrayList<String> parsedRules = new ArrayList<>();


    public void addVariable(String name, String type){
        if(type.equalsIgnoreCase("input"))
            inputVars.add(new Variable(name));
        else {
            outputVars.add(new Variable(name));
        }
    }

    public Variable getVariable(String name){
        for (Variable inputVar : inputVars) {
            if (inputVar.getName().equalsIgnoreCase(name))
                return inputVar;
        }
        for (Variable outputVar : outputVars) {
            if (outputVar.getName().equalsIgnoreCase(name))
                return outputVar;
        }
        return null;
    }

    public void addRule(String rule){
        rules.add(rule);
    }

    public int getRange(float crisp, ArrayList<Point> points){
        for (int i = 0; i < points.size()-1; i++) {
            if((crisp >= points.get(i).getX()) && (crisp <= points.get(i+1).getX()))
                return i;
        }
        return -1;
    }

    public float getSlope(Point p1, Point p2){
        return ((p2.getY() - p1.getY()) / (p2.getX() - p1.getX()));
    }

    public void fuzzification(){
        for (Variable inputVar : inputVars) {
            for (FuzzySet fuzzySet : inputVar.getFuzzySets()) {
                int firstIdx = getRange(inputVar.getCrispValue(), fuzzySet.getPoints());
                if(firstIdx == -1)
                    inputVar.getMembershipDegrees().add(0f);
                else{
                    Point p1 = fuzzySet.getPoints().get(firstIdx);
                    Point p2 = fuzzySet.getPoints().get(firstIdx+1);
                    if (p1.getX() == p2.getX())
                        inputVar.getMembershipDegrees().add(1f);
                    else {
                        float m = getSlope(p1, p2);
                        float c = p1.getY() - (m * p1.getX());
                        inputVar.getMembershipDegrees().add((m * inputVar.getCrispValue()) + c);
                    }
                }
            }
        }
    }

    public float getRuleValue(String str){
        String[] temp = str.split("=");
        boolean not = false;

        if(temp[1].contains("!")) {
            temp[1] = temp[1].substring(1);
            not = true;
        }
        int idx = getVariable(temp[0]).getFSByName(temp[1]);
        float value = getVariable(temp[0]).getMembershipDegrees().get(idx);
        if(not)
            value = 1 - value;

        return value;
    }

    public void parse(String rule){
        String[] splittedRule = rule.split(" then ");
        String[] ifPart = splittedRule[0].split(" ");
        float ruleOutput = getRuleValue(ifPart[0]);
        ArrayList<Float> values = new ArrayList<>();

        for (int i = 1; i < ifPart.length; i++) {
            values.add(ruleOutput);
            values.add(getRuleValue(ifPart[++i]));
            if(ifPart[i-1].equalsIgnoreCase("or"))
                ruleOutput = Collections.max(values);
            else
                ruleOutput = Collections.min(values);
            values.clear();
        }

        String[] thenPart = splittedRule[1].split("=");
        int idx = getVariable(thenPart[0]).getFSByName(thenPart[1]);
        float value = getVariable(thenPart[0]).getMembershipDegrees().get(idx);
        parsedRules.add("μ(" + thenPart[1] + ") = " + ruleOutput);
        getVariable(thenPart[0]).getMembershipDegrees().set(idx, Math.max(ruleOutput, value));
    }

    public void inference(){
        for (Variable outputVar : outputVars)
            for (int j = 0; j < outputVar.getFuzzySets().size(); j++)
                outputVar.getMembershipDegrees().add(0f);
        for (String rule : rules) {
            parse(rule);
        }
    }

    public void defuzzification(){
        for (Variable outputVar : outputVars) {
            float num = 0, deno = 0;
            for (int i = 0; i < outputVar.getFuzzySets().size(); i++) {
                outputVar.getFuzzySets().get(i).setCentroid();
                num += (outputVar.getFuzzySets().get(i).getCentroid() * outputVar.getMembershipDegrees().get(i));
                deno += outputVar.getMembershipDegrees().get(i);
            }
            outputVar.setCrispValue(num/deno);
        }
    }

    public void printOutput(){
        System.out.println("Fuzzification: ");
        for (Variable inputVar : inputVars) {
            System.out.println("\t" + inputVar.getName() + ":");
            for (int i = 0; i < inputVar.getFuzzySets().size(); i++) {
                System.out.println("\t\t- μ(" + inputVar.getFuzzySets().get(i).getName()
                        + ")[" + inputVar.getCrispValue() + "] = " + inputVar.getMembershipDegrees().get(i));
            }
        }

        System.out.println("\nInference: ");
        for (int i = 0; i < parsedRules.size(); i++)
            System.out.println("\tRule #" + (i+1) + " -> " + parsedRules.get(i));

        System.out.println("\nDefuzzification: ");
        for (Variable outputVar : outputVars) {
            System.out.println("\t" + outputVar.getName() + ":");
            for (int i = 0; i < outputVar.getFuzzySets().size(); i++)
                System.out.println("\t\t- μ(" + outputVar.getFuzzySets().get(i).getName()
                        + ") = " + outputVar.getMembershipDegrees().get(i));
            System.out.println("\tPredicted Value = " + outputVar.getCrispValue());
            float max = Collections.max(outputVar.getMembershipDegrees());
            System.out.println("\t" + outputVar.getName() + " will be "
                    + outputVar.getFuzzySets().get(outputVar.getMembershipDegrees().indexOf(max)).getName());
            System.out.println();
        }
    }

    public void runFuzzyLogic(){
        fuzzification();
        inference();
        defuzzification();
        printOutput();
    }

}
