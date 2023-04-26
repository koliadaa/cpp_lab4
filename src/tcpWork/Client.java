package tcpWork;

import tcpWork.CardOperations.*;
import tcpWork.CardOperations.ShowOperations.ShowBalanceOperation;
import tcpWork.CardOperations.ShowOperations.ShowCardInfoOperation;
import tcpWork.CardOperations.ShowOperations.ShowCardUserInfoOperation;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;

public class Client {

    private ObjectInputStream is = null;
    private ObjectOutputStream os = null;

    public Client(String server, int port) {
        try {
            Socket socket = new Socket();
            socket.connect(new InetSocketAddress(server, port), 1000);
            os = new ObjectOutputStream(socket.getOutputStream());
            is = new ObjectInputStream(socket.getInputStream());
        } catch (IOException e) {
            System.out.println("Error: " + e);
        }
    }

    public void finish() {
        try {
            os.writeObject(new StopOperation());
            os.flush();
            System.out.println(is.readObject());
        } catch (IOException | ClassNotFoundException ex) {
            System.out.println("Error: " + ex);
        }
    }

    public void applyOperation(CardOperation op) {
        try {
            os.writeObject(op);
            os.flush();
            System.out.println(is.readObject());
        } catch (IOException | ClassNotFoundException ex) {
            System.out.println("Error: " + ex);
        }
    }

    public static void main(String[] args) {
        Client client = new Client("localhost", 6666);

        // Регистрация карты в системе
        AddMetroCardOperation addCardOperation = new AddMetroCardOperation();
        addCardOperation.getCard().setUser(new User("Petr", "Petrov", "M", "25.12.1968"));
        addCardOperation.getCard().setSerialNumber("00001");
        addCardOperation.getCard().setCollege("KhNU");
        addCardOperation.getCard().setBalance(0);
        client.applyOperation(addCardOperation);
        System.out.println();

        // Операция получения информации о клиенте
        ShowCardUserInfoOperation showCardUserInfoOperation = new ShowCardUserInfoOperation("00001");
        client.applyOperation(showCardUserInfoOperation);
        System.out.println();

        // Операция пополнения счёта
        AddMoneyOperation addMoneyOperation = new AddMoneyOperation("00001", 70);
        client.applyOperation(addMoneyOperation);
        System.out.println();

        // Операция оплаты поездки
        PayMoneyOperation payMoneyOperation = new PayMoneyOperation("00001", 25);
        client.applyOperation(payMoneyOperation);
        System.out.println();

        // Операция получения информации о балансе
        ShowBalanceOperation showBalanceOperation = new ShowBalanceOperation("00001");
        client.applyOperation(showBalanceOperation);
        System.out.println();

        // Операция получения полной информации о состоянии карты
        ShowCardInfoOperation showInfoOperation = new ShowCardInfoOperation("00001");
        client.applyOperation(showInfoOperation);
        System.out.println();

        client.finish();
    }
}
