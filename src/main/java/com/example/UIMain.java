package com.example;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileSystemView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import java.awt.image.BufferedImage;
import java.awt.*;

public class UIMain extends JFrame {
    private MainApp MA;
    private JPanel mainPanel;
    JFileChooser jfc;

    public UIMain(MainApp MA) throws ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException {
        super("Worktitle");
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        this.MA = MA;
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 400);
        setResizable(true);
        mainPanel = new JPanel(new GridBagLayout());
        JButton b = new JButton("test");
        mainPanel.add(b);
        b.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
                jfc.setDialogTitle("Select an image");
                jfc.setAcceptAllFileFilterUsed(false);
                FileNameExtensionFilter filter = new FileNameExtensionFilter("PNG and GIF images", "png", "gif");
                jfc.addChoosableFileFilter(filter);
                jfc.updateUI();
                int returnValue = jfc.showOpenDialog(null);
                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    System.out.println(jfc.getSelectedFile().getPath());
                }
        
            }
        });
        GridBagConstraints gbc = new GridBagConstraints();

        this.add(mainPanel);
        setVisible(true);

    }

    public void drawPicture(BufferedImage img) {

    }

}