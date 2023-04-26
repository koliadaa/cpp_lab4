package tcpWork.CardOperations.ShowOperations;

import tcpWork.CardOperations.CardOperation;

public class ShowBalanceOperation implements CardOperation {

    private String serialNumber;

    public ShowBalanceOperation(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }
}
