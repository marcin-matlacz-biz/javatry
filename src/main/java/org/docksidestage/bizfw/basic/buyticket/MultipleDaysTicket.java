package org.docksidestage.bizfw.basic.buyticket;

public class MultipleDaysTicket implements Ticket {
    private int numberOfDays;
    private final int displayPrice;
    private final TicketType type;
    private boolean alreadyIn = false;

    public MultipleDaysTicket(int displayPrice, TicketType type, int numberOfDays) {
        this.displayPrice = displayPrice;
        this.type = type;
        this.numberOfDays = numberOfDays;
    }

    @Override
    public void doInPark() {
        if (numberOfDays > 0) {
            --numberOfDays;
            alreadyIn = true;
        } else {
            throw new TicketExpired("The ticked is used.");
        }
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
    public int getNumberOfDays() {
        return numberOfDays;
    }

    public static class TicketExpired extends RuntimeException {

        private static final long serialVersionUID = 1L;

        public TicketExpired(String msg) {
            super(msg);
        }
    }
}
