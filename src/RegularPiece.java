import java.awt.Point;
import java.util.ArrayList;

/**
 * Representing a regular piece ("men") in a checkers game.
 */
public class RegularPiece extends Piece {
    //Paths for images that will be used for the animation
    static final String WHITE_PIECE_PATH = "images/whitePiece.png";
    static final String BLACK_PIECE_PATH = "images/blackPiece.png";

    public RegularPiece(int x, int y, boolean isWhite) {
        super(x, y, isWhite);
        if (isWhite)
            this.imgPath = WHITE_PIECE_PATH;
        else
            this.imgPath = BLACK_PIECE_PATH;
    }

    @Override
    public ArrayList<MoveInfo> getPossibleMoves(GameManager game) {
        ArrayList<MoveInfo> possibleMoves = new ArrayList<>();
        int pieceX = this.getLocation().x;
        int pieceY = this.getLocation().y;
        Piece[][] board = game.getBoard();
        //down-right direction
        if (boundsCheck(pieceX + 1, pieceY + 1) && game.getOpponentPiece(pieceX + 1, pieceY + 1) != null) {
            if (boundsCheck(pieceX + 2, pieceY + 2) && board[pieceX + 2][pieceY + 2] == null)
                possibleMoves.add(new MoveInfo(new Point(pieceX + 2, pieceY + 2), board[pieceX + 1][pieceY + 1]));
        }
        //down-left direction
        if (boundsCheck(pieceX - 1, pieceY + 1) && game.getOpponentPiece(pieceX - 1, pieceY + 1) != null) {
            if (boundsCheck(pieceX - 2, pieceY + 2) && board[pieceX - 2][pieceY + 2] == null)
                possibleMoves.add(new MoveInfo(new Point(pieceX - 2, pieceY + 2), board[pieceX - 1][pieceY + 1]));
        }
        //up-right direction
        if (boundsCheck(pieceX + 1, pieceY - 1) && game.getOpponentPiece(pieceX + 1, pieceY - 1) != null) {
            if (boundsCheck(pieceX + 2, pieceY - 2) && board[pieceX + 2][pieceY - 2] == null)
                possibleMoves.add(new MoveInfo(new Point(pieceX + 2, pieceY - 2), board[pieceX + 1][pieceY - 1]));
        }
        //up-left direction
        if (boundsCheck(pieceX - 1, pieceY - 1) && game.getOpponentPiece(pieceX - 1, pieceY - 1) != null) {
            if (boundsCheck(pieceX - 2, pieceY - 2) && board[pieceX - 2][pieceY - 2] == null)
                possibleMoves.add(new MoveInfo(new Point(pieceX - 2, pieceY - 2), board[pieceX - 1][pieceY - 1]));
        }

        //if the player must (and can) jump we won't check moves where he doesn't jump over the opponent.
        if (!possibleMoves.isEmpty() && game.mustJump == true)
            return possibleMoves;

        //if current player is white check moves downwards, if black check moves upwards.
        if (this.isItWhite()) {
            if (boundsCheck(pieceX - 1, pieceY + 1) && board[pieceX - 1][pieceY + 1] == null)
                possibleMoves.add(new MoveInfo(new Point(pieceX - 1, pieceY + 1)));
            if (boundsCheck(pieceX + 1, pieceY + 1) && board[pieceX + 1][pieceY + 1] == null)
                possibleMoves.add(new MoveInfo(new Point(pieceX + 1, pieceY + 1)));
        } else {
            if (boundsCheck(pieceX - 1, pieceY - 1) && board[pieceX - 1][pieceY - 1] == null)
                possibleMoves.add(new MoveInfo(new Point(pieceX - 1, pieceY - 1)));
            if (boundsCheck(pieceX + 1, pieceY - 1) && board[pieceX + 1][pieceY - 1] == null)
                possibleMoves.add(new MoveInfo(new Point(pieceX + 1, pieceY - 1)));
        }
        return possibleMoves;
    }

    @Override
    protected boolean canJump(GameManager game) {
        int pieceX = this.getLocation().x;
        int pieceY = this.getLocation().y;
        Piece[][] board = game.getBoard();
        //down-right direction
        if (boundsCheck(pieceX + 1, pieceY + 1) && game.getOpponentPiece(pieceX + 1, pieceY + 1) != null) {
            if (boundsCheck(pieceX + 2, pieceY + 2) && board[pieceX + 2][pieceY + 2] == null)
                return true;
        }
        //down-left direction
        if (boundsCheck(pieceX - 1, pieceY + 1) && game.getOpponentPiece(pieceX - 1, pieceY + 1) != null) {
            if (boundsCheck(pieceX - 2, pieceY + 2) && board[pieceX - 2][pieceY + 2] == null)
                return true;
        }
        //up-right direction
        if (boundsCheck(pieceX + 1, pieceY - 1) && game.getOpponentPiece(pieceX + 1, pieceY - 1) != null) {
            if (boundsCheck(pieceX + 2, pieceY - 2) && board[pieceX + 2][pieceY - 2] == null)
                return true;
        }
        //up-left direction
        if (boundsCheck(pieceX - 1, pieceY - 1) && game.getOpponentPiece(pieceX - 1, pieceY - 1) != null) {
            if (boundsCheck(pieceX - 2, pieceY - 2) && board[pieceX - 2][pieceY - 2] == null)
                return true;
        }
        return false;
    }
}