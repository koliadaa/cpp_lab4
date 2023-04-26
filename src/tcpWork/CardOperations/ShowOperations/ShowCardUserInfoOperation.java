package tcpWork.CardOperations.ShowOperations;

import tcpWork.CardOperations.CardOperation;

public class ShowCardUserInfoOperation implements CardOperation {

    private String serialNumber;

    public ShowCardUserInfoOperation(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public ShowCardUserInfoOperation() {}

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }
}
