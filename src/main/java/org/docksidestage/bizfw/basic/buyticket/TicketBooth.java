/*
 * Copyright 2019-2020 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */
package org.docksidestage.bizfw.basic.buyticket;

/**
 * @author jflute
 */
public class TicketBooth {

    // ===================================================================================
    //                                                                          Definition
    //                                                                          ==========
    private static final int MAX_QUANTITY = 10;
    private static final int ONE_DAY_PRICE = 7400; // when 2019/06/15
    private static final int TWO_DAY_PRICE = 13200;

    // ===================================================================================
    //                                                                           Attribute
    //                                                                           =========
    private int quantity = MAX_QUANTITY;
    private int twoDaysQuantity = MAX_QUANTITY;
    private Integer salesProceeds;

    // ===================================================================================
    //                                                                         Constructor
    //                                                                         ===========
    public TicketBooth() {
    }

    // ===================================================================================
    //                                                                          Buy Ticket
    //                                                                          ==========
    private void checkQuantity(int quantity, int numberOfTickets) {
        if (quantity <= numberOfTickets) {
            throw new TicketSoldOutException("Sold out");
        }
    }

    private void checkEnoughMoney(int handedMoney, int price) {
        if (handedMoney < price) {
            throw new TicketShortMoneyException("Short money: " + handedMoney);
        }
    }

    private void sellTicket(Integer price) {
        if (salesProceeds != null) {
            salesProceeds = salesProceeds + price;
        } else {
            salesProceeds = price;
        }
    }

    public Ticket buyOneDayPassport(int handedMoney) {
        checkQuantity(quantity, 1);
        checkEnoughMoney(handedMoney, ONE_DAY_PRICE);
        --quantity;
        sellTicket(ONE_DAY_PRICE);
        return new OneDayTicket(ONE_DAY_PRICE, TicketType.ONE_DAY);
    }

    public TicketBuyResult buyTwoDayPassport(int handedMoney) {
        checkQuantity(twoDaysQuantity, 1);
        checkEnoughMoney(handedMoney, TWO_DAY_PRICE);
        --twoDaysQuantity;
        sellTicket(TWO_DAY_PRICE);
        return new TicketBuyResult(TWO_DAY_PRICE, handedMoney - TWO_DAY_PRICE, TicketType.TWO_DAY, 2);
    }

    public TicketBuyResult buyMultipleDaysTicket(int handedMoney, int numberOfDays) {
        int numOneDayTickets = numberOfDays % 2;
        int numTwoDayTickets = numberOfDays / 2;
        int price = numOneDayTickets * ONE_DAY_PRICE + numTwoDayTickets * TWO_DAY_PRICE;
        checkQuantity(quantity, numOneDayTickets);
        checkQuantity(twoDaysQuantity, numTwoDayTickets);
        checkEnoughMoney(handedMoney, price);
        quantity -= numOneDayTickets;
        twoDaysQuantity -= numTwoDayTickets;
        sellTicket(price);
        return new TicketBuyResult(price, handedMoney - price, TicketType.MULTIPLE_DAY, numberOfDays);

    }

    public static class TicketSoldOutException extends RuntimeException {

        private static final long serialVersionUID = 1L;

        public TicketSoldOutException(String msg) {
            super(msg);
        }
    }

    public static class TicketShortMoneyException extends RuntimeException {

        private static final long serialVersionUID = 1L;

        public TicketShortMoneyException(String msg) {
            super(msg);
        }
    }

    // ===================================================================================
    //                                                                            Accessor
    //                                                                            ========
    public int getQuantity() {
        return quantity;
    }

    public int getTwoDaysQuantity() {
        return twoDaysQuantity;
    }

    public Integer getSalesProceeds() {
        return salesProceeds;
    }
}
