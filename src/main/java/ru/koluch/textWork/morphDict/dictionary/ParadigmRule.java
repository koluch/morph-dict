package ru.koluch.textWork.morphDict.dictionary;

import java.util.Optional;

/**
 * @author Nikolay Mavrenkov <koluch@koluch.ru>
 *         Date: 9/5/13
 *         Time: 11:41 PM
 */

public class ParadigmRule {

    public final Optional<String> ending;
    public final String ancode;
    public final Optional<String> prefix;


    public ParadigmRule(Optional<String> ending, String ancode, Optional<String> prefix) {
        this.ending = ending;
        this.ancode = ancode;
        this.prefix = prefix;
    }


}
