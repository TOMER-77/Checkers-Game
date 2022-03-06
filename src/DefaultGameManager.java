import javax.swing.*;
import java.awt.Point;
import java.util.ArrayList;

/**
 * A simple version of the checkers game. A regular piece can move one step diagonally forwards. Jumping is
 * mandatory and can be made forward or backward.
 */
public class DefaultGameManager extends GameManager {

    //Arrays of the kings each player has
    protected ArrayList<King> blackKings;
    protected ArrayList<King> whiteKings;

    /**
     * In a default case, player must jump over the opponent if he has an opportunity.
     */
    public DefaultGameManager() {
        this(true);
    }

    public DefaultGameManager(boolean mustJump) {
        super(mustJump);
        blackKings = new ArrayList<>();
        whiteKings = new ArrayList<>();
    }

    @Override
    public void initializeGame() {
        //The player with white pieces has the first move
        this.isWhitesTurn = true;

        //Adding black pieces
        whites.add(new RegularPiece(1, 0, true));
        whites.add(new RegularPiece(3, 0, true));
        whites.add(new RegularPiece(5, 0, true));
        whites.add(new RegularPiece(7, 0, true));
        whites.add(new RegularPiece(1, 2, true));
        whites.add(new RegularPiece(3, 2, true));
        whites.add(new RegularPiece(5, 2, true));
        whites.add(new RegularPiece(7, 2, true));
        whites.add(new RegularPiece(0, 1, true));
        whites.add(new RegularPiece(2, 1, true));
        whites.add(new RegularPiece(4, 1, true));
        whites.add(new RegularPiece(6, 1, true));

        for (Piece white : whites) {
            board[white.getLocation().x][white.getLocation().y] = white;
        }
        this.whitesSize = whites.size();

        //Adding white pieces
        blacks.add(new RegularPiece(0, 7, false));
        blacks.add(new RegularPiece(2, 7, false));
        blacks.add(new RegularPiece(4, 7, false));
        blacks.add(new RegularPiece(6, 7, false));
        blacks.add(new RegularPiece(0, 5, false));
        blacks.add(new RegularPiece(2, 5, false));
        blacks.add(new RegularPiece(4, 5, false));
        blacks.add(new RegularPiece(6, 5, false));
        blacks.add(new RegularPiece(1, 6, false));
        blacks.add(new RegularPiece(3, 6, false));
        blacks.add(new RegularPiece(5, 6, false));
        blacks.add(new RegularPiece(7, 6, false));

        for (Piece black : blacks) {
            board[black.getLocation().x][black.getLocation().y] = black;
        }
        this.blacksSize = blacks.size();
    }

    @Override
    public void playTurn(Piece attackingPiece, int clickedX, int clickedY, ArrayList<MoveInfo> possibleMoves) {

        /*
         * If jumping is mandatory we check if there are pieces that can jump. If there are such pieces we check if
         * the current Piece is one of them. If not the choice is invalid.
         */
        if (this.mustJump) {
            ArrayList<Piece> mustJumpList = canJumpList();
            if (!mustJumpList.isEmpty()) {
                if (!mustJumpList.contains(attackingPiece)) {
                    JOptionPane.showMessageDialog(null, "Jumping over you opponent is mandatory. You have at least " +
                                    "one piece which can do this.", "Message",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }
        }
        MoveInfo chosenMove = null;
        //Checking if the asked move is a possible move
        for (MoveInfo move : possibleMoves) {
            if (move.getLocation().x == clickedX && move.getLocation().y == clickedY) {
                chosenMove = move;
                break;
            }
        }
        //If so, the move will be made. Else, nothing happens.
        if (chosenMove != null) {
            //Updating the board and the locations of the pieces
            this.board[attackingPiece.getLocation().x][attackingPiece.getLocation().y] = null;
            this.board[clickedX][clickedY] = attackingPiece;
            attackingPiece.setLocation(new Point(clickedX, clickedY));
            Piece removedPiece = chosenMove.getRemovedPiece();
            //Removing a piece if needed and updating the board.
            if (removedPiece == null)
                shiftTurns();
            else {
                this.board[removedPiece.getLocation().x][removedPiece.getLocation().y] = null;
                if (removedPiece.isItWhite()) {
                    this.whites.remove(removedPiece);
                    --this.whitesSize;
                } else {
                    this.blacks.remove(removedPiece);
                    --this.blacksSize;
                }
                if (!attackingPiece.canJump(this))
                    shiftTurns();
            }
            //Checking if the moved piece is a regular piece that should be crowned
            if ((chosenMove.getLocation().y == this.COL_SIZE - 1 && attackingPiece.isItWhite() && !this.whiteKings.contains(attackingPiece)) ||
                    (chosenMove.getLocation().y == 0 && !attackingPiece.isItWhite() && !this.blackKings.contains(attackingPiece)))
                crown(attackingPiece);
        }
    }

    @Override
    protected void shiftTurns() {
        this.isWhitesTurn = !this.isWhitesTurn;
    }

    /**
     * Crowning a piece (promoting regular piece to a king)
     *
     * @param attackingPiece The piece which will be replaced by a king
     */
    private void crown(Piece attackingPiece) {
        King addedKing = new King(attackingPiece.getLocation().x, attackingPiece.getLocation().y,
                attackingPiece.isItWhite());
        //Putting the king on it's place on the board
        this.board[attackingPiece.getLocation().x][attackingPiece.getLocation().y] = addedKing;
        //Adding the king to the player it belongs to
        if (attackingPiece.isItWhite()) {
            this.whites.remove(attackingPiece);
            this.whites.add(addedKing);
            this.whiteKings.add(addedKing);
        } else {
            this.blacks.remove(attackingPiece);
            this.blacks.add(addedKing);
            this.blackKings.add(addedKing);
        }
    }

    @Override
    public ArrayList<Piece> canJumpList() {
        //Returning a list of pieces (of the currently playing player) which can jump over an opponent piece
        if (isWhitesTurn)
            return canJumpListHelper(this.whites);
        return canJumpListHelper(this.blacks);

    }

    @Override
    public boolean checkWinner() {
        //Checking if one of the player got no pieces left. If so, we have a winner
        if (!this.isWhitesTurn && this.blacksSize == 0)
            return true;
        if (this.isWhitesTurn && this.whitesSize == 0)
            return true;
        //Checking if the currently playing player have no possible moves at all. If so, we have a winner
        if (noMovesLeft())
            return true;
        return false;
    }

    /**
     * Return true if the currently playing player have no possible moves
     */
    private boolean noMovesLeft() {
        if (this.isWhitesTurn)
            return allMovesLeftHelper(this.whites);
        return allMovesLeftHelper(this.blacks);
    }

    /**
     * Return true if all the pieces in the array have no possible moves
     */
    private boolean allMovesLeftHelper(ArrayList<Piece> pieces) {
        for (Piece piece : pieces) {
            if (!piece.getPossibleMoves(this).isEmpty())
                return false;
        }
        return true;
    }

    /**
     * Helper method for canJumpList method.
     *
     * @param pieces The pieces of a player
     * @return a list which can jump over an opponent piece
     */
    public ArrayList<Piece> canJumpListHelper(ArrayList<Piece> pieces) {
        ArrayList<Piece> jumpable = new ArrayList<>();
        for (Piece piece : pieces) {
            if (piece.canJump(this))
                jumpable.add(piece);
        }
        return jumpable;
    }

    @Override
    public void prepareForNewRound() {
        super.prepareForNewRound();
        this.blackKings.clear();
        this.whiteKings.clear();
    }
}