package ru.koluch.textWork.dictionary;

/**
 * @author Nikolay Mavrenkov <koluch@koluch.ru>
 *         Date: 9/5/13
 *         Time: 11:41 PM
 */

public class ParadigmRule {

    public final String ending;
    public final String ancode;
    public final String prefix;


    public ParadigmRule(String ending, String ancode, String prefix) {
        this.ending = ending != null ? ending : "";
        this.ancode = ancode;
        this.prefix = prefix != null ? prefix : "";
    }


}
