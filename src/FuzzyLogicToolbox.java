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
        else
            outputVars.add(new Variable(name));
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
}
