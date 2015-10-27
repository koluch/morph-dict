package ru.koluch.textWork.mParser.morphParser;

import org.apache.log4j.Logger;

import java.util.*;

/**
 * @author Nikolay Mavrenkov <koluch@koluch.ru>
 *         Date: 9/5/13
 *         Time: 11:41 PM
 */

public class ParadigmRule {
    private static final Logger log = Logger.getLogger(ParadigmRule.class);

    private final Map<Ending,List<Ancode>> endingsToAncodes = new HashMap<>();

    private Ending firstEnding;
    private Ancode firstAncode;

    public Ending getFirstEnding() {
        return firstEnding;
    }

    public void setFirstEnding(Ending firstEnding) {
        this.firstEnding = firstEnding;
    }

    public Ancode getFirstAncode() {
        return firstAncode;
    }

    public void setFirstAncode(Ancode firstAncode) {
        this.firstAncode = firstAncode;
    }

    public ParadigmRule addAncode(Ending ending, Ancode ancode)
    {
        if(!endingsToAncodes.containsKey(ending))
        {
            endingsToAncodes.put(ending, new ArrayList<Ancode>());
        }
        List<Ancode> ancodeList = endingsToAncodes.get(ending);
        ancodeList.add(ancode);
        return this;
    }

    public Map<Ending, List<Ancode>> getEndingsToAncodes() {
        return endingsToAncodes;
    }
}
