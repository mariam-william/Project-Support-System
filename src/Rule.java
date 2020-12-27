public class Rule {
    private String output;

    public Rule(String rule) {
        parse(rule);
    }

    public String getOutput() {
        return output;
    }

    public void setOutput(String output) {
        this.output = output;
    }

    public void parse(String rule){}
}
