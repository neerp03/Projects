package chess;

import java.util.ArrayList;

public class Rook extends Piece {

    public Rook(Position position, Color color) {
        super(color,position);
    }

    @Override
    public ArrayList<Position> getLegalMoves(Board board) {
        ArrayList<Position> moves = new ArrayList<>();

        // Upward moves
        for (int r = position.getRow() - 1; r >= 0; r--) {
            // Check the cell first before creating a Position
            if (board.isEmpty(r, position.getCol())) {
                moves.add(new Position(r, position.getCol()));
            } else {
                // Add capture move if the piece is of opposite color, then stop.
                if (board.getPiece(r, position.getCol()).getColor() != this.color) {
                    moves.add(new Position(r, position.getCol()));
                }
                break;
            }
        }

        // Downward moves
        for (int r = position.getRow() + 1; r < 8; r++) {
            if (board.isEmpty(r, position.getCol())) {
                moves.add(new Position(r, position.getCol()));
            } else {
                if (board.getPiece(r, position.getCol()).getColor() != this.color) {
                    moves.add(new Position(r, position.getCol()));
                }
                break;
            }
        }

        // Leftward moves
        for (int c = position.getCol() - 1; c >= 0; c--) {
            if (board.isEmpty(position.getRow(), c)) {
                moves.add(new Position(position.getRow(), c));
            } else {
                if (board.getPiece(position.getRow(), c).getColor() != this.color) {
                    moves.add(new Position(position.getRow(), c));
                }
                break;
            }
        }

        // Rightward moves
        for (int c = position.getCol() + 1; c < 8; c++) {
            if (board.isEmpty(position.getRow(), c)) {
                moves.add(new Position(position.getRow(), c));
            } else {
                if (board.getPiece(position.getRow(), c).getColor() != this.color) {
                    moves.add(new Position(position.getRow(), c));
                }
                break;
            }
        }

        return moves;
    }

    private boolean inBounds(Position pos) {
        return pos.getRow() >= 0 && pos.getRow() < 8 && pos.getCol() >= 0 && pos.getCol() < 8;
    }

    @Override
    public Piece copy() {
        return new Rook(new Position(this.position.getRow(), this.position.getCol()), this.color);
    }

    @Override
    public String toString() {
        return (color == Color.WHITE) ? "wR" : "bR";
    }
}