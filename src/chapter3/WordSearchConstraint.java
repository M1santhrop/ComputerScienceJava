package chapter3;

import java.util.*;
import java.util.stream.Collectors;

public class WordSearchConstraint extends Constraint<String, List<WordGrid.GridLocation>> {

    public WordSearchConstraint(List<String> words) {
        super(words);
    }

    @Override
    public boolean satisfied(Map<String, List<WordGrid.GridLocation>> assignment) {
        List<WordGrid.GridLocation> allLocations = assignment.values()
                .stream()
                .flatMap(Collection::stream)
                .collect(Collectors.toList());

        Set<WordGrid.GridLocation> allLocationsSet = new HashSet<>(allLocations);

        return allLocations.size() == allLocationsSet.size();
    }

    public static void main(String[] args) {
        WordGrid wordGrid = new WordGrid(9, 9);
        List<String> words = List.of("MATTHEW", "JOE", "MARRY", "SARAH", "SALLY");
        Map<String, List<List<WordGrid.GridLocation>>> domains = new HashMap<>();
        for (String word : words) {
            domains.put(word, wordGrid.generateDomains(word));
        }
        CSP<String, List<WordGrid.GridLocation>> csp = new CSP<>(words, domains);
        csp.addConstraint(new WordSearchConstraint(words));
        Map<String, List<WordGrid.GridLocation>> solution = csp.backtrackingSearch();
        if (solution == null) {
            System.out.println("No solution found.");
        } else {
            Random random = new Random();
            for (Map.Entry<String, List<WordGrid.GridLocation>> entry : solution.entrySet()) {
                String word = entry.getKey();
                List<WordGrid.GridLocation> locations = entry.getValue();
                if (random.nextBoolean()) {
                    Collections.reverse(locations);
                }
                wordGrid.mark(word, locations);
            }
            System.out.println(wordGrid);
        }
    }
}
