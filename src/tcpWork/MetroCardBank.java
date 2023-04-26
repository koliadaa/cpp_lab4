package tcpWork;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MetroCardBank implements Serializable {

    List<MetroCard> store;

    public MetroCardBank() {
        store = new ArrayList<>();
    }

    public List<MetroCard> getStore() {
        return new ArrayList<>(store);
    }

    public int numCards() {
        return store.size();
    }

    public int findMetroCardIndex(String serialNumber) {
        for (int i = 0; i < numCards(); i++) {
            if (store.get(i).getSerialNumber().equals(serialNumber)) {
                return i;
            }
        }
        return -1;
    }

    public boolean addCard(MetroCard card) {
        if (findMetroCardIndex(card.getSerialNumber()) == -1) {
            store.add(card);
            return true;
        }
        return false;
    }

    public boolean removeCard(String serialNumber) {
        int index = findMetroCardIndex(serialNumber);
        if (index != -1) {
            store.remove(index);
            return true;
        }
        return false;
    }

    public boolean addMoney(String serialNumber, double money) {
        int index = findMetroCardIndex(serialNumber);
        if (index != -1) {
            store.get(index).setBalance(store.get(index).getBalance() + money);
            return true;
        }
        return false;
    }

    public boolean pay(String serialNumber, double money) {
        int index = findMetroCardIndex(serialNumber);
        if (index != -1) {
            if (store.get(index).getBalance() - money > 0) { // check if enough money
                store.get(index).setBalance(store.get(index).getBalance() - money);
                return true;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return "MetroCardBank{" +
                "store=" + store.toString() +
                '}';
    }
}
