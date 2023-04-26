package tcpWork.CardOperations;

public class RemoveCardOperation implements CardOperation {

    private String serialNumber;

    public RemoveCardOperation(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public RemoveCardOperation() {
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }
}
