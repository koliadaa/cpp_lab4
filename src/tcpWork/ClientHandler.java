package tcpWork;

import tcpWork.CardOperations.*;
import tcpWork.CardOperations.ShowOperations.ShowBalanceOperation;
import tcpWork.CardOperations.ShowOperations.ShowCardInfoOperation;
import tcpWork.CardOperations.ShowOperations.ShowCardUserInfoOperation;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ClientHandler extends Thread {
    private ObjectInputStream is = null;
    private ObjectOutputStream os = null;
    private boolean doJob;
    private final MetroCardBank bank;
    private final Socket socket;

    public ClientHandler(MetroCardBank bank, Socket socket) {
        this.bank = bank;
        this.socket = socket;
        this.doJob = true;
        try {
            this.is = new ObjectInputStream(socket.getInputStream());
            this.os = new ObjectOutputStream(socket.getOutputStream());
        } catch (IOException error) {
            error.printStackTrace();
        }
    }

    @Override
    public void run() {
        synchronized (bank) {
            System.out.println("Client Handler Started for " + socket + " socket");
            while (doJob) {
                Object obj;
                try {
                    obj = is.readObject();
                    processOperation(obj);
                } catch (IOException | ClassNotFoundException error) {
                    error.printStackTrace();
                }
            }

            try {
                System.out.println("Client Handler Stopped for: " + socket + " socket");
                socket.close();
            } catch (IOException error) {
                error.printStackTrace();
            }
        }
    }

    private void processOperation(Object obj) throws IOException, ClassNotFoundException {
        if (obj instanceof StopOperation) {
            finish();
        } else if (obj instanceof AddMetroCardOperation) {
            addCard(obj);
        } else if (obj instanceof AddMoneyOperation) {
            addMoney(obj);
        } else if (obj instanceof PayMoneyOperation) {
            payMoney(obj);
        } else if (obj instanceof RemoveCardOperation) {
            removeCard(obj);
        } else if (obj instanceof ShowBalanceOperation) {
            showBalance(obj);
        } else if (obj instanceof ShowCardInfoOperation){
            showCardInfo(obj);
        } else if (obj instanceof ShowCardUserInfoOperation) {
            showCardUserInfo(obj);
        } else {
            error();
        }
    }

    private void finish() throws IOException {
        doJob = false;
        os.writeObject("Job for " + socket + " socket was finished!");
        os.flush();
    }

    private void addCard(Object obj) throws IOException {
        boolean result = bank.addCard(((AddMetroCardOperation) obj).getCard());
        if (result) {
            os.writeObject("Card was added!");
        } else {
            os.writeObject("Card was not added! The same card already exists.");
        }
        os.flush();
    }

    private void addMoney(Object obj) throws IOException {
        AddMoneyOperation operation = (AddMoneyOperation) obj;
        boolean result = bank.addMoney(operation.getSerialNumber(), operation.getMoney());
        if (result) {
            os.writeObject("Money have been added!");
        } else {
            os.writeObject("Money have not been added!");
        }
        os.flush();
    }

    private void payMoney(Object obj) throws IOException {
        PayMoneyOperation operation = (PayMoneyOperation) obj;
        boolean result = bank.pay(operation.getSerialNumber(), operation.getMoney());
        if (result) {
            os.writeObject("Money have been withdraw!");
        } else {
            os.writeObject("Money have not been withdraw!");
        }
        os.flush();
    }

    private void removeCard(Object obj) throws IOException {
        RemoveCardOperation operation = (RemoveCardOperation) obj;
        boolean result = bank.removeCard(operation.getSerialNumber());
        if (result) {
            os.writeObject("Card with serial number " + operation.getSerialNumber() + " successfully removed!");
        } else {
            os.writeObject("Cannot remove card with " + operation.getSerialNumber() + " serial number");
        }
        os.flush();
    }

    private void showBalance(Object obj) throws IOException {
        ShowBalanceOperation operation = (ShowBalanceOperation) obj;
        int index = bank.findMetroCardIndex(operation.getSerialNumber());
        if (index >= 0) {
            os.writeObject("Card: " + operation.getSerialNumber() + ", balance: " + bank.getStore().get(index).getBalance());
            os.flush();
        } else {
            os.writeObject("Cannot show balance for card with " + operation.getSerialNumber() + " serial number!");
        }
    }

    private void showCardInfo(Object obj) throws IOException {
        ShowCardInfoOperation operation = (ShowCardInfoOperation) obj;
        int index = bank.findMetroCardIndex(operation.getSerialNumber());
        if (index >= 0) {
            os.writeObject(bank.getStore().get(index).toString());
            os.flush();
        } else {
            os.writeObject("Cannot show info for card with " + operation.getSerialNumber() + " serial number!");
        }
    }

    private void showCardUserInfo(Object obj) throws IOException {
        ShowCardUserInfoOperation operation = (ShowCardUserInfoOperation) obj;
        int index = bank.findMetroCardIndex(operation.getSerialNumber());
        if (index >= 0) {
            os.writeObject(bank.getStore().get(index).getUser().toString());
            os.flush();
        } else {
            os.writeObject("Cannot show user info for card with " + operation.getSerialNumber() + " serial number!");
        }
    }

    private void error() throws IOException {
        os.writeObject("Bad operation!");
        os.flush();
    }
}
