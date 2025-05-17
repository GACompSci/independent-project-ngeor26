import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class App extends Application {
    private Board board;
    private GridPane grid;
    private Scene gameScene;
    private Scene introScene;
    private Stage primaryStage;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        primaryStage = stage;
        showIntroScreen();
    }

    private void showIntroScreen() {
        Label title = new Label("Minesweeper");
        title.setStyle("-fx-font-size: 32px; -fx-font-weight: bold;");

        Button easyBtn = new Button("Easy");
        Button mediumBtn = new Button("Medium");
        Button hardBtn = new Button("Hard");

        easyBtn.setOnAction(e -> startGame(10, 10, 10));
        mediumBtn.setOnAction(e -> startGame(15, 15, 40));
        hardBtn.setOnAction(e -> startGame(16, 30, 99));

        VBox layout = new VBox(20, title, easyBtn, mediumBtn, hardBtn);
        layout.setAlignment(Pos.CENTER);
        introScene = new Scene(layout, 500, 400);
        primaryStage.setScene(introScene);
        primaryStage.setTitle("Minesweeper");
        primaryStage.show();
    }

    private void startGame(int rows, int cols, int bombs) {
        board = new Board(bombs, rows, cols);
        grid = new GridPane();
        grid.setHgap(1);
        grid.setVgap(1);
        buildUI(grid, board, rows, cols);

        gameScene = new Scene(grid);
        primaryStage.setScene(gameScene);
    }

    private void buildUI(GridPane grid, Board board, int rows, int cols) {
        grid.getChildren().clear();
        Square[][] squares = board.getBoard();

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                Square square = squares[i][j];
                square.getButton().setMinSize(30, 30);
                square.getButton().setOnAction(e -> {
                    Square.GameState result = square.handleClick(board);
                    if (result == Square.GameState.LOSS) {
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Game Over");
                        alert.setHeaderText("You hit a bomb!");
                        alert.setContentText("Better luck next time.");
                        alert.showAndWait();
                        showIntroScreen();
                    } else if (result == Square.GameState.WIN) {
                        Alert winAlert = new Alert(Alert.AlertType.INFORMATION);
                        winAlert.setTitle("You Win!");
                        winAlert.setHeaderText("Congratulations!");
                        winAlert.setContentText("You revealed all the safe tiles!");
                        winAlert.showAndWait();
                        showIntroScreen();
                    }
                });
                grid.add(square.getButton(), j, i);
            }
        }
    }
}
