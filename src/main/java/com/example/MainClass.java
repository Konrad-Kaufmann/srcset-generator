package com.example;

import javax.swing.UnsupportedLookAndFeelException;

import javafx.application.Application;

public class MainClass {
    // make dynamic in the program later
    

    public static void main(String[] args) throws InterruptedException {
        Application.launch(UIMainFX.class, args);
        /*
        try {
            new UIMain(new MainApp());
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (InstantiationException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (UnsupportedLookAndFeelException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } */
    }

}
