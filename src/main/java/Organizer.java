package main.java;


import javax.swing.*;
import java.awt.*;

public class Organizer {
    JFrame frame;
    //JButton b1;
    JPanel panel1;
    JPanel panel2;
    JPanel panel3;
    JEditorPane editor1;
    JEditorPane editor2;
    JList<String> list;
    JMenu menu;

    public static void main(String[] args){
        Organizer org = new Organizer();
        org.go();
    }

    public void go(){
        String[] str = {"First","Second","Third"};
        panel3 = new JPanel();
        frame = new JFrame("Organizer");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        panel3.setLayout(new BoxLayout(panel3,BoxLayout.X_AXIS));


        panel2 = new JPanel();
        list = new JList<String>(str);
        panel2.add(list);


        panel1 = new JPanel();
        editor1 = new JEditorPane();
        editor1.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        editor2 = new JEditorPane();
        editor2.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        panel1.setLayout(new BoxLayout(panel1,BoxLayout.Y_AXIS));
        panel1.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        panel1.add(BorderLayout.NORTH,editor1);
        panel1.add(BorderLayout.SOUTH,editor2);


        panel3.add(BorderLayout.NORTH, panel1);
        panel3.add(BorderLayout.SOUTH,panel2);


        menu = new JMenu();
        frame.add(panel3);
        frame.setSize(500, 500);
        frame.setVisible(true);
        frame.add(menu);

        
    }


}
