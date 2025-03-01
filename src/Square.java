public class Square {
    private boolean isBomb;
    private int adjacency = 0;
    private boolean isRevealed = false;
    private int x;
    private int y;

    public Square(boolean shouldBeBomb, int xPos, int yPos) {
        isBomb = shouldBeBomb;
        x = xPos;
        y = yPos;
    }

    public void handleClick(Board board) {
        if (!isRevealed) {
            if (isBomb) {
                board.reset();
            } else {
                reveal();
            }
        }
    }

    public boolean isBomb() {
        return isBomb;
    }

    public boolean isRevealed() {
        return isRevealed;
    }

    public int calculateAdjacency(int[][] bombCoords) {
        int adj = 0;
        for (int[] coord : bombCoords) {
            int bombX = coord[0];
            int bombY = coord[1];
            if (Math.max(Math.abs(bombX - x), Math.abs(bombY - y)) == 1) {
                adj++;
            }
        }
        adjacency = adj;
        return adj;
    }

    public void reveal() {
        // for now
        if (isBomb) {
            System.out.print("B ");
        } else {
            System.out.print(adjacency + " ");
        }
    }
}
