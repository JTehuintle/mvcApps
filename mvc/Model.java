package game;

import java.util.Random;
import mvc.Utilities;
import mvc.Publisher;

public class Model extends Publisher implements java.io.Serializable {
    public static int percentMined = 5;
    private boolean[][] mines;
    private boolean[][] visited;
    private int rows = 20, cols = 20;
    private int playerRow = 0, playerCol = 0;
    private boolean gameOver = false;
    private String fileName = null;
    private boolean unsavedChanges = false;

    public Model() {
        initGrid();
    }
    private void initGrid() {
        mines = new boolean[rows][cols];
        visited = new boolean[rows][cols];
        Random rand = Utilities.rng;
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                if (!(r == 0 && c == 0) && !(r == rows - 1 && c == cols - 1)) {
                    mines[r][c] = rand.nextInt(100) < percentMined;
                }
            }
        }
        visited[0][0] = true;
    }
    public void move(Movement movement) throws Exception {
        if (gameOver)
            throw new Exception("Game Over. No further moves allowed!");
        int newRow = playerRow, newCol = playerCol;
        switch (movement) {
            case North: newRow--; break;
            case South: newRow++; break;
            case East: newCol++; break;
            case West: newCol--; break;
            case NorthWest: newRow--; newCol--; break;
            case NorthEast: newRow--; newCol++; break;
            case SouthWest: newRow++; newCol--; break;
            case SouthEast: newRow++; newCol++; break;
        }
        if (newRow < 0 || newRow >= rows || newCol < 0 || newCol >= cols)
            throw new Exception("Can't move off the grid!");
        if (mines[newRow][newCol]) {
            gameOver = true;
            throw new Exception("Stepped on a mine! Game over.");
        }
        playerRow = newRow;
        playerCol = newCol;
        visited[playerRow][playerCol] = true;
        if (playerRow == rows - 1 && playerCol == cols - 1) {
            gameOver = true;
            throw new Exception("Congrats, you reached the end!");
        }
        changed();
    }

    // New method to count mines around any (r,c)
    public int getPeriferalMinesAt(int r, int c) {
        int count = 0;
        for (int dr = -1; dr <= 1; dr++) {
            for (int dc = -1; dc <= 1; dc++) {
                if (dr == 0 && dc == 0) continue;
                int nr = r + dr, nc = c + dc;
                if (nr >= 0 && nr < rows && nc >= 0 && nc < cols && mines[nr][nc]) {
                    count++;
                }
            }
        }
        return count;
    }

    public int getRows() { return rows; }
    public int getColums() { return cols; }
    public boolean isVisited(int r, int c) { return visited[r][c]; }
    public int getPlayerRowStart() { return playerRow; }
    public int getPlayerColStart() { return playerCol; }
    public boolean isMine(int r, int c) { return mines[r][c]; }
    public boolean isGameOver() { return gameOver; }
    public void setFileName(String fName) { fileName = fName; }
    public String getFileName() { return fileName; }
    public boolean getUnsavedChanges() { return unsavedChanges; }
    public void setUnsavedChanges(boolean flag) { unsavedChanges = flag; }
    @Override
    public void changed() { super.changed(); }
}
