package game_code;

public class WellAndNumberOfHolesScoreHeuristic implements IScoreHeuristic{
    int wellImportance = 1;
    int numberImportance = 1;

    @Override
    public int getGemeScore(Mankala mankala, boolean maxPlayerIsFirst) {
        IScoreHeuristic wellHueristic = new WellScoreHeuristic();
        IScoreHeuristic numHeuristic = new NumberOfHolesScoreHeuristic();
        return wellImportance*wellHueristic.getGemeScore(mankala,maxPlayerIsFirst)+numberImportance*numHeuristic.getGemeScore(mankala,maxPlayerIsFirst);
    }


    @Override
    public String getHeuristicName() {
        return "WellAndNumberOfHolesScoreHeuristic";
    }


}
