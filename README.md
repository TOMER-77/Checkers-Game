## About
A simple checkers game implemented in Java. I made the game in order to practice Java programming and get a first introduction to Java Swing framework.

The program supports a chckers game of Two human players. At each turn the current player chooses a piece to move. The Chosen piece is highlited in red and the possible positions to where the piece can move are highlited in green. At the bottom of the windows threre is an indication for the player who is currently playing. 
To cancel a choice of a piece the player can click on it again or click on any other square on the board which is not highlited in green.
If a chosen piece has no possible moves or the move that the player made is invalid a message will be shown to the screen.
At the end of the game a message indicating the winner will be displayed and the players will be able to chose if they wish to start another game of exit the game.
## Classes

 - The **GameManager** abstract class is in charge of the game rules.  The **DefaultGameManager** is a basic implemantation of the GameManager with basic rules such as: jumping over an opponent piece is mandatory, the white player begin the game, each player have 12 pieces.
 The GameManager can be extended by other class in order to implement other rules and variations of the game.
 - The  **CheckersGameEngine** class in in charge of displaying the board and pieces. It includes a private class which handles the mouse clicks of the players.
 - The **Piece** abstract class is representing the pieces of the Game. The **RegularPiece** and **King** implementing the abstract class. The abstract class can be extended to add other types of pieces to the game (which can be included in other game managers). The RegularPiece and King class can be extended in order to change the moving abilities of the pieces.

## Running the game

 1. Navigate to the src folder.
 2. Compile with `javac *.java`
 3. Run with `java CheckersGameMain`

## Screenshots from the game



