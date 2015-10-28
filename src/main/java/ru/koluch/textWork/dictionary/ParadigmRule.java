package ru.koluch.textWork.dictionary;

import org.apache.log4j.Logger;

import java.util.*;

/**
 * @author Nikolay Mavrenkov <koluch@koluch.ru>
 *         Date: 9/5/13
 *         Time: 11:41 PM
 */

public class ParadigmRule {
    private static final Logger log = Logger.getLogger(ParadigmRule.class);

    public final List<ParadigmRuleRecord> paradigmRuleRecords = new ArrayList<>();

    public ParadigmRule(List<ParadigmRuleRecord> paradigmRuleRecords) {
        this.paradigmRuleRecords.addAll(paradigmRuleRecords);
    }
}
