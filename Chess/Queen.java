package chess;

import java.util.ArrayList;

public class Queen extends Piece {

    public Queen(Position position, Color color) {
        super(color,position);
    }

    @Override
    public ArrayList<Position> getLegalMoves(Board board) {
        ArrayList<Position> moves = new ArrayList<>();
        int[] rowDirs = { -1, -1, -1, 0, 0, 1, 1, 1 };
        int[] colDirs = { -1, 0, 1, -1, 1, -1, 0, 1 };

        for (int i = 0; i < rowDirs.length; i++) {
            int newRow = position.getRow();
            int newCol = position.getCol();
            while (true) {
                newRow += rowDirs[i];
                newCol += colDirs[i];
                // Check bounds before creating the Position object.
                if (newRow < 0 || newRow >= 8 || newCol < 0 || newCol >= 8) {
                    break;
                }
                Position newPos = new Position(newRow, newCol);
                if (board.isEmpty(newRow, newCol)) {
                    moves.add(newPos);
                } else {
                    if (board.getPiece(newRow, newCol).getColor() != this.color) {
                        moves.add(newPos);
                    }
                    break;
                }
            }
        }
        return moves;
    }

    private boolean inBounds(Position pos) {
        return pos.getRow() >= 0 && pos.getRow() < 8 && pos.getCol() >= 0 && pos.getCol() < 8;
    }

    @Override
    public Piece copy() {
        return new Queen(new Position(this.position.getRow(), this.position.getCol()), this.color);
    }

    @Override
    public String toString() {
        return (color == Color.WHITE) ? "wQ" : "bQ";
    }
}