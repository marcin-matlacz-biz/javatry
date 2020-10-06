package org.docksidestage.bizfw.basic.buyticket;

public class TicketBuyResult {
    private final Ticket ticket;
    private final int change;

    public TicketBuyResult(int displayPrice, int change, TicketType type, int numberOfDays) {
        this.ticket = new MultipleDaysTicket(displayPrice, type, numberOfDays);
        this.change = change;
    }
    public Ticket getTicket() {
        return ticket;
    }

    public int getChange() {
        return change;
    }
}
