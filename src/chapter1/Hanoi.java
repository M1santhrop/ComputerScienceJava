package chapter1;

import java.util.ArrayDeque;
import java.util.Deque;

public class Hanoi {
    private final int numDiscs;
    public final Deque<Integer> towerA = new ArrayDeque<>() {
    };
    public final Deque<Integer> towerB = new ArrayDeque<>();
    public final Deque<Integer> towerC = new ArrayDeque<>();

    public Hanoi(int discs) {
        numDiscs = discs;
        for (int i = 1; i <= numDiscs; i++) {
            towerA.push(i);
        }
    }

    public static void main(String[] args) {
        Hanoi hanoi = new Hanoi(20);
        hanoi.solve();
        System.out.println(hanoi.towerA.size());
        System.out.println(hanoi.towerB.size());
        System.out.println(hanoi.towerC.size());
    }

    public void solve() {
        move(towerA, towerB, towerC, numDiscs);
    }

    private void move(Deque<Integer> begin, Deque<Integer> end, Deque<Integer> temp, int n) {
        if (n == 1) {
            end.push(begin.pop());
        } else {
            move(begin, temp, end, n - 1);
            move(begin, end, temp, 1);
            move(temp, end, begin, n - 1);
        }
    }
}
