package main.java;

import javax.swing.*;
import java.awt.event.*;


public class SimpleGui1B {
    JButton button;

    public static void main (String[] args)
    {
        SimpleGui1B gui = new SimpleGui1B();
        gui.go();
    }

    public void go(){
        JFrame frame = new JFrame();
        button = new JButton("Click!");

        button.addActionListener(this);
        /* т.е. теперь прослушиваем .
        Передаваемый элемент должен быть объектом класса реализуемого ActionListener
         */

        frame.getContentPane().add(button);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300,300);
        frame.setVisible(true);
    }

    public void actionPerformed (ActionEvent event)
            /*
            Это метод из интерфейса!!!
            Обработка событий!
             */
    {
        button.setText("done");
    }
}
