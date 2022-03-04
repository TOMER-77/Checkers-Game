/**
 * A class which initializes the program.
 */
public class CheckersGameMain {
    private GameFrame gui;

    public static void main(String[] args) {
        CheckersGameMain checkersGame = new CheckersGameMain();
        checkersGame.gui = new GameFrame();
        //checkersGame.gui.setResizable(false);
        checkersGame.gui.setBounds(0, 0, 1000, 1000);
        //checkersGame.gui.pack();
        checkersGame.gui.setVisible(true);
    }
}
