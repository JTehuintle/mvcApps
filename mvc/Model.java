package mvc;

import java.util.Random;

public class Model {
    public static int percentMined = 5;
    private boolean [][] mines;
    private boolean [][] visited;
    private int rows  = 20, coloums = 20;
    private int playerRowStart = 0, playerColStart = 0;
    private boolean gameOver = false;

    public Model(){
        initGrid();
    }
    //grid structure
    private void initGrid(){
    mines = new boolean[rows][coloums];
    visited = new boolean[rows][coloums];
    Random rand = Utilities.rng;
        for (int r = 0; r < rows; r++) {
        for (int c = 0; c < coloums; c++) {
            if (!(r == 0 && c == 0) && !(r == rows - 1 && c == coloums - 1)) {
                mines[r][c] = rand.nextInt(100) < percentMined;
            }
        }
    }
    visited[0][0] = true;
    }
    public void move(Movement movement) throws Exception{
        if(gameOver) throw new Exception("Game Over bud");

        int newRow = playerRowStart,newCol = playerColStart;

        switch (movement){
            case North: newRow--;
                break;
            case South: newRow++;
                break;
            case East: newCol++;
                break;
            case West: newCol--;
                break;
            case NorthWest: newRow--;newCol--;
                break;
            case NorthEast: newRow--;newCol++;
                break;
            case SouthWest: newRow++;newCol--;
                break;
            case SouthEast: newRow++;newCol++;
                break;
        }
        if(newRow < 0 || newRow >= rows || newCol < 0 ||newCol >= coloums){
            throw new Exception("Can't move off grid");
        }
        if(mines[newRow][newCol]){
            gameOver = true;
            throw new Exception("Steped on Mine");
        }
        playerColStart = newCol;
        playerRowStart = newRow;
        visited[playerRowStart][playerColStart] = true;

        if(playerRowStart == rows - 1 && playerColStart == coloums -1){
            gameOver = true;
            throw new Exception("Congrats, you reached the end!");
        }
        //
    }
    public int getPeriferalMines(){
        int count = 0;
        for(int pr = -1; pr <= 1; pr++){
            for(int pc = -1; pc <= 1; pc++){
                if(pr ==0 && pc == 0){
                    continue;
                }
                int nr = playerRowStart + pr, nc = playerColStart + pc;
                if(nr >= 0 && nr < rows && nc >= 0 && nc < coloums && mines[nr][nc]){
                    count++;
                }
            }
        }
        return count;
    }

    public int getRows(){
        return rows;
    }
    public int getColoums(){
        return coloums;
    }
    public boolean isVisted(int r, int c ){
        return visited[r][c];
    }
    public int getPlayerRowStart(){
        return playerRowStart;
    }
    public int getPlayerColStart(){
        return playerColStart;
    }
}
enum Movement{
    North, South, East, West, NorthWest, NorthEast, SouthWest, SouthEast

}
