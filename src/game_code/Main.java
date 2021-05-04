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
        primaryStage.setScene(new Scene(root, 710, 400));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);

        System.out.println("Start Main");
        Mankala mankala = new Mankala();
        //mankala.setFirstPlayerTurn(false);

        System.out.println("tworzenie");

//        for (int i = 0; i < 100; i++) {
//            int aiMove = AlgMax.getBestMove(mankala);//, mankala.isFirstPlayerTurn());
//            mankala.makeMove(aiMove);
//            System.out.print(i+") "+aiMove+" ");
//            mankala.printGameState();
//        }
        makeMoveAi(mankala, mankala.getRandomMove());
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
