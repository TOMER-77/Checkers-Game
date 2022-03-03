import java.awt.Point;
import java.util.ArrayList;

/**
 * Representing a piece in a checkers game.A piece can go forwards but jump forward and backward.
 */
public abstract class Piece {
    private Point location;
    protected String imgPath;
    private boolean isWhite;

    public Piece(int x, int y, boolean isWhite) {
        this.location = new Point(x, y);
        this.imgPath = null;
        this.isWhite = isWhite;
    }

    public void setLocation(Point location) {
        this.location = location;
    }

    public Point getLocation() {
        return location;
    }

    public String getImgPath() {
        return imgPath;
    }

    /**
     * returning true if the piece is white. Else, false is returned.
     */
    public boolean isItWhite() {
        return this.isWhite;
    }

    /**
     * @param game The manager of the game in which the piece participates
     * @return List of the possible moves for the piece
     */
    public abstract ArrayList<MoveInfo> getPossibleMoves(GameManager game);

    /**
     * return true if the coordinate is in the bounds of the game board.
     */
    protected boolean boundsCheck(int x, int y) {
        if (x < 0 || x >= GameManager.ROW_SIZE)
            return false;
        if (y < 0 || y >= GameManager.COL_SIZE)
            return false;
        return true;
    }

    /**
     * @param game The manager of the game in which the piece participates
     * @return true if the piece can jump over an opponent piece of false if else.
     */
    protected abstract boolean canJump(GameManager game);
}
