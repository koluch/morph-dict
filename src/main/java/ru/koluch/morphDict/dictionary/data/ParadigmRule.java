/**
 * Copyright (c) 2015 Nikolai Mavrenkov <koluch@koluch.ru>
 * <p>
 * Distributed under the MIT License (See accompanying file LICENSE or copy at http://opensource.org/licenses/MIT).
 * <p>
 * Created: 09/05/13 23:41
 */
package ru.koluch.morphDict.dictionary.data;

import java.util.Optional;

/**
 * Record in record in paradigm table (in morphs.mrd file)
 * <p>
 * Immutable data-class
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
