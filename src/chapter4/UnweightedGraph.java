package chapter4;

import chapter2.GenericSearch;

import java.util.List;

import static chapter4.CitiesConstants.*;

public class UnweightedGraph<V> extends Graph<V, Edge> {

    public UnweightedGraph(List<V> vertices) {
        super(vertices);
    }

    public void addEdge(Edge edge) {
        edges.get(edge.getU()).add(edge);
        edges.get(edge.getV()).add(edge.reversed());
    }

    public void addEdge(int u, int v) {
        addEdge(new Edge(u, v));
    }

    public void addEdge(V first, V second) {
        addEdge(new Edge(indexOf(first), indexOf(second)));
    }

    @Override
    public void addEdge(V first, V second, float weight) {
        addEdge(first, second);
    }

    public static void main(String[] args) {
        Graph<String, Edge> graph = new UnweightedGraph<>(List.of(SEATTLE, SAN_FRANCISCO, LOS_ANGELES,
                RIVERSIDE, PHOENIX, CHICAGO, BOSTON, NEW_YORK, ATLANTA, MIAMI, DALLAS, HOUSTON,
                DETROIT, PHILADELPHIA, WASHINGTON));
        graph.addEdge(SEATTLE, CHICAGO);
        graph.addEdge(SEATTLE, SAN_FRANCISCO);
        graph.addEdge(SAN_FRANCISCO, RIVERSIDE);
        graph.addEdge(SAN_FRANCISCO, LOS_ANGELES);
        graph.addEdge(LOS_ANGELES, RIVERSIDE);
        graph.addEdge(LOS_ANGELES, PHOENIX);
        graph.addEdge(RIVERSIDE, PHOENIX);
        graph.addEdge(RIVERSIDE, CHICAGO);
        graph.addEdge(PHOENIX, DALLAS);
        graph.addEdge(PHOENIX, HOUSTON);
        graph.addEdge(DALLAS, CHICAGO);
        graph.addEdge(DALLAS, ATLANTA);
        graph.addEdge(DALLAS, HOUSTON);
        graph.addEdge(HOUSTON, ATLANTA);
        graph.addEdge(HOUSTON, MIAMI);
        graph.addEdge(ATLANTA, CHICAGO);
        graph.addEdge(ATLANTA, WASHINGTON);
        graph.addEdge(ATLANTA, MIAMI);
        graph.addEdge(MIAMI, WASHINGTON);
        graph.addEdge(CHICAGO, DETROIT);
        graph.addEdge(DETROIT, BOSTON);
        graph.addEdge(DETROIT, WASHINGTON);
        graph.addEdge(DETROIT, NEW_YORK);
        graph.addEdge(BOSTON, NEW_YORK);
        graph.addEdge(NEW_YORK, PHILADELPHIA);
        graph.addEdge(PHILADELPHIA, WASHINGTON);
        System.out.println(graph);

        final String from = NEW_YORK;
        final String to = WASHINGTON;
        GenericSearch.Node<String> solution = GenericSearch.bfs(from, s -> s.equals(to), graph::neighborsOf);
        if (solution == null) {
            System.out.println("No found solution!");
        } else {
            List<String> path = GenericSearch.nodeToPath(solution);
            System.out.printf("Path from %s to %s:%n", from, to);
            System.out.println(path);
        }
    }
}
