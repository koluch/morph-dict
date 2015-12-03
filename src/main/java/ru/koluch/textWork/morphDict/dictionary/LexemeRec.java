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
 * Record in lexemes table (in morphs.mrd file)
 * <p>
 * Immutable data-class
 */
public class LexemeRec {
    public final String basis;
    public final Integer paradigmIndex;
    public final Integer accentParadigmIndex;
    public final Integer userSessionIndex;
    public final Optional<String> ancode;
    public final Optional<Integer> prefixParadigmIndex;


    public LexemeRec(String basis, Integer paradigmIndex, Optional<String> ancode, Integer accentParadigmIndex, Integer userSessionIndex, Optional<Integer> prefixParadigmIndex) {
        this.basis = basis;
        this.paradigmIndex = paradigmIndex;
        this.accentParadigmIndex = accentParadigmIndex;
        this.userSessionIndex = userSessionIndex;
        this.ancode = ancode;
        this.prefixParadigmIndex = prefixParadigmIndex;
    }
}
