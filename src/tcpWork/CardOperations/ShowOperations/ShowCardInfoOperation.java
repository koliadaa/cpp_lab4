package tcpWork.CardOperations.ShowOperations;

import tcpWork.CardOperations.CardOperation;

public class ShowCardInfoOperation implements CardOperation {

    private String serialNumber;

    public ShowCardInfoOperation(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }
}
