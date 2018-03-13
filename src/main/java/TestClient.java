package main.java;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.Socket;

public class TestClient {
    public void goo(){
        try{
            /**
             * Создаём соединение через сокет к приложению,
             * работающему на порту 4242, на том же ПК(localhost)
             */
            Socket s = new Socket("127.0.0.1",4242);
            /**
             * Создаём InputStreamReader и связываем его
             * с низкоуровневым потоком (соединением) через сокет
             * (Фактически это мост, соединяющий низкоуровневый
             * поток байтов из сокета и высокоуровневый символьный поток,
             * предоставляемый BufferedReader-ом.)
             */
            InputStreamReader streamReader = new InputStreamReader(s.getInputStream());
            /**
             * Создаем BufferedReader и считываем данные
             */
            BufferedReader reader = new BufferedReader(streamReader);

            String advice = reader.readLine();
            System.out.println("Today you should"+ advice);
            reader.close();
        }catch (Exception e){e.printStackTrace();}
    }

    public static void main(String[] args){
        TestClient client = new TestClient();
        client.goo();
    }

}
