package chapter2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class Maze {
    private final int rows;
    private final int columns;
    private final MazeLocation start;
    private final MazeLocation goal;
    private Cell[][] grid;

    public Maze(int rows, int columns, MazeLocation start, MazeLocation goal, double sparseness) {
        this.rows = rows;
        this.columns = columns;
        this.start = start;
        this.goal = goal;

        grid = new Cell[rows][columns];
        for (Cell[] row : grid) {
            Arrays.fill(row, Cell.EMPTY);
        }
        randomlyFill(sparseness);
        grid[start.row][start.column] = Cell.START;
        grid[goal.row][goal.column] = Cell.GOAL;
    }

    public Maze() {
        this(10, 10, new MazeLocation(0, 0), new MazeLocation(9, 9), 0.2);
    }

    private void randomlyFill(double sparseness) {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                if (Math.random() < sparseness) {
                    grid[i][j] = Cell.BLOCKED;
                }
            }
        }
    }

    public boolean goalTest(MazeLocation mazeLocation) {
        return goal.equals(mazeLocation);
    }

    public List<MazeLocation> successors(MazeLocation mazeLocation) {
        List<MazeLocation> locations = new ArrayList<>();
        if (mazeLocation.row + 1 < rows && grid[mazeLocation.row + 1][mazeLocation.column] != Cell.BLOCKED) {
            locations.add(new MazeLocation(mazeLocation.row + 1, mazeLocation.column));
        }
        if (mazeLocation.row - 1 >= 0 && grid[mazeLocation.row - 1][mazeLocation.column] != Cell.BLOCKED) {
            locations.add(new MazeLocation(mazeLocation.row - 1, mazeLocation.column));
        }
        if (mazeLocation.column + 1 < columns && grid[mazeLocation.row][mazeLocation.column + 1] != Cell.BLOCKED) {
            locations.add(new MazeLocation(mazeLocation.row, mazeLocation.column + 1));
        }
        if (mazeLocation.column - 1 >= 0 && grid[mazeLocation.row][mazeLocation.column - 1] != Cell.BLOCKED) {
            locations.add(new MazeLocation(mazeLocation.row, mazeLocation.column - 1));
        }
        return locations;
    }

    public void mark(List<Maze.MazeLocation> path) {
        for (MazeLocation mazeLocation : path) {
            grid[mazeLocation.row][mazeLocation.column] = Cell.PATH;
        }
        grid[start.row][start.column] = Cell.START;
        grid[goal.row][goal.column] = Cell.GOAL;
    }

    public void clear(List<MazeLocation> path) {
        for (MazeLocation mazeLocation : path) {
            grid[mazeLocation.row][mazeLocation.column] = Cell.EMPTY;
        }
        grid[start.row][start.column] = Cell.START;
        grid[goal.row][goal.column] = Cell.GOAL;
    }

    public double euclideanDistance(MazeLocation mazeLocation) {
        int xdist = mazeLocation.column - goal.column;
        int ydist = mazeLocation.row - goal.row;
        return Math.sqrt((xdist * xdist) + (ydist * ydist));
    }

    public double manhattanDistance(MazeLocation mazeLocation) {
        int xdist = Math.abs(mazeLocation.column - goal.column);
        int ydist = Math.abs(mazeLocation.row - goal.row);
        return (xdist + ydist);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Cell[] row : grid) {
            for (Cell cell : row) {
                sb.append(cell.toString());
            }
            sb.append(System.lineSeparator());
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        Maze maze = new Maze();
        System.out.println(maze);
        GenericSearch.Node<MazeLocation> solution1 = GenericSearch.dfs(maze.start, maze::goalTest, maze::successors);
        if (solution1 == null) {
            System.out.println("No solution found using depth-first search!");
        } else {
            List<MazeLocation> path1 = GenericSearch.nodeToPath(solution1);
            maze.mark(path1);
            System.out.println(maze);
            maze.clear(path1);
        }

        GenericSearch.Node<MazeLocation> solution2 = GenericSearch.bfs(maze.start, maze::goalTest, maze::successors);
        if (solution2 == null) {
            System.out.println("No solution found using breadth-first search!");
        } else {
            List<MazeLocation> path2 = GenericSearch.nodeToPath(solution2);
            maze.mark(path2);
            System.out.println(maze);
            maze.clear(path2);
        }

        GenericSearch.Node<MazeLocation> solution3 = GenericSearch.astar(maze.start, maze::goalTest, maze::successors, maze::manhattanDistance);
        if (solution3 == null) {
            System.out.println("No solution found using A* with manhattan distance!");
        } else {
            List<MazeLocation> path3 = GenericSearch.nodeToPath(solution3);
            maze.mark(path3);
            System.out.println(maze);
            maze.clear(path3);
        }

        GenericSearch.Node<MazeLocation> solution4 = GenericSearch.astar(maze.start, maze::goalTest, maze::successors, maze::euclideanDistance);
        if (solution4 == null) {
            System.out.println("No solution found using A* with euclidean distance!");
        } else {
            List<MazeLocation> path4 = GenericSearch.nodeToPath(solution4);
            maze.mark(path4);
            System.out.println(maze);
            maze.clear(path4);
        }
    }

    public enum Cell {
        EMPTY(" "),
        BLOCKED("X"),
        START("S"),
        GOAL("G"),
        PATH("*");

        private final String code;

        Cell(String code) {
            this.code = code;
        }

        @Override
        public String toString() {
            return code;
        }
    }

    public static class MazeLocation {
        public final int row;
        public final int column;

        public MazeLocation(int row, int column) {
            this.row = row;
            this.column = column;
        }

        @Override
        public int hashCode() {
            return Objects.hash(row, column);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            MazeLocation that = (MazeLocation) o;
            return row == that.row && column == that.column;
        }
    }
}
