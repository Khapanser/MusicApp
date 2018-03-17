package main.java;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.*;
import java.net.Socket;

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
    private DefaultListModel<QCard> listModel;
    private QCard selCard;   // ссылка на текущий выбранный объект
    private JLabel label1;
    private JLabel label2;
    private Font font;
    //MenuBar
    private JMenuBar menuBar;
    private JMenu menu;
    private JMenuItem loadMenuItem;
    private  JMenuItem writeLocalyItem;
    private JMenuItem loadLocalyItem;

    // Jlist for all QCards
    JList<QCard> list;


    // Объявляем сокет
    Socket socket;
    // Указываем порт подключения
    int portNumber = 5000;

    public static void main(String[] args){
        Organizer org = new Organizer();
        org.go();
    }

    private  void go(){
        /**
         * Создаем DefaultListModel для добавления
         * в него карт из файла.
         */
        //font = new Font("Garamond", Font.BOLD,14);
        listModel = new DefaultListModel<QCard>();

        /**
         * Добавляем пока новый пустой listModel на list.
         */
        list = new JList<>(listModel);

        /**
         * добавим менюбар и первый менюитем
         */
            //Create the menu bar.
        menuBar = new JMenuBar();
            //Build the first menu.
        menu = new JMenu("File");
        menuBar.add(menu);
        loadMenuItem = new JMenuItem("Загрузить последнюю версию с сервера");
        menu.add(loadMenuItem);
        writeLocalyItem = new JMenuItem("Сохранить последнюю версию локально");
        menu.add(writeLocalyItem);
        loadLocalyItem = new JMenuItem("Загрузить локальную версию");
        menu.add(loadLocalyItem);

        /**
         * TODO сделать возможность загрузки локального списка
         */
        /*
        XmlParser loadFile = new XmlParser();
        JFileChooser fileOpen = new JFileChooser();
        fileOpen.showOpenDialog(openFrame);
        listModel = loadFile.parser(fileOpen.getSelectedFile());
        */

        //GUI
        mainPanel = new JPanel();
        frame = new JFrame("Organizer");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainPanel.setLayout(new BoxLayout(mainPanel,BoxLayout.X_AXIS));


        //Add list's scroll for list (GUI)
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

        //Добавим скроллы на редакторы
        JScrollPane scroller1 = new JScrollPane(editor1);
        scroller1.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        scroller1.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

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

        //Добавлен menuBar
        frame.setJMenuBar(menuBar);
        frame.add(mainPanel);
        frame.setSize(1000, 800);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        /**
         * Слушаем нажатие на элемент списка
         */
        list.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if(!list.isSelectionEmpty()) {
                    selCard = list.getSelectedValue();
                    editor1.setText(selCard.getTitle());
                    System.out.println("selCard.getTitle() = " + selCard.getTitle());
                    editor2.setText(selCard.getDescription());
                    System.out.println("selCard.getDescription() = " + selCard.getDescription());
                }
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
               //selCard = listModel.firstElement();
           }
        });

        /**
         * Пишем обратно в файл со всеми изменениями
         */
       loadLocalyItem.addActionListener(event -> {
           XmlParser loadFile = new XmlParser();
           JFileChooser fileOpen = new JFileChooser();
           fileOpen.showOpenDialog(openFrame);

           DefaultListModel<QCard> listModel3 = loadFile.parser(fileOpen.getSelectedFile());
           for (int j = 0; j<listModel3.getSize();j++){
               listModel.addElement(new QCard(listModel3.get(j).getTitle(),listModel3.get(j).getDescription()));
           }

       });

       writeLocalyItem.addActionListener(event -> {
           XmlWriter wr = new XmlWriter();
           JFileChooser fileSave = new JFileChooser();
           fileSave.showSaveDialog(frame);
           wr.writer(listModel,fileSave.getSelectedFile());
       });

        loadMenuItem.addActionListener(event -> {
            System.out.println("Попытка загрузить файл с сервера...");
            try {
                socket = new Socket("127.0.0.1", portNumber);
                ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
                byte[] b = (byte[]) ois.readObject();

                // Пишем byte[] в file:
                try (FileOutputStream fos = new FileOutputStream("C:\\Users\\Александра\\OrganizerClientFiles\\ttt.xml")) {
                    fos.write(b);
                    fos.close();
                }

                File text = new File("C:\\Users\\Александра\\OrganizerClientFiles\\ttt.xml");
                XmlParser par = new XmlParser();
                DefaultListModel<QCard> listModel2;
                listModel2  = par.parser(text);

                for (int i = 0; i< listModel2.getSize(); i++)
                {
                    QCard card = new QCard(listModel2.get(i).getTitle(),listModel2.get(i).getDescription());
                    listModel.addElement(card);
                }
                list.updateUI();

                System.out.println("Подключение к серверу выполнено успешно!");
                System.out.println("Данные выгружены в listModel.");

            } catch (Exception e){
                e.printStackTrace();
                System.out.println("WARN: Ошибка со считыванием данных с сервера...");

            }
        });


    }

}
