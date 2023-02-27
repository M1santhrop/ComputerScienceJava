package chapter4;

import java.util.*;
import java.util.function.IntConsumer;

import static chapter4.CitiesConstants.*;

public class WeightedGraph<V> extends Graph<V, WeightedEdge> {

    public WeightedGraph(List<V> vertices) {
        super(vertices);
    }

    @Override
    public void addEdge(V first, V second) {
        addEdge(first, second, 0);
    }

    public void addEdge(WeightedEdge edge) {
        edges.get(edge.getU()).add(edge);
        edges.get(edge.getV()).add(edge.reversed());
    }

    public void addEdge(int u, int v, float weight) {
        addEdge(new WeightedEdge(u, v, weight));
    }


    public void addEdge(V first, V second, float weight) {
        addEdge(indexOf(first), indexOf(second), weight);
    }

    public static double totalWeight(List<WeightedEdge> path) {
        return path.stream().mapToDouble(WeightedEdge::getWeight).sum();
    }

    public List<WeightedEdge> mst(int start) {
        List<WeightedEdge> result = new LinkedList<>();

        if (start < 0 || start > (getVertexCount() - 1)) {
            return result;
        }

        Queue<WeightedEdge> priorityQueue = new PriorityQueue<>();
        boolean[] visited = new boolean[getVertexCount()];

        IntConsumer visit = index -> {
            visited[index] = true;
            for (WeightedEdge weightedEdge : edgesOf(index)) {
                if (!visited[weightedEdge.getV()]) {
                    priorityQueue.offer(weightedEdge);
                }
            }
        };

        visit.accept(start);

        while (!priorityQueue.isEmpty()) {
            WeightedEdge edge = priorityQueue.poll();
            if (visited[edge.getV()]) {
                continue;
            }
            result.add(edge);
            visit.accept(edge.getV());
        }

        return result;
    }

    public void printWeightedPath(List<WeightedEdge> weightedPath) {
        for (WeightedEdge edge : weightedPath) {
            System.out.println(vertexAt(edge.getU()) + " " + edge.getWeight() + "> " + vertexAt(edge.getV()));
        }
        System.out.printf("Total weight: %.1f %n", totalWeight(weightedPath));
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < getVertexCount(); i++) {
            sb.append(vertexAt(i));
            sb.append(" -> ");
            sb.append(
                    Arrays.toString(
                            edgesOf(i)
                                    .stream()
                                    .map(weightedEdge -> "(" +
                                            vertexAt(weightedEdge.getV()) +
                                            ", " +
                                            weightedEdge.getWeight() +
                                            ")"
                                    )
                                    .toArray()
                    ));
            sb.append(System.lineSeparator());
        }
        return sb.toString();
    }

    public DijkstraResult dijkstra(V root) {
        int first = indexOf(root);
        double[] distances = new double[getVertexCount()];
        distances[first] = 0;
        boolean[] visited = new boolean[getVertexCount()];
        visited[first] = true;
        Map<Integer, WeightedEdge> pathMap = new HashMap<>();
        Queue<DijkstraNode> priorityQueue = new PriorityQueue<>();
        priorityQueue.offer(new DijkstraNode(first, 0));

        while (!priorityQueue.isEmpty()) {
            int u = priorityQueue.poll().vertex;
            double distU = distances[u];
            for (WeightedEdge weightedEdge : edgesOf(u)) {
                double distV = distances[weightedEdge.getV()];
                double pathWeight = weightedEdge.getWeight() + distU;
                if (!visited[weightedEdge.getV()] || (distV > pathWeight)) {
                    visited[weightedEdge.getV()] = true;
                    distances[weightedEdge.getV()] = pathWeight;
                    pathMap.put(weightedEdge.getV(), weightedEdge);
                    priorityQueue.offer(new DijkstraNode(weightedEdge.getV(), pathWeight));
                }
            }
        }
        return new DijkstraResult(distances, pathMap);
    }

    public Map<V, Double> distanceArrayToDistanceMap(double[] distances) {
        Map<V, Double> distanceMap = new HashMap<>();
        for (int i = 0; i < distances.length; i++) {
            distanceMap.put(vertexAt(i), distances[i]);
        }
        return distanceMap;
    }

    public static List<WeightedEdge> pathMapToPath(int start, int end, Map<Integer, WeightedEdge> pathMap) {
        if (pathMap.size() == 0) {
            return List.of();
        }

        List<WeightedEdge> path = new LinkedList<>();
        WeightedEdge edge = pathMap.get(end);
        path.add(edge);
        while (edge.getU() != start) {
            edge = pathMap.get(edge.getU());
            path.add(edge);
        }
        Collections.reverse(path);
        return path;
    }

    public static class DijkstraNode implements Comparable<DijkstraNode> {
        private final int vertex;
        private final double distance;

        public DijkstraNode(int vertex, double distance) {
            this.vertex = vertex;
            this.distance = distance;
        }

        @Override
        public int compareTo(DijkstraNode dijkstraNode) {
            Double mine = distance;
            Double theirs = dijkstraNode.distance;
            return mine.compareTo(theirs);
        }
    }

    public static final class DijkstraResult {
        private final double[] distances;
        private final Map<Integer, WeightedEdge> pathMap;

        public DijkstraResult(double[] distances, Map<Integer, WeightedEdge> pathMap) {
            this.distances = distances;
            this.pathMap = pathMap;
        }
    }

    public static void main(String[] args) {
        WeightedGraph<String> graph = new WeightedGraph<>(List.of(SEATTLE, SAN_FRANCISCO, LOS_ANGELES,
                RIVERSIDE, PHOENIX, CHICAGO, BOSTON, NEW_YORK, ATLANTA, MIAMI, DALLAS, HOUSTON,
                DETROIT, PHILADELPHIA, WASHINGTON));
        graph.addEdge(SEATTLE, CHICAGO, 1737);
        graph.addEdge(SEATTLE, SAN_FRANCISCO, 678);
        graph.addEdge(SAN_FRANCISCO, RIVERSIDE, 386);
        graph.addEdge(SAN_FRANCISCO, LOS_ANGELES, 348);
        graph.addEdge(LOS_ANGELES, RIVERSIDE, 50);
        graph.addEdge(LOS_ANGELES, PHOENIX, 357);
        graph.addEdge(RIVERSIDE, PHOENIX, 307);
        graph.addEdge(RIVERSIDE, CHICAGO, 1704);
        graph.addEdge(PHOENIX, DALLAS, 887);
        graph.addEdge(PHOENIX, HOUSTON, 1015);
        graph.addEdge(DALLAS, CHICAGO, 805);
        graph.addEdge(DALLAS, ATLANTA, 721);
        graph.addEdge(DALLAS, HOUSTON, 225);
        graph.addEdge(HOUSTON, ATLANTA, 702);
        graph.addEdge(HOUSTON, MIAMI, 968);
        graph.addEdge(ATLANTA, CHICAGO, 588);
        graph.addEdge(ATLANTA, WASHINGTON, 543);
        graph.addEdge(ATLANTA, MIAMI, 604);
        graph.addEdge(MIAMI, WASHINGTON, 923);
        graph.addEdge(CHICAGO, DETROIT, 238);
        graph.addEdge(DETROIT, BOSTON, 613);
        graph.addEdge(DETROIT, WASHINGTON, 396);
        graph.addEdge(DETROIT, NEW_YORK, 482);
        graph.addEdge(BOSTON, NEW_YORK, 190);
        graph.addEdge(NEW_YORK, PHILADELPHIA, 81);
        graph.addEdge(PHILADELPHIA, WASHINGTON, 123);
        System.out.println(graph);

        List<WeightedEdge> mst = graph.mst(0);
        graph.printWeightedPath(mst);

        System.out.println();

        final String from = LOS_ANGELES;
        DijkstraResult dijkstraResult = graph.dijkstra(from);
        Map<String, Double> nameDistance = graph.distanceArrayToDistanceMap(dijkstraResult.distances);
        System.out.printf("Distance from %s:%n", from);
        nameDistance.forEach((name, distance) -> System.out.printf("%s : %.1f %n", name, distance));

        System.out.println();

        final String to = BOSTON;
        System.out.printf("Shortest path from %s to %s: %n", from, to);
        List<WeightedEdge> path = pathMapToPath(graph.indexOf(from), graph.indexOf(to), dijkstraResult.pathMap);
        graph.printWeightedPath(path);
    }
}
