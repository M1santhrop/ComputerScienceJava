package chapter6;

import java.util.DoubleSummaryStatistics;
import java.util.List;
import java.util.stream.Collectors;

public final class Statistics {
    private List<Double> list;
    private DoubleSummaryStatistics dss;

    public Statistics(List<Double> list) {
        this.list = list;
        dss = list.stream().collect(Collectors.summarizingDouble(aDouble -> aDouble));
    }

    public double sum() {
        return dss.getSum();
    }

    public double mean() {
        return dss.getAverage();
    }

    public double variance() {
        double mean = mean();
        return list.stream().mapToDouble(x -> Math.pow((x - mean), 2)).average().getAsDouble();
    }

    public double std() {
        return Math.sqrt(variance());
    }

    public List<Double> zscored() {
        double mean = mean();
        double std = std();
        return list.stream().map(x -> (std != 0) ? ((x - mean) / std) : 0.0).collect(Collectors.toList());
    }

    public double max() {
        return dss.getMax();
    }

    public double min() {
        return dss.getMin();
    }
}
