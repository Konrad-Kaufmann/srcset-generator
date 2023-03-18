package com.example;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class UIMainFX extends Application {
    public UIMainFX() {
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Automatic srcset generator");
        primaryStage.initStyle(StageStyle.DECORATED);


        VBox vBox = new VBox(new Label("A JavaFX Label"));
        Scene scene = new Scene(vBox);
        primaryStage.setScene(scene);
        primaryStage.setWidth(600);
        primaryStage.setHeight(300);

        primaryStage.show();

    }

}
