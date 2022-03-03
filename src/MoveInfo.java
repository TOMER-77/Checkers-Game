import java.awt.Point;

/**
 * A info class about a move in the checkers game
 */
public class MoveInfo {
    //The location a piece will be moved to
    private Point location;
    //A piece that was jumped over in this move. Will be null if there is no such piece
    private Piece removedPiece;

    public MoveInfo(Point location, Piece removedPiece) {
        this.location = location;
        this.removedPiece = removedPiece;
    }

    /**
     * A move that doesn't includes a jump
     */
    public MoveInfo(Point location) {
        this(location, null);
    }

    public Point getLocation() {
        return this.location;
    }

    public Piece getRemovedPiece() {
        return this.removedPiece;
    }
}


