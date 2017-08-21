# The Old Lady Game

"The Old Lady Game" is how "Noughts-And-Crosses" is called in Brazil ("Jogo da Velha").

This is an implementation of that game for a coding challenge in Clojure.

## Task description
Your task: write a game of tic-tac-toe (noughts and crosses). In case the game is new to you,
the rules can be found here: https://en.wikipedia.org/wiki/Tic-tac-toe.
Other than the rules of tic-tac-toe there are no restrictions on what you produce. Take it in
whatever direction best allows you to show us what you’re particularly interested in, and what
you can do. Some possible ideas you could try:

* Write a rich interactive frontend to show off your React skills (if you’re more of a backend
  dev, a text interface is fine too.)
* Show us your skills with SQL by designing a database to store the history of games.
* Build an AI player that plays the optimal move in any situation.
* Once you’re done with the 3x3 board, extend the game to 4x4, or NxN.

We’d prefer to see one idea executed well than many ideas executed poorly.
Technologies we like to see include React on the frontend and Clojure on the backend - but
don’t feel restricted to these.

## Task completion

The chosen extension was to extend the game to NxN. An upper limt has been set for a board size of 100, but the logic supports much higher dimensions.

## Compiling / Building

You can build the project with `lein uberjar`. The jar file will be generated under
the subdirectory `target/uberjar/`

## Usage

You should provide an integer number that will determine the board size.

    $ java -jar the-old-lady-game-0.1.0-standalone.jar [args]

## Examples

Regular noughts-and-crosses game, dimension 3:

    $ java -jar the-old-lady-game-0.1.0-standalone.jar 3

Try a harder game with dimension 5:

    $ java -jar the-old-lady-game-0.1.0-standalone.jar 5

Once the program is running, you should enter a valid board position (visually indicated by numbers inside square brackets e.g. ``"[4]"``). To end the game, enter word `quit`.
