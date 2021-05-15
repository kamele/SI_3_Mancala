package game_code;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.transform.Translate;
import javafx.util.Duration;

import java.io.FileWriter;
import java.io.IOException;
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


    public RadioButton minMaxRadio;
    public RadioButton alfaBetaRadio;
    public ToggleGroup group;
    boolean chosenMinMaxAlg;

    public Slider depthSlider;
    double chosenDepth;

    public IScoreHeuristic heuristic;
    public ToggleGroup heuristicGroup;
    public RadioButton wellHeuristicRadio;
    public RadioButton wellAndHolesHeuristicRadio;
    public RadioButton holesHeuristicRadio;




    @FXML
    private void initialize(){
        mankala= new Mankala();
        mankala.setFirstPlayerTurn(false);


        Buttons = new ArrayList<>();
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




        group = new ToggleGroup();
        minMaxRadio.setToggleGroup(group);
        alfaBetaRadio.setToggleGroup(group);
        minMaxRadio.setSelected(true);
        chosenMinMaxAlg=minMaxRadio.isSelected();
        minMaxRadio.setOnAction(event -> setChosenAlgorytm());

        depthSlider.setMin(1);
        depthSlider.setMax(5);
        depthSlider.setValue(2);

        depthSlider.setShowTickLabels(true);
        depthSlider.setShowTickMarks(true);
        depthSlider.setBlockIncrement(1);
        chosenDepth=depthSlider.getValue();
        //depthSlider.setOnMouseDragReleased(event -> setChosenDepth());
        //depthSlider.addEventHandler(MouseEvent.MOUSE_DRAGGED, setChosenDepth());

        depthSlider.valueProperty().addListener(new ChangeListener<Number>() {

                @Override
                public void changed(
                        ObservableValue<? extends Number> observableValue,
                        Number oldValue,
                        Number newValue) {

                    setChosenDepth(newValue.intValue());
                }
            });

        heuristicGroup = new ToggleGroup();
        wellHeuristicRadio.setOnAction(event -> setChosenHeuristic());
        wellHeuristicRadio.setToggleGroup(heuristicGroup);
        wellAndHolesHeuristicRadio.setOnAction(event -> setChosenHeuristic());
        wellAndHolesHeuristicRadio.setToggleGroup(heuristicGroup);
        holesHeuristicRadio.setOnAction(event -> setChosenHeuristic());
        holesHeuristicRadio.setToggleGroup(heuristicGroup);
        setChosenHeuristic();

    }

    public int chosenAlgorytmGetMove(){

        printCurrentState();
        if(minMaxRadio.isSelected()){
            return AlgMax.getBestMove(mankala,(int) chosenDepth, mankala.isFirstPlayerTurn(), heuristic);
        }else{
            return AlfaBeta.getBestMove(mankala, (int) chosenDepth, mankala.isFirstPlayerTurn(), heuristic);
        }

    }

    public void makeMove(int holeIndex){
        if(!mankala.isIndexPermitted(holeIndex)) return;

        //liczenie ruchów
        mankala.increaseMoveCounter(mankala.isFirstPlayerTurn());

        mankala.makeMove(holeIndex);

        updateBoardView();

        //czlowiek ai
        if(!mankala.isFirstPlayerTurn() && !mankala.isGameFinished()){
            //int [] computedMove = AlgMax.getBestMove(mankala, mankala.isFirstPlayerTurn());//AlgMax.bestMove(mankala);
            int aiMove = chosenAlgorytmGetMove();//AlgMax.getBestMove(mankala, mankala.isFirstPlayerTurn());//AlgMax.bestMove(mankala);
            //mankala.addProcessTime(computedMove[1]);
            makeMove(aiMove);
        }
    }

    public void makeMoveAi(int holeIndex){
        if(!mankala.isIndexPermitted(holeIndex)){
            //System.out.println("Ruch niedozwolonyu"+holeIndex);
            return;
        }

        //liczenie ruchów
        mankala.increaseMoveCounter(mankala.isFirstPlayerTurn());

        //System.out.println("wykonaj ruch "+holeIndex+". Tura:"+mankala.isFirstPlayerTurn());
        mankala.makeMove(holeIndex);
        //System.out.println("wykonano ruch "+holeIndex+". Tura:"+mankala.isFirstPlayerTurn());

        //mankala.printGameState();

//        try {
//            TimeUnit.SECONDS.sleep(2);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }

    }


    public void playAiVsAi(){
        mankala = new Mankala();
        updateBoardView();

        makeMoveAi(mankala.getRandomMove());

        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(500), event -> updateBoardView()));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();


        System.out.println(this.mankala.isGameFinished()+" ; "+this.mankala.getWinner()+" ; "+this.mankala.getWinnerMovesCount()+" ; "+this.mankala.getWinnerProcessTime()+"\n");

        Thread thread = new Thread(()->{
            while (!mankala.isGameFinished()){
                int aiMove = chosenAlgorytmGetMove();//int aiMove = AlgMax.getBestMove(mankala, mankala.isFirstPlayerTurn());
                makeMoveAi(aiMove);

            }

            //mankala.printGameState();
            if(minMaxRadio.isSelected()){
                saveGame("AiVsAi_min_max.txt");
            }else{
                saveGame("AiVsAi_alfa_beta.txt");
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
        System.out.println(this.mankala.isGameFinished()+" ; "+this.mankala.getWinner()+" ; "+this.mankala.getWinnerMovesCount()+" ; "+this.mankala.getWinnerProcessTime()+"\n");


    }

    public void updateBoardView(){
        //System.out.println("update");

        if(mankala.isGameFinished()){
            if(mankala.getMyScore()>= mankala.getOponentScore()){
                if(mankala.getMyScore()==mankala.getOponentScore())
                    gemeStateLabel.setText("Tie");
                else
                    gemeStateLabel.setText("Win");
            }else{
                gemeStateLabel.setText("Lost");
            }
        }else{
            gemeStateLabel.setText("");
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
    }

    public void setChosenAlgorytm(){
        chosenMinMaxAlg=minMaxRadio.isSelected();
    }

    public void setChosenHeuristic(){

        if(wellHeuristicRadio.isSelected())
            heuristic = new WellScoreHeuristic();
        else if(wellAndHolesHeuristicRadio.isSelected())
            heuristic = new WellAndNumberOfHolesScoreHeuristic();
        else if(holesHeuristicRadio.isSelected())
            heuristic = new NumberOfHolesScoreHeuristic();

        if(heuristic==null)
            heuristic = new WellScoreHeuristic();
    }

    public void setChosenDepth(int depthValue){
        chosenDepth=depthValue;
    }


    public void printCurrentState(){
        System.out.println("-----Stan gry---------");
        //System.out.println("Algorytm"+chosenMinMaxAlg);
        System.out.println("Głębokość "+chosenDepth);
        System.out.println("Heurystyka "+heuristic.getHeuristicName());
        mankala.printGameState();
        System.out.println("Liczba ruchów pierwszego gracza: "+mankala.getFirstPlayerMovesCount()+" Czas:"+mankala.getFirstPlayerProcessTime());
        System.out.println("Liczba ruchów drugiego gracza: "+mankala.getSecondPlayerMovesCount()+" Czas:"+mankala.getSecondPlayerProcessTime());

    }

    public void saveGame(String fileName){
        FileWriter fileWriter = null;

        try {
            fileWriter = new FileWriter(fileName, true);
            //fileWriter.write(this.mankala.isGameFinished()+" ; "+this.mankala.getWinner()+" ; "+this.mankala.getWinnerMovesCount()+" ; "+this.mankala.getWinnerProcessTime()+"\n");
            fileWriter.write(this.mankala.isGameFinished()+" ; "+this.mankala.getWinner()+" ; "+this.mankala.getWinnerMovesCount()+" ; "+this.mankala.getWinnerProcessTime()+"\n");

            if (fileWriter != null) {
                fileWriter.close();
            }
        } catch (IOException e){
            e.printStackTrace();
        }
    }

}
