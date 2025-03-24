package chess;

import java.util.ArrayList;

public class Pawn extends Piece {

    public Pawn(Position position, Color color) {
        super(color, position);
    }
    
    @Override
    public ArrayList<Position> getLegalMoves(Board board) {
        ArrayList<Position> moves = new ArrayList<>();
        // For this board configuration:
        // White pawns move upward (decrease row), black pawns move downward.
        int direction = (this.color == Color.WHITE) ? -1 : 1;
        int startRow = (this.color == Color.WHITE) ? 6 : 1;

        // Single square forward.
        int forwardRow = position.getRow() + direction;
        if (forwardRow >= 0 && forwardRow < 8 && board.isEmpty(forwardRow, position.getCol())) {
            moves.add(new Position(forwardRow, position.getCol()));
            // Two squares forward from starting row.
            if (position.getRow() == startRow) {
                int doubleRow = position.getRow() + 2 * direction;
                if (doubleRow >= 0 && doubleRow < 8 && board.isEmpty(doubleRow, position.getCol())) {
                    moves.add(new Position(doubleRow, position.getCol()));
                }
            }
        }
        
        // Normal diagonal captures.
        int[] diagOffsets = { -1, 1 };
        for (int offset : diagOffsets) {
            int diagRow = position.getRow() + direction;
            int diagCol = position.getCol() + offset;
            if (diagRow >= 0 && diagRow < 8 && diagCol >= 0 && diagCol < 8) {
                if (!board.isEmpty(diagRow, diagCol)) {
                    if (board.getPiece(diagRow, diagCol).getColor() != this.color) {
                        moves.add(new Position(diagRow, diagCol));
                    }
                }
            }
        }
        
        // En passant diagonal capture:
        // Check both diagonal directions.
        for (int offset : diagOffsets) {
            int diagRow = position.getRow() + direction;
            int diagCol = position.getCol() + offset;
            if (diagRow >= 0 && diagRow < 8 && diagCol >= 0 && diagCol < 8) {
                Position enPassantTarget = new Position(diagRow, diagCol);
                if (Rules.canEnPassant(this, enPassantTarget, board)) {
                    moves.add(enPassantTarget);
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
        return new Pawn(new Position(this.position.getRow(), this.position.getCol()), this.color);
    }
    
    @Override
    public String toString() {
        return (color == Color.WHITE) ? "wP" : "bP";
    }
}
