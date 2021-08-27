package com.mds.wingame.window;

import com.mds.game.MainInterface;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class MainWindow extends JFrame{

    private MainMenuWindow mainMenuWindow;
    private MainInterface mainInterface;
    private GameWindow gameWindow;
    private boolean gameControl = false;
    private AuthWindow authWindow;
    public MainWindow() throws HeadlessException {
    }

    public void startWindow() {
        settingWindowStart();
    }
    public void startMenu(MainInterface mainInterface){
        this.mainInterface=mainInterface;
        mainMenuWindow = new MainMenuWindow();
        add(mainMenuWindow);
        addListener();
    }

    private void settingWindowStart(){
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLocation(200,100);
        setSize(400,400);
        setResizable(false);
        setVisible(true);
    }

    private void addListener(){
        this.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                int key= e.getKeyCode();
                if(key==65||key==68||key==32||key==37||key==39){
                    keyTap(key,true);
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                int key= e.getKeyCode();
                if(key==65||key==68||key==37||key==39){
                    keyTap(key,false);
                }
            }
        });
    }

    public void keyTap(int i,boolean is){
        if(gameControl){
            gameWindow.keyTap(i,is);
        }
    }

    public void openCloseMainMenu(){
        mainMenuWindow.setVisible(!mainMenuWindow.isVisible());
    }
    public void beginStartGame(){
        if(authWindow==null){
            authWindow = new AuthWindow();
            add(authWindow);
        }
        authWindow.setVisible(true);
        openCloseMainMenu();
    }
    private void startGame(boolean login){
        if(login){
            if(authWindow.checkBoxOnline.isSelected()){
                mainInterface.authServer(authWindow.textLogin.getText(),authWindow.textPassword.getText());
            } else {
                mainInterface.authServer();
            }
            authWindow.setVisible(false);
            remove(authWindow);
            authWindow=null;
            mainInterface.createGame();
        }else {
            mainInterface.registerServer(authWindow.textLogin.getText(),authWindow.textPassword.getText());
        }
    }
    public void gameIsStart(){
        gameWindow = new GameWindow(this,mainInterface.getGameInterface());
        add(gameWindow);
        gameControl=true;
        requestFocusInWindow();
    }

    private class MainMenuWindow extends JPanel{
        public MainMenuWindow(){
            setFocusable(false);
            ButStart butStart = new ButStart();
            setLayout(null);
            add(butStart);

        }
        private class ButStart extends JButton{
            public ButStart() {
                setFocusable(false);
                setBounds(10,10,100,25);
                setFocusPainted(false);
                setText("Start game");
                addActionListener(new MyActionListener());
            }
            private class MyActionListener implements ActionListener {
                public void actionPerformed(ActionEvent e) {
                    beginStartGame();
                }
            }
        }
    }
    private class AuthWindow extends JPanel{
        private TextLogin textLogin;
        private TextPassword textPassword;
        private CheckBoxOnline checkBoxOnline;
        public AuthWindow() {
            settingWindowStart();
        }

        private void settingWindowStart(){
            setLocation(200,100);
            setSize(400,200);
            setResizable(false);
            setLayout(null);
            setVisible(true);
            add(new ButMenu());
            add(new ButLogin());
            add(new ButRegister());
            checkBoxOnline = new CheckBoxOnline();
            add(checkBoxOnline);
            textLogin = new TextLogin();
            add(textLogin);
            textPassword = new TextPassword();
            add(textPassword);
        }
        private class ButMenu extends JButton{
            public ButMenu() {
                setFocusable(false);
                setBounds(120,10,100,25);
                setText("Menu");
                addActionListener(new MyActionListener());
            }
            private class MyActionListener implements ActionListener {
                public void actionPerformed(ActionEvent e) {
                    authWindow.setVisible(false);
                    openCloseMainMenu();
                }
            }
        }
        private class ButLogin extends JButton{
            public ButLogin() {
                setFocusable(false);
                setBounds(10,60,100,25);
                setText("Login");
                addActionListener(new MyActionListener());
            }
            private class MyActionListener implements ActionListener {
                public void actionPerformed(ActionEvent e) {
                    startGame(true);
                }
            }
        }
        private class ButRegister extends JButton{
            public ButRegister() {
                setFocusable(false);
                setBounds(120,60,100,25);
                setText("Register");
                addActionListener(new MyActionListener());
            }
            private class MyActionListener implements ActionListener {
                public void actionPerformed(ActionEvent e) {
                    startGame(false);
                }
            }
        }
        private class CheckBoxOnline extends JCheckBox{
            public CheckBoxOnline() {
                setFocusable(false);
                setBounds(10,90,100,25);
                setText("Online");
                setSelected(false);
            }
        }
        private class TextLogin extends JTextArea{
            public TextLogin() {
                setBounds(10,10,100,15);
            }
        }
        private class TextPassword extends JTextArea{
            public TextPassword() {
                setBounds(10,35,100,15);
            }
        }
    }
}