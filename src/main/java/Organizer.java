package main.java;


import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.*;
import java.net.Socket;
import java.util.Iterator;
//import org.apache.commons.io;
//import org.apache.commons.io.FileUtils;

public class Organizer {
    private JFrame frame;
    private JFrame openFrame;
    private JButton newButton;
    private JButton saveButton;
    private JButton delButton;
    private JButton writeButton;
    private JPanel panel1;
    private JPanel panel2;
    private JPanel mainPanel;
    private JEditorPane editor1;
    private JEditorPane editor2;
    private DefaultListModel<QCard> listModel;
    private QCard selCard;   // ссылка на текущий выбранный объект
    private JLabel label1;
    private JLabel label2;
    //MenuBar
    private JMenuBar menuBar;
    private JMenu menu;
    private JMenuItem loadMenuItem;

    // лист, в который всё загоняем
    JList<QCard> list;


    // Указываем начальные параметры для подключения к серверу
    Socket socket;
    //порт подключения
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
        listModel = new DefaultListModel<QCard>();

        /**
         * Добавляем пока новый пустой listModel на list.
         */
        list = new JList<>(listModel);
        //list.setVisible(false);
        //list = new JList<>();

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
        /**
         * Реализовать загрузку элемента listModel с сервера
         */




        /**
         * Добавляем инициацию считывания из файла при запуске.
         */
        /*
        XmlParser loadFile = new XmlParser();
        JFileChooser fileOpen = new JFileChooser();
        fileOpen.showOpenDialog(openFrame);
        listModel = loadFile.parser(fileOpen.getSelectedFile());
        */

        //GUI секция
        //__________________________________________________________________________________________
        mainPanel = new JPanel();
        frame = new JFrame("Organizer");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainPanel.setLayout(new BoxLayout(mainPanel,BoxLayout.X_AXIS));


        /**
         * Добавление в JList списка объектов.
         * В дальнеёшем, это должен быть подгружаемый список из файла
         * (уже перенесён в makeCard)
         */





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
        writeButton = new JButton("Write to file...");


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
        panel2.add(writeButton);


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
               selCard = listModel.firstElement();
           }
        });

        /**
         * Пишем обратно в файл со всеми изменениями
         */

       writeButton.addActionListener(event -> {
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
                //System.out.println("10ый байт: " + b[10]);

                try (FileOutputStream fos = new FileOutputStream("C:\\Users\\Александра\\OrganizerClientFiles\\ttt.xml")) {
                    fos.write(b);
                    fos.close(); //There is no more need for this line since you had created the instance of "fos" inside the try. And this will automatically close the OutputStream
                }

                File text = new File("C:\\Users\\Александра\\OrganizerClientFiles\\ttt.xml");
                XmlParser par = new XmlParser();
                DefaultListModel<QCard> listModel2 = new DefaultListModel<>();
                listModel2  = par.parser(text);

                System.out.println("СЧИТАННАЯ ПЕРВАЯ КАРТА(desc):"+listModel2.get(1).getDescription());
                // TODO Раз считанную карту показывает, значит выгрузка всё работает. Остаётся проблема с работой в UI
                for (int i = 0; i< listModel2.getSize(); i++)
                {
                    QCard card = new QCard(listModel2.get(i).getTitle(),listModel2.get(i).getDescription());
                    listModel.addElement(card);
                }
                //попробуем просто прогрузить всё.
                //Удаляем старое содержание
                //list.removeAll();
                //list = new JList<>(listModel);
                //selCard = listModel.firstElement();
                list.updateUI();

                //FileUtils.writeByteArrayToFile(new File("pathname"), b);
                //QCard cardFromServer = (QCard)ois.readObject();
        //System.out.println("NewCard: "+cardFromServer.getTitle() + ": " + cardFromServer.getDescription());
                //listModel = (DefaultListModel<QCard>) ois.readObject();
                //String mes = (String)ois.readObject();
                //System.out.println("MessageFromServer is: " + mes);
                //listModel = (DefaultListModel<QCard>) ois.readObject();
                //QCard cardFromServer = listModel.elementAt(1);
                //QCard cardFromServer = (QCard)ois.readObject();
                //System.out.println("NewCard: "+cardFromServer.getTitle() + ": " + cardFromServer.getDescription());
                //надо сразу использовать загруженный listModel:
                //System.out.println("Пробуем получить первый элемент "+ listModel.get(1));
                //System.out.println("посмотрим размер listModel после выгрузки:" + listModel.getSize());
            /*

                list = new JList<>(listModel);
                list.updateUI();
*/
                System.out.println("Подключение к серверу выполнено успешно!");
                System.out.println("Данные выгружены в listModel.");

            } catch (Exception e){
                e.printStackTrace();
                System.out.println("WARN: Ошибка со считыванием данных с сервера...");

            }
        });


    }

    /**
     * Считываем построчно из файла и создаем карту через makeCard;
     * ЭТО РАБОЧИЙ МЕТОД ДЛЯ ФАЙЛА ВИДА TITLE/DESCRIPTION
     * Закоментирован вместе с методом makeCard
     * @param file
     */

    /*
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
*/

    /**
     * Принимаем строку,
     * Делим её пополам через "/",
     * Создаём новую карту.
     //* @param lineToParse
     */

    /*
    private void makeCard(String lineToParse) {
        String[] result = lineToParse.split("/");
        QCard card = new QCard(result[0],result[1]);
        listModel.addElement(card);
    }
    */

    /*
    //РАБОЧЕЕ СОЗРАНЕНИЕ ДЛЯ ВИДА title/description
    private void saveFile(File file){
        try{
            BufferedWriter writer = new BufferedWriter(new FileWriter(file));
            for (int i = 0; i<listModel.getSize();i++ ){
                QCard ca = listModel.getElementAt(i);
                writer.write(ca.getTitle() + "/");
                writer.write(ca.getDescription() + "\n");
            }
            writer.close();
        } catch (Exception exc){exc.printStackTrace();}
    }
    */

}
