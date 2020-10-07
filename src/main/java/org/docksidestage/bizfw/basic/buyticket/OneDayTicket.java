package org.docksidestage.bizfw.basic.buyticket;

public class OneDayTicket implements Ticket {
    private final int displayPrice;
    private final TicketType type;
    private boolean alreadyIn = false;

    public OneDayTicket(int displayPrice, TicketType type) {
        this.displayPrice = displayPrice;
        this.type = type;
    }

    @Override
    public void doInPark() {
        if (alreadyIn) {
            throw new IllegalStateException("Already in park by this ticket: displayPrice=" + displayPrice);
        }
        alreadyIn = true;
    }
    @Override
    public int getDisplayPrice() {
        return displayPrice;
    }
    @Override
    public boolean isAlreadyIn() {
        return alreadyIn;
    }
    @Override
    public TicketType getType() {
        return type;
    }
}
