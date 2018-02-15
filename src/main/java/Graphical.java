package main.java;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Graphical {
    //Сообщаем какие элементы у нас будут: создаем ссылки
    JFrame frame;
    JButton button;
    JButton buttonTwo;
    //Инициализируем начальные переменные
    int x = 70;
    int y = 70;

    public static void main (String[] args)
    {
        Graphical gui = new Graphical();
        gui.go();
    }

    public void go(){
        //Собственно сам фрейм, на который всё будем закидывать
        frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Создаем кнопки
        button = new JButton("Change colour");
        buttonTwo = new JButton("Click?");
        //Добавляем прослушки на кнопки
        buttonTwo.addActionListener(new LabelListener());
        button.addActionListener(new ColorListener());
        //Создаём область рисования
        //MyDrawPanel drawPanel = new MyDrawPanel();
        MiniDrawPanel drawPanel = new MiniDrawPanel();

        //Добавляем всё на фрейм
        frame.getContentPane().add(BorderLayout.SOUTH,button);
        frame.getContentPane().add(BorderLayout.CENTER,drawPanel);
        frame.getContentPane().add(BorderLayout.EAST, buttonTwo);
        frame.setSize(700,700);
        frame.setVisible(true);

        //Напишем код, чтобы наш рисунок передвигался
        for(int i = 0; i<50; i++)
        {
            x=x+7;
            y= (int) (70+10*Math.sin(x));

           drawPanel.repaint();
            try{
                Thread.sleep(50);
            }
            catch(Exception ex) {}

        }
    }

    /*Внутренний класс: реакция на нажатие кнопки 1
    Имеет доступ ко всем переменным внешнего касса!
     */

    class ColorListener implements ActionListener{
        public void actionPerformed(ActionEvent event)
        {
            frame.repaint();
        }
    }
    //Второй внутренний класс: реакция на нажатие кнопки 2
    class LabelListener implements ActionListener{
        public void actionPerformed(ActionEvent event){
            buttonTwo.setText("It's work");
        }
    }

    class MiniDrawPanel extends JPanel{
        public void paintComponent(Graphics g)
        {
            /*g.setColor(Color.WHITE);
            g.fillOval(x-1,y-1,40,40);*/

            g.setColor(Color.white);
            g.fillRect(0,0,this.getWidth(),this.getHeight());

            g.setColor(Color.green);
            g.fillOval(x,y,40,40);
        }
    }
    }