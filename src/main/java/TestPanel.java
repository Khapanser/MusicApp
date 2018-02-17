package main.java;

import javax.swing.*;
import java.awt.*;

public class TestPanel {
    JFrame f;
    JButton b1;

    public static void main(String[] args)
    {
        TestPanel p = new TestPanel();
        p.go();
    }

    public void go()
    {
        f = new JFrame("Chat");
        b1 = new JButton("CLICK!");
        JButton b2 = new JButton("CLICK!");
       // Font bifFont = new Font("serif",Font.BOLD, 28);

        JPanel panel = new JPanel();
        JPanel panel2 = new JPanel();
        panel.setBackground(Color.BLACK);
        panel2.setBackground(Color.GREEN);
        /*
        Add BoxLayout:
         */
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        f.getContentPane().add(BorderLayout.EAST, panel);
        f.getContentPane().add(BorderLayout.NORTH, panel2);

      //  b1.setFont(bifFont);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        panel.add(b1);
        panel.add(b2);

        JTextField field = new JTextField(20);
        JTextField field2 = new JTextField("Your name");
        field2.selectAll();
        field2.requestFocus();

        JLabel label = new JLabel("Окно ввода --> ");

        //f.getContentPane().add(BorderLayout.CENTER, field2);
        panel2.add(label);
        panel2.add(field);
       /* f.getContentPane().add(BorderLayout.WEST, label);
        f.getContentPane().add(BorderLayout.WEST, field);*/


        f.setSize(500,500);
        f.setVisible(true);


    }

}
