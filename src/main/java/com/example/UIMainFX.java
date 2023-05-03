package com.example;

import java.io.File;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class UIMainFX extends Application {

    @FXML
    private Slider screensizeSlider;
    @FXML
    private TextField filePathText;
    @FXML
    private TextField picturePathText;

    private Stage primaryStage;
    private MainApp mA;

    private File htmlFile = null;

    public UIMainFX() {
        try {
            mA = new MainApp();
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;
        primaryStage.setTitle("Automatic srcset generator");
        primaryStage.initStyle(StageStyle.DECORATED);
        Parent root = FXMLLoader.load(getClass().getResource("/Gui.fxml"));
        

    

        Scene scene = new Scene(root);

        primaryStage.setScene(scene);
        //primaryStage.setWidth(600);
        //primaryStage.setHeight(300);

        primaryStage.show();

    }
    public void skipBtnPressed(){
            System.out.println("Skipped");
    }
    public void okBtnPressed(){
        System.out.println(screensizeSlider.valueProperty().doubleValue());
    }
    public void openFileSearch(){
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose HTML-File");
        fileChooser.getExtensionFilters().addAll(
            new FileChooser.ExtensionFilter("HTML", "*.html")
        );
        htmlFile = fileChooser.showOpenDialog(primaryStage);
        setFilePath(htmlFile.getAbsolutePath());
    }
    public void setFilePath(String fP){
        filePathText.setText(fP);
    }
    public void setPicturePath(String fP){
        picturePathText.setText(fP);
    }

}
