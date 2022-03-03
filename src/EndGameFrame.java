import javax.swing.*;
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
        this.setSize(300,220);
        this.setTitle("We have a winner!");
        this.setResizable(false);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);

        //Adding a text message which declares who won
        JLabel winnerLabel = new JLabel(message + " Thank you for playing!", JLabel.CENTER);
        winnerLabel.setBounds(40, 20, 220, 30);
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
        restart.setBounds(100, 70, 100, 40);
        this.add(restart);

        //Button that will exit and close the game
        JButton exit = new JButton("Exit");
        exit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        exit.setBounds(120, 130, 60, 40);
        this.add(exit);
    }
}
