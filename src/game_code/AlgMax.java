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

    public static int getBestMove(Mankala mankala){
        int bestMove = AlgMax.minMax(mankala,mankala.getAvalibleMoves().get(0),4)[0];
        System.out.println("getBestMove: move="+bestMove);
        return bestMove;
    }

    public static int[] minMax(Mankala mankala, int moveMade, int depth){
        System.out.print("minMax: move="+moveMade+"  depth="+depth);
        //warunek zakonczenia przeszukiwania
        if(mankala.isGameFinished() || depth==0){
            int s=mankala.getMyScore()-mankala.getOponentScore();
            System.out.println("    zwraca "+moveMade+"  wynik="+s);
            return new int[]{moveMade, mankala.getMyScore()-mankala.getOponentScore()};
        }else{
            System.out.println();
        }

        ArrayList<Integer> moves =mankala.getAvalibleMoves();
        int bestMove = moves.get(0);
        int bestScore = mankala.isFirstPlayerTurn() ? Integer.MIN_VALUE : Integer.MAX_VALUE;

        for (Integer move: moves ) {
            Mankala copyMankala = new Mankala(mankala);
            copyMankala.makeMove(move);
            //int score = copyMankala.getMyScore()-copyMankala.getOponentScore();
            int[] copyScore = minMax(copyMankala, move,depth-1);

            if(mankala.isFirstPlayerTurn()){
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
