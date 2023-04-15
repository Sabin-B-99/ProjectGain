package com.projectgain.views;

import com.projectgain.controllers.*;
import com.projectgain.manager.CountDownTimerManager;
import com.projectgain.manager.WorkRoutineManager;
import com.projectgain.models.WorkCard;
import com.projectgain.models.WorkGroup;
import com.projectgain.models.WorkRoutine;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.time.LocalTime;
import java.util.ArrayList;

public class ViewFactory{

    private WorkRoutineManager manager;

    public ViewFactory(WorkRoutineManager manager) {
        this.manager = manager;
    }

    public void showLandingStage(){
        BaseController controller = new LandingWindowController("LandingWindow.fxml", this, manager);
        initializeStage(controller);
    }

    public Pane getWorkCard(){
        BaseController controller = new WorkCardController("WorkCard.fxml", this, manager, new WorkCard());
        return getLayoutPane(controller);
    }

    public Pane getWorkCardGroup(){
        BaseController controller  = new WorkCardGroupController("WorkCardGroup.fxml", this, manager, new WorkGroup());
        return getLayoutPane(controller);
    }

    public Pane getWorkRoutineForm(){
        BaseController controller = new WorkRoutineController("WorkRoutine.fxml", this, manager, new WorkRoutine());
        return getLayoutPane(controller);
    }

    public Pane getCountDownTimer(){
        //TODO: Clean up these codes below after setting up database operations
        //code just for test
        WorkCard card = new WorkCard();
        card.setTitle("First");
        card.setColorHexCode("#777777");
        card.setTime(LocalTime.of(0,0,5));

        WorkCard card1 = new WorkCard();
        card1.setTitle("Mid");
        card1.setColorHexCode("#444444");
        card1.setTime(LocalTime.of(0,0,15));

        WorkCard card2 = new WorkCard();
        card2.setTitle("Last");
        card2.setColorHexCode("#222222");
        card2.setTime(LocalTime.of(0,0,25));

        ArrayList<WorkCard> workCards = new ArrayList<>();
        workCards.add(card);
        workCards.add(card1);
        workCards.add(card2);

        BaseController controller = new CountDownTimerController("CountDownTimer.fxml", this, new CountDownTimerManager(workCards));
        return getLayoutPane(controller);
    }

    private void initializeStage(BaseController controller){
        Parent parent = null;
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/" + controller.getFxmlViewName()));
            fxmlLoader.setController(controller);
            parent = fxmlLoader.load();
        }catch (Exception e){
            e.printStackTrace();
        }

        Scene scene = new Scene(parent);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
    }

    public Pane getLayoutPane(BaseController controller) {
        Pane view = null;
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/" + controller.getFxmlViewName()));
            fxmlLoader.setController(controller);
            view = fxmlLoader.load();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return view;
    }

    //from stackoverflow
    public String getColorHex(Color color){
        return String.format("#%02X%02X%02X",
                (int) (color.getRed() * 255),
                (int) (color.getGreen() * 255),
                (int) (color.getBlue() * 255));
    }
}
