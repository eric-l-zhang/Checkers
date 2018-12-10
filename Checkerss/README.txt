=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=
CIS 120 Game Project README
PennKey: __ez21_____
=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=

===================
=: Core Concepts :=
===================

- List the four core concepts, the features they implement, and why each feature
  is an appropriate use of the concept. Incorporate the feedback you got after
  submitting your proposal.

  1. 2D Arrays: A standard checkerboard is 8 by 8. To represent the board, 
  I used a 2D array of integers. An empty spot is represented by a 0, 
  a yellow piece is represented by a 1, a red piece is represented by a 2, 
  a yellow King is represented by a 3, and a red King is represented by a 4. 
  When the game starts, 24 slots in the 2D board array will be filled with 
  yellow and red pieces in their corresponding starting positions of the 
  regular checker game. The rest of the 2D array will be 0s. This is an
  appropriate use of the concept because a checkerboard is a grid and a 2-D array 
  directly represents the rows and columns of the grid/board.

  2. Collections: I will store each of the moves that occur during a game in a collection, 
  so that a user can undo a move. Specifically, I will store them in a list since order is important. 
  I will be using a LinkedList, because I will only ever need to add/remove from the tail of the list. 
  When a user needs to undo, it will pop the last move off of the list and move the checker, that 
  moved last, back to its original location before the move took place. I will also put the elements
  that were taken in the previous turn back onto the board. The list stores entries of type 
  UndoObject which contains two objects that represent the initial and final positions of 
  the moved checker; it also contains a list of TakenObjects that were taken in the move, so that
  they can be returned to the board after the undo button is clicked.

  3. Testing: The main state of my game is the board (2D array). I created methods that take in 
  coordinates as a parameter, and update the game accordingly. I have tested that the 2D array is 
  updated correctly and that that the available moves and jumps for a red piece, yellow piece,
  red king, and yellow king are correct. I tested the available moves of each piece for when they were 
  on an edge, in the middle of the board with no pieces surrounding them, or when there were 
  pieces in the places where they could move. I also test the available jumps of kings and pieces with varied
  number of pieces surrounding them. I tested that the move going to the opposite side 
  of the board turns the piece into the king of the same color, and that jumps get rid of the piece
  they jumped over. I have a well-designed game containing an encapsulated model that functions 
  independently of the GUI, as well as JUnit tests for this model.

  4. I/O: I used I/O to implement high scores, which fulfills the requirements: 
  1) The displayed scores is in sorted order (highest score to lowest score), 
  2) I displayed 3 scores, 3) my high scores have the player names associated with them. My file
  is organized with each value on a seperate line: p1, hs1, ps2, hs2, p3, hs3. I read in the current 
  high scores file and store the high scores and the corresponding names into my fields in the game. 
  After the game is over, I see if a player has received a higher score than the current top 3 high 
  scores; if so, I update the ordering and high scores appropriately. Then I write out the new 
  high scores to the same file after clearing it first. 


=========================
=: Your Implementation :=
=========================

- Provide an overview of each of the classes in your code, and what their
  function is in the overall game.
	My Gameboard class encapsulates the checkerboard state in the 2D array and has methods to update
	the state and check availability of moves and jumps in the board. It basically can check the any part
	of the current state of the board. My Game class allows the game to be played and incorporates the GUI
	and interactive mouse clicking, as long with creating a instance of the Gameboard object.
	The most important part of this class is the mouse clicking subclass because it calls most of the 
	functions in the Gameboard class to allow or not allow the user to move to their piece to their
	desired location. I also have Scorebox, Turnbox, and Canvas classes which are all for displaying 
	certain elements of my game. The Scorebox updates the number of pieces each color has left, the turnbox
	displays whos turn it is and displays an error if you committed one, and the canvas displays the gameboard
	that has a mouse listener. My Location class is simply just storing an x and y coordinate of a row or column
	that I want to refer to later. Instead of making a bunch of x and y variables, I used Location objects
	throughout Gameboard and Game to refer to a row and column of the board. My UndoObject class was used to
	created UndoObjects for my undo functionality in my game. I have explained the overview in
	my Collections concept. My TakenClass stores a Location and a color. It is used as an object by the 
	UndoObject to keep track of which pieces were taken in a move.

- Were there any significant stumbling blocks while you were implementing your
  game (related to your design, or otherwise)?
	
	No, not really.

- Evaluate your design. Is there a good separation of functionality? How well is
  private state encapsulated? What would you refactor, if given the chance?
	
	I think there is a good separation of functionality and my private state is encapsulated
	very well. Maybe I would combine Location and Taken Object together because they are very
	similar to each other, but they do have slightly different functionalities, so I'm not sure.


========================
=: External Resources :=
========================

- Cite any external resources (libraries, images, tutorials, etc.) that you may
  have used while implementing your game.
