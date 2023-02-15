package chapter1;

import java.util.stream.IntStream;

public class Fibonacci {
    private int last = 0;
    private int next = 1;

    public static void main(String[] args) {
        System.out.println(fib(4));

        Fibonacci fibonacci = new Fibonacci();
        fibonacci.fibWithStream().limit(41).forEachOrdered(System.out::println);
    }

    private static int fib(int n) {
        int last = 0;
        int next = 1;
        for (int i = 0; i < n; i++) {
            int oldLast = last;
            last = next;
            next = oldLast + last;
        }
        return last;
    }
    
    private IntStream fibWithStream() {
        return IntStream.generate(() -> {
            int oldLast = last;
            last = next;
            next = oldLast + last;
            return oldLast;
        });
    } 
}
