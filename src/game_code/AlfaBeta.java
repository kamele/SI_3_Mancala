package game_code;
import java.util.ArrayList;

public class AlfaBeta {

    public static int getBestMove(Mankala mankala, int depth, boolean maxPlayerIsFirst, IScoreHeuristic heuristic){
        double startTime = System.nanoTime();
        System.out.print("alfaBeta - getBestMove: turn="+mankala.isFirstPlayerTurn()+"   ");
        boolean player = mankala.isFirstPlayerTurn();

        int bestMove = AlfaBeta.alfaBeta(mankala,mankala.getAvalibleMoves().get(0),4, Integer.MIN_VALUE,Integer.MAX_VALUE,   mankala.isFirstPlayerTurn(), heuristic)[0];
        System.out.println("getBestMove: move="+bestMove);


        double finishTime = System.nanoTime();
        double processTime = finishTime-startTime;
        mankala.addProcessTime(processTime, player);

        return bestMove;
    }

    public static int[] alfaBeta(Mankala mankala, int moveMade, int depth, int alfa, int beta, boolean maxPlayerIsFirst, IScoreHeuristic heuristic){
        //warunek zakonczenia przeszukiwania
        if(mankala.isGameFinished() || depth==0){
            return new int[]{moveMade, heuristic.getGemeScore(mankala,maxPlayerIsFirst)};
        }

        ArrayList<Integer> moves = mankala.getAvalibleMoves();
        int bestMove = moves.get(0);
        int bestScore = mankala.isFirstPlayerTurn()==maxPlayerIsFirst ? Integer.MIN_VALUE : Integer.MAX_VALUE;

        for (Integer move: moves ) {
            Mankala copyMankala = new Mankala(mankala);
            copyMankala.makeMove(move);
            int[] copyScore = alfaBeta(copyMankala, move,depth-1, alfa, beta, maxPlayerIsFirst, heuristic);

            if(mankala.isFirstPlayerTurn()==maxPlayerIsFirst){
                //ustawianie alfy
                if(copyScore[1]>alfa){
                    alfa=copyScore[1];
                }
                if(copyScore[1]>bestScore){
                    bestMove = move;
                    bestScore = copyScore[1];
                }
            }else {
                //ustawianie bety
                if(copyScore[1]<beta){
                    beta=copyScore[1];
                }
                if(copyScore[1]<bestScore){
                    bestMove = move;
                    bestScore = copyScore[1];
                }
            }

            //alfa-beta
            if(beta<=alfa){
                break;
            }
        }
        return new int[]{bestMove, bestScore};
    }

}
