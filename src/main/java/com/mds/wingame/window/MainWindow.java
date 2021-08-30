package com.mds.wingame.window;

import com.mds.game.MainInterface;
import com.mds.game.client.ScoreInfo;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.List;

public class MainWindow extends JFrame{

    private MainMenuWindow mainMenuWindow;
    private MainInterface mainInterface;
    private GameWindow gameWindow;
    private boolean gameControl = false;
    private AuthWindow authWindow;
    private ScoresWindow scoresWindow;
    public MainWindow() throws HeadlessException {
    }

    public void startWindow() {
        settingWindowStart();
    }
    public void deleteAuth(){
        remove(authWindow);
        authWindow=null;
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
        } else {
            if(mainInterface.isAuth()){
                startGame(true);
            } else {
                authWindow.setVisible(true);
            }
        }
        openCloseMainMenu();
    }
    public void openScores(){
        if(scoresWindow==null){
            scoresWindow = new ScoresWindow();
            add(scoresWindow);
        }
        scoresWindow.updateScores(mainInterface.getScoresTop10());
        scoresWindow.setVisible(true);
        openCloseMainMenu();
    }
    private void startGame(boolean login){
        if(login){
            if(!mainInterface.isAuth()){
                if(authWindow.checkBoxOnline.isSelected()){
                    if(!mainInterface.authServer(authWindow.textLogin.getText(),authWindow.textPassword.getText())){
                        return;
                    }
                } else {
                    if(!mainInterface.authServer()){
                        return;
                    }
                }
                authWindow.setVisible(false);
                mainMenuWindow.login();
            }
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
        private ButLogout butLogout;
        public MainMenuWindow(){
            setFocusable(false);
            setLayout(null);
            butLogout=new ButLogout();
            add(new ButStart());
            add(new ButScores());
            add(butLogout);
        }
        public void login(){ butLogout.setEnabled(true);}
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
        private class ButScores extends JButton{
            public ButScores() {
                setFocusable(false);
                setBounds(10,40,100,25);
                setFocusPainted(false);
                setText("Scores");
                addActionListener(new MyActionListener());
            }
            private class MyActionListener implements ActionListener {
                public void actionPerformed(ActionEvent e) {
                    openScores();
                }
            }
        }
        private class ButLogout extends JButton{
            public ButLogout() {
                setFocusable(false);
                setBounds(10,70,100,25);
                setFocusPainted(false);
                setEnabled(false);
                setText("Logout");
                addActionListener(new MyActionListener());
            }
            private class MyActionListener implements ActionListener {
                public void actionPerformed(ActionEvent e) {
                    setEnabled(false);
                    mainInterface.loggout();
                    deleteAuth();
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

    private class ScoresWindow extends JPanel{
        private TextPanel scoresPanel;
        public ScoresWindow(){
            setFocusable(false);
            setLayout(null);
            scoresPanel = new TextPanel();
            add(new ButMenu());
            add(scoresPanel);
        }
        public void updateScores(List<ScoreInfo> scoreInfoList){
            StringBuilder stringBuilder=new StringBuilder();
            for(ScoreInfo scoreInfo:scoreInfoList){
                stringBuilder.append(scoreInfo.name+" : "+scoreInfo.score+"\n");
            }
            scoresPanel.setText(stringBuilder.toString());
        }
        private class ButMenu extends JButton{
            public ButMenu() {
                setFocusable(false);
                setBounds(10,10,100,25);
                setText("Menu");
                addActionListener(new MyActionListener());
            }
            private class MyActionListener implements ActionListener {
                public void actionPerformed(ActionEvent e) {
                    scoresWindow.setVisible(false);
                    openCloseMainMenu();
                }
            }
        }
        private class TextPanel extends JTextPane{
            public TextPanel() {
                setEditable(false);
                setBounds(10,40,100,200);
            }
        }
    }
}