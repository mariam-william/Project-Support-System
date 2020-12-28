import java.util.ArrayList;

public class FuzzyLogicToolbox {
    private ArrayList<Variable> inputVars = new ArrayList<>();
    private ArrayList<Variable> outputVars = new ArrayList<>();
    private ArrayList<Rule> rules = new ArrayList<>();

    public ArrayList<Variable> getInputVars() {
        return inputVars;
    }

    public void setInputVars(ArrayList<Variable> inputVars) {
        this.inputVars = inputVars;
    }

    public ArrayList<Variable> getOutputVars() {
        return outputVars;
    }

    public void setOutputVars(ArrayList<Variable> outputVars) {
        this.outputVars = outputVars;
    }

    public ArrayList<Rule> getRules() {
        return rules;
    }

    public void setRules(ArrayList<Rule> rules) {
        this.rules = rules;
    }

    public void addVariable(String name, String type){
        if(type.equalsIgnoreCase("input"))
            inputVars.add(new Variable(name));
        else {
            outputVars.add(new Variable(name));
        }
    }

    public Variable getVariable(String name){
        for (int i = 0; i < inputVars.size(); i++) {
            if (inputVars.get(i).getName().equalsIgnoreCase(name))
                return inputVars.get(i);
        }
        for (int i = 0; i < outputVars.size(); i++) {
            if (outputVars.get(i).getName().equalsIgnoreCase(name))
                return outputVars.get(i);
        }
        return null;
    }

    public void addRule(String rule){
        rules.add(new Rule(rule));
    }

    public int getRange(float crisp, ArrayList<Point> points){
        for (int i = 0; i < points.size()-1; i++) {
            if(crisp <= points.get(i+1).getX())
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
}
