public class Board {
    private Square[][] board;
    private int numBombs;
    private int rows;
    private int cols;

    public Board(int numOfBombs, int rows, int cols) {
        numBombs = numOfBombs;
        this.rows = rows;
        this.cols = cols;
        init();
    }

    private void init() {
        board = new Square[rows][cols];
        int[][] bombCoords = new int[numBombs][2];

        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                board[row][col] = new Square(false, row, col);
            }
        }

        for (int i = 0; i < numBombs; i++) {
            int row = (int) (Math.random() * rows);
            int col = (int) (Math.random() * cols);
            if (board[row][col].isBomb()) {
                i--; // already a bomb here, retry
                continue;
            }
            bombCoords[i] = new int[] { row, col };
            board[row][col] = new Square(true, row, col);
        }

        for (Square[] row : board) {
            for (Square square : row) {
                square.calculateAdjacency(bombCoords);
            }
        }
    }

    public void revealAdjacentZeros(int x, int y) {
        if (x < 0 || y < 0 || x >= rows || y >= cols)
            return;
        Square square = board[x][y];
        if (square.isRevealed() || square.isBomb())
            return;

        square.reveal();

        if (square.getAdjacency() == 0) {
            for (int dx = -1; dx <= 1; dx++) {
                for (int dy = -1; dy <= 1; dy++) {
                    if (dx != 0 || dy != 0) {
                        revealAdjacentZeros(x + dx, y + dy);
                    }
                }
            }
        }
    }

    public void reset() {
        init();
    }

    public boolean checkWin() {
        for (Square[] row : board) {
            for (Square s : row) {
                if (!s.isBomb() && !s.isRevealed()) {
                    return false;
                }
            }
        }
        return true;
    }

    public Square[][] getBoard() {
        return board;
    }
}
