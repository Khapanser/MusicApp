package main.java;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Graphical {
    JFrame frame;
    JButton button;
    JButton buttonTwo;

    public static void main (String[] args)
    {
        Graphical gui = new Graphical();
        gui.go();
    }


    public void go(){

        frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);



        button = new JButton("Change colour");
        buttonTwo = new JButton("Click?");
        buttonTwo.addActionListener(new LabelListener());
        button.addActionListener(new ColorListener());

        MyDrawPanel drawPanel = new MyDrawPanel();
       // button.addActionListener(this);

        frame.getContentPane().add(BorderLayout.SOUTH,button);
        frame.getContentPane().add(BorderLayout.CENTER,drawPanel);
        frame.getContentPane().add(BorderLayout.EAST, buttonTwo);
        frame.setSize(400,400);
        frame.setVisible(true);
    }

    //Внутренний класс:
    class ColorListener implements ActionListener{
        public void actionPerformed(ActionEvent event)
        {
           frame.repaint();
        }
    }
    //Второй внутренний класс:
    class LabelListener implements ActionListener{
        public void actionPerformed(ActionEvent event){
            buttonTwo.setText("It's work");
        }
    }

}
