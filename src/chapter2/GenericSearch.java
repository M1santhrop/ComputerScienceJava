package chapter2;

import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.ToDoubleFunction;

public class GenericSearch {

    public static void main(String [] args) {
        List<Integer> integerListForBinarySearch = Arrays.asList(1,5,15,15,15,15,20);
        Collections.sort(integerListForBinarySearch);
        System.out.println(linearSearch(List.of(1,5,15,15,15,15,20), 5));
        System.out.println(binarySearch(integerListForBinarySearch, 5));
        List<String> stringListForBinarySearch = Arrays.asList("a","d","e","f","z");
        Collections.sort(stringListForBinarySearch);
        System.out.println(linearSearch(List.of("a","d","e","f","z"), "f"));
        System.out.println(binarySearch(stringListForBinarySearch, "f"));
        stringListForBinarySearch = Arrays.asList("john","mark","ronald","sarah");
        Collections.sort(stringListForBinarySearch);
        System.out.println(linearSearch(List.of("john","mark","ronald","sarah"), "sheila"));
        System.out.println(binarySearch(stringListForBinarySearch, "sheila"));
    }

    public static <T extends Comparable<T>> boolean linearSearch(List<T> list, T key) {
        for (T t : list) {
            if (t.compareTo(key) == 0) {
                return true;
            }
        }
        return false;
    }

    public static <T extends Comparable<T>> boolean binarySearch(List<T> list, T key) {
        int begin = 0;
        int end = list.size() - 1;
        while (begin <= end) {
            int middle = (begin + end) / 2;
            T currentElement = list.get(middle);
            if (currentElement.compareTo(key) > 0) {
                end = middle - 1;
            } else if (currentElement.compareTo(key) < 0) {
                begin = middle + 1;
            } else {
                return true;
            }
        }
        return false;
    }

    public static <T> Node<T> dfs(T initial, Predicate<T> goalTest, Function<T, List<T>> successors) {
        Deque<Node<T>> frontier = new ArrayDeque<>();
        frontier.push(new Node<>(initial, null));
        Set<T> explored = new HashSet<>();
        explored.add(initial);

        while (!frontier.isEmpty()) {
            Node<T> currentNode = frontier.pop();
            T currentState = currentNode.state;

            if (goalTest.test(currentState)) {
                return currentNode;
            }

            for (T child : successors.apply(currentState)) {
                if (explored.contains(child)) {
                    continue;
                }
                explored.add(child);
                frontier.push(new Node<>(child, currentNode));
            }
        }
        return null;
    }

    public static <T> Node<T> bfs(T initial, Predicate<T> goalTest, Function<T, List<T>> successors) {
        Queue<Node<T>> frontier = new LinkedList<>();
        frontier.offer(new Node<>(initial, null));
        Set<T> explored = new HashSet<>();
        explored.add(initial);

        while (!frontier.isEmpty()) {
            Node<T> currentNode = frontier.poll();
            T currentState = currentNode.state;

            if (goalTest.test(currentState)) {
                return currentNode;
            }

            for (T child : successors.apply(currentState)) {
                if (explored.contains(child)) {
                    continue;
                }
                explored.add(child);
                frontier.offer(new Node<>(child, currentNode));
            }
        }
        return null;
    }

    public static <T> Node<T> astar(T initial, Predicate<T> goalTest, Function<T, List<T>> successors, ToDoubleFunction<T> heuristic) {
        PriorityQueue<Node<T>> frontier = new PriorityQueue<>();
        frontier.offer(new Node<>(initial, null, 0.0, heuristic.applyAsDouble(initial)));
        Map<T, Double> explored = new HashMap<>();
        explored.put(initial, 0.0);

        while (!frontier.isEmpty()) {
            Node<T> currentNode = frontier.poll();
            T currentState = currentNode.state;

            if (goalTest.test(currentState)) {
                return currentNode;
            }

            for (T child : successors.apply(currentState)) {
                double newCost = currentNode.cost + 1;
                if (!explored.containsKey(child) || explored.get(child) > newCost) {
                    explored.put(child, newCost);
                    frontier.offer(new Node<>(child, currentNode, newCost, heuristic.applyAsDouble(child)));
                }
            }
        }

        return null;
    }

    public static <T> List<T> nodeToPath(Node<T> node) {
        List<T> path = new ArrayList<>();
        path.add(node.state);
        while (node.parent != null) {
            node = node.parent;
            path.add(0, node.state);
        }
        return path;
    }

    public static class Node<T> implements Comparable<Node<T>> {
        final T state;
        Node<T> parent;
        double cost;
        double heuristic;

        public Node(T state, Node<T> parent) {
            this.state = state;
            this.parent = parent;
        }

        public Node(T state, Node<T> parent, double cost, double heuristic) {
            this.state = state;
            this.parent = parent;
            this.cost = cost;
            this.heuristic = heuristic;
        }

        @Override
        public int compareTo(Node<T> tNode) {
            Double mine = cost + heuristic;
            Double theirs = tNode.cost + tNode.heuristic;
            return mine.compareTo(theirs);
        }
    }
}
