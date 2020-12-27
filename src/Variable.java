import java.util.ArrayList;

public class Variable {
    private String name;
    private float crispValue;
    private ArrayList<FuzzySet> fuzzySets = new ArrayList<>();
    private ArrayList<Float> membershipDegrees = new ArrayList<>();

    public Variable(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getCrispValue() {
        return crispValue;
    }

    public void setCrispValue(float crispValue) {
        this.crispValue = crispValue;
    }

    public ArrayList<FuzzySet> getFuzzySets() {
        return fuzzySets;
    }

    public void setFuzzySets(ArrayList<FuzzySet> fuzzySets) {
        this.fuzzySets = fuzzySets;
    }

    public ArrayList<Float> getMembershipDegrees() {
        return membershipDegrees;
    }

    public void setMembershipDegrees(ArrayList<Float> membershipDegrees) {
        this.membershipDegrees = membershipDegrees;
    }

    public void addFuzzySet(String setName, FuzzySetType type, float[]xValues){
        fuzzySets.add(new FuzzySet(setName, type, xValues));
    }
}
