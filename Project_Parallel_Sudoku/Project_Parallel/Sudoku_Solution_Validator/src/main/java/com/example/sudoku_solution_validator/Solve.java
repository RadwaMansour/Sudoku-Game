package com.example.sudoku_solution_validator;
public class Solve extends Check{
    private final static int SIZE = 9;
    boolean solveBoard(int[][] board,boolean[][] board2) throws InterruptedException {
        for (int row = 0; row < SIZE; row++) {
            for (int column = 0; column < SIZE; column++) {
                if(!board2[row][column]){
                if (!isValid(board, board[row][column], row, column)|| board[row][column]==0) {
                        for (int numberToTry = 1; numberToTry <= SIZE; numberToTry++) {
                            if (isValid(board, numberToTry, row, column)) {
                                board[row][column] = numberToTry;
                                if (solveBoard(board,board2)) {
                                    return true;
                                } else {
                                    board[row][column] = 0;
                            }
                        }
                    }
                    return false;
                }
            }
        }}
        return true;
}
}


