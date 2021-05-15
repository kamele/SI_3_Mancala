package game_code;

public interface IScoreHeuristic {
    abstract int getGemeScore(Mankala mankala, boolean maxPlayerIsFirst);
    abstract String getHeuristicName();
}
