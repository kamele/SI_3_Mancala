package game_code;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.util.ArrayList;

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

//        for (Button b: Buttons ) {
//            b.setText("4");
//        }
//
//        Buttons.get(6).setText("0");
//        Buttons.get(13).setText("0");

        for (int i=0; i<Buttons.size(); i++) {
            int finalI = i;
            Buttons.get(i).setOnAction(event -> makeMove(finalI));
        }


    }

    public void makeMove(int holeIndex){
        if(!mankala.isIndexPermitted(holeIndex)) return;

        mankala.makeMove(holeIndex);


        updateBoardView();

        if(!mankala.isFirstPlayerTurn() && !mankala.isGameFinished()){
            int aiMove = AlgMax.getBestMove(mankala);//AlgMax.bestMove(mankala);
            makeMove(aiMove);
        }
    }


    public void updateBoardView(){
        if(mankala.isFirstPlayerTurn()){
            RuchKolejnoscEtykieta.setText("Twój");
        }else{
            RuchKolejnoscEtykieta.setText("Przeciwnik");
        }
        for (int i=0; i<Buttons.size(); i++) {
            String buttonState = String.valueOf(mankala.getGameState()[i]);
            Buttons.get(i).setText(buttonState);
        }
    }
}
