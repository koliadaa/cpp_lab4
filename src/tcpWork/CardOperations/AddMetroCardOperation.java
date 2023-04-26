package tcpWork.CardOperations;

import tcpWork.MetroCard;

public class AddMetroCardOperation implements CardOperation {

    private final MetroCard card;

    public AddMetroCardOperation(MetroCard card) {
        this.card = card;
    }

    public AddMetroCardOperation() {
        card = new MetroCard();
    }

    public MetroCard getCard() {
        return card;
    }
}
