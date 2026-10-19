import java.util.*;

public class MazeGenerator {

    static final int ROWS = 21;
    static final int COLS = 21;

    static int[][] maze = new int[ROWS][COLS];

    // Represents a cell in the maze
    static class Cell {
        int row;
        int col;

        Cell(int row, int col) {
            this.row = row;
            this.col = col;
        }
    }

    // Directions: Up, Right, Down, Left
    static int[][] directions = {
        {-2, 0},
        {0, 2},
        {2, 0},
        {0, -2}
    };

    public static void main(String[] args) {

        initializeMaze();

        generateMaze();

        createEntryExit();

        printMaze();
    }

    // Initially make every cell a wall
    static void initializeMaze() {

        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                maze[i][j] = 1;
            }
        }
    }

    // Generate maze using DFS + Stack
    static void generateMaze() {

        Stack<Cell> stack = new Stack<>();

        // Starting position
        Cell start = new Cell(1, 1);

        maze[1][1] = 0;
        stack.push(start);

        Random random = new Random();

        while (!stack.isEmpty()) {

            Cell current = stack.peek();

            ArrayList<int[]> neighbors = new ArrayList<>();

            // Find unvisited neighbors
            for (int[] direction : directions) {

                int newRow = current.row + direction[0];
                int newCol = current.col + direction[1];

                if (newRow > 0 &&
                    newRow < ROWS - 1 &&
                    newCol > 0 &&
                    newCol < COLS - 1 &&
                    maze[newRow][newCol] == 1) {

                    neighbors.add(new int[]{
                        newRow,
                        newCol,
                        direction[0],
                        direction[1]
                    });
                }
            }

            // If there are unvisited neighbors
            if (!neighbors.isEmpty()) {

                // Select random neighbor
                int[] selected =
                    neighbors.get(random.nextInt(neighbors.size()));

                int newRow = selected[0];
                int newCol = selected[1];

                int rowDirection = selected[2];
                int colDirection = selected[3];

                // Remove wall between current and new cell
                maze[
                    current.row + rowDirection / 2
                ][
                    current.col + colDirection / 2
                ] = 0;

                // Open new cell
                maze[newRow][newCol] = 0;

                // Push new cell
                stack.push(new Cell(newRow, newCol));

            } else {

                // Backtrack
                stack.pop();
            }
        }
    }

    // Create entry and exit points
    static void createEntryExit() {

        // Entry point on the left side
        maze[1][0] = 0;

        // Exit point on the right side
        maze[ROWS - 2][COLS - 1] = 0;
    }

    // Display maze
    static void printMaze() {

        for (int i = 0; i < ROWS; i++) {

            for (int j = 0; j < COLS; j++) {

                if (maze[i][j] == 1) {
                    System.out.print("██");
                } else {
                    System.out.print("  ");
                }
            }

            System.out.println();
        }
    }
}