package com.projectgain;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;

import java.io.FileNotFoundException;
import java.net.URL;

public class FxmlViewLoader {

    public static Pane getPage(String fileName){
        Pane view = null;
        try {
            URL fileUrl = Main.class.getResource("/views/" + fileName + ".fxml");
            if(fileUrl == null){
                throw new FileNotFoundException("FXML file cannot be found");
            }

            view = FXMLLoader.load(fileUrl);
        }catch (Exception e){
            e.printStackTrace();
        }
        return view;
    }
}
