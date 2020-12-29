import java.util.ArrayList;

public class FuzzySet {
    private FuzzySetType type;
    private String name;
    private float centroid;
    private ArrayList<Point> points = new ArrayList<>();

    public FuzzySet(String name, FuzzySetType type, float[]xValues) {
        this.name = name;
        this.type = type;
        setPoints(xValues);
    }

    public String getName() {
        return name;
    }

    public float getCentroid() {
        return centroid;
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

    public float calculateSignedArea(){
        float sum = 0;
        for (int i = 0; i < points.size()-1; i++)
            sum += ((points.get(i).getX() * points.get(i+1).getY()) - (points.get(i+1).getX() * points.get(i).getY()));
        return (sum/2);
    }

    public void setCentroid(){
        float sum = 0;
        for (int i = 0; i < points.size()-1; i++){
            float term1 = points.get(i).getX() + points.get(i+1).getX();
            float term2 = ((points.get(i).getX() * points.get(i+1).getY()) - (points.get(i+1).getX() * points.get(i).getY()));
            sum += (term1 * term2);
        }
        centroid = (1/(6 * calculateSignedArea()) * sum);
    }
}
