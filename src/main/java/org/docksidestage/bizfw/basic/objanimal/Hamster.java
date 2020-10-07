package org.docksidestage.bizfw.basic.objanimal;

public class Hamster extends Animal {
    private static final int initialHitPoints = 4;

    @Override
    protected String getBarkWord() {
        return "**squeak**";
    }
    @Override
    protected int getInitialHitPoint() {
        return initialHitPoints;
    }
}
