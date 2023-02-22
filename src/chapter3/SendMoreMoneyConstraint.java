package chapter3;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

public class SendMoreMoneyConstraint extends Constraint<Character, Integer> {
    private List<Character> letters;

    public SendMoreMoneyConstraint(List<Character> letters) {
        super(letters);
        this.letters = letters;
    }

    @Override
    public boolean satisfied(Map<Character, Integer> assignment) {
        if ((new HashSet<>(assignment.values())).size() < assignment.size()) {
            return false;
        }

        if (assignment.size() == letters.size()) {
            Integer s = assignment.get('S');
            Integer e = assignment.get('E');
            Integer n = assignment.get('N');
            Integer d = assignment.get('D');
            Integer m = assignment.get('M');
            Integer o = assignment.get('O');
            Integer r = assignment.get('R');
            Integer y = assignment.get('Y');

            int send = s * 1_000 + e * 100 + n * 10 + d;
            int more = m * 1_000 + o * 100 + r * 10 + e;
            int money = m * 10_000 + o * 1_000 + n * 100 + e * 10 + y;
            return send + more == money;
        }
        return true;
    }

    public static void main(String[] args) {
        List<Character> letters = List.of('S', 'E', 'N', 'D', 'M', 'O', 'R', 'Y');
        Map<Character, List<Integer>> possibleDigits = new HashMap<>();
        for (Character letter : letters) {
            possibleDigits.put(letter, List.of(0, 1, 2, 3, 4, 5, 6, 7, 8, 9));
        }
        possibleDigits.replace('M', List.of(1));
        CSP<Character, Integer> csp = new CSP<>(letters, possibleDigits);
        csp.addConstraint(new SendMoreMoneyConstraint(letters));
        Map<Character, Integer> solution = csp.backtrackingSearch();
        if (solution == null) {
            System.out.println("No solution found!");
        } else {
            System.out.println(solution);
        }
    }
}
