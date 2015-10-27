package ru.koluch.textWork.mParser.morphParser;

import org.apache.log4j.Logger;

/**
 * @author Nikolay Mavrenkov <koluch@koluch.ru>
 *         Date: 9/5/13
 *         Time: 11:35 PM
 */

public class Paradigm {
    public final Ending ending;
    public final Ancode ancode;

    public Paradigm(Ending ending, Ancode ancode) {
        this.ending = ending;
        this.ancode = ancode;
    }
}
