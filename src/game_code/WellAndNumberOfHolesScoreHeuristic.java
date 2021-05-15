package game_code;

public class WellAndNumberOfHolesScoreHeuristic implements IScoreHeuristic{
    @Override
    public int getGemeScore(Mankala mankala, boolean maxPlayerIsFirst) {
        if(maxPlayerIsFirst){
            int numberOfHoles = 0;
            int[] gameState = mankala.getGameState();
            for(int i=0; i<mankala.getFirstPlayerWell();i++){
                if(gameState[i]!=0) numberOfHoles++;
            }

            return  mankala.getMyScore()-mankala.getOponentScore()+numberOfHoles;
        }else{
            int numberOfHoles = 0;
            int[] gameState = mankala.getGameState();
            for(int i=mankala.getFirstPlayerWell()+1; i<mankala.getSecondPlayerWell();i++){
                if(gameState[i]!=0) numberOfHoles++;
            }
            return mankala.getOponentScore()-mankala.getMyScore()+numberOfHoles;
        }
    }


    @Override
    public String getHeuristicName() {
        return "WellAndNumberOfHolesScoreHeuristic";
    }


}
