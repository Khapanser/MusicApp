package main.java;

import javax.swing.*;
import java.io.File;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.Path;

/**
 * В рамках первой версии, сервер должен:
 * 1. Найти XML
 * 2. Преобразовать его в listModel с помощью XmlReader.
 * 3. Отправить listModel на клиент
 */
public class OrganaizerServer {
    ServerSocket serverSocket;
    int portNumber = 5000;
    Socket socket;
    byte[] b;
    private static DefaultListModel<QCard> listModel;

    public static void main(String[] args){
        OrganaizerServer server = new OrganaizerServer();



        try {
            server.go();
        } catch(Exception e){
            e.printStackTrace();
            System.out.println("SERVER: файл уже редактируется другим пользователем");
        }
    }

        //Делаем метод synchronize
    public synchronized void go(){
        try {
            serverSocket = new ServerSocket(portNumber);
            while(true) {
                socket = serverSocket.accept();

                //Попробуем перегнать file to byte[]
                Path path = Paths.get("C:\\Users\\Александра\\OrganizerServerFiles\\test.xml");
                byte[] data = Files.readAllBytes(path);
                //Считываем файл
                //File file = new File("C:\\Users\\Александра\\OrganizerServerFiles\\test.xml");
                //XmlParser par = new XmlParser();
                //listModel = par.parser(file);
                //System.out.println("Получен listModel размера"+ listModel.getSize());

                ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
                oos.writeObject(data);

                // Со строкой работает...
                // String send = "MessageFromServer";
                // oos.writeObject(send);

                //Пробуем отправить карточки:
                //for (int i = 0; )
                //oos.writeObject(listModel);
                //oos.writeObject(listModel.get(0));

                oos.close();
            }
        } catch(Exception e){
            System.out.println("SERVER: Ошибка в методе go()");
            e.printStackTrace();
        }
    }
}
