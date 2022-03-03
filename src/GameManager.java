import java.util.ArrayList;

/**
 * A class that defines the rules of the game. It is abstract in order to enable creating different variations of
 * the checkers game. The board size is 8x8.
 */
public abstract class GameManager {
    static final int ROW_SIZE = 8;
    static final int COL_SIZE = 8;
    //Paths for images that will be used for the animation
    static final String WHITE_PIECE_PATH = "images\\whitePiece.png";
    static final String WHITE_KING_PATH = "images\\whiteKing.png";
    static final String BLACK_PIECE_PATH = "images\\blackPiece.png";
    static final String BLACK_KING_PATH = "images\\blackKing.png";

    protected boolean mustJump; //true if jumping over the opponent is mandatory (if possible)
    protected boolean isWhitesTurn; //true if the current turn is of the white player
    int blacksSize;
    int whitesSize;
    protected ArrayList<Piece> blacks;
    protected ArrayList<Piece> whites;
    protected Piece[][] board;


    /**
     * In a default case, player must jump over the opponent if he has an opportunity.
     */
    public GameManager() {
        this(true);
    }

    public GameManager(boolean mustJump) {
        this.board = new Piece[ROW_SIZE][COL_SIZE];
        this.blacks = new ArrayList<>();
        this.whites = new ArrayList<>();
        this.blacksSize = 0;
        this.whitesSize = 0;
        this.isWhitesTurn = true;
        this.mustJump = mustJump;
    }

    public ArrayList<Piece> getWhites() {
        return whites;
    }

    public ArrayList<Piece> getBlacks() {
        return blacks;
    }

    public boolean isWhitesTurn() {
        return isWhitesTurn;
    }

    public Piece[][] getBoard() {
        return board;
    }

    public boolean isMustJump() {
        return mustJump;
    }

    /**
     * Initializes a new game
     */
    public abstract void initializeGame();

    /**
     * Getting a piece of the current player with the same x,y location
     */
    public Piece getCurrentPlayerPiece(int x, int y) {
        if (isWhitesTurn)
            return getPiece(x, y, true);
        return getPiece(x, y, false);
    }

    /**
     * Getting a piece of the opponent of the current player with the same x,y location
     */
    public Piece getOpponentPiece(int x, int y) {
        if (isWhitesTurn)
            return getPiece(x, y, false);
        return getPiece(x, y, true);
    }

    /**
     * Getting a piece with the same x,y location if it is from the color specified by isWhite (true=white, false=black)
     */
    private Piece getPiece(int x, int y, Boolean isWhite) {
        Piece askedPiece = board[x][y];
        if (askedPiece == null)
            return null;
        if ((isWhite == true && askedPiece.isItWhite() == true) ||
                (isWhite == false && askedPiece.isItWhite() == false))
            return askedPiece;
        return null;
    }

    /**
     * Playing a turn of the current playes
     *
     * @param attackingPiece The piece chosen by the current player
     * @param clickedX       The x of the square that the piece should be moved
     * @param clickedY       The y of the square that the piece should be moved
     * @param possibleMoves  List of possible moves for the piece
     */
    public abstract void playTurn(Piece attackingPiece, int clickedX, int clickedY, ArrayList<MoveInfo> possibleMoves);

    /**
     * Shifting the game turn from one player to another
     */
    protected abstract void shiftTurns();

    /**
     * Returning a list of pieces of the current player that can jump over an opponent piece
     */
    public abstract ArrayList<Piece> canJumpList();

    /**
     * Checking if the current player won the game
     *
     * @return true if the current player won ,else false is returned
     */
    public abstract boolean checkWinner();

    /**
     * Removing all remaining pieces from the board and setting the whites to begin.
     */
    public void prepareForNewRound() {
        this.blacks.clear();
        this.blacksSize = 0;
        this.whites.clear();
        this.whitesSize = 0;
        this.board = new Piece[ROW_SIZE][COL_SIZE];
        this.isWhitesTurn = true;
    }
}
