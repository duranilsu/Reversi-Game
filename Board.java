import java.util.ArrayList;
import java.lang.*;

public class Board {
    protected char[][] gameboard;
    private final char[] indexes2 = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H'};
    private final char[] indexes1 = {'A', 'B','C', 'D'};
    private int userColor;
    public int boardsize;


    public Board(int color, int boardsize) 
    {
        //8 x 8 gameboard
        if (boardsize == 8)
        {
            gameboard = new char[][]{
                {'_', '_', '_', '_', '_', '_', '_', '_',},
                {'_', '_', '_', '_', '_', '_', '_', '_',},
                {'_', '_', '_', '_', '_', '_', '_', '_',},
                {'_', '_', '_', 'o', 'x', '_', '_', '_',},
                {'_', '_', '_', 'x', 'o', '_', '_', '_',},
                {'_', '_', '_', '_', '_', '_', '_', '_',},
                {'_', '_', '_', '_', '_', '_', '_', '_',},
                {'_', '_', '_', '_', '_', '_', '_', '_',},
            };

        }
        else
        {
                // 4 x 4 gameboard 
            gameboard = new char[][]{
                {'_', '_', '_', '_'},
                {'_', 'o', 'x', '_'},
                {'_', 'x', 'o', '_'},
                {'_', '_', '_', '_'},
           
            };
        }
        this.boardsize = boardsize;
    
        this.userColor = color;
    }

    public Board (Board b, int boardsize)
    {
        gameboard = new char[boardsize][boardsize];
        for (int i = 0; i < boardsize; i++) 
        {
            for (int j = 0; j < boardsize; j++) 
            {
                this.gameboard[i][j] = b.gameboard[i][j];
            }
        }
        this.boardsize = boardsize;
        this.userColor = b.userColor;
    }
    /**
     *
     * @return usercolor
     */
    public int getUserColor()
     {
        return userColor;
    }

    /**
     *
     * @param player
     * @return total pieces for the given player
     */
    public int CalculatePieces(int player) {
        char ATMPlayer = player == 1 ? 'x' : 'o';
        int pieces = 0;
        for (int i = 0; i < boardsize; i++) {
            for (int j = 0; j < boardsize; j++) {
                if (gameboard[i][j] == ATMPlayer)
                    pieces++;
            }
        }
        return pieces;
    }

    /**
     *
     * @return difference between player's and AI's pieces
     */
    public int CalculateAiPieceDifference() {
        int diff = CalculatePieces(1) - CalculatePieces(2);
        return userColor == 1 ? diff : -diff;
    }
    public void printBoard() {
		for (int i = -1; i <= boardsize; i++) {
			// i is row
			if (i == -1 || i == boardsize) {

				if (boardsize == 4) {
					System.out.println("   a b c d   ");
				} else if (boardsize == 6) {
					System.out.println("   a b c d e f   ");
				} else if (boardsize == 8) {
					System.out.println("   a b c d e f g h   ");
				}
			} else {
				for (int j = 0; j < boardsize; j++) {
					// j is column
					if (boardsize == 8) {
						if (j == 0) {
							System.out.print((i + 1) + "  " + gameboard[i][j] + " ");
						} else if (j == 7) {
							System.out.print(gameboard[i][j] + "  " + (i + 1));
						} else {
							System.out.print(gameboard[i][j] + " ");
						}
					} else if (boardsize == 6) {
						if (j == 0) {
							System.out.print((i + 1) + "  " + gameboard[i][j] + " ");
						} else if (j == 5) {
							System.out.print(gameboard[i][j] + "  " + (i + 1));
						} else {
							System.out.print(gameboard[i][j] + " ");
						}
					} else {
						if (j == 0) {
							System.out.print((i + 1) + "  " + gameboard[i][j] + " ");
						} else if (j == 3) {
							System.out.print(gameboard[i][j] + "  " + (i + 1));
						} else {
							System.out.print(gameboard[i][j] + " ");
						}
					}

				}

			}
			if (i != -1) {
				// prevent extra space on the top
				System.out.println();
			}

		}

	}

/*
    public void displayBoard() {
        System.out.print("\n  ");
        for (int i = 0; i < boardsize; ++i) 
        {
            System.out.print(indexes[i] + " ");
        }
        System.out.println();
        for (int i = 0; i < boardsize; ++i)
        {
            System.out.print((i + 1) + " ");
            for (int j = 0; j < boardsize; ++j)
            {
                System.out.print(gameboard[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println();
    }
*/
    public char[] getIndex()
    {
        if (boardsize == 8)
        {
            return indexes2;
        }
        return indexes1;
    }

    /**
     *
     * @param gameboard
     * sets current gameboard as the one given
     */
    public void setGameboard(char[][] gameboard) {
        this.gameboard = gameboard;
    }


    /**
     * @param X
     * @param Y
     * @param player
     * @return True if the move is possible, false otherwise
     * also makes the move
     */

    public boolean makeMove(int X, int Y, int player) {
        char playerPiece = player == 1 ? 'x' : 'o';
        char OpponentPiece = playerPiece == 'x' ? 'o' : 'x';
        boolean validMove = false;
        //Board temp = new Board(this);
        if (gameboard[X][Y] == '_') {
            for (int i = -1; i <= 1; i++) {
                for (int j = -1; j <= 1; j++) {
                    if (i == 0 && j == 0)
                        continue;
                    boolean piecesToFlip = false, passedOpponent = false;
                    int k = 1;
                    while ((X + j * k >= 0 && X + j * k < boardsize) && (Y + i * k >= 0 && Y + i * k < boardsize)) {
                        if ((gameboard[X + j * k][Y + i * k] == '_') || (gameboard[X + j * k][Y + i * k] == playerPiece && !passedOpponent)) {
                            break;
                        }
                        if (gameboard[X + j * k][Y + i * k] == playerPiece && passedOpponent) {
                            piecesToFlip = true;
                            break;
                        } else if (gameboard[X + j * k][Y + i * k] == OpponentPiece) {
                            passedOpponent = true;
                            k++;
                        }
                    }
                    if (piecesToFlip) {

                        gameboard[X][Y] = playerPiece;

                        for (int h = 1; h <= k; h++) {
                            gameboard[X + j * h][Y + i * h] = playerPiece;
                        }

                        validMove = true;
                    }
                }
            }
        }
        return validMove;
    }

    /**
     * @param player
     * @return an  arraylist with boards
     * that contain all possible moves
     */
    public ArrayList<Board> findValidMoves(int player) {
        ArrayList<Board> moves = new ArrayList<>();
        Board b = new Board(this, boardsize);
        for (int i = 0; i < boardsize; i++) {
            for (int j = 0; j < boardsize; j++) {
                if (b.makeMove(i, j, player)) {
                    moves.add(b);
                    b = new Board(this, boardsize);
                }
            }
        }
        return moves;
    }

    /**
     *
     * boolean function
     * @return True if the game is over, false elsewhere
     */
    public boolean IsTerminal(){
        //boolean endgame = false;
        ArrayList <Board> moves1 = new ArrayList<>();
        ArrayList <Board> moves2 = new ArrayList<>();
        moves1 = findValidMoves(1);
        moves2 = findValidMoves(2);
        return moves1.isEmpty() && moves2.isEmpty();
    }

    public boolean isMovesPossible(int player){
       char playertype= (player==1)?'x':'o';

        int [][] moves = validMovesGenerate(playertype) ;
        
        for(int i =0; i<boardsize; i++){
            for(int j=0; j<boardsize; j++){
                if(moves[i][j]==1)
                return true;
            }
        }
        return false;

    }
    /*
     * displayValidMoves
     */

    /**
     *
     * @param move
     * @param player
     * @return true if the string given by the user is a valid move
     * also makes the move
     */
    public boolean placeMove(String move, int player) {
        int Y = Integer.parseInt(String.valueOf(move.charAt(0) - 96)) - 1;
        int X = Integer.parseInt(String.valueOf(move.charAt(1))) - 1;
        return  makeMove(X, Y, player) ;
    }


    /**
     *
     * @return the current gameboard
     */
    public char[][] getGameboard() {
        return gameboard;
    }

    public int[][] validMovesGenerate(char player) {

		int[][] validBoard = new int[boardsize][boardsize];

		// put 0s into the board which should store the valid moves
		// there will be 1s for valid moves and 0s for invalid moves
		for (int i = 0; i < boardsize; i++) {
			for (int j = 0; j < boardsize; j++) {
				validBoard[i][j] = 0;
			}
		}

		for (int i = 0; i < boardsize; i++) {
			for (int j = 0; j < boardsize; j++) {

				if (gameboard[i][j]==('_')) {
					// you only care about the positions on the board that are not occupied yet
					if (isValidMove(player, i, j, "")) {
						validBoard[i][j] = 1;
					}
				}

			}
		}
        return validBoard;
    }

    public boolean isValidMove(char player, int i, int j, String func) {

		// this method checks for the direction for making the valid move;

		// if i=0{
		// if j=0 e se s
		// elseif j=boardsize-1 w sw s
		// else e se s sw w
		// } .......top line taken care of
		// if i=boardsize-1{
		// if j=0 e ne n
		// elseif j=boardsize-1 w nw n
		// else e ne n nw w
		// }........bottom line taken care of
		// if j=0{
		// if i!=0 || i!=boardsize //already taken care of n ne e se s
		// }
		// if j=boardsize-1{
		// if i!=0 || i!=boardsize //already taken care of n nw w sw s
		// }

		if (i == 0) {
			if (j == 0) {
				boolean d1 = checkLineFormedPossibility(player, i, j, "e", func);
				boolean d2 = checkLineFormedPossibility(player, i, j, "se", func);
				boolean d3 = checkLineFormedPossibility(player, i, j, "s", func);
				if (d1 || d2 || d3) {
					return true;
				}
				return false;
			} else if (j == boardsize - 1) {
				boolean d1 = checkLineFormedPossibility(player, i, j, "w", func);
				boolean d2 = checkLineFormedPossibility(player, i, j, "sw", func);
				boolean d3 = checkLineFormedPossibility(player, i, j, "s", func);
				if (d1 || d2 || d3) {
					return true;
				}
				return false;
			} else {
				boolean d1 = checkLineFormedPossibility(player, i, j, "e", func);
				boolean d2 = checkLineFormedPossibility(player, i, j, "se", func);
				boolean d3 = checkLineFormedPossibility(player, i, j, "s", func);
				boolean d4 = checkLineFormedPossibility(player, i, j, "sw", func);
				boolean d5 = checkLineFormedPossibility(player, i, j, "w", func);

				if (d1 || d2 || d3 || d4 || d5) {
					return true;
				}
				return false;
			}
		}

		if (i == boardsize - 1) {
			if (j == 0) {
				boolean d1 = checkLineFormedPossibility(player, i, j, "e", func);
				boolean d2 = checkLineFormedPossibility(player, i, j, "ne", func);
				boolean d3 = checkLineFormedPossibility(player, i, j, "n", func);
				if (d1 || d2 || d3) {
					return true;
				}
				return false;
			} else if (j == boardsize - 1) {
				boolean d1 = checkLineFormedPossibility(player, i, j, "w", func);
				boolean d2 = checkLineFormedPossibility(player, i, j, "nw", func);
				boolean d3 = checkLineFormedPossibility(player, i, j, "n", func);
				if (d1 || d2 || d3) {
					return true;
				}
				return false;
			} else {
				boolean d1 = checkLineFormedPossibility(player, i, j, "e", func);
				boolean d2 = checkLineFormedPossibility(player, i, j, "ne", func);
				boolean d3 = checkLineFormedPossibility(player, i, j, "n", func);
				boolean d4 = checkLineFormedPossibility(player, i, j, "nw", func);
				boolean d5 = checkLineFormedPossibility(player, i, j, "w", func);

				if (d1 || d2 || d3 || d4 || d5) {
					return true;
				}
				return false;
			}
		}

		if (j == boardsize - 1) {
			if (i != 0 || i != boardsize) {
				// n nw w sw s
				boolean d1 = checkLineFormedPossibility(player, i, j, "n", func);
				boolean d2 = checkLineFormedPossibility(player, i, j, "nw", func);
				boolean d3 = checkLineFormedPossibility(player, i, j, "w", func);
				boolean d4 = checkLineFormedPossibility(player, i, j, "sw", func);
				boolean d5 = checkLineFormedPossibility(player, i, j, "s", func);

				if (d1 || d2 || d3 || d4 || d5) {
					return true;
				}
				return false;
			}
		}

		if (j == 0) {
			if (i != 0 || i != boardsize) {
				//
				boolean d1 = checkLineFormedPossibility(player, i, j, "n", func);
				boolean d2 = checkLineFormedPossibility(player, i, j, "ne", func);
				boolean d3 = checkLineFormedPossibility(player, i, j, "e", func);
				boolean d4 = checkLineFormedPossibility(player, i, j, "se", func);
				boolean d5 = checkLineFormedPossibility(player, i, j, "s", func);

				if (d1 || d2 || d3 || d4 || d5) {
					return true;
				}
				return false;
			}
		}

		// taken care of all corner cases now you dont have to worry
		boolean d1 = checkLineFormedPossibility(player, i, j, "n", func);
		boolean d2 = checkLineFormedPossibility(player, i, j, "nw", func);
		boolean d3 = checkLineFormedPossibility(player, i, j, "w", func);
		boolean d4 = checkLineFormedPossibility(player, i, j, "sw", func);
		boolean d5 = checkLineFormedPossibility(player, i, j, "s", func);
		boolean d6 = checkLineFormedPossibility(player, i, j, "se", func);
		boolean d7 = checkLineFormedPossibility(player, i, j, "e", func);
		boolean d8 = checkLineFormedPossibility(player, i, j, "ne", func);

		if (d1 || d2 || d3 || d4 || d5 || d6 || d7 || d8) {
			return true;
		}
		return false;

	}

	public boolean checkLineFormedPossibility(char player, int i, int j, String direction, String function) {

		char opponentColour = getOpponentPlayer(player);
		ArrayList<String> flippedMoves = new ArrayList<>();

		if (direction.equals("n")) {
			i--;

			if (gameboard[i][j]==(player)) {
				return false;// first move i see my player
			}

			while (gameboard[i][j]==(opponentColour)) {
				boolean reachEdgebeforeUpdate = edgeOfTheBoard(i, j);
				if (reachEdgebeforeUpdate) { // do not keep going
					break;
				}
				i--;// keep going in that direction
				boolean reachEdge = edgeOfTheBoard(i, j);
				if (reachEdge) { // do not keep going
					break;
				}

			}

			// not see opponent anymore
			if (gameboard[i][j]==(player)) {
				return true;
			} else {
				return false;
			}
		} else if (direction.equals("ne")) {
			i--;
			j++;
			if (gameboard[i][j]==(player)) {
				return false;
			}

			while (gameboard[i][j]==(opponentColour)) {
				boolean reachEdgebeforeUpdate = edgeOfTheBoard(i, j);
				if (reachEdgebeforeUpdate) { // do not keep going
					break;
				}
				i--;
				j++;
				//System.out.println("i and j values idhar hai " + i + " " + j);
				boolean reachEdge = edgeOfTheBoard(i, j);
				if (reachEdge) { // do not keep going
					break;
				}
			}

			// not see opponent anymore
			if (gameboard[i][j]==(player)) {
				return true;
			} else {
				return false;
			}
		} else if (direction.equals("e")) {
			j++;
			if (gameboard[i][j]==(player)) {
				return false;
			}

			while (gameboard[i][j]==(opponentColour)) {
				boolean reachEdgebeforeUpdate = edgeOfTheBoard(i, j);
				if (reachEdgebeforeUpdate) { // do not keep going
					break;
				}
				j++;
				boolean reachEdge = edgeOfTheBoard(i, j);
				if (reachEdge) { // do not keep going
					break;
				}
			}

			// not see opponent anymore
			if (gameboard[i][j]==(player)) {
				return true;
			} else {
				return false;
			}
		} else if (direction.equals("se")) {
			i++;
			j++;
			if (gameboard[i][j]==(player)) {
				return false;
			}

			while (gameboard[i][j]==(opponentColour)) {
				if (function.equals("flip")) {
					gameboard[i][j] = player;
				}
				boolean reachEdgebeforeUpdate = edgeOfTheBoard(i, j);
				if (reachEdgebeforeUpdate) { // do not keep going
					break;
				}
				i++;
				j++;
				boolean reachEdge = edgeOfTheBoard(i, j);
				if (reachEdge) { // do not keep going
					break;
				}
			}

			// not see opponent anymore
			if (gameboard[i][j]==(player)) {
				return true;
			} else {
				return false;
			}
		} else if (direction.equals("s")) {
			i++;

			while (gameboard[i][j]==(opponentColour)) {
				boolean reachEdgebeforeUpdate = edgeOfTheBoard(i, j);
				if (reachEdgebeforeUpdate) { // do not keep going
					break;
				}
				i++;
				boolean reachEdge = edgeOfTheBoard(i, j);
				if (reachEdge) { // do not keep going
					break;
				}
			}

			// not see opponent anymore
			if (gameboard[i][j]==(player)) {
				return true;
			} else {
				return false;
			}
		} else if (direction.equals("sw")) {
			i++;
			j--;
			if (gameboard[i][j]==(player)) {
				return false;
			}

			while (gameboard[i][j]==(opponentColour)) {
				boolean reachEdgebeforeUpdate = edgeOfTheBoard(i, j);
				if (reachEdgebeforeUpdate) { // do not keep going
					break;
				}
				i++;
				j--;
				boolean reachEdge = edgeOfTheBoard(i, j);
				if (reachEdge) { // do not keep going
					break;
				}
			}

			// not see opponent anymore
			if (gameboard[i][j]==(player)) {
				return true;
			} else {
				return false;
			}
		} else if (direction.equals("w")) {
			j--;
			if (gameboard[i][j]==(player)) {
				return false;
			}

			while (gameboard[i][j]==(opponentColour)) {
				if (function.equals("flip")) {
					gameboard[i][j] = player;
				}
				boolean reachEdgebeforeUpdate = edgeOfTheBoard(i, j);
				if (reachEdgebeforeUpdate) { // do not keep going
					break;
				}
				j--;
				boolean reachEdge = edgeOfTheBoard(i, j);
				if (reachEdge) { // do not keep going
					break;
				}
			}

			// not see opponent anymore
			if (gameboard[i][j]==(player)) {
				return true;
			} else {
				return false;
			}

		} else {// nw
			i--;
			j--;
			if (gameboard[i][j]==(player)) {
				return false;
			}

			while (gameboard[i][j]==(opponentColour)) {
				boolean reachEdgebeforeUpdate = edgeOfTheBoard(i, j);
				if (reachEdgebeforeUpdate) { // do not keep going
					break;
				}
				i--;
				j--;
				boolean reachEdge = edgeOfTheBoard(i, j);
				if (reachEdge) { // do not keep going
					break;
				}
			}

			// not see opponent anymore
			if (gameboard[i][j]==(player)) {
				return true;
			} else {
				return false;
			}
		}
	}

    public char getOpponentPlayer(char player){
       return (player == 'x') ? 'o': 'x';
    }

    public int getOpponentPlayerNo (int player)
    {
        return (player == 1) ? 2: 1;
    }

    public boolean edgeOfTheBoard(int i, int j) {
		if (i <= 0 || i >= boardsize - 1) {
			return true;
		}
		if (j <= 0 || j >= boardsize - 1) {
			return true;
		}
		return false;
	}

    // 	public ArrayList<String> helpFunctionToShowValidMoves(String player) {
	// 	ArrayList<String> possibleMoves = new ArrayList<String>();
	// 	int[][] boardWithValidMoves = validMovesGenerate(player);

	// 	for (int i = 0; i < sizeOfBoard; i++) {
	// 		for (int j = 0; j < sizeOfBoard; j++) {
	// 			// it is a valid move
	// 			if (boardWithValidMoves[i][j] == 1) {
	// 				String r = getRowValueFromInt(i);
	// 				String c = getColValueFromInt(j);
	// 				String value = c + r;
	// 				possibleMoves.add(value);
	// 			}
	// 		}
	// 	}

	// 	return possibleMoves;
	// }
    // public String getRowValueFromInt(int i) {
	// // i=0 is row 1 and so on
	// i++;
	// return i + "";
	// }
    // public String getColValueFromInt(int j) {
	// // j=0 is a...j=1 is b
	// char result = (char) (j + 97);
	// return result + "";
	// }


}
