package game_code;

public class Mankala {
    private int[] gameState = new int[]{4,4,4,4,4,4,0,4,4,4,4,4,4,0};
    private boolean firstPlayerTurn = true;
    private int firstPlayerWell =6;
    private int secondPlayerWell =13;
    private boolean isGameFinished = false;

    //konstruktory
    public Mankala(){//defoult state
        gameState = new int[]{4,4,4,4,4,4,0,4,4,4,4,4,4,0};
        firstPlayerTurn = true;
    }
    public Mankala(int [] state, boolean turn){
        gameState = state;
        firstPlayerTurn = turn;
    }

    public void makeMove(int holeIndex) {//throws Exception {
        System.out.println("makeMove("+holeIndex+")");
        if(!isIndexPermitted(holeIndex)) System.out.println("Nie spełnione warunki wyboru - niedozwolona akcja");//throw new Exception("Nie spełnione warunki wyboru studni");

        //rozdawanie kamieni
        int stonesNumber = gameState[holeIndex];
        gameState[holeIndex]=0;

        int currentHoleIndex=holeIndex+1;
        int lastStoneIndex = currentHoleIndex;

        while (stonesNumber>0){
            lastStoneIndex = currentHoleIndex;

            //dolozenie kamienia
            gameState[currentHoleIndex]++;
            stonesNumber--;

            currentHoleIndex++;

            //zapętlenie po planszy
            if(currentHoleIndex>secondPlayerWell){
                currentHoleIndex=0;
            }

            //omijanie studni przeciwnika
            if(firstPlayerTurn && currentHoleIndex==secondPlayerWell){
                currentHoleIndex=0;
            }
            if((!firstPlayerTurn) && currentHoleIndex==firstPlayerWell){
                currentHoleIndex++;
            }


            //printGameState();
        }



        //zabieranie kamków przeciwnika jesli ostatni w swoim pustym
        if(isYourSiteIndex(lastStoneIndex)){//po naszej stronie
            //System.out.println("Byl"+gameState[lastStoneIndex]);
            if(gameState[lastStoneIndex]==1){//wczesniej dolek pusty byl
                //System.out.println("Byl pusty"+lastStoneIndex);
                int oppositeIndex = getOppositeIndex(lastStoneIndex);
                if(gameState[oppositeIndex]>0) {
                    if (firstPlayerTurn) {
                        gameState[firstPlayerWell] += gameState[oppositeIndex];
                        gameState[oppositeIndex] = 0;

                        gameState[firstPlayerWell] += gameState[lastStoneIndex];
                        gameState[lastStoneIndex] = 0;
                    } else {
                        gameState[secondPlayerWell] += gameState[oppositeIndex];
                        gameState[oppositeIndex] = 0;

                        gameState[secondPlayerWell] += gameState[lastStoneIndex];
                        gameState[lastStoneIndex] = 0;
                    }
                }
            }
        }

        //powtórzona tura po wrzuceniu do studni
        if(firstPlayerTurn){
            if(lastStoneIndex==firstPlayerWell){
                firstPlayerTurn=true;
            }else{
                firstPlayerTurn=false;
            }
        }else{
            if(lastStoneIndex==secondPlayerWell){
                firstPlayerTurn=false;
            }else{
                firstPlayerTurn=true;
            }
        }


    }

    //metody pomocnicze
    public boolean isIndexPermitted(int holeIndex){
        if( gameState[holeIndex]==0)    return false;
        return isYourSiteIndex(holeIndex);
    }

    public boolean isYourSiteIndex(int holeIndex){
        if(holeIndex==firstPlayerWell || holeIndex == secondPlayerWell)
            return false;

        if(firstPlayerTurn){
            if(0<=holeIndex &&holeIndex<firstPlayerWell) return true;
            else return false;
        }else{
            if(firstPlayerWell<holeIndex &&holeIndex<secondPlayerWell) return true;
            else return false;
        }
    }

    public int getOppositeIndex(int holeIndex){
        if(holeIndex>secondPlayerWell || holeIndex<0) return -1;
        int [] oppositeSideIndexTable = new int[]{12,11,10,9,8,7,13,5,4,3,2,1,0,6};
        return oppositeSideIndexTable[holeIndex];
    }

    public void printGameState(){

        System.out.println("Ruch: "+firstPlayerTurn);
        for (int n:gameState) {
            System.out.print(n+", ");
        }
        System.out.println();
    }
    //setery , gettery
    public int[] getGameState() {
        return gameState;
    }
    public void setGameState(int[] gameState) {
        this.gameState = gameState;
    }

    public boolean isFirstPlayerTurn() {
        return firstPlayerTurn;
    }
    public void setFirstPlayerTurn(boolean firstPlayerTurn) {
        this.firstPlayerTurn = firstPlayerTurn;
    }


}