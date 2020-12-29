public class Main {

    public static void main(String[] args) {
        FuzzyLogic FL = new FuzzyLogic();
        FL.addVariable("project_funding", "input");
        FL.getVariable("project_funding").addFuzzySet("veryLow", FuzzySetType.Trapezium, new float[]{0f,0f,10f,30f});
        FL.getVariable("project_funding").addFuzzySet("low", FuzzySetType.Trapezium, new float[]{10f,30f,40f,60f});
        FL.getVariable("project_funding").addFuzzySet("medium", FuzzySetType.Trapezium, new float[]{40f,60f,70f,90f});
        FL.getVariable("project_funding").addFuzzySet("high", FuzzySetType.Trapezium, new float[]{70f,90f,100f,100f});

        FL.addVariable("team_experience_level", "input");
        FL.getVariable("team_experience_level").addFuzzySet("beginner", FuzzySetType.Triangle, new float[]{0f,15f,30f});
        FL.getVariable("team_experience_level").addFuzzySet("intermediate", FuzzySetType.Triangle, new float[]{15f,30f,45f});
        FL.getVariable("team_experience_level").addFuzzySet("expert", FuzzySetType.Triangle, new float[]{30f,60f,60f});

        FL.addVariable("risk", "output");
        FL.getVariable("risk").addFuzzySet("high", FuzzySetType.Triangle, new float[]{0f,25f,50f});
        FL.getVariable("risk").addFuzzySet("normal", FuzzySetType.Triangle, new float[]{25f,50f,75f});
        FL.getVariable("risk").addFuzzySet("low", FuzzySetType.Triangle, new float[]{50f,100f,100f});

        FL.addRule("project_funding=high or team_experience_level=expert then risk=low");
        FL.addRule("project_funding=medium and team_experience_level=intermediate or team_experience_level=beginner then risk=normal");
        FL.addRule("project_funding=veryLow then risk=high");
        FL.addRule("project_funding=low and team_experience_level=beginner then risk=high");

        FL.getVariable("project_funding").setCrispValue(50);
        FL.getVariable("team_experience_level").setCrispValue(40);

        /*
        FL.addVariable("Dirt", "input");
        FL.getVariable("Dirt").addFuzzySet("small", FuzzySetType.Trapezium, new float[]{0f, 0f, 20f, 40f});
        FL.getVariable("Dirt").addFuzzySet("medium", FuzzySetType.Trapezium, new float[]{20f, 40f, 60f, 80f});
        FL.getVariable("Dirt").addFuzzySet("large", FuzzySetType.Trapezium, new float[]{60f, 80f, 100f, 100f});

        FL.addVariable("Fabric", "input");
        FL.getVariable("Fabric").addFuzzySet("soft", FuzzySetType.Trapezium, new float[]{0f, 0f, 20f, 40f});
        FL.getVariable("Fabric").addFuzzySet("ordinary", FuzzySetType.Trapezium, new float[]{20f, 40f, 60f, 80f});
        FL.getVariable("Fabric").addFuzzySet("stiff", FuzzySetType.Trapezium, new float[]{60f, 80f, 100f, 100f});

        FL.addVariable("WashTime", "output");
        FL.getVariable("WashTime").addFuzzySet("verySmall", FuzzySetType.Triangle, new float[]{0f, 0f, 15f});
        FL.getVariable("WashTime").addFuzzySet("small", FuzzySetType.Triangle, new float[]{0f, 15f, 30f});
        FL.getVariable("WashTime").addFuzzySet("standard", FuzzySetType.Triangle, new float[]{15f, 30f, 45f});
        FL.getVariable("WashTime").addFuzzySet("large", FuzzySetType.Triangle, new float[]{30f, 45f, 60f});
        FL.getVariable("WashTime").addFuzzySet("veryLarge", FuzzySetType.Triangle, new float[]{45f, 60f, 60f});

        FL.addRule("Dirt=small and fabric=soft then WashTime=verySmall");
        FL.addRule("Dirt=medium and fabric=ordinary then WashTime=standard");
        FL.addRule("Dirt=small and fabric=!soft or Dirt=medium and fabric=soft then WashTime=small");
        FL.addRule("Dirt=medium and fabric=stiff then WashTime=large");
        FL.addRule("Dirt=large and fabric=!soft then WashTime=verylarge");
        FL.addRule("Dirt=large and fabric=soft then WashTime=standard");

        FL.getVariable("Dirt").setCrispValue(60);
        FL.getVariable("fabric").setCrispValue(25);
        */

        FL.runFuzzyLogic();
    }
}
