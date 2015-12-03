/**
 * Copyright (c) 2015 Nikolai Mavrenkov <koluch@koluch.ru>
 * <p>
 * Distributed under the MIT License (See accompanying file LICENSE or copy at http://opensource.org/licenses/MIT).
 * <p>
 * Created: 27.10.2015 23:07
 */
package ru.koluch.textWork.morphDict.dictionary;

import java.util.*;

/**
 * Class containing parsed info from morphs.mrd.
 * <p>
 * Immutable data-class.
 */
public class Dictionary {
    public final List<List<ParadigmRule>> paradigmList;
    public final List<LexemeRec> lexemeRecs;
    public final List<String> prefixeParadigmList;

    public Dictionary(List<List<ParadigmRule>> paradigmList, List<LexemeRec> lexemeRecList, List<String> prefixeParadigmList) {
        this.paradigmList = paradigmList;
        this.lexemeRecs = lexemeRecList;
        this.prefixeParadigmList = prefixeParadigmList;
    }

}
