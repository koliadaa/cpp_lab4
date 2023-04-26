package tcpWork;

import java.io.Serializable;

public class MetroCard implements Serializable {

    private String serialNumber;
    private User user;
    private String college;
    private double balance;

    public MetroCard(String serialNumber, User user, String college, double balance) {
        this.serialNumber = serialNumber;
        this.user = user;
        this.college = college;
        this.balance = balance;
    }

    public MetroCard(){}

    public String getSerialNumber() {
        return serialNumber;
    }

    public User getUser() {
        return user;
    }

    public String getCollege() {
        return college;
    }

    public double getBalance() {
        return balance;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setCollege(String college) {
        this.college = college;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    @Override
    public String toString() {
        return "MetroCard{" +
                "Serial number='" + serialNumber + '\'' +
                ", User=" + user +
                ", College='" + college + '\'' +
                ", Balance=" + balance +
                '}';
    }
}
