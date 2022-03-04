import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

/**
 * Initializes the game ,draws the board and reacts to the player's mouse clicks.
 */
public class CheckersGameEngine extends JComponent {
    static final int ROW_SIZE = 8;
    static final int COL_SIZE = 8;
    static final int TURN_INDICATOR_HEIGHT = 20;

    //TODO
    private ScheduledExecutorService timer = Executors.newScheduledThreadPool(1);

    //Paths for images that will be used for the animation
    static final String BOARD_PATH = "images/board.png";
    static final String CHOSEN_PATH = "images/chosen.png";
    static final String POSSIBLE_MOVE_PATH = "images/possibleMove.png";

    private MouseAdapter mouseAdapter;
    //Height and width of each square on the board.
    private int squareWidth;
    private int squareHeight;
    //The last piece a the player clicked on and it's possible moves.
    private Piece chosenPiece;
    private ArrayList<MoveInfo> possibleMoves;
    //Images that will be used for the animation
    private ImageIcon boardIMG;
    private ImageIcon chosenPieceIMG;
    private ImageIcon possibleMoveIMG;
    //Setting the "rules" for the game, managing the turns, decides who win the game.
    private GameManager gameManager;
    //The turn indicator will display which player currently plays
    private TurnIndicator turnIndicator;


    public CheckersGameEngine() {
        mouseAdapter = new MyMouseAdapter();
        this.addMouseListener(mouseAdapter);
        chosenPiece = null;
        possibleMoves = null;
        boardIMG = new ImageIcon(BOARD_PATH);
        chosenPieceIMG = new ImageIcon(CHOSEN_PATH);
        possibleMoveIMG = new ImageIcon(POSSIBLE_MOVE_PATH);
        squareWidth = boardIMG.getIconWidth() / ROW_SIZE;
        squareHeight = boardIMG.getIconHeight() / COL_SIZE;
        gameManager = new DefaultGameManager(true);
        turnIndicator = new TurnIndicator();

        this.add(turnIndicator);
        this.setPreferredSize(new Dimension(boardIMG.getIconWidth(), boardIMG.getIconHeight() + TURN_INDICATOR_HEIGHT));
        this.setVisible(true);
        this.requestFocusInWindow();

        //Initializing new game
        gameManager.initializeGame();
        drawGameBoard();
    }

    /**
     * Drawing the game board to the screen
     */
    public void drawGameBoard() {
        this.turnIndicator.updateTurn();
        this.repaint();

    }

    int i = 0;

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        //Painting the game board.
        boardIMG.paintIcon(this, g, 0, 0);
        //Highlighting the piece that was chosen by a player.
        if (chosenPiece != null) {
            chosenPieceIMG.paintIcon(this, g, chosenPiece.getLocation().x * this.squareWidth,
                    chosenPiece.getLocation().y * this.squareHeight);
            for (MoveInfo move : this.possibleMoves) {
                possibleMoveIMG.paintIcon(this, g, move.getLocation().x * this.squareWidth,
                        move.getLocation().y * this.squareHeight);
            }
        }
        //Displaying the pieces.
        displayPieceArray(this.gameManager.getWhites(), g);
        displayPieceArray(this.gameManager.getBlacks(), g);
    }

    /**
     * Drawing each of the game pieces to the board
     */
    private void displayPieceArray(ArrayList<Piece> pieces, Graphics g) {
        ImageIcon tmpPieceImage;
        Point tmpPieceLocation;

        for (Piece piece : pieces) {
            tmpPieceImage = new ImageIcon(piece.getImgPath());
            tmpPieceLocation = piece.getLocation();
            tmpPieceImage.paintIcon(this, g, tmpPieceLocation.x * squareWidth, tmpPieceLocation.y * squareHeight);
        }
    }

    /**
     * Private class to handle the mouse events issued by the players
     */
    private class MyMouseAdapter extends MouseAdapter {
        @Override
        public void mouseClicked(MouseEvent e) {
        }

        @Override
        public void mousePressed(MouseEvent e) {
            //Getting the exact square the the player clicked on
            int clickedSquareX = e.getX() / squareWidth;
            int clickedSquareY = e.getY() / squareHeight;
            Piece clickedPiece = gameManager.getCurrentPlayerPiece(clickedSquareX, clickedSquareY);

            //Selecting a piece to move
            if (chosenPiece == null && clickedPiece != null) {
                chosenPiece = clickedPiece;
                possibleMoves = chosenPiece.getPossibleMoves(gameManager);
                if (possibleMoves.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Piece has no possible moves", "Message",
                            JOptionPane.ERROR_MESSAGE);
                    chosenPiece = null;
                }
            }

            //Can't move to a square which has a piece of the same player, canceling the previous choice of the player
            else if (chosenPiece != null && clickedPiece != null)
                chosenPiece = null;

                //Playing a turn if the move choice of the player is valid
            else if (chosenPiece != null && clickedPiece == null) {
                //Playing a turn and after that resetting the chosen piece and it's possible moves.
                gameManager.playTurn(chosenPiece, clickedSquareX, clickedSquareY, possibleMoves);
                chosenPiece = null;
                possibleMoves = null;
                //Checking if the current player won the game
                if (gameManager.checkWinner() == true) {
                    //If so, an end-game frame is shown
                    EndGameFrame endFrame = new EndGameFrame("Blacks won!", gameManager, CheckersGameEngine.this);
                    if (!gameManager.isWhitesTurn) {
                        endFrame = new EndGameFrame("Whites won!", gameManager, CheckersGameEngine.this);
                    }
                    endFrame.setVisible(true);
                }
            }
            drawGameBoard();
        }

        @Override
        public void mouseDragged(MouseEvent e) {
        }

        @Override
        public void mouseReleased(MouseEvent e) {
        }

        @Override
        public void mouseWheelMoved(MouseWheelEvent e) {
        }
    }

    ;


    /**
     * Private class of an label that indicates which player currently playing.
     */
    private class TurnIndicator extends JLabel {
        TurnIndicator() {
            this.updateTurn();
            this.setBounds(0, boardIMG.getIconHeight(), boardIMG.getIconWidth(),
                    TURN_INDICATOR_HEIGHT);
            this.setHorizontalAlignment(SwingConstants.CENTER);
        }

        /**
         * Updating the color of the player which currently playing
         */
        public void updateTurn() {
            if (gameManager.isWhitesTurn)
                this.setText("White's turn");
            else
                this.setText("Black's turn");
        }
    }
};