package com.example;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Deque;

import javax.imageio.ImageIO;

import javafx.application.Application;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
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
    @FXML
    private ImageView imageView;

    private Stage primaryStage;
    private MainApp mA;

    private File htmlFile = null;
    private Deque<Path> imagePathStack;

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
        // primaryStage.setWidth(600);
        // primaryStage.setHeight(300);

        primaryStage.show();

    }

    public void skipBtnPressed() {
        System.out.println("Skipped");
    }

    public void okBtnPressed() {
        System.out.println(screensizeSlider.valueProperty().doubleValue());
    }

    public void openFileSearch() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose HTML-File");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("HTML", "*.html"));
        htmlFile = fileChooser.showOpenDialog(primaryStage);
        setFilePath(htmlFile.getAbsolutePath());
        showNextImage();
    }

    public void setFilePath(String fP) {
        filePathText.setText(fP);
        try {
            imagePathStack = mA.imageStack(fP);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    public void setPicturePath(String fP) {
        picturePathText.setText(fP);
    }

    private void showNextImage() {

        // needs to load the file into an BufferedImage in case it is a webp file
        Path pI = imagePathStack.pop();
        setPicturePath(pI.toString());
        BufferedImage shownImage;
        try {
            shownImage = ImageIO.read(pI.toFile());
            WritableImage image = SwingFXUtils.toFXImage(shownImage, null);
            imageView.setImage(image);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

}
