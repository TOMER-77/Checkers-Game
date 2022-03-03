import java.awt.*;
import java.util.ArrayList;

/**
 * Representing a king piece in checkers game. A king can move forward and backward any distance along unblocked
 * diagonals.
 */
public class King extends Piece {
    //Paths for images that will be used for the animation
    static final String WHITE_KING_PATH = "images\\whiteKing.png";
    static final String BLACK_KING_PATH = "images\\blackKing.png";

    public King(int x, int y, boolean isWhite) {
        super(x, y, isWhite);
        if (isWhite)
            this.imgPath = WHITE_KING_PATH;
        else
            this.imgPath = BLACK_KING_PATH;
    }

    @Override
    public ArrayList<MoveInfo> getPossibleMoves(GameManager game) {
        ArrayList<MoveInfo> possibleMoves = new ArrayList<>();
        int pieceX = this.getLocation().x;
        int pieceY = this.getLocation().y;
        Piece[][] board = game.getBoard();
        boolean mustJump = game.isMustJump();
        boolean ableToJump = canJump(game);
        //Will indicate if the king jumped over an opponent piece
        booleanWrapper hasJumped = new booleanWrapper(false);
        //The opponent piece that was jumped over
        Piece jumpedPiece = null;
        int i = 1;
        //down-right direction, Checking while the square is in the bounds of the board
        while (boundsCheck(pieceX + i, pieceY + i)) {
            if (possibleMovesHelper(game, possibleMoves, mustJump, pieceX + i, pieceY + i, hasJumped,
                    ableToJump, jumpedPiece)) {
                //continuing two squares forward if a jump was made at some iteration and updating the jumped piece.
                ++i;
                jumpedPiece = possibleMoves.get(possibleMoves.size() - 1).getRemovedPiece();
            }
            //Breaking the loop if we encounter a piece that we can't jump over and blocking the movement
            else if (board[pieceX + i][pieceY + i] != null)
                break;
            ++i;
        }

        //up-right direction, Checking while the square is in the bounds of the board
        hasJumped.setValue(false);
        jumpedPiece = null;
        i = 1;
        while (boundsCheck(pieceX + i, pieceY - i)) {
            if (possibleMovesHelper(game, possibleMoves, mustJump, pieceX + i, pieceY - i, hasJumped,
                    ableToJump, jumpedPiece)) {
                //continuing two squares forward if a jump was made at some iteration and updating the jumped piece.
                ++i;
                jumpedPiece = possibleMoves.get(possibleMoves.size() - 1).getRemovedPiece();
            }
            //Breaking the loop if we encounter a piece that we can't jump over and blocking the movement
            else if (board[pieceX + i][pieceY - i] != null)
                break;
            ++i;
        }

        //down-left direction, Checking while the square is in the bounds of the board
        hasJumped.setValue(false);
        jumpedPiece = null;
        i = 1;
        while (boundsCheck(pieceX - i, pieceY + i)) {
            if (possibleMovesHelper(game, possibleMoves, mustJump, pieceX - i, pieceY + i, hasJumped,
                    ableToJump, jumpedPiece)) {
                //continuing two squares forward if a jump was made at some iteration and updating the jumped piece.
                ++i;
                jumpedPiece = possibleMoves.get(possibleMoves.size() - 1).getRemovedPiece();
            } else if (board[pieceX - i][pieceY + i] != null)
                break;
            //Breaking the loop if we encounter a piece that we can't jump over and blocking the movement
            ++i;
        }

        //up-left direction, Checking while the square is in the bounds of the board
        hasJumped.setValue(false);
        jumpedPiece = null;
        i = 1;
        while (boundsCheck(pieceX - i, pieceY - i)) {
            if (possibleMovesHelper(game, possibleMoves, mustJump, pieceX - i, pieceY - i, hasJumped,
                    ableToJump, jumpedPiece)) {
                //continuing two squares forward if a jump was made at some iteration and updating the jumped piece.
                ++i;
                jumpedPiece = possibleMoves.get(possibleMoves.size() - 1).getRemovedPiece();
            }
            //Breaking the loop if we encounter a piece that we can't jump over and blocking the movement
            else if (board[pieceX - i][pieceY - i] != null)
                break;
            ++i;
        }
        return possibleMoves;
    }

    /**
     * @param game          The game in which the king piece participates
     * @param possibleMoves A list of possible moves for the king
     * @param mustJump      True if jumping is mandatory (when possible). If not it is false
     * @param newX          The x value of the position that the king might be moved to
     * @param newY          The y value of the position that the king might be moved to
     * @param hasJumped     True if the king already jump over a piece in the checked direction
     * @param ableToJump    True if there exists an opponent piece that the king can jump over. False there isn't
     * @param jumpedPiece   If a piece was already jumped in this direction it will point to this piece. If there is
     *                      no such piece it will hold the value null.
     * @return True if a jump was made in this specific move. False if a jump wasn't made.
     */
    private boolean possibleMovesHelper(GameManager game, ArrayList<MoveInfo> possibleMoves, Boolean mustJump, int newX,
                                        int newY, booleanWrapper hasJumped, boolean ableToJump, Piece jumpedPiece) {
        Piece[][] board = game.getBoard();

        //If the kings already jumped over the opponent he can't continue jumping over another piece
        if (hasJumped.getValue() == true && board[newX][newY] != null) {
            return false;
        }
        //If the king already jumped over a piece it can land on free squares afterwards
        if (hasJumped.getValue() == true && board[newX][newY] == null) {
            possibleMoves.add(new MoveInfo(new Point(newX, newY), jumpedPiece));
            return false;
        }
        //Checking a case where the king has to jump over an opponent piece
        else if (hasJumped.getValue() == false && board[newX][newY] != null) {
            //Can't jump over a piece of his own color
            if (game.getOpponentPiece(newX, newY) == null)
                return false;
            int currentX = this.getLocation().x;
            int currentY = this.getLocation().y;

            //Executing the jump according to it's direction
            //down-right
            if (currentX < newX && currentY < newY) {
                if (boundsCheck(newX + 1, newY + 1) && board[newX + 1][newY + 1] == null) {
                    addJump(newX + 1, newY + 1, hasJumped, jumpedPiece, possibleMoves, board[newX][newY]);
                    jumpedPiece = possibleMoves.get(possibleMoves.size() - 1).getRemovedPiece();
                }
                //A case where the jump is blocked and the king can't continue
                else
                    return false;
            }

            //down-left
            else if (currentX > newX && currentY < newY) {
                if (boundsCheck(newX - 1, newY + 1) && board[newX - 1][newY + 1] == null) {
                    addJump(newX - 1, newY + 1, hasJumped, jumpedPiece, possibleMoves, board[newX][newY]);
                }
                //A case where the jump is blocked and the king can't continue
                else
                    return false;
            }

            //up-right
            else if (currentX < newX && currentY > newY) {
                if (boundsCheck(newX + 1, newY - 1) && board[newX + 1][newY - 1] == null) {
                    addJump(newX + 1, newY - 1, hasJumped, jumpedPiece, possibleMoves, board[newX][newY]);
                }
                //A case where the jump is blocked and the king can't continue
                else
                    return false;
            }

            //up-left
            else {
                if (boundsCheck(newX - 1, newY - 1) && board[newX - 1][newY - 1] == null) {
                    addJump(newX - 1, newY - 1, hasJumped, jumpedPiece, possibleMoves, board[newX][newY]);
                }
                //A case where the jump is blocked and the king can't continue
                else
                    return false;
            }
            return true;
        }

        /*
         * If the king hasn't jumped yet but it also can't jump at all or he is not obligated to jump then he
         * can move to a free square (a case where the square isn't free has already been checked)
         */
        else if ((!ableToJump && mustJump) || (!mustJump)) {
            possibleMoves.add(new MoveInfo(new Point(newX, newY)));
            return false;
        }
        return false;
    }


    /**
     * Adding a possible move with a jump to the possibleMoves list
     *
     * @param newX           x value of the new position
     * @param newY           y value of the new position
     * @param hasJumped      Indicates if the king jumped over the opponent in the curently checked diagonal. Will be
     *                       changed to true.
     * @param jumpedPiece    The piece that was jumped over
     * @param possibleMoves  The list of possible moves for the king
     * @param newJumpedPiece The piece that was jumped over
     */
    private void addJump(int newX, int newY, booleanWrapper hasJumped, Piece jumpedPiece,
                         ArrayList<MoveInfo> possibleMoves, Piece newJumpedPiece) {
        jumpedPiece = newJumpedPiece;
        hasJumped.setValue(true);
        possibleMoves.add(new MoveInfo(new Point(newX, newY), jumpedPiece));

    }

    @Override
    protected boolean canJump(GameManager game) {
        int pieceX = this.getLocation().x;
        int pieceY = this.getLocation().y;
        Piece[][] board = game.getBoard();
        int i = 1;
        //down-right direction, Checking while the square is in the bounds of the board
        while (boundsCheck(pieceX + i, pieceY + i)) {
            if (canJumpHelper(game, pieceX + i, pieceY + i))
                return true;
            //If there is a piece that we can't jump over we can't continue in this direction.
            if (board[pieceX + i][pieceY + i] != null)
                break;
            ++i;
        }

        //up-right direction, Checking while the square is in the bounds of the board
        i = 1;
        while (boundsCheck(pieceX + i, pieceY - i)) {
            if (canJumpHelper(game, pieceX + i, pieceY - i))
                return true;
            //If there is a piece that we can't jump over we can't continue in this direction.
            if (board[pieceX + i][pieceY - i] != null)
                break;
            ++i;
        }

        //down-left direction, Checking while the square is in the bounds of the board
        i = 1;
        while (boundsCheck(pieceX - i, pieceY + i)) {
            if (canJumpHelper(game, pieceX - i, pieceY + i))
                return true;
            //If there is a piece that we can't jump over we can't continue in this direction.
            if (board[pieceX - i][pieceY + i] != null)
                break;
            ++i;
        }

        //up-left direction, Checking while the square is in the bounds of the board
        i = 1;
        while (boundsCheck(pieceX - i, pieceY - i)) {
            if (canJumpHelper(game, pieceX - i, pieceY - i))
                return true;
            //If there is a piece that we can't jump over we can't continue in this direction.
            if (board[pieceX - i][pieceY - i] != null)
                break;
            ++i;
        }
        return false;
    }

    /**
     * Helper method to canJump
     *
     * @param game The game in which the king piece participates
     * @param newX The x value of the piece might be jumped over
     * @param newY The y value of the piece might be jumped over
     * @return true if a jump can be made. Else, false is returned.
     */
    private boolean canJumpHelper(GameManager game, int newX, int newY) {
        int currentX = this.getLocation().x;
        int currentY = this.getLocation().y;
        Piece[][] board = game.getBoard();

        /*
         * If there is an opponent piece in the (newX,newY) coordinate we check if the king can jump over it. We
         * first check from which direction the king came from (checking if the newX/Y if bigger/lesser than the
         * currentX/Y values) and according to that we check if the piece might be jumped over
         */
        if (game.getOpponentPiece(newX, newY) != null) {
            //down-right
            if (currentX < newX && currentY < newY) {
                if (boundsCheck(newX + 1, newY + 1) && board[newX + 1][newY + 1] == null) {
                    return true;
                }
            }

            //down-left
            else if (currentX > newX && currentY < newY) {
                if (boundsCheck(newX - 1, newY + 1) && board[newX - 1][newY + 1] == null) {
                    return true;
                }
            }

            //up-right
            else if (currentX < newX && currentY > newY) {
                if (boundsCheck(newX + 1, newY - 1) && board[newX + 1][newY - 1] == null) {
                    return true;
                }
            }

            //up-left
            else {
                if (boundsCheck(newX - 1, newY - 1) && board[newX - 1][newY - 1] == null) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Private class which simulates passing boolean values "by reference"
     */
    private class booleanWrapper {
        private boolean value;

        public booleanWrapper(boolean value) {
            this.value = value;
        }

        public void setValue(boolean value) {
            this.value = value;
        }

        public boolean getValue() {
            return this.value;
        }
    }
}
