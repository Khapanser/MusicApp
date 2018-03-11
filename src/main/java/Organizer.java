package main.java;


import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class Organizer {
    private JFrame frame;
    private JFrame openFrame;
    private JButton newButton;
    private JButton saveButton;
    private JButton delButton;
    private JPanel panel1;
    private JPanel panel2;
    private JPanel mainPanel;
    private JEditorPane editor1;
    private JEditorPane editor2;
    private JMenu menu;
    private DefaultListModel<QCard> listModel;
    private QCard selCard;   // ссылка на текущий выбранный объект

    private JLabel label1;
    private JLabel label2;

    public static void main(String[] args){
        Organizer org = new Organizer();
        org.go();
    }

    private  void go(){
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
        scroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        scroller.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.setVisibleRowCount(4);

        //GUI
        newButton = new JButton("  New note  ");
        saveButton = new JButton(" Save note");
        delButton = new JButton("Delete note");

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

        label1 = new JLabel("Title");
        label2 = new JLabel("Description");
        editor1 = new JEditorPane();
        editor1.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        editor2 = new JEditorPane();
        editor2.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        panel1.setLayout(new BoxLayout(panel1,BoxLayout.Y_AXIS));
        panel1.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        /*
        panel1.add(BorderLayout.NORTH,editor1);
        panel1.add(BorderLayout.SOUTH,editor2);
        */

        //Добавим скроллы на редакторы
        JScrollPane scroller1 = new JScrollPane(editor1);
        scroller1.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        scroller1.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        //list.setVisibleRowCount(4);

        //Добавим скроллы на редакторы
        JScrollPane scroller2 = new JScrollPane(editor2);
        scroller2.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        scroller2.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        //list.setVisibleRowCount(4);


        panel1.add(label1);
        panel1.add(scroller1);
        panel1.add(label2);
        panel1.add(scroller2);


        mainPanel.add(BorderLayout.NORTH, panel1);
        mainPanel.add(BorderLayout.SOUTH,panel2);


        menu = new JMenu();
        frame.add(mainPanel);
        frame.setSize(1000, 800);
        frame.setVisible(true);
        frame.add(menu);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        /**
         * Слушаем нажатие на элемент списка
         */
       /*
       list.addListSelectionListener(event -> {
            selCard = list.getSelectedValue();
            editor1.setText(selCard.getTitle());
            //System.out.println("selCard.getTitle() = "+ selCard.getTitle());
            editor2.setText(selCard.getDescription());
            //System.out.println("selCard.getDescription() = "+ selCard.getDescription());
        });*/

        list.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                selCard = list.getSelectedValue();
                editor1.setText(selCard.getTitle());
                System.out.println("selCard.getTitle() = "+ selCard.getTitle());
                editor2.setText(selCard.getDescription());
                System.out.println("selCard.getDescription() = "+ selCard.getDescription());
            }
        });



        /**
         * Слушаем сохранение изменений (Button Save)
         */
        saveButton.addActionListener(event -> {
            selCard.setTitle(editor1.getText());
            selCard.setDescription(editor2.getText());
            list.updateUI();  // отображение изменений в списке( title)
        });

        /**
         * Слушаем создание новой заметки (Button NEW)
         */
        newButton.addActionListener(event -> {
            QCard newCard = new QCard("new","new");
            listModel.addElement(newCard);
        });

        /**
         * Удаление элемента из списка (Button DELETE)
         */
       delButton.addActionListener(event -> {
           if (!list.isSelectionEmpty()) {
               listModel.removeElement(selCard);
               selCard = listModel.firstElement();
           }
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

    /**
     * TODO пишем обратно в файл со всеми изменениями
     */


}
