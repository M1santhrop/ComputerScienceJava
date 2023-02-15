package chapter1;

public class PiCalculator {

    public static double calculatePi(int nTerms){
        final double numerator = 4.0;
        double denominator = 1.0;
        double operation = 1.0;
        double pi = 0.0;
        for (int i = 0; i < nTerms; i++) {
            pi += (numerator / denominator) * operation;
            denominator += 2.0;
            operation *= -1.0;
        }
        return pi;
    }

    public static void main(String[] args) {
        System.out.println(calculatePi(10));
        System.out.println(calculatePi(100));
        System.out.println(calculatePi(1000));
        System.out.println(calculatePi(10000));
        System.out.println(calculatePi(100000));
        System.out.println(calculatePi(1000000));
    }
}
