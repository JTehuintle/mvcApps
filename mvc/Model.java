package mvc;

import java.util.Random;

public class Model extends Publisher {
    public static int percentMined = 5;
    private boolean [][] mines;
    private boolean [][] visited;
    private int rows  = 20, columns = 20;
    private int playerRowStart = 0, playerColStart = 0;
    private boolean gameOver = false;

    public Model(){
        initGrid();
    }
    //grid structure
    private void initGrid(){
    mines = new boolean[rows][columns];
    visited = new boolean[rows][columns];
    Random rand = Utilities.rng;
        for (int r = 0; r < rows; r++) {
        for (int c = 0; c < columns; c++) {
            if (!(r == 0 && c == 0) && !(r == rows - 1 && c == columns - 1)) {
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
        if(newRow < 0 || newRow >= rows || newCol < 0 ||newCol >= columns){
            throw new Exception("Can't move off grid");
        }

        //make it throw expection form appPanel java 3-19-20
        if(mines[newRow][newCol]){
            gameOver = true;
            throw new Exception("Steped on Mine");
        }
        playerColStart = newCol;
        playerRowStart = newRow;
        visited[playerRowStart][playerColStart] = true;

        if(playerRowStart == rows - 1 && playerColStart == columns -1){
            gameOver = true;
            throw new Exception("Congrats, you reached the end!");
        }
        changed();
    }
    public int getPeriferalMines(){
        int count = 0;
        for(int pr = -1; pr <= 1; pr++){
            for(int pc = -1; pc <= 1; pc++){
                if(pr ==0 && pc == 0){
                    continue;
                }
                int nr = playerRowStart + pr, nc = playerColStart + pc;
                if(nr >= 0 && nr < rows && nc >= 0 && nc < columns && mines[nr][nc]){
                    count++;
                }
            }
        }
        return count;
    }

    public int getRows(){
        return rows;
    }
    public int getColums(){
        return columns;
    }
    public boolean isVisited(int r, int c ){
        return visited[r][c];
    }
    public int getPlayerRowStart(){
        return playerRowStart;
    }
    public int getPlayerColStart(){
        return playerColStart;
    }
    private String fileName = null;
    private boolean UnsavedChanges = false;
    public void setFileName(String fName){
        fileName = fName;
    }
    public String getFileName(){
        return fileName;
    }
    public boolean getUnsavedChanges(){
        return UnsavedChanges;
    }
    public void setUnsavedChanges (boolean flag){
        UnsavedChanges = flag ;
    }
    public boolean isMine(int r, int c){
        return mines[r][c];
    }
    public boolean isGameOver(){
        return gameOver;
    }

    @Override
    public void changed() {
        super.changed();
    }
}
enum Movement{
    North, South, East, West, NorthWest, NorthEast, SouthWest, SouthEast

}
