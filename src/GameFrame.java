import javax.swing.JFrame;
import javax.swing.JComponent;
import java.awt.BorderLayout;

/**
 * The frame of the gui window of the game.
 */
public class GameFrame extends JFrame {

    GameFrame() {
        this.setTitle("Checkers Game");
        this.setResizable(false);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        JComponent game = new CheckersGameEngine();
        this.add(game, BorderLayout.CENTER);
        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }
}
