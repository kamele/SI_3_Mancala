package game_code;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("mankalaBoard.fxml"));
        primaryStage.setTitle("Mankala");
        primaryStage.setScene(new Scene(root, 710, 500));
        primaryStage.show();
    }


    public static void main(String[] args) {
        testsEtap3();

        launch(args);



    }

    public static void testsEtap3(){
        System.out.println("Start Etap 3 tests");
        Mankala mankala = new Mankala();
        mankala.setFirstPlayerTurn(false);

        System.out.println("---Wylicz najlepszy ruch------------");
        System.out.println("Tura: "+mankala.isFirstPlayerTurn());
        System.out.println("Stan gry przed: ");
        mankala.printGameState();
        System.out.println("Liczba ruchów pierwszego gracza: "+mankala.getFirstPlayerMovesCount()+" Czas:"+mankala.getFirstPlayerProcessTime());
        System.out.println("Liczba ruchów drugiego gracza: "+mankala.getSecondPlayerMovesCount()+" Czas:"+mankala.getSecondPlayerProcessTime());
        System.out.println();

        System.out.println("Wylicz ruch alfa-beta: "+AlfaBeta.getBestMove(mankala,4, mankala.isFirstPlayerTurn()));
        System.out.println("Liczba ruchów pierwszego gracza: "+mankala.getFirstPlayerMovesCount()+" Czas:"+mankala.getFirstPlayerProcessTime());
        System.out.println("Liczba ruchów drugiego gracza: "+mankala.getSecondPlayerMovesCount()+" Czas:"+mankala.getSecondPlayerProcessTime());
        System.out.println();


        System.out.println("Wylicz ruch min-max : "+AlgMax.getBestMove(mankala,4, mankala.isFirstPlayerTurn()));
        System.out.println("Liczba ruchów pierwszego gracza: "+mankala.getFirstPlayerMovesCount()+" Czas:"+mankala.getFirstPlayerProcessTime());
        System.out.println("Liczba ruchów drugiego gracza: "+mankala.getSecondPlayerMovesCount()+" Czas:"+mankala.getSecondPlayerProcessTime());
        System.out.println();

    }





    public static void testsEtap2(){
        System.out.println("Start Etap 2 tests");
        Mankala mankala = new Mankala();
        System.out.println("---Losowy ruch------------");
        System.out.println("Tura: "+mankala.isFirstPlayerTurn());
        System.out.println("Stan gry przed: ");
        mankala.printGameState();
        System.out.println("Losowy ruch 1 : "+mankala.getRandomMove());
        System.out.println("Losowy ruch 2 : "+mankala.getRandomMove());
        System.out.println("Losowy ruch 3 : "+mankala.getRandomMove());
        System.out.println("Losowy ruch 4 : "+mankala.getRandomMove());
    }

    public static void makeMoveAi(Mankala mankala, int holeIndex){
        if(!mankala.isIndexPermitted(holeIndex)){
            System.out.println("Ruch niedozwolonyu"+holeIndex);
            //return;
        }

        System.out.println("wykonanj ruch. Tura:"+mankala.isFirstPlayerTurn());
        mankala.makeMove(holeIndex);
        System.out.println("wykonano ruch. Tura:"+mankala.isFirstPlayerTurn());

        if( !mankala.isGameFinished()){
            System.out.println("wywołaj kolejny. Tura:"+mankala.isFirstPlayerTurn());
            int aiMove = AlgMax.bestMove(mankala, mankala.isFirstPlayerTurn());
            makeMoveAi(mankala, aiMove);

        }else{
            if(mankala.getMyScore()>= mankala.getOponentScore()){
                if(mankala.getMyScore()==mankala.getOponentScore())
                    System.out.println("Tie "+mankala.getMyScore()+"  :  "+mankala.getOponentScore());
                else
                    System.out.println("Win "+mankala.getMyScore()+"  :  "+mankala.getOponentScore());
            }else{
                System.out.println("Lost "+mankala.getMyScore()+"  :  "+mankala.getOponentScore());
            }
        }
    }

    public static void tests(){
        Mankala mankala = new Mankala();
        System.out.println("1   "+mankala.getOppositeIndex(0));
        System.out.println("2   "+mankala.getOppositeIndex(1));
        System.out.println("3   "+mankala.getOppositeIndex(2));
        System.out.println("4   "+mankala.getOppositeIndex(3));
        System.out.println("5   "+mankala.getOppositeIndex(4));
        System.out.println("6   "+mankala.getOppositeIndex(5));
        System.out.println("7   "+mankala.getOppositeIndex(6));
        System.out.println("8   "+mankala.getOppositeIndex(7));
        System.out.println("9   "+mankala.getOppositeIndex(8));
        System.out.println("10  "+mankala.getOppositeIndex(9));
        System.out.println("11  "+mankala.getOppositeIndex(10));
        System.out.println("12  "+mankala.getOppositeIndex(11));
        System.out.println("13  "+mankala.getOppositeIndex(12));
        System.out.println("14  "+mankala.getOppositeIndex(13));
        System.out.println("15  "+mankala.getOppositeIndex(14));
    }
}
