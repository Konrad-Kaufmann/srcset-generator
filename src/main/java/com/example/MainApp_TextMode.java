package com.example;

import java.io.IOException;
import java.util.Scanner;



public class MainApp_TextMode {
    private int[] sizes = { 1920, 1600, 1366, 1024, 768, 640 };

    private MainApp_old mA;

    public MainApp_TextMode() throws InterruptedException {
        mA = new MainApp_old();
        start();
    }

    private void start() throws InterruptedException {

        Scanner in = new Scanner(System.in);

        while (true) {
            System.out.println("Please enter the absolute path to the html-file or \"exit\":");

            String inS = in.nextLine();

            // imgMode(path, dimension, sizes);
            if (inS.equalsIgnoreCase("exit")) {
                break;
            }
            try {
                mA.codeMode(inS, sizes, in);
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        in.close();

    }

}
