/**
 * Copyright (c) 2015 Nikolai Mavrenkov <koluch@koluch.ru>
 * <p>
 * Distributed under the MIT License (See accompanying file LICENSE or copy at http://opensource.org/licenses/MIT).
 * <p>
 * Created: 30.10.2015 01:20
 */
package ru.koluch.textWork.morphDict.dictionary;

import java.util.Optional;

/**
 * Record in lexemes table
 *
 * "Nikolay Mavrenkov <koluch@koluch.ru>"
 */
public class LexemeRec {
    public final String basis;
    public final Integer paradigmNum;
    public final Integer accentParadigmNum;
    public final Integer userSessionNum;
    public final Optional<String> ancode;
    public final Optional<Integer> prefixParadigmNum;


    public LexemeRec(String basis, Integer paradigmNum, Optional<String> ancode, Integer accentParadigmNum, Integer userSessionNum, Optional<Integer> prefixParadigmNum) {
        this.basis = basis;
        this.paradigmNum = paradigmNum;
        this.accentParadigmNum = accentParadigmNum;
        this.userSessionNum = userSessionNum;
        this.ancode = ancode;
        this.prefixParadigmNum = prefixParadigmNum;
    }
}
