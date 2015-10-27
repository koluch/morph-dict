package ru.koluch.textWork.dictionaryParser;

import org.apache.log4j.Logger;

import java.util.*;

/**
 * @author Nikolay Mavrenkov <koluch@koluch.ru>
 *         Date: 9/5/13
 *         Time: 11:41 PM
 */

public class ParadigmRule {
    private static final Logger log = Logger.getLogger(ParadigmRule.class);

    private final Map<Ending,List<String>> endingsToAncodes = new HashMap<>();

    private Ending firstEnding;
    private String firstAncode;

    public Ending getFirstEnding() {
        return firstEnding;
    }

    public void setFirstEnding(Ending firstEnding) {
        this.firstEnding = firstEnding;
    }

    public String getFirstAncode() {
        return firstAncode;
    }

    public void setFirstAncode(String firstAncode) {
        this.firstAncode = firstAncode;
    }

    public ParadigmRule addAncode(Ending ending, String ancode)
    {
        if(!endingsToAncodes.containsKey(ending))
        {
            endingsToAncodes.put(ending, new ArrayList<>());
        }
        List<String> ancodeList = endingsToAncodes.get(ending);
        ancodeList.add(ancode);
        return this;
    }

    public Map<Ending, List<String>> getEndingsToAncodes() {
        return endingsToAncodes;
    }
}
