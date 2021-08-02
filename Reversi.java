import java.util.ArrayList;
import java.util.Random;

public class Reversi {
    private int maxLevel;


    public Reversi(int lvl){
        this.maxLevel = lvl;
    }


    public void setMaxLevel(int maxLevel) {
        this.maxLevel = maxLevel;
    }

    /**
     *
     * @param depth
     * @param board
     * @param maximizingPlayer
     * @param score1
     * @param score2
     * @return max or min score possible depending on the player
     */

    // public int random(int depth, Board board){
    //     //get all possible valid moves for that player
    //     ArrayList<Board> moves=board.findValidMoves(board.getUserColor()%2+1);

    //     Random rand = new Random();
    //     int random_integer = rand.nextInt(moves.size()-1);//will be the index of the random move to be made by the computer
    //     //update the state of the board by making that random move
        

    // }


    public int mini(int depth, Board board, boolean maximizingPlayer) 
    {
        if (depth == 0 || board.IsTerminal()) {
            return board.CalculateAiPieceDifference();
        }

        ArrayList<Board> moves = board.findValidMoves(board.getUserColor()%2+1);

        if (moves.size() == 0) {
            if (board.boardsize == 8)
            {
                return maximizingPlayer ? 64 : -64;
            }
            return maximizingPlayer ? 16 : -16;
            
        }

        if (maximizingPlayer) {
            int top = 0;
            int maxEval = -99999;
            for (int i = 0; i < moves.size(); i++) {
                int eval = mini(depth - 1, moves.get(i), false);
                maxEval = Math.max(maxEval, eval);
                top = i;
            }
            //marked 
            if (depth == 8) board.setGameboard(moves.get(top).getGameboard());
                return maxEval;

        } 
        else
        {
            int miniVal = 99999;
            for (Board b2 : moves){
                int eval =  mini(depth - 1, b2, true);
                miniVal = Math.min(miniVal, eval); 
               
            }
            return miniVal;
        }


    }
    public int minimax(int depth, Board board, boolean maximizingPlayer, int score1, int score2) {
        if (depth > maxLevel || board.IsTerminal()) {
            return board.CalculateAiPieceDifference();
        }
        // get all the possible valid moves //
        // let it be moves//
        ArrayList<Board> moves = board.findValidMoves(board.getUserColor()%2+1);

        if (moves.size() == 0) {
            if (board.boardsize == 8)
            {
                return maximizingPlayer ? 64 : -64;
            }
            return maximizingPlayer ? 16 : -16;
            
        }

        if (maximizingPlayer) {
            int top = 0;
            for (int i = 0; i < moves.size(); i++) {
                int score = minimax(depth + 1, moves.get(i), false, score1, score2);

                if (score > score1) {
                    score1 = score;
                    top = i;
                }
                if (score1 > score2)
                    break;
            }
            if (depth == 1)
                board.setGameboard(moves.get(top).getGameboard());
            return score1;
        }
        else {
            for (Board b2 : moves){
                int score = minimax(depth + 1, b2, true, score1, score2);

                if (score < score2)
                    score2 = score;

                if (score1 > score2)
                    break;
            }
        return score2;
        }
    }
}
