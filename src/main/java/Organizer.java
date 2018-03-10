package main.java;


import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;

public class Organizer {
    JFrame frame;
    JFrame openFrame;
    JButton newButton;
    JButton saveButton;
    JButton delButton;
    JPanel panel1;
    JPanel panel2;
    JPanel mainPanel;
    JEditorPane editor1;
    JEditorPane editor2;
    JMenu menu;
    DefaultListModel<QCard> listModel;


    public static void main(String[] args){
        Organizer org = new Organizer();
        org.go();
    }

    public void go(){
        /**
         * Создаем DefaultListModel для добавления
         * в него карт из файла.
         */
        listModel = new DefaultListModel<>();

        /**
         * Добавляем инициацию считывания из файла при запуске.
         */
        JFileChooser fileOpen = new JFileChooser();
        fileOpen.showOpenDialog(openFrame);
        loadFile(fileOpen.getSelectedFile());

        //GUI
        mainPanel = new JPanel();
        frame = new JFrame("Organizer");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainPanel.setLayout(new BoxLayout(mainPanel,BoxLayout.X_AXIS));


        /**
         * Добавление в JList списка объектов.
         * В дальнеёшем, это должен быть подгружаемый список из файла
         * (уже перенесён в makeCard)
         */
        JList<QCard> list = new JList<>(listModel);



        //Add list's scroll (GUI)
        JScrollPane scroller = new JScrollPane(list);
        scroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        scroller.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.setVisibleRowCount(4);

        //GUI
        newButton = new JButton("  NEW  ");
        saveButton = new JButton(" SAVE ");
        delButton = new JButton("DELETE");

        panel2 = new JPanel();
        panel2.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        panel2.setLayout(new BoxLayout(panel2,BoxLayout.Y_AXIS));
        panel2.add(scroller);

        newButton.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        saveButton.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        delButton.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        panel2.add(newButton);
        panel2.add(saveButton);
        panel2.add(delButton);


        panel1 = new JPanel();

        editor1 = new JEditorPane();
        editor1.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        editor2 = new JEditorPane();
        editor2.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        panel1.setLayout(new BoxLayout(panel1,BoxLayout.Y_AXIS));
        panel1.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        panel1.add(BorderLayout.NORTH,editor1);
        panel1.add(BorderLayout.SOUTH,editor2);


        mainPanel.add(BorderLayout.NORTH, panel1);
        mainPanel.add(BorderLayout.SOUTH,panel2);


        menu = new JMenu();
        frame.add(mainPanel);
        frame.setSize(500, 500);
        frame.setVisible(true);
        frame.add(menu);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


       /* list.addActionListener(event -> {

        });*/
        list.addListSelectionListener(event -> {
            editor1.setContentType(list.getSelectedValue());

        });


    }

    /**
     * Считываем построчно из файла и создаем карту через makeCard;
     * @param file
     */
    private void loadFile(File file)
    {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line;
            while ((line = reader.readLine()) != null)
            {
                System.out.println("получена строка line = "+line);
                makeCard(line);
                System.out.println("сработала makeCard для line = "+line);

            }
        }   catch(Exception ex) {
            System.out.println("ERROR: провалилась загрузка файла!");
            ex.printStackTrace();}
    }

    /**
     * Принимаем строку,
     * Делим её пополам через "/",
     * Создаём новую карту.
     * @param lineToParse
     */
    private void makeCard(String lineToParse) {
        String[] result = lineToParse.split("/");
        QCard card = new QCard(result[0],result[1]);
        listModel.addElement(card);
    }

}
