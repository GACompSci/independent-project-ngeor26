public class Board {
    private Square[][] board;
    private int numBombs;
    private int length;

    public Board(int numOfBombs, int lengthOfBoard) {
        numBombs = numOfBombs;
        length = lengthOfBoard;
        init();
    }

    private void init() {
        board = new Square[length][length];
        int[][] bombCoords = new int[numBombs][2];

        // place normal squares
        for (int row = 0; row < length; row++) {
            for (int col = 0; col < length; col++) {
                board[row][col] = new Square(false, row, col);
            }
        }

        // place bombs
        for (int i = 0; i < numBombs; i++) {
            int row = (int) (Math.random() * length);
            int col = (int) (Math.random() * length);
            bombCoords[i] = new int[] { row, col };
            board[row][col] = new Square(true, row, col);
        }

        for (Square[] row : board) {
            for (Square square : row) {
                square.calculateAdjacency(bombCoords);
            }
        }
    }

    public void reset() {
        init();
    }

    public boolean checkWin() {
        for (Square[] row : board) {
            for (int i = 0; i < row.length; i++) {
                if (!row[i].isBomb() && !row[i].isRevealed()) {
                    return false;
                }
            }
        }
        return true;
    }

    public void printBoard() {
        for (Square[] row : board) {
            for (Square square : row) {
                square.reveal();
            }
            System.out.println();
        }
    }
}