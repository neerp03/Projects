package chess;

public class Rules {

    // Determines if castling is allowed between the given king and rook.
    // Checks:
    // They are on the same row.
    // All squares between them are empty.
    // Additional rules such as king not in check are assumed to be handled
    // elsewhere.)

    public static boolean canCastle(King king, Rook rook, Board board) {
        if (king.getPosition().getRow() != rook.getPosition().getRow()) {
            return false;
        }
        int kingCol = king.getPosition().getCol();
        int rookCol = rook.getPosition().getCol();
        int start = Math.min(kingCol, rookCol) + 1;
        int end = Math.max(kingCol, rookCol);
        for (int col = start; col < end; col++) {
            Position pos = new Position(king.getPosition().getRow(), col);
            if (!board.isEmpty(pos.getRow(), pos.getCol()))
                return false;
        }
        return true;
    }

    public static boolean canEnPassant(Pawn pawn, Position targetPawn, Board board) {
    	Pawn doublePawn = board.advPawn;
    	if (doublePawn == null) {
    		return false;
    	}
        if(doublePawn.getColor() == pawn.getColor()) {
        	return false;
        }
        
        int capturingFile = pawn.getPosition().getCol();
        int doublePawnFile = doublePawn.getPosition().getCol();
        if(Math.abs(capturingFile - doublePawnFile)!= 1) {
        	return false;
        }
        
        int capturingRow = pawn.getPosition().getRow();
        if(pawn.getColor() == Color.WHITE && capturingRow != 3) {
        	return false;
        }
        if(pawn.getColor() == Color.BLACK && capturingRow != 4) {
        	return false;
        }
        int TarRow = (doublePawn.getColor() == Color.BLACK) ? 2 : 5;
        int TarCol = doublePawn.getPosition().getCol();
        Position expTar = new Position(TarRow,TarCol);
        
        return targetPawn.equals(expTar);
        
    }

    public static boolean isInCheck(Color player, Board board) {
        King king = findKing(player, board);
        if (king == null) {
            // Should not happen if the king is always on the board.
            return false;
        }
        Position kingPos = king.getPosition();
        // Check every opponent piece.
        for (Piece piece : board.getAllPieces()) {
            if (piece.getColor() != player) {
                // Debug print: Uncomment for troubleshooting.
                // System.out.println("Checking moves for piece " + piece + " for king at " + kingPos);
                if (piece.getLegalMoves(board).contains(kingPos)) {
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean isCheckmate(Color player, Board board) {
        // If the king is not in check, it's not checkmate.
        if (!isInCheck(player, board)) {
            return false;
        }
        // Try every legal move for each piece of the player.
        for (Piece piece : board.getAllPieces()) {
            if (piece.getColor() == player) {
                for (Position move : piece.getLegalMoves(board)) {
                    // Simulate the move.
                    Board simulated = board.simulateMove(piece, move);
                    // If this move gets the king out of check, it's not checkmate.
                    if (!isInCheck(player, simulated)) {
                        // Debug print: Uncomment to see which move escapes check.
                        // System.out.println("Escape move for " + piece + " to " + move);
                        return false;
                    }
                }
            }
        }
        return true;
    }
    
    public static Piece Promotion(Pawn pawn, String letter, Position pos) {
    	switch(letter.toUpperCase())  {
    	case "N" : return new Knight(pos, pawn.getColor());
    	case "B" : return new Bishop(pos, pawn.getColor());
    	case "R" : return new Rook(pos, pawn.getColor());
    		default: return new Queen(pos, pawn.getColor());

    	}
    }

    // Helper method
    private static King findKing(Color player, Board board) {
        for (Piece piece : board.getAllPieces()) {
            if (piece instanceof King && piece.getColor() == player) {
                return (King) piece;
            }
        }
        return null;
    }
}