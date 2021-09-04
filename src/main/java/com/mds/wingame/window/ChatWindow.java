package com.mds.wingame.window;

import com.mds.game.MainInterface;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class ChatWindow extends JPanel {
    private TextMessage textMessage;
    private Scroll scroll;
    private ButSendMessage butSendMessage;
    private ContainerChatMessage containerChatMessage;
    private List<ChatMessage> chatMessageList = new ArrayList<ChatMessage>();
    private MainInterface mainInterface;
    private ScrollPaneChat scrollPaneChat;
    public ChatWindow(int x,int y,MainInterface mainInterface){
        this.mainInterface =mainInterface;
        setLayout(null);
        setFocusable(false);
        textMessage = new TextMessage();
        butSendMessage = new ButSendMessage();
        containerChatMessage = new ContainerChatMessage();
        scrollPaneChat = new ScrollPaneChat();
//        scroll = new Scroll();
        PaneChat paneChat = new PaneChat();
        paneChat.add(scrollPaneChat);
        add(butSendMessage);
//        add(scroll);
        add(textMessage);
        add(paneChat);
        setBounds(x,y,200,150);
        setVisible(true);
    }

    public void loginInChat(){
        butSendMessage.setEnabled(true);
    }
    public void logoutInChat(){
        butSendMessage.setEnabled(false);
    }

    public void addMessage(List<String> list){
        list.stream().forEach(item->{ChatMessage chatMessage = new ChatMessage(item);
        chatMessageList.add(chatMessage);
        containerChatMessage.addMessage(chatMessage); });
    }
    private void sendChatMessage(){
        mainInterface.chatSendMessage(textMessage.getText());
        textMessage.setText("");
    }

    private class PaneChat extends JPanel{
        public PaneChat(){
            setLayout(null);
            setFocusable(false);
            setBounds(0,0,190,100);
            setBackground(Color.white);
        }
    }
    private class ContainerChatMessage extends JPanel{
        private int height=0;
        public ContainerChatMessage() {
            setLayout(null);
            setFocusable(false);
            setBackground(Color.GREEN);
            setBounds(0,0,190,height);
            setPreferredSize(new Dimension(190,height));
        }
        public void addMessage(ChatMessage chatMessage){
            add(chatMessage);
            height=height+25;
            setSize(190,height);
            if(height>100){
//                scroll.setMaximum((height-100)/25);
//                scroll.setValue(scroll.getMaximum());
                setPreferredSize(new Dimension(190,height));
//                setLocation(0,-(height-100));
            }
            repaint();
        }
    }
    private class ScrollPaneChat extends JScrollPane{
        public ScrollPaneChat() {
            setViewportView(containerChatMessage);
            setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
            setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
            setBounds(0,0,190,100);
        }
    }
    private class ButSendMessage extends JButton{
        public ButSendMessage(){
            setFocusable(false);
            setBounds(150,105,50,20);
            setHorizontalTextPosition(Label.LEFT);
            setBorder(BorderFactory.createLineBorder(Color.BLACK,1));
            setText("send");
            setEnabled(false);
            addActionListener(new MyActionListener());
        }
        private class MyActionListener implements ActionListener {
            public void actionPerformed(ActionEvent e) {sendChatMessage();}
        }
    }
    private class Scroll extends JScrollBar{
        public Scroll(){
            setFocusable(false);
            setBounds(190,0,10,100);
            setMaximum(0);
            setValue(0);
        }
        private class MyActionListener implements ActionListener {
            public void actionPerformed(ActionEvent e) {sendChatMessage();}
        }
    }
    private class TextMessage extends JTextArea{
        public TextMessage() {
            setBounds(0,105,140,20);
        }
    }
    private class ChatMessage extends JLabel{
        public ChatMessage(String message){
            int y = (chatMessageList.size())*25;
            System.out.println("int y ="+y );
            setText(message);
            setBounds(0,y,185,20);
        }
    }
}
