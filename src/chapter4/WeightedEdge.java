package chapter4;

public class WeightedEdge extends Edge implements Comparable<WeightedEdge> {
    private final double weight;

    public WeightedEdge(int u, int v, double weight) {
        super(u, v);
        this.weight = weight;
    }

    @Override
    public WeightedEdge reversed() {
        return new WeightedEdge(getV(), getU(), weight);
    }

    @Override
    public int compareTo(WeightedEdge weightedEdge) {
        Double mine = weight;
        Double theirs = weightedEdge.weight;
        return mine.compareTo(theirs);
    }

    @Override
    public String toString() {
        return getU() + " " + weight + "> " + getV();
    }

    public double getWeight() {
        return weight;
    }
}
