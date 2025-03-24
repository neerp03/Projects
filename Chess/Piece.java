package chess;

import java.util.ArrayList;

public abstract class Piece implements Cloneable {
    protected Color color;
    protected Position position;
    
    public Piece(Color color, Position position) {
        this.color = color;
        this.position = position;
    }
    
    public Color getColor() {
        return color;
    }
    
    public Position getPosition() {
        return position;
    }
    
    public void setPosition(Position pos) {
        this.position = pos;
    }
    
     //Returns a list of legal moves based solely on the piece's movement rules.
     
    public abstract ArrayList<Position> getLegalMoves(Board board);
    
    
     //Returns a deep copy of the piece.
    
    public abstract Piece copy();
    
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
