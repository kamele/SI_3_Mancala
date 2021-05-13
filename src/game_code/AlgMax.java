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
            }else{
                if(score<bestScore){
                    bestScore = score;
                    bestMove = move;
                }
            }

        }

        return bestMove;
    }

    public static int getBestMove(Mankala mankala,int depth, boolean maxPlayerIsFirst){
        double startTime = System.nanoTime();
        //System.out.print("minMax - getBestMove: turn="+mankala.isFirstPlayerTurn()+"   ");
        boolean player = mankala.isFirstPlayerTurn();
        int bestMove = AlgMax.minMax(mankala,mankala.getAvalibleMoves().get(0),4, player)[0];
        //System.out.println("getBestMove: move="+bestMove);

        double finishTime = System.nanoTime();
        double processTime = finishTime-startTime;
        mankala.addProcessTime(processTime, player);

        return bestMove;
    }

    public static int[] minMax(Mankala mankala, int moveMade, int depth, boolean maxPlayerIsFirst){
        //warunek zakonczenia przeszukiwania
        if(mankala.isGameFinished() || depth==0){
            if(maxPlayerIsFirst){
                return new int[]{moveMade, mankala.getMyScore()-mankala.getOponentScore()};
            }else{
                return new int[]{moveMade, mankala.getOponentScore()-mankala.getMyScore()};
            }
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
                    bestMove = move;
                    bestScore = copyScore[1];
                }
            }else {
                if(copyScore[1]<bestScore){
                    bestMove = move;
                    bestScore = copyScore[1];
                }
            }

        }
        return new int[]{bestMove, bestScore};
    }

}
