package game_code;

import java.util.ArrayList;

public class AlgMax {

    public static int bestMove(Mankala mankala){
        ArrayList<Integer> moves =mankala.getAvalibleMoves();
        int bestMove = moves.get(0);
        int bestScore = mankala.isFirstPlayerTurn() ? Integer.MIN_VALUE : Integer.MAX_VALUE;
        for (Integer move: moves ) {
            Mankala copyMankala = new Mankala(mankala);
            copyMankala.makeMove(move);
            int score = copyMankala.getMyScore()-copyMankala.getOponentScore();
            if(mankala.isFirstPlayerTurn()){
                if(score>bestScore){
                    bestScore = score;
                    bestMove = move;
                }
            }else {
                if(score<bestScore){
                    bestScore = score;
                    bestMove = move;
                }
            }

        }

        return bestMove;
    }


}
