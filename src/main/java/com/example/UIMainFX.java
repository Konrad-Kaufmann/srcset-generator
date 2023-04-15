package com.example;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;


import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class UIMainFX extends Application {
    public UIMainFX() {
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Automatic srcset generator");
        primaryStage.initStyle(StageStyle.DECORATED);
        Parent root = FXMLLoader.load(getClass().getResource("/Gui.fxml"));


        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        //primaryStage.setWidth(600);
        //primaryStage.setHeight(300);

        primaryStage.show();

    }

}
