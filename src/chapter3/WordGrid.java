package chapter3;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

public class WordGrid {
    private final char ALPHABET_LENGTH = 26;
    private final char FIRST_LETTER = 'A';
    private final int rows;
    private final int columns;
    private char[][] grid;

    public WordGrid(int rows, int columns) {
        this.rows = rows;
        this.columns = columns;
        grid = new char[rows][columns];

        Random random = new Random();
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                char randomLetter = (char) (random.nextInt(ALPHABET_LENGTH) + FIRST_LETTER);
                grid[i][j] = randomLetter;
            }
        }
    }

    public void mark(String word, List<GridLocation> locations) {
        for (int i = 0; i < word.length(); i++) {
            GridLocation gridLocation = locations.get(i);
            grid[gridLocation.row][gridLocation.column] = word.charAt(i);
        }
    }

    public List<List<GridLocation>> generateDomains(String word) {
        List<List<GridLocation>> domain = new ArrayList<>();
        int length = word.length();

        for (int row = 0; row < rows; row++) {
            for (int column = 0; column < columns; column++) {
                if (column + length <= columns) {
                    fillRight(domain, row, column, length);
                    if (row + length <= rows) {
                        fillDiagonalRight(domain, row, column, length);
                    }
                }
                if (row + length <= rows) {
                    fillDown(domain, row, column, length);
                    if (column - length >= 0) {
                        fillDiagonalLeft(domain, row, column, length);
                    }
                }
            }
        }

        return domain;
    }

    private void fillRight(List<List<GridLocation>> domain, int row, int column, int length) {
        List<GridLocation> locations = new ArrayList<>();
        for (int c = column; c < (column + length); c++) {
            locations.add(new GridLocation(row, c));
        }
        domain.add(locations);
    }

    private void fillDiagonalRight(List<List<GridLocation>> domain, int row, int column, int length) {
        List<GridLocation> locations = new ArrayList<>();
        for (int c = column; c < (column + length); c++) {
            locations.add(new GridLocation(row++, c));
        }
        domain.add(locations);
    }

    private void fillDown(List<List<GridLocation>> domain, int row, int column, int length) {
        List<GridLocation> locations = new ArrayList<>();
        for (int r = row; r < (row + length); r++) {
            locations.add(new GridLocation(r, column));
        }
        domain.add(locations);
    }

    private void fillDiagonalLeft(List<List<GridLocation>> domain, int row, int column, int length) {
        List<GridLocation> locations = new ArrayList<>();
        for (int r = row; r < (row + length); r++) {
            locations.add(new GridLocation(r, column--));
        }
        domain.add(locations);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (char[] row : grid) {
            sb.append(row);
            sb.append(System.lineSeparator());
        }
        return sb.toString();
    }

    public static class GridLocation {
        private int row;
        private int column;

        public GridLocation(int row, int column) {
            this.row = row;
            this.column = column;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof GridLocation)) return false;
            GridLocation that = (GridLocation) o;
            return row == that.row && column == that.column;
        }

        @Override
        public int hashCode() {
            return Objects.hash(row, column);
        }
    }
}
