package org.docksidestage.bizfw.basic;

import org.docksidestage.bizfw.basic.objanimal.BarkedSound;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class BarkingProcess {
    private static final Logger logger = LoggerFactory.getLogger(BarkingProcess.class);

    protected int hitPoint;

    protected void prepareAbdominalMuscle() {
        logger.debug("...Using my abdominal muscle"); // dummy implementation
    }

    protected void breatheIn() {
        logger.debug("...Breathing in"); // dummy implementation
    }

    protected abstract String getBarkWord();

    protected BarkedSound doBark(String barkWord) {
        return new BarkedSound(barkWord);
    }
}
