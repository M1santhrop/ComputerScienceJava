package chapter2;

import chapter2.GenericSearch.Node;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;

public class MCState {
    private static final int MAX_NUM = 3;
    private final int em;
    private final int wm;
    private final int ec;
    private final int wc;
    private final boolean isWestBoat;

    public MCState(int missionaries, int cannibals, boolean isWestBoat) {
        wm = missionaries;
        wc = cannibals;
        em = MAX_NUM - wm;
        ec = MAX_NUM - wc;
        this.isWestBoat = isWestBoat;
    }

    public boolean goalTest() {
        return isLegal() && em == MAX_NUM && ec == MAX_NUM;
    }

    public boolean isLegal() {
        if (wm < wc && wm > 0) {
            return false;
        }
        if (em < ec && em > 0) {
            return false;
        }
        return true;
    }

    public static List<MCState> successors(MCState mcState) {
        List<MCState> mcStates = new ArrayList<>();
        if (mcState.isWestBoat) {
            if (mcState.wm > 1) {
                mcStates.add(new MCState(mcState.wm - 2, mcState.wc, !mcState.isWestBoat));
            }
            if (mcState.wm > 0) {
                mcStates.add(new MCState(mcState.wm - 1, mcState.wc, !mcState.isWestBoat));
            }
            if (mcState.wc > 1) {
                mcStates.add(new MCState(mcState.wm, mcState.wc - 2, !mcState.isWestBoat));
            }
            if (mcState.wc > 0) {
                mcStates.add(new MCState(mcState.wm, mcState.wc - 1, !mcState.isWestBoat));
            }
            if (mcState.wm > 0 && mcState.wc > 0) {
                mcStates.add(new MCState(mcState.wm - 1, mcState.wc - 1, !mcState.isWestBoat));
            }
        } else {
            if (mcState.em > 1) {
                mcStates.add(new MCState(mcState.wm + 2, mcState.wc, !mcState.isWestBoat));
            }
            if (mcState.em > 0) {
                mcStates.add(new MCState(mcState.wm + 1, mcState.wc, !mcState.isWestBoat));
            }
            if (mcState.ec > 1) {
                mcStates.add(new MCState(mcState.wm, mcState.wc + 2, !mcState.isWestBoat));
            }
            if (mcState.ec > 0) {
                mcStates.add(new MCState(mcState.wm, mcState.wc + 1, !mcState.isWestBoat));
            }
            if (mcState.em > 0 && mcState.ec > 0) {
                mcStates.add(new MCState(mcState.wm + 1, mcState.wc + 1, !mcState.isWestBoat));
            }
        }
        mcStates.removeIf(Predicate.not(MCState::isLegal));
        return mcStates;
    }

    public static void displaySolution(List<MCState> path) {
        if (path.isEmpty()) {
            return;
        }
        MCState oldState = path.get(0);
        System.out.println(oldState);
        for (MCState currentState : path.subList(1, path.size())) {
            if (currentState.isWestBoat) {
                System.out.printf("%d missionaries and %d cannibals moved " +
                        "from the east bank to the west bank.%n",
                        oldState.em - currentState.em,
                        oldState.ec - currentState.ec);
            } else {
                System.out.printf("%d missionaries and %d cannibals moved " +
                                "from the west bank to the east bank.%n",
                        oldState.wm - currentState.wm,
                        oldState.wc - currentState.wc);
            }
            System.out.println(currentState);
            oldState = currentState;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MCState)) return false;
        MCState mcState = (MCState) o;
        return em == mcState.em && wm == mcState.wm && ec == mcState.ec && wc == mcState.wc && isWestBoat == mcState.isWestBoat;
    }

    @Override
    public int hashCode() {
        return Objects.hash(em, wm, ec, wc, isWestBoat);
    }

    @Override
    public String toString() {
        return String.format("On the west bank there are %d missionaries and %d cannibals. %n" +
                "On the east bank there are %d missionaries and %d cannibals. %n" +
                "The boat is on the %s bank", wm, wc, em, ec, isWestBoat ? "west" : "east");
    }

    public static void main(String[] args) {
        MCState start = new MCState(MAX_NUM, MAX_NUM, true);
        Node<MCState> solution = GenericSearch.bfs(start, MCState::goalTest, MCState::successors);
        if (solution == null) {
            System.out.println("No solution found!");
        } else {
            List<MCState> mcStates = GenericSearch.nodeToPath(solution);
            displaySolution(mcStates);
        }
    }
}
