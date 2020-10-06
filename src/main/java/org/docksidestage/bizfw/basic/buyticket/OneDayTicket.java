package org.docksidestage.bizfw.basic.buyticket;

public class OneDayTicket implements Ticket {
    private final int displayPrice;
    private final TicketType type;
    private boolean inParked = false;

    public OneDayTicket(int displayPrice, TicketType type) {
        this.displayPrice = displayPrice;
        this.type = type;
    }

    @Override
    public void doInPark() {
        inParked = true;
    }
    @Override
    public int getDisplayPrice() {
        return displayPrice;
    }
    @Override
    public boolean isAlreadyIn() {
        return inParked;
    }
    @Override
    public TicketType getType() {
        return type;
    }
}
