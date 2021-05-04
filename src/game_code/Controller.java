package game_code;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class Controller {
    public Mankala mankala;
    public ArrayList<Button> Buttons;
    public Button Button0;
    public Button Button1;
    public Button Button2;
    public Button Button3;
    public Button Button4;
    public Button Button5;
    public Button Button6;
    public Button Button7;
    public Button Button8;
    public Button Button9;
    public Button Button10;
    public Button Button11;
    public Button Button12;
    public Button Button13;
    public Label RuchKolejnoscEtykieta;
    public Label gemeStateLabel;
    public Button AiVsAiButton;

    @FXML
    private void initialize(){
        mankala= new Mankala();


        Buttons = new ArrayList<Button>();
        Buttons.add(Button0);
        Buttons.add(Button1);
        Buttons.add(Button2);
        Buttons.add(Button3);
        Buttons.add(Button4);
        Buttons.add(Button5);
        Buttons.add(Button6);

        Buttons.add(Button7);
        Buttons.add(Button8);
        Buttons.add(Button9);
        Buttons.add(Button10);
        Buttons.add(Button11);
        Buttons.add(Button12);
        Buttons.add(Button13);

        updateBoardView();

        for (Button b: Buttons ) {
            b.setText("4");
        }

        Buttons.get(6).setText("0");
        Buttons.get(13).setText("0");

        //człowiek ai gui actions

        for (int i=0; i<Buttons.size(); i++) {
            int finalI = i;
            Buttons.get(i).setOnAction(event -> makeMove(finalI));
        }
        //ai vs ai
        AiVsAiButton.setOnAction(event -> playAiVsAi());

    }

    public void makeMove(int holeIndex){
        if(!mankala.isIndexPermitted(holeIndex)) return;

        mankala.makeMove(holeIndex);


        updateBoardView();

        //czlowiek ai
        if(!mankala.isFirstPlayerTurn() && !mankala.isGameFinished()){

            int aiMove = AlgMax.getBestMove(mankala, mankala.isFirstPlayerTurn());//AlgMax.bestMove(mankala);
            makeMove(aiMove);
        }
    }

    public void makeMoveAi(int holeIndex){
        if(!mankala.isIndexPermitted(holeIndex)){
            System.out.println("Ruch niedozwolonyu"+holeIndex);
            return;
        }

        System.out.println("wykonaj ruch "+holeIndex+". Tura:"+mankala.isFirstPlayerTurn());
        mankala.makeMove(holeIndex);
        //System.out.println("wykonano ruch "+holeIndex+". Tura:"+mankala.isFirstPlayerTurn());

        

        //updateBoardView();
        mankala.printGameState();

        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

//
//        if( !mankala.isGameFinished()){
//
//            System.out.println("wywołaj kolejny. Tura:"+mankala.isFirstPlayerTurn());
//            int aiMove = AlgMax.bestMove(mankala, mankala.isFirstPlayerTurn());
//
//            makeMoveAi(aiMove);
//
//        }else{
//            if(mankala.getMyScore()>= mankala.getOponentScore()){
//                if(mankala.getMyScore()==mankala.getOponentScore())
//                    System.out.println("Tie "+mankala.getMyScore()+"  :  "+mankala.getOponentScore());
//                else
//                    System.out.println("Win "+mankala.getMyScore()+"  :  "+mankala.getOponentScore());
//            }else{
//                System.out.println("Lost "+mankala.getMyScore()+"  :  "+mankala.getOponentScore());
//            }
//        }
    }


    public void playAiVsAi(){
        mankala = new Mankala();
        updateBoardView();

        makeMoveAi(mankala.getRandomMove());
        //updateBoardView();

        Thread thread = null;
        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(500),new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event) {
                updateBoardView();
            }
        }));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
        thread = new Thread(()->{
            while (!mankala.isGameFinished()){
                int aiMove = AlgMax.bestMove(mankala, mankala.isFirstPlayerTurn());
                makeMoveAi(aiMove);

            }

        });
        thread.start();



        if(mankala.getMyScore()>= mankala.getOponentScore()){
            if(mankala.getMyScore()==mankala.getOponentScore())
                System.out.println("Tie "+mankala.getMyScore()+"  :  "+mankala.getOponentScore());
            else
                System.out.println("Win "+mankala.getMyScore()+"  :  "+mankala.getOponentScore());
        }else{
            System.out.println("Lost "+mankala.getMyScore()+"  :  "+mankala.getOponentScore());
        }
        updateBoardView();

    }

    public void updateBoardView(){
        System.out.println("update");

        if(mankala.isGameFinished()){
            if(mankala.getMyScore()>= mankala.getOponentScore()){
                if(mankala.getMyScore()==mankala.getOponentScore())
                    gemeStateLabel.setText("Tie");
                else
                    gemeStateLabel.setText("Win");
            }else{
                gemeStateLabel.setText("Lost");
            }
        }

        if(mankala.isFirstPlayerTurn()){
            RuchKolejnoscEtykieta.setText("Twój");
        }else{
            RuchKolejnoscEtykieta.setText("Przeciwnik");
        }

        for (int i=0; i<Buttons.size(); i++) {
            String buttonState = String.valueOf(mankala.getGameState()[i]);
            Buttons.get(i).setText(buttonState);
        }
//        try {
//                TimeUnit.SECONDS.sleep(3);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
    }
}
