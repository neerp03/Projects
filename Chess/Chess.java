package chess;

public class Chess {

    private static Board board;
    private static Rules rules;
    private static Color currentPlayer;
    private static boolean gameOver;

    enum Player {
        white, black
    }

    /**
     * Plays the next move for whichever player has the turn.
     * 
     * @param move String for next move, e.g. "a2 a3"
     * 
     * @return A ReturnPlay instance that contains the result of the move.
     *         See the section "The Chess class" in the assignment description for
     *         details of
     *         the contents of the returned ReturnPlay instance.
     */
    public static ReturnPlay play(String move) {
    	

        ReturnPlay result = new ReturnPlay();
        if (gameOver) {
            result.piecesOnBoard = board.getReturnPieces();
            // Optionally, you can keep the terminal message or leave it null.
            return result;
        }
        move = move.trim();

        // Handle resigning.
        if (move.equals("resign")) {
            result.piecesOnBoard = board.getReturnPieces();
            // If the current player resigns, the opponent wins.
            result.message = (currentPlayer == Color.WHITE)
                    ? ReturnPlay.Message.RESIGN_BLACK_WINS
                    : ReturnPlay.Message.RESIGN_WHITE_WINS;
            gameOver = true;
            return result;
        }

        boolean drawRequested = move.contains("draw?");
        if (drawRequested) {
            // Remove the "draw?" part from the move string.
            move = move.replace("draw?", "").trim();
        }
        
        
        String[] tokens = move.split("\\s+");
        if (tokens.length < 2) {
            result.piecesOnBoard = board.getReturnPieces();
            result.message = ReturnPlay.Message.ILLEGAL_MOVE;
            return result;
        }
        String src = tokens[0];
        String dest = tokens[1];

        // Convert algebraic notation into internal coordinates.
        // For example: "e1" -> col = 'e'-'a', row = 8 - (1)
        int srcCol = src.charAt(0) - 'a';
        int srcRow = 8 - (src.charAt(1) - '0');
        int destCol = dest.charAt(0) - 'a';
        int destRow = 8 - (dest.charAt(1) - '0');

        System.out.println("Source position: (" + srcRow + ", " + srcCol + ")");
        System.out.println("Destination position: (" + destRow + ", " + destCol + ")");

        Position srcPos = new Position(srcRow, srcCol);
        Position destPos = new Position(destRow, destCol);

        // Get the piece from the source square.
        Piece piece = board.getPiece(srcRow, srcCol);
        if (piece == null || piece.getColor() != currentPlayer) {
            result.piecesOnBoard = board.getReturnPieces();
            result.message = ReturnPlay.Message.ILLEGAL_MOVE;
            return result;
        }
        
  

        // -------- CASTLING DETECTION --------
        // If the moving piece is a King and the horizontal move is 2 squares,
        // treat it as a castling attempt.
        if (piece instanceof King && Math.abs(destCol - srcCol) == 2) {
            King king = (King) piece;
            // Determine kingside vs. queenside castling.
            if (destCol > srcCol) { // Kingside castling.
                // The rook should be on the far right of the same row.
                Piece rookPiece = board.getPiece(srcRow, 7);
                if (!(rookPiece instanceof Rook)) {
                    result.piecesOnBoard = board.getReturnPieces();
                    result.message = ReturnPlay.Message.ILLEGAL_MOVE;
                    return result;
                }
                Rook rook = (Rook) rookPiece;
                if (!Rules.canCastle(king, rook, board)) {
                    result.piecesOnBoard = board.getReturnPieces();
                    result.message = ReturnPlay.Message.ILLEGAL_MOVE;
                    return result;
                }
                // Move the king.
                board.setPiece(destPos, king);
                board.setPiece(srcPos, null);
                king.setPosition(destPos);
                // For kingside, the rook moves from (srcRow, 7) to (srcRow, destCol - 1).
                Position rookDest = new Position(srcRow, destCol - 1);
                board.setPiece(rookDest, rook);
                board.setPiece(new Position(srcRow, 7), null);
                rook.setPosition(rookDest);
            } else { // Queenside castling.
                // The rook should be on the far left of the same row.
                Piece rookPiece = board.getPiece(srcRow, 0);
                if (!(rookPiece instanceof Rook)) {
                    result.piecesOnBoard = board.getReturnPieces();
                    result.message = ReturnPlay.Message.ILLEGAL_MOVE;
                    return result;
                }
                Rook rook = (Rook) rookPiece;
                if (!Rules.canCastle(king, rook, board)) {
                    result.piecesOnBoard = board.getReturnPieces();
                    result.message = ReturnPlay.Message.ILLEGAL_MOVE;
                    return result;
                }
                // Move the king.
                board.setPiece(destPos, king);
                board.setPiece(srcPos, null);
                king.setPosition(destPos);
                // For queenside, the rook moves from (srcRow, 0) to (srcRow, destCol + 1).
                Position rookDest = new Position(srcRow, destCol + 1);
                board.setPiece(rookDest, rook);
                board.setPiece(new Position(srcRow, 0), null);
                rook.setPosition(rookDest);
            }
            // After castling, switch the turn.
            currentPlayer = (currentPlayer == Color.WHITE) ? Color.BLACK : Color.WHITE;
            result.piecesOnBoard = board.getReturnPieces();
            return result;
        }
        
        Board simulated = board.simulateMove(piece, destPos);
        if (Rules.isInCheck(currentPlayer, simulated)) {
            result.piecesOnBoard = board.getReturnPieces();
            result.message = ReturnPlay.Message.ILLEGAL_MOVE;
            return result;
        }
        // Attempt to move the piece.
        // The movePiece method (to be implemented in Board) should:
        // - Parse the move string into source and destination positions.
        // - Validate the move using the piece's movement rules and the provided Rules
        // instance.
        // - Update the board if the move is legal.
        boolean moveExecuted = board.movePiece(move);

        // If the move is illegal, return the unchanged board state with an error
        // message.
        if (!moveExecuted) {
            result.piecesOnBoard = board.getReturnPieces();
            result.message = ReturnPlay.Message.ILLEGAL_MOVE;
            return result;
        }

        // Check game state: e.g., check, checkmate, draw, etc.
        // (Implement these methods in your Rules class as needed.)
        Color playerWhoJustMoved = (currentPlayer == Color.WHITE) ? Color.BLACK : Color.WHITE; // since movePiece switched already

        if (rules.isCheckmate(currentPlayer, board)) {
            // currentPlayer is the one who is about to move and is checkmated,
            // so the playerWhoJustMoved wins.
            result.message = (playerWhoJustMoved == Color.WHITE)
                    ? ReturnPlay.Message.CHECKMATE_WHITE_WINS
                    : ReturnPlay.Message.CHECKMATE_BLACK_WINS;
            gameOver = true;
        } else if (rules.isInCheck(currentPlayer, board)) {
            result.message = ReturnPlay.Message.CHECK;
        }
        // After a successful move, if a draw was requested, override the message.
        if (drawRequested) {
            result.message = ReturnPlay.Message.DRAW;
            gameOver = true;
        }

       
        // After a valid move, switch the turn to the other player.
        switchPlayer();

        // Update the board state in the ReturnPlay object.
        result.piecesOnBoard = board.getReturnPieces();
        return result;
        
        
    }

    /**
     * This method should reset the game, and start from scratch.
     */
    public static void start() {
        board = new Board();
        board.initialize();
        rules = new Rules();
        currentPlayer = Color.WHITE;
        gameOver = false;

    }

    private static void switchPlayer() {
        currentPlayer = (currentPlayer == Color.WHITE) ? Color.BLACK : Color.WHITE;
    }
    
    
}