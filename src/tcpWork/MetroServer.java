package tcpWork;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class MetroServer extends Thread {

    MetroCardBank bank;
    private ServerSocket serverSocket;
    private final int serverPort;

    public MetroServer(int port) {
        bank = new MetroCardBank();
        serverPort = port;
    }

    @Override
    public void run() {
        try {
            serverSocket = new ServerSocket(serverPort);
            System.out.println("Metro Server started!");
            while (true) {
                System.out.println("Waiting for client connection...");
                Socket socket = serverSocket.accept();
                System.out.println("New client with " + socket + " socket");
                ClientHandler ch = new ClientHandler(bank, socket);
                ch.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                serverSocket.close();
                System.out.println("Metro Server stopped");
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        MetroServer srv = new MetroServer(6666);
        srv.start();
    }
}
