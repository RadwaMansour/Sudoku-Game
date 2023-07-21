package com.example.sudoku_solution_validator;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.css.PseudoClass;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import javax.swing.*;
import java.io.IOException;

import static java.lang.Integer.parseInt;

public class HelloApplication extends Application{
    private final static int SIZE = 9;
    GridPane grid = new GridPane();
    int[][]  board = new int[SIZE][SIZE];
    static TextField[][] textfiels;
    boolean emptyCell = false;

    public int[][] getArray(){
        int [][] storeArray = new int[SIZE][SIZE];
        boolean [][] storeArray1 = new boolean[SIZE][SIZE];
        emptyCell = false;
        for (int i = 0; i < SIZE;i ++) {
            for (int j = 0; j < SIZE; j ++) {
                String num = textfiels[i][j].getText();
                if(num != ""){
                    int number = parseInt(textfiels[i][j].getText());
                    storeArray[i][j] = number;
                    if(textfiels[i][j].isDisabled()){
                        storeArray1[i][j] = true;
                    }
                    else{
                        storeArray1[i][j] = false;
                    }
                }
                else{
                    storeArray[i][j] = 0;
                    emptyCell = true;
                }
            }
        }
        return storeArray;
    }

    public boolean[][] getArray2(){
        boolean [][] storeArray1 = new boolean[SIZE][SIZE];
        for (int i = 0; i < SIZE;i ++) {
            for (int j = 0; j < SIZE; j ++) {
                String num = textfiels[i][j].getText();
                if(num != ""){
                    if(textfiels[i][j].isDisabled()){
                        storeArray1[i][j] = true;
                    }
                }
                else{
                    storeArray1[i][j] = false;
                }
            }
        }
        return storeArray1;
    }

    public void clear() {
        for (int i = 0; i < SIZE;i ++) {
            for (int j = 0; j < SIZE; j ++) {
                textfiels[i][j].setText("");
            }
        }

    }
    private void printBoard(int[][] board){
        for (int row = 0; row < SIZE; row++) {
            for (int column = 0; column < SIZE; column++) {
                textfiels[row][column].setText(String.valueOf(board[row][column]));
                System.out.print(board[row][column]);
            }
            System.out.println();
        }
    }

    public void generate(){
        generate g = new generate();
        int max=8;
        int min=0;
        int min1=3;
        int times = (int)Math.floor(Math.random()*(max-min1+1)+min1);
        for(int i=0;i<times;i++){
            board= getArray();
            try {
                int row = (int)Math.floor(Math.random()*(max-min+1)+min);
                int column = (int)Math.floor(Math.random()*(max-min+1)+min);
                //System.out.println(times);
                int x = g.newNum(board,row,column);
                if(x==0)
                    continue;
                else{
                    textfiels[row][column].setText(String.valueOf(x));
                }
            }
            catch (InterruptedException e){
                throw new RuntimeException(e);
            }
        }}


    private void background(){
        for(int i = 0; i<9; i++){
            for(int k=0; k<9; k++) {
                textfiels[i][k] = new TextField();
                TextField text=textfiels[i][k];
                text.textProperty().addListener(new ChangeListener<String>() {
                    @Override
                    public void changed(ObservableValue<? extends String> observable,String oldValue,String newValue) {
                            if (!newValue.matches("[1-9]") ){
                                    text.setText(newValue.replaceAll(newValue,""));}
                            try{
                                if (Integer.parseInt(newValue) > 9) {
                                    text.setText(newValue.replaceAll(newValue, String.valueOf(Integer.parseInt(newValue) % 10)));
                                }}
                            catch(Exception e){}
                        }});


                textfiels[i][k].setMaxWidth(30);
                textfiels[i][k].setAlignment(Pos.CENTER);
                textfiels[i][k].setStyle("-fx-font-weight: bold;");
                if (((i < 6 && i > 2) && (k < 6 && k > 2))
                        || ((i < 3) && (k < 3))
                        || ((i < 3) && (k > 5))
                        || ((i > 5) && (k > 5))
                        || ((i > 5) && (k < 3)))
                    textfiels[i][k].setStyle("-fx-background-color: rgba(255, 178, 0, 0.9);" + "-fx-font-weight: bold;");
                GridPane.setConstraints(textfiels[i][k], k, i);
                grid.getChildren().addAll(textfiels[i][k]);
            }
        }
    }
    private void isEmpty(){
        board= getArray();
        for (int row = 0; row < SIZE; row++) {
            for (int column = 0; column < SIZE; column++) {
                if(board[row][column]!=0){
                    textfiels[row][column].setDisable(true);
                }
                else{
                    textfiels[row][column].setDisable(false);
                }}
    }}


    @Override
    public void start(Stage primaryStage) {

        //GridPane grid = new GridPane();
        primaryStage.setTitle("Sudoku Solver !");
        grid.setPadding(new Insets(20, 10, 10, 20));
        grid.setVgap(3);
        grid.setHgap(3);
        textfiels = new TextField[9][9];

        background();

        Button solveButton = new Button("Solve");
        grid.add( solveButton,1,11,11,1);
        solveButton.setOnAction(e -> {
            board= getArray();
            boolean [][] board2=getArray2();
            Solve validator = new Solve();

            try {
                if (validator.solveBoard(board,board2)) {
                    printBoard(board);
                } else {
                    JOptionPane.showMessageDialog(null , "Unsolvable board :(", "Error", JOptionPane.ERROR_MESSAGE);

                }
            } catch (InterruptedException ex) {
                throw new RuntimeException(ex);
            }
        });

        Button checkButton = new Button("Check");
        grid.add( checkButton,4,11,11,1);
        checkButton.setOnAction(e1 -> {
            board=getArray();
            Check validator = new Check();
            try {
                if(emptyCell){
                    JOptionPane.showMessageDialog(null, "You must complete the grid before you can check its validity.", "Error", JOptionPane.ERROR_MESSAGE);
                }
                else if(validator.check(board)) {
                    JOptionPane.showMessageDialog(null, "valid sudoku", "success", JOptionPane.INFORMATION_MESSAGE);
                }else {
                    JOptionPane.showMessageDialog(null, "Invalid sudoku", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

        });


        Button reset = new Button("Start");
        grid.add(reset,7,11,11,1);
        reset.setOnAction(e -> { clear(); generate(); isEmpty();});


        Scene scene = new Scene(grid,350,320);
        primaryStage.setScene(scene);
        generate();
        isEmpty();
        primaryStage.show();
    }
    public static void main(String[] args) {
        launch();
    }
}