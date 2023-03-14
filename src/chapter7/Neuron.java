package chapter7;

import java.util.function.DoubleUnaryOperator;

public class Neuron {
    public final double learningRate;
    public final DoubleUnaryOperator activationFunction;
    public final DoubleUnaryOperator derivativeActivationFunction;
    public double[] weights;
    public double outputCache;
    public double delta;

    public Neuron(double learningRate,
                  DoubleUnaryOperator activationFunction,
                  DoubleUnaryOperator derivativeActivationFunction,
                  double[] weights) {
        this.learningRate = learningRate;
        this.activationFunction = activationFunction;
        this.derivativeActivationFunction = derivativeActivationFunction;
        this.weights = weights;
        outputCache = 0.0;
        delta = 0.0;
    }

    public double output(double[] inputs) {
        outputCache = Util.dotProduct(weights, inputs);
        return activationFunction.applyAsDouble(outputCache);
    }
}
