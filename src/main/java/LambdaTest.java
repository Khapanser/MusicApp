package main.java;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LambdaTest {
    JFrame frame;
    JButton but1;
    JPanel panel;
    public static void main(String[] args){
        LambdaTest test = new LambdaTest();
        test.Starter();

    }

    public void Starter(){
        frame = new JFrame("LambdaTest");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        panel = new JPanel();
        but1 = new JButton("Change");
        frame.add(panel);
        //frame.add(panel);
        panel.add(but1);

        frame.setSize(400,400);
        frame.setVisible(true);
        // Lambda-function
        but1.addActionListener(event ->
                {
                    panel.setBackground(Color.black);
                            but1.setText("done");
                });
      /**Changer c1 = new Changer();
        but1.addActionListener(c1);*/

    }

   /** public class Changer implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            panel.setBackground(java.awt.Color.YELLOW);
            but1.setText("done");
        }
    }*/
}
