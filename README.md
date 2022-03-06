## About
A simple checkers game implemented in Java. I made the game in order to practice Java programming and get a first introduction to Java Swing framework.

The program supports a checkers game of Two human players. At each turn the current player chooses a piece to move. The Chosen piece is highlighted in red and the possible positions to where the piece can move are highlighted in green. At the bottom of the window, there is an indication of the player who is currently playing. To cancel a choice of a piece the player can click on it again or click on any other square on the board which is not highlighted in green. If a chosen piece has no possible moves or the move that the player made is invalid a message will be shown to the screen. At the end of the game, a message indicating the winner will be displayed and the players will be able to choose if they wish to start another game or exit the game.
## Classes

 - The **GameManager** abstract class is in charge of the game rules.  The **DefaultGameManager** is a basic implementation of the GameManager with basic rules such as: jumping over an opponent piece is mandatory, the white player begins the game, each player has 12 pieces. The GameManager can be extended by other classes to implement other rules and variations of the game.
 - The  **CheckersGameEngine** class is in charge of displaying the board and pieces. It includes a private class that handles the mouse clicks of the players.
 - The **Piece** abstract class is representing the pieces of the Game. The **RegularPiece** and **King** implement the abstract class. The abstract class can be extended to add other types of pieces to the game (which can be included in other game managers). The RegularPiece and King class can be extended in order to change the moving abilities of the pieces.

## Running the game

 1. Download the game files or clone the project with the command: `https://github.com/TOMER-77/Checkers-Game.git`
 2. Navigate to the src folder.
 3. Compile with `javac *.java`
 4. Run with `java CheckersGameMain`

## Screenshots from the game

<img width="389" alt="pieceExample" src="https://user-images.githubusercontent.com/92518651/156904072-a12c8563-aa95-4494-b4ed-6474de8d50c9.png">

<img width="391" alt="kingExample" src="https://user-images.githubusercontent.com/92518651/156904075-8e352487-4840-44dc-9930-1a589ebc8d89.png">

