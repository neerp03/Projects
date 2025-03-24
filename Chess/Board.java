package chess;

import java.util.ArrayList;

public class Board implements Cloneable {

    private Piece[][] grid = new Piece[8][8];
    private Color currentPlayer;
    public Pawn advPawn;

    public Board() {
        currentPlayer = Color.WHITE;
    }

    // Copy constructor for deep copy
    public Board(Board other) {
        this.currentPlayer = other.currentPlayer;
        this.grid = new Piece[8][8];
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                if (other.grid[row][col] != null) {
                    try {
                        this.grid[row][col] = (Piece) other.grid[row][col].clone();
                    } catch (CloneNotSupportedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    public void initialize() {
        grid = new Piece[8][8]; // Reset j in case

        // Black Pieces
        grid[0][0] = new Rook(new Position(0, 0), Color.BLACK);
        grid[0][1] = new Knight(new Position(0, 1), Color.BLACK);
        grid[0][2] = new Bishop(new Position(0, 2), Color.BLACK);
        grid[0][3] = new Queen(new Position(0, 3), Color.BLACK);
        grid[0][4] = new King(new Position(0, 4), Color.BLACK);
        grid[0][5] = new Bishop(new Position(0, 5), Color.BLACK);
        grid[0][6] = new Knight(new Position(0, 6), Color.BLACK);
        grid[0][7] = new Rook(new Position(0, 7), Color.BLACK);

        // Black Pawns
        for (int i = 0; i < 8; i++) {
            grid[1][i] = new Pawn(new Position(1, i), Color.BLACK);
        }

        // White Pieces
        grid[7][0] = new Rook(new Position(7, 0), Color.WHITE);
        grid[7][1] = new Knight(new Position(7, 1), Color.WHITE);
        grid[7][2] = new Bishop(new Position(7, 2), Color.WHITE);
        grid[7][3] = new Queen(new Position(7, 3), Color.WHITE);
        grid[7][4] = new King(new Position(7, 4), Color.WHITE);
        grid[7][5] = new Bishop(new Position(7, 5), Color.WHITE);
        grid[7][6] = new Knight(new Position(7, 6), Color.WHITE);
        grid[7][7] = new Rook(new Position(7, 7), Color.WHITE);

        // White Pawns
        for (int i = 0; i < 8; i++) {
            grid[6][i] = new Pawn(new Position(6, i), Color.WHITE);
        }

    }

    public boolean movePiece(String move) { // Add this to parameter when Neer makes it , Rules rule

        String parts[] = move.split(" "); // Assume move is something like "e2 e4"

        if (parts.length != 2 && parts.length != 3) {
            return false; // Invalid move format
        }
        boolean pawnPromotion = parts.length == 3;

        String src = parts[0];
        String dest = parts[1];

        int srcCol = (src.charAt(0) - 'a');
        int srcRow = 8 - (src.charAt(1) - '0');
        int destCol = (dest.charAt(0) - 'a');
        int destRow = 8 - (dest.charAt(1) - '0');

//        System.out.println("Source position: (" + srcRow + ", " + srcCol + ")");
//        System.out.println("Destination position: (" + destRow + ", " + destCol + ")");

        Piece piece = grid[srcRow][srcCol];

        if (piece == null) {
            // No piece at source.
            return false;
        }

        if (piece.getColor() != currentPlayer) {
            return false; // Not this player's turn
        }
        
        Position destPos = new Position(destRow, destCol);
        
        // Get the legal moves for the piece.
        ArrayList<Position> legalMoves = piece.getLegalMoves(this);
        
        // Check if the destination is a legal move.
        if (!legalMoves.contains(destPos)) {
            return false; // The move is not allowed.
        }
        // Validate move with piece's own logic, board, and additional rules

        if (grid[destRow][destCol] != null && grid[destRow][destCol].getColor() == piece.getColor()) {
            return false; // Can't capture your own piece
        }

        if(pawnPromotion) {
            // Replace the pawn with the promoted piece based on the promotion token.
            switch(parts[2].toUpperCase())  {
                case "N":
                    grid[destRow][destCol] = new Knight(new Position(destRow, destCol), piece.getColor());
                    break;
                case "B":
                    grid[destRow][destCol] = new Bishop(new Position(destRow, destCol), piece.getColor());
                    break;
                case "R":
                    grid[destRow][destCol] = new Rook(new Position(destRow, destCol), piece.getColor());
                    break;
                default:
                    grid[destRow][destCol] = new Queen(new Position(destRow, destCol), piece.getColor());
                    break;
            }
        } else {
            // Normal move: place the piece at the destination.
            grid[destRow][destCol] = piece;
        }
        grid[srcRow][srcCol] = null;
        piece.setPosition(destPos);


        // After successful move
        currentPlayer = (currentPlayer == Color.WHITE) ? Color.BLACK : Color.WHITE;

        return true;
    }

    public ArrayList<ReturnPiece> getReturnPieces() {
        ArrayList<ReturnPiece> piecesList = new ArrayList<>();
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                Piece piece = grid[row][col];
                if (piece != null) {
                    ReturnPiece rp = new ReturnPiece();

                    // Determine the piece type based on your internal Piece object.
                    if (piece instanceof Pawn) {
                        rp.pieceType = (piece.getColor() == Color.WHITE)
                                ? ReturnPiece.PieceType.WP
                                : ReturnPiece.PieceType.BP;
                    } else if (piece instanceof Rook) {
                        rp.pieceType = (piece.getColor() == Color.WHITE)
                                ? ReturnPiece.PieceType.WR
                                : ReturnPiece.PieceType.BR;
                    } else if (piece instanceof Knight) {
                        rp.pieceType = (piece.getColor() == Color.WHITE)
                                ? ReturnPiece.PieceType.WN
                                : ReturnPiece.PieceType.BN;
                    } else if (piece instanceof Bishop) {
                        rp.pieceType = (piece.getColor() == Color.WHITE)
                                ? ReturnPiece.PieceType.WB
                                : ReturnPiece.PieceType.BB;
                    } else if (piece instanceof Queen) {
                        rp.pieceType = (piece.getColor() == Color.WHITE)
                                ? ReturnPiece.PieceType.WQ
                                : ReturnPiece.PieceType.BQ;
                    } else if (piece instanceof King) {
                        rp.pieceType = (piece.getColor() == Color.WHITE)
                                ? ReturnPiece.PieceType.WK
                                : ReturnPiece.PieceType.BK;
                    }

                    // Convert the column index (0-7) into a file (a-h)
                    char fileChar = (char) ('a' + col);
                    rp.pieceFile = ReturnPiece.PieceFile.valueOf(String.valueOf(fileChar));

                    // Convert the row index (0-7) into the chess rank (1-8)
                    // Our grid row 0 is rank 8, row 7 is rank 1.
                    rp.pieceRank = 8 - row;

                    piecesList.add(rp);
                }
            }
        }
        return piecesList;
    }

    public boolean isEmpty(int row, int col) {
        // Ensure indices are within bounds.
        if (row < 0 || row >= grid.length || col < 0 || col >= grid[0].length) {
            throw new IllegalArgumentException("Row or column index is out of bounds.");
        }
        return grid[row][col] == null;
    }

    public Piece getPiece(int row, int col) {
        // Ensure indices are within bounds.
        if (row < 0 || row >= grid.length || col < 0 || col >= grid[0].length) {
            throw new IllegalArgumentException("Row or column index is out of bounds.");
        }
        return grid[row][col];
    }

    public Board simulateMove(Piece piece, Position move) {
        // Create a deep copy of the current board
        Board simulatedBoard = new Board(this);

        // Get the current position of the piece
        Position currentPos = piece.getPosition();

        // Remove the piece from its current position
        simulatedBoard.grid[currentPos.getRow()][currentPos.getCol()] = null;

        try {
            // Place the piece at the new position
            simulatedBoard.grid[move.getRow()][move.getCol()] = (Piece) piece.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }

        simulatedBoard.grid[move.getRow()][move.getCol()].setPosition(move);

        return simulatedBoard;
    }

    public ArrayList<Piece> getAllPieces() {
        ArrayList<Piece> piecesList = new ArrayList<>();
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                Piece piece = grid[row][col];
                if (piece != null) {
                    piecesList.add(piece);
                }
            }
        }
        return piecesList;
    }
    
    public Pawn getDoublePawn() {
    	return advPawn;
    }
    
    public void setDoublePawn(Pawn pawn) {
  this.advPawn = pawn;
    }
    
    public void setPiece(Position pos, Piece piece) {
        grid[pos.getRow()][pos.getCol()] = piece;
    }


} // Might need to handle if they put draw or something