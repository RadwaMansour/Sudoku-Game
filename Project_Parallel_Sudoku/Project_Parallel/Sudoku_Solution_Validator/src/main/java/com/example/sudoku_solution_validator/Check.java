package com.example.sudoku_solution_validator;

public class Check {
    private final static int SIZE = 9;
    boolean check = true;

    public boolean isValid(int[][] board, int number, int row, int column) throws InterruptedException {
        int localBoxRow = row - row % 3;
        int localBoxColumn = column - column % 3;
        check = true;
        //Thread.sleep(100);

        Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                //for loop that checks the col

                for (int i = 0; i < SIZE; i++) {
                    if ((board[i][column] == number) && (i != row)) {
                        check =false;
                    }
                }


            }});
        thread1.run();


        Thread thread2 = new Thread(new Runnable() {
            @Override
            public void run() {
                //for loop that checks the row
                for (int i = 0; i < SIZE; i++) {
                    if (board[row][i] == number && (i != column)) {
                        check = false;
                    }
                }
            }
        });
        thread2.run();

        //another thread to take on it's 3x3 part via cell by cell
        Thread thread3 = new Thread(new Runnable() {
            @Override
            public void run() {
                //for loop that checks the specific cell
                for (int i = localBoxRow; i < localBoxRow + 3; i++) {
                    for (int j = localBoxColumn; j < localBoxColumn + 3; j++) {
                        if ((board[i][j] == number) && (i != row) && (j != column)) {
                            check = false;
                        }
                    }
                }
            }
        });
        thread3.run();

        return check;
    }


    boolean check(int[][] board) throws InterruptedException {
        //boolean[][] b= true;
        for (int row = 0; row < SIZE; row++) {
            for (int column = 0; column < SIZE; column++) {
                int num = board[row][column];
                if (!isValid(board, num, row, column)) {
                    return false;
                }
            }
        }
        return true;

    }
}




