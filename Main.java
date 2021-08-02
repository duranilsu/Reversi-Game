import java.util.*;

public class Main
{

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("REVERSI GAME by Godbless Chille, Nilsu Duran, Anisha Bhattacharya");
        System.out.println("Welcome to Reversi! You will be playing" +
                " vs an AI bot\n" +
                "Choose the colour of your choice, remember black goes first!");
                System.out.print("\nEnter the colour of your choice ('x' for black or 'o' for white): ");
                String choice = scanner.nextLine();
       // int depth = Integer.parseInt(scanner.nextLine());
        System.out.print("\nPlease type in size of board ('4' or '8'): ");
        int boardsize = Integer.parseInt(scanner.nextLine());
        System.out.println("\nChoose algorithm mode (Press '1' for Minimax, '2' for Heuristic Minimax with Alpha-Beta Pruning): ");
        int gamemode = Integer.parseInt(scanner.nextLine());

        if (gamemode == 1) {
            System.out.println("Minimax running...");
        } else {
            System.out.println("Heuristic Alpha-Beta Minimax running...");
        }

        Reversi AI = new Reversi(boardsize);
        int playerNo;
        int AIno;
        int nowPlaying;
        switch (choice) 
        {
            case "x":
                System.out.println("\nYou selected black and chose to go first, select one of the possible moves: ");
                playerNo = 1;
                AIno = 2;
                break;
            case "o": 
                System.out.println("You selected white, therefore AI starts: ");
                playerNo = 2;
                AIno = 1;
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + choice);
        }
        Board b = new Board(playerNo, boardsize);
        nowPlaying = 1 ;
        b.printBoard();
        int scorePlayer = 2;
        int scoreAI = 2;
        while (!b.IsTerminal())
        {
            //only ask for moves if the player has possible valid moves to go to
            if (nowPlaying == playerNo)
            {
                boolean flag = b.isMovesPossible(playerNo);
                if (flag == false) System.out.println("\nYou have no moves, switching to AI....\n");
                if(b.isMovesPossible(playerNo) && (flag==true))
                {
                    System.out.println("Enter your move: (e.g. a6)");
                    choice = scanner.nextLine();
                    while (!b.placeMove(choice, playerNo))
                    {
                        System.out.println("Incorrect move please choose one of the valid moves shown");
                        choice = scanner.nextLine();
                    }
                }
                else
                {
                    //make the computer play cause the player has no possible moves
                    //nowPlaying=b.getOpponentPlayerNo(playerNo);
                    
                    if(b.isMovesPossible(AIno))
                    {
                        if(gamemode == 1) {
                            AI.mini(8, b, true);
                        } else {
                            AI.minimax(1,b,true,AIno,playerNo);
                        }
                        
                    }
                    else
                    {
                        break;
                    }
                }
               

            }
            else
            {
                if(b.isMovesPossible(AIno))
                {
                    if(gamemode == 1) {
                        AI.mini(8, b, true);
                    } else {
                        AI.minimax(1,b,true,AIno,playerNo);
                    }
                    
                }

                
            }
            b.printBoard();
            //scoreAI = (AIno == 1) ? b.CalculatePieces(1) : b.CalculatePieces(2);
            scorePlayer = b.CalculatePieces(playerNo);
            scoreAI = b.CalculatePieces(AIno);
            System.out.println("Current score: " + scorePlayer + " "+ " "+scoreAI);
            nowPlaying = nowPlaying % 2 + 1;
        }

        if (scoreAI > scorePlayer ) {
            System.out.println("The winner is AI with score: " + scorePlayer + " "+ " "+scoreAI);
        }
        else  if (scoreAI < scorePlayer ){
            System.out.println("The winner is you with score: " + scorePlayer + " "+ " "+scoreAI);
        }
        else{
            System.out.println("Tie with score: " + scorePlayer + " "+ " "+scoreAI);
        }
    }
}
