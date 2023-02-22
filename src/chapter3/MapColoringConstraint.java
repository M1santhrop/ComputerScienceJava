package chapter3;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class MapColoringConstraint extends Constraint<String, String> {
    private static final String WESTERN_AUSTRALIA = "Western Australia";
    private static final String NORTHERN_TERRITORY = "Northern Territory";
    private static final String SOUTH_AUSTRALIA = "South Australia";
    private static final String QUEENSLAND = "Queensland";
    private static final String NEW_SOUTH_WALES = "New South Wales";
    private static final String VICTORIA = "Victoria";
    private static final String TASMANIA = "Tasmania";
    private String place1;
    private String place2;

    public MapColoringConstraint(String place1, String place2) {
        super(List.of(place1, place2));
        this.place1 = place1;
        this.place2 = place2;
    }

    @Override
    public boolean satisfied(Map<String, String> assignment) {
        if (!assignment.containsKey(place1) || !assignment.containsKey(place2)) {
            return true;
        }
        return !assignment.get(place1).equals(assignment.get(place2));
    }

    public static void main(String[] args) {
        List<String> variables = List.of(WESTERN_AUSTRALIA, NORTHERN_TERRITORY, SOUTH_AUSTRALIA, QUEENSLAND,
                NEW_SOUTH_WALES, VICTORIA, TASMANIA);
        Map<String, List<String>> domains = new HashMap<>();
        for (String variable : variables) {
            domains.put(variable, List.of("red", "green", "blue"));
        }

        CSP<String, String> csp = new CSP<>(variables, domains);
        csp.addConstraint(new MapColoringConstraint(WESTERN_AUSTRALIA, NORTHERN_TERRITORY));
        csp.addConstraint(new MapColoringConstraint(WESTERN_AUSTRALIA, SOUTH_AUSTRALIA));
        csp.addConstraint(new MapColoringConstraint(SOUTH_AUSTRALIA, NORTHERN_TERRITORY));
        csp.addConstraint(new MapColoringConstraint(QUEENSLAND, NORTHERN_TERRITORY));
        csp.addConstraint(new MapColoringConstraint(QUEENSLAND, SOUTH_AUSTRALIA));
        csp.addConstraint(new MapColoringConstraint(QUEENSLAND, NEW_SOUTH_WALES));
        csp.addConstraint(new MapColoringConstraint(NEW_SOUTH_WALES, SOUTH_AUSTRALIA));
        csp.addConstraint(new MapColoringConstraint(VICTORIA, SOUTH_AUSTRALIA));
        csp.addConstraint(new MapColoringConstraint(VICTORIA, NEW_SOUTH_WALES));
        csp.addConstraint(new MapColoringConstraint(VICTORIA, TASMANIA));

        Map<String, String> solution = csp.backtrackingSearch();
        if (solution == null) {
            System.out.println("No solution found!");
        } else {
            System.out.println(solution);
        }
    }
}
