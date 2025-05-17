import javafx.scene.control.Button;
import javafx.scene.input.MouseButton;

public class Square {
    private boolean isBomb;
    private int adjacency = 0;
    private boolean isRevealed = false;
    private final int x;
    private final int y;
    private final Button button;
    private boolean isFlagged = false;

    public enum GameState {
        CONTINUE,
        WIN,
        LOSS
    }

    public Square(boolean shouldBeBomb, int x, int y) {
        isBomb = shouldBeBomb;
        this.x = x;
        this.y = y;
        button = new Button();
        button.setStyle("-fx-background-color: #cccccc; -fx-text-fill: black;");
        button.setOnMouseEntered(e -> {
            if (!isRevealed)
                button.setStyle("-fx-background-color: #dddddd; -fx-text-fill: black;");
        });
        button.setOnMouseExited(e -> {
            if (!isRevealed)
                button.setStyle("-fx-background-color: #cccccc; -fx-text-fill: black;");
        });
        button.setOnMouseClicked(e -> {
            if (e.getButton() == MouseButton.SECONDARY) {
                toggleFlag();
            }
        });
    }

    public void toggleFlag() {
        if (!isRevealed) {
            isFlagged = !isFlagged;
            button.setText(isFlagged ? "🚩" : "");
        }
    }

    public GameState handleClick(Board board) {
        if (isFlagged || isRevealed) {
            return GameState.CONTINUE;
        }

        if (!isRevealed) {
            if (isBomb) {
                reveal();
                return GameState.LOSS;
            } else {
                board.revealAdjacentZeros(x, y);
            }
        }

        if (board.checkWin()) {
            return GameState.WIN;
        }

        return GameState.CONTINUE;
    }

    public boolean isBomb() {
        return isBomb;
    }

    public boolean isRevealed() {
        return isRevealed;
    }

    public int getAdjacency() {
        return adjacency;
    }

    public int calculateAdjacency(int[][] bombCoords) {
        int adj = 0;
        for (int[] coord : bombCoords) {
            int bombX = coord[0];
            int bombY = coord[1];
            if (Math.abs(bombX - x) <= 1 && Math.abs(bombY - y) <= 1 && !(bombX == x && bombY == y)) {
                adj++;
            }
        }
        adjacency = adj;
        return adj;
    }

    public void reveal() {
        isRevealed = true;
        if (isBomb) {
            button.setText("💣");
        } else {
            button.setText(adjacency == 0 ? "" : String.valueOf(adjacency));
        }
        button.setDisable(true);
        button.setStyle("-fx-background-color: #eeeeee; -fx-text-fill: black;");
    }

    public Button getButton() {
        return button;
    }
}
