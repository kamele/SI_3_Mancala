package game_code;

import java.util.ArrayList;

public class AlgMax {

    public static int bestMove(Mankala mankala,boolean maxPlayerIsFirst ){
        ArrayList<Integer> moves =mankala.getAvalibleMoves();

        int bestMove = moves.get(0);
        int bestScore = mankala.isFirstPlayerTurn()==maxPlayerIsFirst ? Integer.MIN_VALUE : Integer.MAX_VALUE;
        for (Integer move: moves ) {
            Mankala copyMankala = new Mankala(mankala);
            copyMankala.makeMove(move);
            int score = copyMankala.getMyScore()-copyMankala.getOponentScore();
            if(mankala.isFirstPlayerTurn()==maxPlayerIsFirst){
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

    public static int getBestMove(Mankala mankala, boolean maxPlayerIsFirst){
        System.out.print("getBestMove: turn="+mankala.isFirstPlayerTurn()+"   ");
        int bestMove = AlgMax.minMax(mankala,mankala.getAvalibleMoves().get(0),4, mankala.isFirstPlayerTurn())[0];
        System.out.println("getBestMove: move="+bestMove);
        return bestMove;
    }

    public static int[] minMax(Mankala mankala, int moveMade, int depth, boolean maxPlayerIsFirst){
        //warunek zakonczenia przeszukiwania
        if(mankala.isGameFinished() || depth==0){
            return new int[]{moveMade, mankala.getMyScore()-mankala.getOponentScore()};
        }

        ArrayList<Integer> moves = mankala.getAvalibleMoves();
        int bestMove = moves.get(0);
        int bestScore = mankala.isFirstPlayerTurn()==maxPlayerIsFirst ? Integer.MIN_VALUE : Integer.MAX_VALUE;

        for (Integer move: moves ) {
            Mankala copyMankala = new Mankala(mankala);
            copyMankala.makeMove(move);
            int[] copyScore = minMax(copyMankala, move,depth-1, maxPlayerIsFirst);

            if(mankala.isFirstPlayerTurn()==maxPlayerIsFirst){
                if(copyScore[1]>bestScore){
                    bestMove = copyScore[0];
                    bestScore = copyScore[1];
                }
            }else {
                if(copyScore[1]<bestScore){
                    bestMove = copyScore[0];
                    bestScore = copyScore[1];
                }
            }

        }
        return new int[]{bestMove, bestScore};
    }

}
