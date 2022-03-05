import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * An frame that will be shown at the end of a game and will give the players to chose between exiting the game of
 * or playing another game.
 */
public class EndGameFrame extends JFrame {

    public EndGameFrame(String message, GameManager game, CheckersGameEngine engine) {
        //Setting the frame properties
        this.setLayout(null);
        this.setSize(220,220);
        this.setTitle("We have a winner!");
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setLayout(new GridLayout(3,1));

        //Adding a text message which declares who won
        JLabel winnerLabel = new JLabel(message + " Thank you for playing!", JLabel.CENTER);
        this.add(winnerLabel);

        //Button that allows the players to start a new game
        JButton restart = new JButton("Play again");
        restart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                game.prepareForNewRound();
                game.initializeGame();
                engine.drawGameBoard();
                dispose();
            }
        });
        this.add(restart);

        //Button that will exit and close the game
        JButton exit = new JButton("Exit");
        exit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        this.add(exit);

        this.pack();
    }
}
