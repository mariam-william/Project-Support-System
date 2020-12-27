import java.util.ArrayList;

public class FuzzySet {
    private FuzzySetType type;
    private String name;
    private ArrayList<Point> points = new ArrayList<>();

    public FuzzySet(String name, FuzzySetType type, float[]xValues) {
        this.name = name;
        this.type = type;
        setPoints(xValues);
    }

    public FuzzySetType getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public ArrayList<Point> getPoints() {
        return points;
    }

    public void setPoints(float[] xValues) {
        float[] yValues;
        if(type.equals(FuzzySetType.Triangle))
            yValues = new float[]{0, 1, 0};
        else
            yValues = new float[]{0, 1, 1, 0};

        for (int i = 0; i < xValues.length; i++)
            points.add(new Point(xValues[i], yValues[i]));
    }
}
