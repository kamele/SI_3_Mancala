package game_code;

public class WellScoreHeuristic implements IScoreHeuristic{
    @Override
    public int getGemeScore(Mankala mankala, boolean maxPlayerIsFirst) {
        if(maxPlayerIsFirst){
            return  mankala.getMyScore()-mankala.getOponentScore();
        }else{
            return mankala.getOponentScore()-mankala.getMyScore();
        }
    }

    @Override
    public String getHeuristicName() {
        return "WellScoreHeuristic";
    }

}
