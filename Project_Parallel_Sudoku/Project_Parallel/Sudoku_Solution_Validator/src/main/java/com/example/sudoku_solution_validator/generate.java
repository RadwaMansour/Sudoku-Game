package com.example.sudoku_solution_validator;

public class generate extends Check {

    int newNum(int[][] board,int row,int column) throws InterruptedException{
          int max=9;
          int min=1;
          int num = (int)Math.floor(Math.random()*(max-min+1)+min);
          if (isValid(board, num, row, column)) {
                return num;}
          return 0;
    }
}
