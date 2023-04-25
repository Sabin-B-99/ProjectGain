package com.projectgain;

import com.projectgain.manager.AppManager;
import com.projectgain.manager.CountDownTimerManager;
import com.projectgain.manager.WorkRoutineManager;
import com.projectgain.views.ViewFactory;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        ViewFactory viewFactory = new ViewFactory(new AppManager());
        viewFactory.showLandingStage();
    }

    public static void main(String[] args) {
        launch();
    }
}