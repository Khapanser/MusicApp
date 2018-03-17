package main.java;

import javax.swing.*;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.Path;

/**
 * В рамках первой версии, сервер должен:
 * 1. Преобразовать XML в byte[]
 * 2. Отправить byte[] на клиент
 */

/** TODO-list
 * TODO 1. Добавить второй поток
 * TODO 2. Добавить новый метод run() (для Runnable)
 * TODO 3. В методе run описать считывание byte[] с клиента
 * TODO 4. Преобразовать byte[] в XML-файл.
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

        //Делаем synchronize в метод
    public synchronized void go(){
        try {
            serverSocket = new ServerSocket(portNumber);
            while(true) {
                socket = serverSocket.accept();

                //Преобразуем file to byte[]
                Path path = Paths.get("C:\\Users\\Александра\\OrganizerServerFiles\\test.xml");
                byte[] data = Files.readAllBytes(path);
                //Отправляем файл на клиент:
                ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
                oos.writeObject(data);
                oos.close();
            }
        } catch(Exception e){
            System.out.println("SERVER: Ошибка в методе go()");
            e.printStackTrace();
        }
    }
}
