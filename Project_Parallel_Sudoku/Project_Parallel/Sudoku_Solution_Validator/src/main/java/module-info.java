module com.example.sudoku_solution_validator {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;


    opens com.example.sudoku_solution_validator to javafx.fxml;
    exports com.example.sudoku_solution_validator;
}