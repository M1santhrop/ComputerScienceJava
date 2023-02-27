package chapter4;

public class Edge {
    private int u;
    private int v;

    public Edge(int u, int v) {
        this.u = u;
        this.v = v;
    }

    public Edge reversed() {
        return new Edge(v, u);
    }

    @Override
    public String toString() {
        return u + "->" + v;
    }

    public int getU() {
        return u;
    }

    public int getV() {
        return v;
    }
}
